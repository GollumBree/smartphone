package de.gollumbree.smartphone.items;

import java.util.List;

import javax.annotation.Nonnull;

import de.gollumbree.smartphone.Config;
import de.gollumbree.smartphone.ModMenus;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class SmartphoneMenu extends AbstractContainerMenu {
    private static final int PIXEL_X = 18; // Breite eines Slots in Pixeln
    private static final int PIXEL_Y = 18; // Höhe eines Slots in Pixeln
    private static final int GUI_LEFT = 8; // X offset of first slot
    private static final int GUI_TOP = 18; // Y offset of first row
    public static final int rows = 3; // Breite eines Slots in Pixeln
    public static final int cols = 9; // Höhe eines Slots in Pixeln
    private final SimpleContainer phoneInv;

    private final ItemStack smartphoneStack;
    // Client‑Konstruktor

    public SmartphoneMenu(int id, Inventory playerInv) {
        this(id, playerInv, playerInv.getSelected()); // Dummy‑Access auf Client
    }

    // Server‑Konstruktor
    public SmartphoneMenu(int id, Inventory playerInv, ItemStack phoneStack) {
        super(ModMenus.SMARTPHONE_MENU.get(), id);
        smartphoneStack = phoneStack;
        // Slots / DataSlots hier einfügen …
        // Check if the data inventory size is some fixed value
        // Then, add slots for data inventory
        // allocate correct size (rows * 9)
        phoneInv = new SimpleContainer(rows * cols);
        System.out.println("phoneStack has following Components: " + phoneStack.getComponents());

        ItemContainerContents contents = phoneStack.get(DataComponents.CONTAINER);
        if (contents != null) {
            List<ItemStack> items = contents.stream().toList();
            for (int i = 0; i < items.size() && i < phoneInv.getContainerSize(); i++) {
                phoneInv.setItem(i, items.get(i).copy());
            }
        }

        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                this.addSlot(new Slot(phoneInv, col + row * cols, GUI_LEFT + col * PIXEL_X, GUI_TOP + row * PIXEL_Y) {
                    // @Override
                    // public boolean mayPickup(@Nonnull Player player) {
                    // ItemStack held = player.getMainHandItem();
                    // ItemStack item = super.getItem();
                    // player.setItemInHand(InteractionHand.MAIN_HAND, item); // set the item in
                    // hand
                    // item.use(player.level(), player, InteractionHand.MAIN_HAND); // use the item
                    // player.setItemInHand(InteractionHand.MAIN_HAND, held); // restore held item
                    // return false; // forbid pickup
                    // }

                    @Override
                    public boolean mayPlace(@Nonnull ItemStack stack) {
                        if (!(stack.is(TagKey.create(BuiltInRegistries.ITEM.key(),
                                ResourceLocation.fromNamespaceAndPath("smartphone", "usable")))
                                ||
                                Config.isAllowed(stack.getItem()))) {
                            return false; // only allow specific items
                        }
                        if (stack.getItem() instanceof SmartphoneItem) {
                            return false; // no smartphone inside smartphone
                        }
                        return super.mayPlace(phoneStack);
                    }; // only allow specific items

                    @Override
                    public int getMaxStackSize() {
                        return 1; // only allow one item per slot
                    }
                });
            }
        }

        // Add slots for player inventory
        int playerInvY = GUI_TOP + rows * PIXEL_Y + 14; // 14‑px spacing
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * PIXEL_X, 84 + row * PIXEL_Y));
            }
        }

        // Adds the hotbar (1 row × 9 columns)
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInv, col, 8 + col * PIXEL_X, playerInvY + rows * PIXEL_Y + 2));
        }

        // // Add data slots for handled integers
        // this.addDataSlot(dataSingle);

        // // ...
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return true; // oder AbstractContainerMenu.stillValid(access, player, <Block>)
    }

    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack original = ItemStack.EMPTY;
        Slot clicked = this.slots.get(index);

        if (clicked != null && clicked.hasItem()) {
            ItemStack stack = clicked.getItem();
            original = stack.copy();

            int phonesize = phoneInv.getContainerSize();
            final int PHONE_START = 0; // phone slots 0‑8
            final int PHONE_END = phonesize; // exclusive
            final int INV_START = phonesize; // player inv 9‑35
            final int HOTBAR_END = phonesize + 36; // exclusive

            /* ---------------------------------------------------------- */
            /* A) Shift‑clicked inside the phone inventory */
            /* ---------------------------------------------------------- */
            if (index < PHONE_END) {
                // first try main inventory, then hot‑bar
                if (!this.moveItemStackTo(stack, INV_START, HOTBAR_END, false)) {
                    return ItemStack.EMPTY;
                }
            }
            /* ---------------------------------------------------------- */
            /* B) Shift‑clicked inside player inv or hot‑bar */
            /* ---------------------------------------------------------- */
            else {
                // move into phone inventory (0‑8)
                if (!this.moveItemStackTo(stack, PHONE_START, PHONE_END, false)) {
                    return ItemStack.EMPTY;
                }
            }

            /* ---------------------------------------------------------- */
            /* C) House‑keeping */
            /* ---------------------------------------------------------- */
            if (stack.isEmpty()) {
                clicked.set(ItemStack.EMPTY);
            } else {
                clicked.setChanged();
            }

            if (stack.getCount() == original.getCount()) {
                return ItemStack.EMPTY; // nothing moved
            }

            clicked.onTake(player, stack);
        }

        return original;
    }

    @Override
    public void removed(@Nonnull Player player) {
        super.removed(player);

        // Get currently held item (e.g. in main hand)
        ItemStack held = player.getMainHandItem();

        // Only save if it's still the same item
        if (ItemStack.isSameItemSameComponents(held, this.smartphoneStack)) {
            held.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(phoneInv.getItems()));
        }
    }

    // @Override
    // public boolean moveItemStackTo(@Nonnull ItemStack stack, int startIndex, int
    // endIndex, boolean reverseDirection) {
    // return stack.is(
    // TagKey.create(
    // BuiltInRegistries.ITEM.key(),
    // ResourceLocation.fromNamespaceAndPath("smartphone", "usable")))
    // ? super.moveItemStackTo(stack, startIndex, endIndex, reverseDirection)
    // : false;
    // }
    @Override
    public void clicked(int slotId, int button, @Nonnull ClickType clickType, @Nonnull Player player) {
        if (button == 1 && slotId < phoneInv.getContainerSize()) {
            // ItemStack phone = player.getMainHandItem();
            ItemStack item = phoneInv.getItem(slotId); // get the item from the slot
            if (item.isEmpty()) {
                return; // nothing to use
            }
            // player.setItemInHand(InteractionHand.MAIN_HAND, item); // set the item in
            // hand
            item.use(player.level(), player, InteractionHand.MAIN_HAND); // use the item
            // player.setItemInHand(InteractionHand.MAIN_HAND, phone); // restore held item

            SmartphoneItem.lastused = item; // store the last used item in a static variable
            return;
        }

        super.clicked(slotId, button, clickType, player);
    }
}
