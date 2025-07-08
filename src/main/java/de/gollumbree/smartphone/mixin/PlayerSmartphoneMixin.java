package de.gollumbree.smartphone.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.gollumbree.smartphone.items.SmartphoneItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

@Mixin(LivingEntity.class)
public abstract class PlayerSmartphoneMixin {
    @Inject(method = "getItemInHand", at = @At("RETURN"), cancellable = true)
    private void onGetItemInHand(InteractionHand hand, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack original = cir.getReturnValue();
        if (original.getItem() instanceof SmartphoneItem) {
            if (SmartphoneItem.using != ItemStack.EMPTY) {
                cir.setReturnValue(SmartphoneItem.using);
            }
        }
    }

    @Inject(method = "getMainHandItem", at = @At("RETURN"), cancellable = true)
    private void onGetItemInMainHand(CallbackInfoReturnable<ItemStack> cir) {
        onGetItemInHand(InteractionHand.MAIN_HAND, cir);
    }

    @Inject(method = "getOffhandItem", at = @At("RETURN"), cancellable = true)
    private void onGetItemInOffHand(CallbackInfoReturnable<ItemStack> cir) {
        onGetItemInHand(InteractionHand.OFF_HAND, cir);
    }
}