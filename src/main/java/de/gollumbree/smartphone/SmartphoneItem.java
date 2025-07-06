package de.gollumbree.smartphone;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SmartphoneItem extends Item {
    public static final String INVENTORY_KEY = "PhoneInventory";
    public static final String LAST_USED_KEY = "LastUsed";
    public static HolderLookup.Provider lookup; // for ItemStack parsing

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

    public static ItemStack getLastUsed(ItemStack phone) {
        ItemStack iStack = null;
        CompoundTag tag = phone.get(Smartphone.PHONE_LAST_USED.get());
        if (tag != null) {
            iStack = ItemStack.parseOptional(lookup, tag);
        }
        return iStack != null ? iStack : ItemStack.EMPTY;
    }
}
