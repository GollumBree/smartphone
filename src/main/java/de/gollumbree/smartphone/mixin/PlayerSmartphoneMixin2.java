package de.gollumbree.smartphone.mixin;

//import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.gollumbree.smartphone.SmartphoneItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

// import net.minecraft.world.InteractionHand;
// import net.minecraft.world.entity.player.Player;
// import net.minecraft.world.item.ItemStack;
// import org.spongepowered.asm.mixin.injection.At;
// import org.spongepowered.asm.mixin.injection.Inject;
// import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
// import de.gollumbree.smartphone.SmartphoneItem;
// import de.gollumbree.smartphone.api.PlayerSmartphoneExt;

@Mixin(LivingEntity.class)
public abstract class PlayerSmartphoneMixin2// implements PlayerSmartphoneExt
{
    @Inject(method = "getItemInHand", at = @At("RETURN"), cancellable = true)
    private void onGetItemInHand(InteractionHand hand, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack original = cir.getReturnValue();
        if (original.getItem() instanceof SmartphoneItem && hand == InteractionHand.MAIN_HAND) {
            System.out.println("SmartphoneItem found.");
            if (SmartphoneItem.lastused != ItemStack.EMPTY) {
                cir.setReturnValue(SmartphoneItem.lastused);
                System.out.println("Returning last used item: " + SmartphoneItem.lastused);
            } else {
                System.err.println("No last used item found, returning original.");
            }
        }
    }

    // public ItemStack getItemInHand(InteractionHand hand) {
    // if (hand == InteractionHand.MAIN_HAND) {
    // return this.getItemBySlot(EquipmentSlot.MAINHAND);
    // } else if (hand == InteractionHand.OFF_HAND) {
    // return this.getItemBySlot(EquipmentSlot.OFFHAND);
    // } else {
    // throw new IllegalArgumentException("Invalid hand " + String.valueOf(hand));
    // }
    // }

}