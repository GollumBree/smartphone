package de.gollumbree.smartphone;

import javax.annotation.Nonnull;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

public class SmartphoneMenu extends AbstractContainerMenu {

    // Client‑Konstruktor
    public SmartphoneMenu(int id, Inventory playerInv) {
        this(id, playerInv, ContainerLevelAccess.NULL); // Dummy‑Access auf Client
    }

    // Server‑Konstruktor
    public SmartphoneMenu(int id, Inventory playerInv, ContainerLevelAccess access) {
        super(ModMenus.SMARTPHONE_MENU.get(), id);
        // Slots / DataSlots hier einfügen …
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return true; // oder AbstractContainerMenu.stillValid(access, player, <Block>)
    }

    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int idx) {
        return ItemStack.EMPTY; // Pflicht‑Implementierung (siehe Menü‑Dokumentation)
    }
}
