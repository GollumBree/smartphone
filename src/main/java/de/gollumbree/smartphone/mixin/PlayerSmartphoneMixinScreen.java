package de.gollumbree.smartphone.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.gollumbree.smartphone.items.SmartphoneItem;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;

@Mixin(Screen.class)
public abstract class PlayerSmartphoneMixinScreen {
    @Inject(method = "onClose", at = @At("HEAD"))
    private void onScreenClosed(CallbackInfo ci) {
        SmartphoneItem.using = ItemStack.EMPTY; // Reset the last used smartphone when the screen is closed
    }

    @Inject(method = "removed", at = @At("HEAD"))
    private void onScreenRemoved(CallbackInfo ci) {
        onScreenClosed(ci);
    }
}