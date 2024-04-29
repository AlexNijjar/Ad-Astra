package earth.terrarium.adastra.common.systems;

import earth.terrarium.adastra.api.events.AdAstraEvents;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.handlers.PlanetHandler;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import earth.terrarium.adastra.common.registry.ModDamageSources;
import earth.terrarium.adastra.common.tags.ModEntityTypeTags;
import earth.terrarium.adastra.common.tags.ModItemTags;
import net.minecraft.Optionull;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.Collection;

public class OxygenApiImpl implements OxygenApi {
    @Override
    public boolean hasOxygen(Level level) {
        return hasOxygen(level.dimension());
    }

    @Override
    public boolean hasOxygen(ResourceKey<Level> level) {
        return Optionull.mapOrDefault(PlanetApi.API.getPlanet(level), Planet::oxygen, true);
    }

    @Override
    public boolean hasOxygen(Level level, BlockPos pos) {
        if (level.isClientSide()) return hasOxygen(level);
        return PlanetHandler.hasOxygen((ServerLevel) level, pos);
    }

    @Override
    public boolean hasOxygen(Entity entity) {
        boolean hasOxygen = hasOxygen(entity.level(), BlockPos.containing(entity.getX(), entity.getEyeY(), entity.getZ()));
        return AdAstraEvents.EntityOxygenEvent.fire(entity, hasOxygen);
    }

    @Override
    public void setOxygen(Level level, BlockPos pos, boolean oxygen) {
        if (level.isClientSide()) return;
        PlanetHandler.setOxygen((ServerLevel) level, pos, oxygen);
    }

    @Override
    public void setOxygen(Level level, Collection<BlockPos> positions, boolean oxygen) {
        if (level.isClientSide()) return;
        PlanetHandler.setOxygen((ServerLevel) level, positions, oxygen);
    }

    @Override
    public void removeOxygen(Level level, BlockPos pos) {
        setOxygen(level, pos, hasOxygen(level));
    }

    @Override
    public void removeOxygen(Level level, Collection<BlockPos> positions) {
        setOxygen(level, positions, hasOxygen(level));
    }

    @Override
    public void entityTick(ServerLevel level, LivingEntity entity) {
        if (AdAstraConfig.disableOxygen) return;
        if (entity.getType().is(ModEntityTypeTags.LIVES_WITHOUT_OXYGEN)) return;
        if (entity.getType().is(ModEntityTypeTags.CAN_SURVIVE_IN_SPACE)) return;
        if (SpaceSuitItem.hasFullSet(entity, ModItemTags.SPACE_RESISTANT_ARMOR)) return;
        if (SpaceSuitItem.hasFullSet(entity, ModItemTags.OXYGEN_SUPPLYING_ARMOR)) return;
        if (SpaceSuitItem.hasFullSet(entity) && SpaceSuitItem.hasOxygen(entity)) return;
        if (this.hasOxygen(entity)) return;
        entity.hurt(ModDamageSources.create(level, ModDamageSources.OXYGEN), 2);
        entity.setAirSupply(-80);
    }
}
