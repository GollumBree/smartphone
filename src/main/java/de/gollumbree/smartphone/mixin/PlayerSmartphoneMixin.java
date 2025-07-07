package de.gollumbree.smartphone.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.gollumbree.smartphone.SmartphoneItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

@Mixin(LivingEntity.class)
public abstract class PlayerSmartphoneMixin {
    @Inject(method = "getItemInHand", at = @At("RETURN"), cancellable = true)
    private void onGetItemInHand(InteractionHand hand,
            CallbackInfoReturnable<ItemStack> cir) {
        ItemStack original = cir.getReturnValue();
        if (original.getItem() instanceof SmartphoneItem) {
            System.out.println("SmartphoneItem found.");
            if (SmartphoneItem.lastused != ItemStack.EMPTY) {
                cir.setReturnValue(SmartphoneItem.lastused);
                System.out.println("Returning last used item: " + SmartphoneItem.lastused);
            } else {
                System.err.println("No last used item found, returning original.");
            }
        }
    }

    @Inject(method = "getMainHandItem", at = @At("RETURN"), cancellable = true)
    private void onGetItemInMainHand(
            CallbackInfoReturnable<ItemStack> cir) {
        onGetItemInHand(InteractionHand.MAIN_HAND, cir);
    }

    @Inject(method = "getMainHandItem", at = @At("RETURN"), cancellable = true)
    private void onGetItemInOffHand(
            CallbackInfoReturnable<ItemStack> cir) {
        onGetItemInHand(InteractionHand.OFF_HAND, cir);
    }
}