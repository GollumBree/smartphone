package de.gollumbree.smartphone.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.LivingEntity;

// import net.minecraft.world.InteractionHand;
// import net.minecraft.world.entity.player.Player;
// import net.minecraft.world.item.ItemStack;
// import org.spongepowered.asm.mixin.injection.At;
// import org.spongepowered.asm.mixin.injection.Inject;
// import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
// import de.gollumbree.smartphone.SmartphoneItem;
// import de.gollumbree.smartphone.api.PlayerSmartphoneExt;

@Mixin(LivingEntity.class)
public abstract class PlayerSmartphoneMixin // implements PlayerSmartphoneExt
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
}
