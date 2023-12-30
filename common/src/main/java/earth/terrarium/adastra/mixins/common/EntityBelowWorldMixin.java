package earth.terrarium.adastra.mixins.common;

import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.planets.AdAstraData;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin({Entity.class, LivingEntity.class})
public abstract class EntityBelowWorldMixin {

    @Inject(method = "onBelowWorld", at = @At("HEAD"), cancellable = true)
    private void adastra$onBelowWorld(CallbackInfo ci) {
        var entity = ((Entity) (Object) this);
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;
        if (!PlanetApi.API.isSpace(serverLevel)) return;

        var planet = AdAstraData.getPlanet(serverLevel.dimension());
        if (planet == null) return;

        planet.getOrbitPlanet().ifPresent(targetLevelKey -> {
            MinecraftServer server = serverLevel.getServer();
            var targetLevel = server.getLevel(targetLevelKey);

            List<Entity> passengers = entity.getPassengers();
            entity.setPos(entity.getX(), Rocket.ATMOSPHERE_LEAVE, entity.getZ());

            var teleportedEntity = ModUtils.teleportToDimension(entity, targetLevel);
            for (var passenger : passengers) {
                Entity teleportedPassenger = ModUtils.teleportToDimension(passenger, targetLevel);
                teleportedPassenger.startRiding(teleportedEntity);
            }
            ci.cancel();
        });
    }
}
