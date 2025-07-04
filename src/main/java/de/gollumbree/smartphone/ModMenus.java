package de.gollumbree.smartphone;

import java.util.function.Supplier;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU,
            Smartphone.MODID); // Registry‑Ziel und Mod‑ID

    public static final Supplier<MenuType<SmartphoneMenu>> SMARTPHONE_MENU = MENUS.register("smartphone_menu",
            () -> new MenuType<>(SmartphoneMenu::new, FeatureFlags.DEFAULT_FLAGS)); // MenuSupplier‑Konstruktor
}
