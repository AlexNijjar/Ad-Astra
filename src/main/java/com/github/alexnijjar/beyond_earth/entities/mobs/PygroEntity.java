package com.github.alexnijjar.beyond_earth.entities.mobs;

import com.github.alexnijjar.beyond_earth.registry.ModEntityTypes;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class PygroEntity extends PiglinEntity {

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.45).add(EntityAttributes.GENERIC_MAX_HEALTH, 24).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6);
    }

    public PygroEntity(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        BlockState blockState = world.getBlockState(new BlockPos(this.getX(), this.getY() - 1, this.getZ()));

        if (blockState.isOf(Blocks.LAVA) || blockState.isOf(Blocks.AIR)) {
            return false;
        }

        return super.canSpawn(world, spawnReason);
    }

    @Override
    protected void zombify(ServerWorld world) {
        ZombifiedPygroEntity zombifiedPygroEntity = this.convertTo(ModEntityTypes.ZOMBIFIED_PYGRO, true);
        if (zombifiedPygroEntity != null) {
            zombifiedPygroEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
        }
    }
}
