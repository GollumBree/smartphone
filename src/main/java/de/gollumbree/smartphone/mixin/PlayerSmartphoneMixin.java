package de.gollumbree.smartphone.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.gollumbree.smartphone.SmartphoneItem;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

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
    // @Inject(method = "set", at = @At("HEAD"), cancellable = true)
    // private <T> void onSet(DataComponentType<? super T> component, @Nullable T
    // value, CallbackInfoReturnable<T> cir) {
    // if (((ItemStack) (Object) this).getItem() instanceof SmartphoneItem) {
    // ItemStack last = SmartphoneItem.getLastUsed(((ItemStack) (Object) this)); //
    // get the last // used item
    // var output = last.set(component, value);
    // cir.setReturnValue(output); // set and return the value
    // // return output; // return the modified value
    // }
    // }

    // {
    // return this.components.set(component, value);
    // }

    @Inject(method = "set", at = @At("HEAD"), cancellable = true)
    private <T> void onSet(DataComponentType<? super T> component, @Nullable T value, CallbackInfoReturnable<T> cir) {
        System.out
                .println("PlayerSmartphoneMixin: set called on Item " + (ItemStack) (Object) this + " with component: "
                        + component + ", value: " + value);
        ItemStack iStack = SmartphoneItem.lastused;
        System.out.println("PlayerSmartphoneMixin: temp ItemStack is: " + iStack);
        System.out.println((ItemStack) (Object) this == iStack);
    }

}
