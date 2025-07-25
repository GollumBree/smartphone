package de.gollumbree.smartphone;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have
// one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    /** A list of item IDs or tag IDs (#namespace:path) allowed in the phone */
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ALLOWED_ITEMS = BUILDER.comment("""
            A list of items (or item tags) that may be placed in the smartphone.
            •  "minecraft:apple"       — single item ID
            •  "#forge:ingots/iron"    — tag entry (note leading #)
            """)
            .defineListAllowEmpty(
                    "allowedItems", // path
                    List.of(
                            "#smartphone:usable",
                            "pvcellphone:cellphone",
                            "create_mobile_packages:portable_stock_ticker",
                            "createrailwaysnavigator:navigator"), // default list

                    () -> "modid:item", // what the GUI suggests for a new entry
                    obj -> {
                        if (!(obj instanceof String s)) // type‑safety
                            return false;
                        if (s.startsWith("#")) // allow #tag:entries
                            s = s.substring(1);
                        return ResourceLocation.tryParse(s) != null;
                    });

    static final ModConfigSpec SPEC = BUILDER.build();

    private static final Set<Item> ALLOWED_CACHE = ConcurrentHashMap.newKeySet();

    /** Called once at load and on every /reload or config change */
    @SuppressWarnings("null")
    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == SPEC && Minecraft.getInstance().getConnection() != null) {
            refresh(Minecraft.getInstance().getConnection().registryAccess());
        }
    }

    public static void refresh(RegistryAccess registryAccess) {
        ALLOWED_CACHE.clear();

        var itemLookup = registryAccess.lookupOrThrow(Registries.ITEM);

        for (String entry : ALLOWED_ITEMS.get()) {
            if (entry.startsWith("#")) {
                // Tag entry – add every item in that tag
                String tagString = entry.substring(1);
                ResourceLocation tagId = ResourceLocation.tryParse(tagString);
                if (tagId == null)
                    continue;
                TagKey<Item> tagKey = TagKey.create(Registries.ITEM, tagId);
                itemLookup.get(tagKey).ifPresent(tag -> tag.forEach(holder -> ALLOWED_CACHE.add(holder.value())));
            } else {
                // Single item entry
                ResourceLocation id = ResourceLocation.tryParse(entry);
                if (id == null) {
                    continue;
                }

                ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
                Optional<Holder.Reference<Item>> holderOpt = itemLookup.get(key);
                holderOpt.ifPresent(holder -> ALLOWED_CACHE.add(holder.value()));
            }
        }
    }

    /** Utility for Slot#mayPlace or helper */
    public static boolean isAllowed(Item item) {
        return ALLOWED_CACHE.contains(item);
    }
}
