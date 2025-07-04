// package de.gollumbree.smartphone;

// import net.minecraft.client.Minecraft;
// import net.minecraft.client.gui.screens.MenuScreens;
// import net.minecraft.client.renderer.item.ItemProperties;
// import net.minecraft.resources.ResourceLocation;
// import net.neoforged.api.distmarker.Dist;
// import net.neoforged.bus.api.SubscribeEvent;
// import net.neoforged.fml.ModContainer;
// import net.neoforged.fml.common.EventBusSubscriber;
// import net.neoforged.fml.common.Mod;
// import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
// import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
// import net.neoforged.neoforge.client.gui.ConfigurationScreen;
// import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// // This class will not load on dedicated servers. Accessing client side code
// // from here is safe.
// @Mod(value = Smartphone.MODID, dist = Dist.CLIENT)
// // You can use EventBusSubscriber to automatically register all static methods
// // in the class annotated with @SubscribeEvent
// // @EventBusSubscriber(modid = Smartphone.MODID, value = Dist.CLIENT)
// public class SmartphoneClient {
//     public SmartphoneClient(ModContainer container) {
//         // Allows NeoForge to create a config screen for this mod's configs.
//         // The config screen is accessed by going to the Mods screen > clicking on your
//         // mod > clicking on config.
//         // Do not forget to add translations for your config options to the en_us.json
//         // file.
//         container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
//     }

//     // @SubscribeEvent
//     // static void onClientSetup(FMLClientSetupEvent event) {
//     // // Some client setup code
//     // Smartphone.LOGGER.info("HELLO FROM CLIENT SETUP");
//     // Smartphone.LOGGER.info("MINECRAFT NAME >> {}",
//     // Minecraft.getInstance().getUser().getName());
//     // }

//     // @SubscribeEvent
//     // static void onClientSetup(FMLClientSetupEvent event) {
//     // // Some client setup code
//     // // ItemProperties.register(
//     // // Smartphone.SMARTPHONE_ITEM.get(),
//     // // ResourceLocation.fromNamespaceAndPath(Smartphone.MODID, "open"),
//     // // (stack, level, entity, seed) -> entity != null && entity.isUsingItem() ?
//     // 1.0F
//     // // : 0.0F);
//     // }

// }

package de.gollumbree.smartphone;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = Smartphone.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods
// in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = Smartphone.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SmartphoneClient {
    public SmartphoneClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your
        // mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json
        // file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        Smartphone.LOGGER.info("HELLO FROM CLIENT SETUP");
        Smartphone.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.SMARTPHONE_MENU.get(), SmartphoneScreen::new);
    }
}