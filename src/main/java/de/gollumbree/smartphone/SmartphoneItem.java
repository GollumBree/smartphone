package de.gollumbree.smartphone;

import javax.annotation.Nonnull;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public class SmartphoneItem extends Item {
    public static final String INVENTORY_KEY = "PhoneInventory";
    private static final String LAST_USED_KEY = "LastUsed";

    public SmartphoneItem(Properties props) {
        super(props); // Register the data component
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level,
            @Nonnull Player player,
            @Nonnull InteractionHand hand) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(
                    new SimpleMenuProvider(
                            (id, inv, p) -> new SmartphoneMenu(id, inv), // Server‑Menü
                            Component.translatable("screen.smartphone.title"))); // IPlayerExtension#openMenu laut
                                                                                 // Docs
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand),
                level.isClientSide());
    }

    public static ListTag getInventory(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null)
            return new ListTag();
        CompoundTag tag = data.copyTag(); // ✅ correct way to access actual NBT
        return tag.getList(INVENTORY_KEY, CompoundTag.TAG_COMPOUND);
    }

    public static void setInventory(ItemStack stack, ListTag inventory) {
        CompoundTag tag = new CompoundTag();
        tag.put(INVENTORY_KEY, inventory);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag)); // ✅ CORRECT
    }

    public static ItemStack getLastUsed(ItemStack phone, HolderLookup.Provider lookup) {
        CustomData cd = phone.get(DataComponents.CUSTOM_DATA);
        if (cd == null)
            return ItemStack.EMPTY;

        CompoundTag root = cd.copyTag();
        if (!root.contains(LAST_USED_KEY, CompoundTag.TAG_COMPOUND))
            return ItemStack.EMPTY;

        return ItemStack.parse(lookup, root.getCompound(LAST_USED_KEY)).orElse(ItemStack.EMPTY);
    }

    public static void setLastUsed(ItemStack phone, ItemStack lastUsed, HolderLookup.Provider lookup) {
        @SuppressWarnings("null")
        CompoundTag tag = phone.get(DataComponents.CUSTOM_DATA) != null
                ? phone.get(DataComponents.CUSTOM_DATA).copyTag()
                : new CompoundTag();

        tag.put(LAST_USED_KEY, lastUsed.saveOptional(lookup));
        phone.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

}
