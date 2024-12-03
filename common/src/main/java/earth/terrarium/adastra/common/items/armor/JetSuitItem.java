package earth.terrarium.adastra.common.items.armor;

import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.adastra.common.utils.KeybindManager;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyItem;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.energy.impl.SimpleEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedItemEnergyContainer;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JetSuitItem extends SpaceSuitItem implements BotariumEnergyItem<WrappedItemEnergyContainer> {
    private final long energyCapacity;

    public JetSuitItem(ArmorMaterial material, Type type, int tankSize, int energy, Properties properties) {
        super(material, type, tankSize, properties);
        this.energyCapacity = energy;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(TooltipUtils.getFluidComponent(
            FluidUtils.getTank(stack),
            FluidConstants.fromMillibuckets(tankSize),
            ModFluids.OXYGEN.get()));
        var energy = getEnergyStorage(stack);
        tooltipComponents.add(TooltipUtils.getEnergyComponent(energy.getStoredEnergy(), energyCapacity));
        tooltipComponents.add(TooltipUtils.getMaxEnergyInComponent(energy.maxInsert()));
        TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.JET_SUIT_INFO);
    }

    @Override
    public WrappedItemEnergyContainer getEnergyStorage(ItemStack holder) {
        return new WrappedItemEnergyContainer(
            holder,
            new SimpleEnergyContainer(energyCapacity) {
                @Override
                public long maxInsert() {
                    return 1000;
                }
            });
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (!(entity instanceof Player player)) return;
        if (player.getItemBySlot(EquipmentSlot.CHEST) != stack) return;

        if (player.getAbilities().flying) return;
        if (player.getCooldowns().isOnCooldown(stack.getItem())) return;
        if (!hasFullJetSuitSet(player)) return;

        if (!KeybindManager.suitFlightEnabled(player)) return;
        if (!canFly(player, stack)) return;

        if (!KeybindManager.suitHoverEnabled(player)) {
            if (!KeybindManager.jumpDown(player)) return;
            if (KeybindManager.sprintDown(player)) {
                fullFlight(player);
                consume(player, stack, 100, slotId);
            } else {
                upwardsFlight(player);
                consume(player, stack, 50, slotId);
            }
            return;
        }
        double factor = -player.getDeltaMovement().y * (0.9 * Math.pow(1 - Math.abs(player.getDeltaMovement().y) / 3.92 /* terminal velocity */, 1.5));
        player.addDeltaMovement(new Vec3(0, factor, 0));
        consume(player, stack, 100, slotId);
        if (KeybindManager.jumpDown(player)) player.addDeltaMovement(new Vec3(0, 0.25, 0));
        if (KeybindManager.sneakDown(player)) player.addDeltaMovement(new Vec3(0, -0.25, 0));
    }

    protected void upwardsFlight(Player player) {
        double acceleration = sigmoidAcceleration(player.tickCount, 5.0, 1.0, 2.0);
        acceleration /= 25.0f;
        player.addDeltaMovement(new Vec3(0, Math.max(0.0025, acceleration), 0));
        player.fallDistance = Math.max(player.fallDistance / 1.5f, 0.0f);
    }

    protected void fullFlight(Player player) {
        Vec3 movement = player.getLookAngle().normalize().scale(0.075);
        if (player.getDeltaMovement().length() > 2.0) return;
        player.addDeltaMovement(movement);
        player.fallDistance = Math.max(player.fallDistance / 1.5f, 0.0f);
        if (!player.isFallFlying()) {
            player.startFallFlying();
        }
    }

    private boolean canFly(Player player, ItemStack stack) {
        return player.isCreative() || getEnergyStorage(stack).getStoredEnergy() > 0;
    }

    private void consume(Player player, ItemStack stack, int amount, int slotId) {
        if (player.isCreative() || player.isSpectator() || player.level().isClientSide()) return;
        ItemStackHolder stackHolder = new ItemStackHolder(stack);
        EnergyContainer container = EnergyContainer.of(stackHolder);
        if (container == null) return;
        container.internalExtract(amount, false);
        player.getInventory().armor.set(slotId, stackHolder.getStack());
    }

    protected boolean isFullFlightEnabled(Player player) {
        return KeybindManager.suitFlightEnabled(player) && KeybindManager.jumpDown(player) && KeybindManager.sprintDown(player) && !KeybindManager.suitHoverEnabled(player);
    }

    public static double sigmoidAcceleration(double t, double peakTime, double peakAcceleration, double initialAcceleration) {
        return ((2 * peakAcceleration) / (1 + Math.exp(-t / peakTime)) - peakAcceleration) + initialAcceleration;
    }

    public void spawnParticles(Level level, LivingEntity entity, HumanoidModel<?> model, ItemStack stack) {
        if (!(entity instanceof Player player)) return;
        if (!canFly(player, stack)) return;
        if (!hasFullJetSuitSet(player)) return;
        if (!KeybindManager.suitFlightEnabled(player)) return;
        if ((!KeybindManager.jumpDown(player) || (!KeybindManager.jumpDown(player) && !KeybindManager.sprintDown(player))) && !KeybindManager.suitHoverEnabled(player))
            return;

        spawnParticles(level, entity, model.rightArm.xRot + 0.05, entity.isFallFlying() ? 0.0 : 0.8, -0.45);
        spawnParticles(level, entity, model.leftArm.xRot + 0.05, entity.isFallFlying() ? 0.0 : 0.8, 0.45);
        spawnParticles(level, entity, model.rightLeg.xRot + 0.05, entity.isFallFlying() ? 0.1 : 0.0, -0.1);
        spawnParticles(level, entity, model.leftLeg.xRot + 0.05, entity.isFallFlying() ? 0.1 : 0.0, 0.1);
    }

    // Spawns particles at the limbs of the player
    private void spawnParticles(Level level, LivingEntity entity, double pitch, double yOffset, double zOffset) {
        double yRot = entity.yBodyRot;
        double forwardOffsetX = Math.cos(yRot * Math.PI / 180) * zOffset;
        double forwardOffsetZ = Math.sin(yRot * Math.PI / 180) * zOffset;
        double sideOffsetX = Math.cos((yRot - 90) * Math.PI / 180) * pitch;
        double sideOffsetZ = Math.sin((yRot - 90) * Math.PI / 180) * pitch;

        level.addParticle(ParticleTypes.FLAME, true,
            entity.getX() + forwardOffsetX + sideOffsetX,
            entity.getY() + yOffset,
            entity.getZ() + sideOffsetZ + forwardOffsetZ,
            0, KeybindManager.suitHoverEnabled((Player) entity) ? -0.5 : 0, 0);
    }

    @SuppressWarnings("unused") // Forge
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (entity.level().isClientSide()) return true;
        if (this.type != Type.CHESTPLATE) return true;
        int nextFlightTick = flightTicks + 1;
        if (nextFlightTick % 10 != 0) return true;

        if (nextFlightTick % 20 == 0) {
            stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(EquipmentSlot.CHEST));
        }

        entity.gameEvent(GameEvent.ELYTRA_GLIDE);

        return true;
    }

    @SuppressWarnings("unused") // Forge
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return entity instanceof Player player && canFly(player, stack) && isFullFlightEnabled(player) && !KeybindManager.suitHoverEnabled(player);
    }
}