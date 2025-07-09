package de.gollumbree.smartphone;

import de.gollumbree.smartphone.items.SmartphoneItem;
import de.gollumbree.smartphone.network.ServerPayloadHandler;
import de.gollumbree.smartphone.network.UsingData;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Main.MODID)
public class Main {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "smartphone";
    // Create a Deferred Register to hold Items which will all be registered under
    // the "smartphone" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Creates a new item with the id "smartphone:smartphone"
    public static final DeferredItem<SmartphoneItem> SMARTPHONE_ITEM = ITEMS.register("smartphone",
            () -> new SmartphoneItem(new Item.Properties().stacksTo(1)));

    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister
            .createDataComponents(net.minecraft.core.registries.Registries.DATA_COMPONENT_TYPE, MODID);

    // Example: a simple compound tag component like CUSTOM_DATA
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CompoundTag>> PHONE_LAST_USED = DATA_COMPONENTS
            .register("phone_last_used", () -> DataComponentType.<CompoundTag>builder()
                    .persistent(CompoundTag.CODEC)
                    .build());

    // The constructor for the mod class is the first code that is run when your mod
    // is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and
    // pass them in automatically.
    public Main(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        // modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);

        DATA_COMPONENTS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Smartphone)
        // to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in
        // this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::registerPayloads);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config
        // file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        // NeoForge.EVENT_BUS.register(Config.class);

        ModMenus.MENUS.register(modEventBus);

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(SMARTPHONE_ITEM);
        }
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        Config.refresh(event.getServer().registryAccess());
    }

    @SubscribeEvent
    public void onDatapackSync(OnDatapackSyncEvent event) {
        MinecraftServer server = event.getPlayer().getServer();
        if (server == null) {
            return; // not on a server
        }
        RegistryAccess access = server.registryAccess();
        Config.refresh(access);
    }

    // on the mod event bus
    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                UsingData.TYPE,
                UsingData.STREAM_CODEC,
                ServerPayloadHandler::handleDataOnMain);
    }
}
