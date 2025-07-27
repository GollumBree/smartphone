package de.gollumbree.smartphone.mixin.compat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.theidler.create_mobile_packages.items.portable_stock_ticker.PortableStockTicker;

import de.gollumbree.smartphone.compat.ItemFinder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

@Mixin(PortableStockTicker.class)
public abstract class PSTMixin {
    @Inject(method = "find", at = @At("RETURN"), cancellable = true)
    private static void onFind(Inventory inv, CallbackInfoReturnable<ItemStack> cir) {
        if (cir.getReturnValue() == null) {
            ItemStack pst = ItemFinder.findItem(inv, PortableStockTicker.class);
            if (pst != null) {
                cir.setReturnValue(pst);
            }
        }
    }
}
