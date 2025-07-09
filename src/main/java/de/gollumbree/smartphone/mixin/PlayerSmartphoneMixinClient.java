package de.gollumbree.smartphone.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.gollumbree.smartphone.items.ClientUsing;
import de.gollumbree.smartphone.network.UsingData;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

@Mixin(Screen.class)
public abstract class PlayerSmartphoneMixinClient {
    @Inject(method = "onClose", at = @At("HEAD"))
    private void onScreenClosed(CallbackInfo ci) {
        ClientUsing.using = ItemStack.EMPTY; // Reset the last used smartphone when the screen is closed
        var data = new UsingData(ItemStack.EMPTY);
        System.err.println("SmartphoneMixin: Data: " + data);
        PacketDistributor.sendToServer(data); // Notify the server to reset the smartphone
    }
}