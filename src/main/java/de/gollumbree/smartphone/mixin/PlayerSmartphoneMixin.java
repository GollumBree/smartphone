package de.gollumbree.smartphone.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.gollumbree.smartphone.SmartphoneItem;
import de.gollumbree.smartphone.SmartphoneMenu;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.ItemStackMap;

// import net.minecraft.world.InteractionHand;
// import net.minecraft.world.entity.player.Player;
// import net.minecraft.world.item.ItemStack;
// import org.spongepowered.asm.mixin.injection.At;
// import org.spongepowered.asm.mixin.injection.Inject;
// import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
// import de.gollumbree.smartphone.SmartphoneItem;
// import de.gollumbree.smartphone.api.PlayerSmartphoneExt;

@Mixin(ItemStack.class)
public abstract class PlayerSmartphoneMixin// implements PlayerSmartphoneExt
{

    // @Shadow
    // public abstract ItemStack getItemInHand(InteractionHand hand);

    // @Unique
    // private static final ThreadLocal<Boolean> ignoreSmartphoneFlag =
    // ThreadLocal.withInitial(() -> false);

    // @Override
    // public ItemStack getItemInHand(InteractionHand hand, boolean
    // ignoreSmartphone) {
    // ignoreSmartphoneFlag.set(ignoreSmartphone);
    // ItemStack result = getItemInHand(hand); // original method with injected swap
    // ignoreSmartphoneFlag.set(false);
    // return result;
    // }

    // @Inject(method = "getItemInHand", at = @At("RETURN"), cancellable = true)
    // private void onGetItemInHand(InteractionHand hand,
    // CallbackInfoReturnable<ItemStack> cir) {
    // // if (ignoreSmartphoneFlag.get())
    // // return;

    // ItemStack returned = cir.getReturnValue();
    // System.err.println("PlayerSmartphoneMixin.onGetItemInHand: " + returned);
    // if (returned.getItem() instanceof SmartphoneItem) {
    // System.err
    // .println("PlayerSmartphoneMixin.onGetItemInHand: Smartphone detected, looking
    // for last used item.");
    // var lookup = ((Player) (Object) this).level().registryAccess();
    // ItemStack last = SmartphoneItem.getLastUsed(returned, lookup);
    // System.err.println("PlayerSmartphoneMixin.onGetItemInHand: Last used item: "
    // + last);
    // if (!last.isEmpty())
    // cir.setReturnValue(last);
    // }
    // }

    @Inject(method = "set", at = @At("HEAD"), cancellable = true)
    private <T> T onSet(DataComponentType<? super T> component, @Nullable T value, CallbackInfoReturnable<T> cir) {
        if (this.getItem() instanceof SmartphoneItem) {
            ItemStack last = ((SmartphoneItem) this).getLastUsed(this, SmartphoneMenu.lookup); // get the last used item
            last.set(component, value);
            return value;
        }
        cir.cancel();
    }
    // {
    // return this.components.set(component, value);
    // }
}
