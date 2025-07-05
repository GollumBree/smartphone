package de.gollumbree.smartphone.mixin;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import de.gollumbree.smartphone.SmartphoneItem;

@Mixin(Player.class)
public abstract class PlayerGetItemInHandMixin {

    /**
     * When Player#getItemInHand(...) returns a Smartphone, replace the stack
     * with the "last‑used item" stored inside the phone's NBT (if any).
     */
    @Inject(method = "getItemInHand", at = @At("RETURN"), cancellable = true)
    private void replaceSmartphoneReturn(InteractionHand hand,
            CallbackInfoReturnable<ItemStack> cir) {

        ItemStack returned = cir.getReturnValue();

        if (returned.getItem() instanceof SmartphoneItem) {
            Player player = (Player) (Object) this;
            HolderLookup.Provider lookup = player.level().registryAccess();
            ItemStack lastUsed = SmartphoneItem.getLastUsed(returned, lookup); // ← your helper
            if (!lastUsed.isEmpty()) {
                cir.setReturnValue(lastUsed);
            }
        }
        // otherwise leave the original value untouched
    }
}
