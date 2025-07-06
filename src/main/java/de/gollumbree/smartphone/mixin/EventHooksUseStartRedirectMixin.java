package de.gollumbree.smartphone.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.neoforged.neoforge.event.EventHooks;

// import de.gollumbree.smartphone.api.PlayerSmartphoneExt;
// import de.gollumbree.smartphone.SmartphoneItem;
// import net.minecraft.world.InteractionHand;
// import net.minecraft.world.entity.player.Player;
// import net.minecraft.world.item.ItemStack;
// import org.spongepowered.asm.mixin.injection.At;
// import org.spongepowered.asm.mixin.injection.Redirect;

// /**
//  * Redirects the single call to player.getItemInHand(hand) inside
//  * EventHooks.onItemUseStart so that, when the stack being used is a Smartphone,
//  * we invoke the NEW overload with ignoreSmartphone = true.
//  */
@Mixin(EventHooks.class)
public abstract class EventHooksUseStartRedirectMixin {

    // /**
    // * Method signature of EventHooks.onItemUseStart in NeoForge 21.1:
    // *
    // * public static int onItemUseStart(ItemStack stack, int duration, Player
    // * player)
    // *
    // * That method contains exactly one invoke of player.getItemInHand(hand).
    // * We redirect that invoke.
    // */
    // @Redirect(method = "onItemUseStart", at = @At(value = "INVOKE", target =
    // "Lnet/minecraft/world/entity/player/Player;"
    // +
    // "getItemInHand(Lnet/minecraft/world/InteractionHand;)" +
    // "Lnet/minecraft/world/item/ItemStack;"))
    // private static ItemStack redirectGetItemInHand(
    // Player player, // original call target
    // InteractionHand hand, // original arg
    // ItemStack stack, // 1st arg of onItemUseStart (captured automatically)
    // int duration, // 2nd arg (ignored here)
    // Player samePlayer) // 3rd arg (same as 'player')
    // {
    // // If the stack being used is a smartphone, ask for RAW hand stack
    // if (stack.getItem() instanceof SmartphoneItem && player instanceof
    // PlayerSmartphoneExt ext) {
    // return ext.getItemInHand(hand, true); // ignoreSmartphone = true
    // }

    // // Otherwise, keep vanilla behaviour
    // return player.getItemInHand(hand);
    // }
}
