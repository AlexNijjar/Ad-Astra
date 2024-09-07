package earth.terrarium.adastra.forge;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.client.forge.AdAstraClientForge;
import earth.terrarium.adastra.common.commands.AdAstraCommands;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import earth.terrarium.adastra.common.tags.ModBlockTags;
import earth.terrarium.adastra.common.compat.create.CreateCompat;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(AdAstra.MOD_ID)
public class AdAstraForge {

    public AdAstraForge() {
        AdAstra.init();
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onAddReloadListener);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onDatapackSync);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onServerTick);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::registerCommands);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onBlockPlace);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(AdAstraForge::onAttributes);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(AdAstraForge::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onServerReloadListeners);
        if (FMLEnvironment.dist.isClient()) {
            AdAstraClientForge.init();
        }
        if(ModList.get().isLoaded("create")) {
            CreateCompat.init();
        }
    }

    public static void onAddReloadListener(AddReloadListenerEvent event) {
        AdAstra.onAddReloadListener((id, listener) -> event.addListener(listener));
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() != null) {
            AdAstra.onDatapackSync(event.getPlayer());
        } else {
            for (var player : event.getPlayerList().getPlayers()) {
                AdAstra.onDatapackSync(player);
            }
        }
    }

    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            AdAstra.onServerTick(event.getServer());
        }
    }

    public static void onAttributes(EntityAttributeCreationEvent event) {
        ModEntityTypes.registerAttributes((entityType, attribute) -> event.put(entityType.get(), attribute.get().build()));
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        AdAstra.postInit();
    }

    private static void registerCommands(RegisterCommandsEvent event) {
        AdAstraCommands.register(event.getDispatcher());
    }

    private static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().is(ModBlockTags.DESTROYED_IN_SPACE)
            && !event.getPlacedBlock().isRandomlyTicking()
            && (event.getLevel() instanceof Level level)
            && !OxygenApi.API.hasOxygen(level, event.getPos())) {
            event.setCanceled(true);
        }
    }

    public static void onServerReloadListeners(AddReloadListenerEvent event) {
        AdAstra.onRegisterReloadListeners((id, listener) -> event.addListener(listener));
    }

}
