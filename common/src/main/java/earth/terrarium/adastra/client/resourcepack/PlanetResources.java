package earth.terrarium.adastra.client.resourcepack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.AdAstraClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.GsonHelper;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlanetResources implements ResourceManagerReloadListener {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        List<SolarSystem> solarSystems = new ArrayList<>();
        List<PlanetRing> planetRings = new ArrayList<>();
        List<Galaxy> galaxies = new ArrayList<>();

        // Solar Systems
        for (ResourceLocation id : manager.listResources("planet_resources/solar_systems", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getResourceStack(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        solarSystems.add(SolarSystem.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, AdAstra.LOGGER::error));
                    }
                }
            } catch (Exception e) {
                AdAstra.LOGGER.error("Failed to load Ad Astra solar system assets from: \"" + id.toString() + "\"", e);
                e.printStackTrace();
            }
        }

        // Planets
        for (ResourceLocation id : manager.listResources("planet_resources/planet_rings", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getResourceStack(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        planetRings.add(PlanetRing.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, AdAstra.LOGGER::error));
                    }
                }
            } catch (Exception e) {
                AdAstra.LOGGER.error("Failed to load Ad Astra planet ring assets from: \"" + id.toString() + "\"", e);
                e.printStackTrace();
            }
        }

        // Galaxy
        for (ResourceLocation id : manager.listResources("planet_resources/galaxy", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getResourceStack(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        galaxies.add(Galaxy.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, AdAstra.LOGGER::error));
                    }
                }
            } catch (Exception e) {
                AdAstra.LOGGER.error("Failed to load Ad Astra galaxy assets from: \"" + id.toString() + "\"", e);
                e.printStackTrace();
            }
        }

        solarSystems.sort(Comparator.comparing(SolarSystem::solarSystem));
        galaxies.sort(Comparator.comparing(Galaxy::galaxy));
        AdAstraClient.solarSystems = solarSystems;
        AdAstraClient.planetRings = planetRings;
        AdAstraClient.galaxies = galaxies;
    }
}
