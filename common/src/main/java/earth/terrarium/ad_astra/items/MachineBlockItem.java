package earth.terrarium.ad_astra.items;

import earth.terrarium.ad_astra.blocks.machines.entity.AbstractMachineBlockEntity;
import earth.terrarium.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import earth.terrarium.ad_astra.blocks.machines.entity.OxygenDistributorBlockEntity;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.PlatformEnergyManager;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class MachineBlockItem extends BlockItem {
    public MachineBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    protected boolean postPlacement(BlockPos pos, World world, PlayerEntity player, ItemStack stack, BlockState state) {
        if (!world.isClient) {
            if (world.getBlockEntity(pos) instanceof AbstractMachineBlockEntity machineBlock) {
                NbtCompound nbt = stack.getOrCreateNbt();
                Inventories.readNbt(nbt, machineBlock.getItems());
                if (nbt.contains("energy")) {
                    Optional<PlatformEnergyManager> energyBlock = EnergyHooks.safeGetBlockEnergyManager(machineBlock, null);
                    energyBlock.ifPresent(platformEnergyManager -> platformEnergyManager.insert(nbt.getLong("energy"), false));
                }

                if (machineBlock instanceof FluidMachineBlockEntity fluidMachine) {
                    if (nbt.contains("inputFluid")) {
                        fluidMachine.getInputTank().setFluid(FluidHooks.fluidFromCompound(nbt.getCompound("inputFluid")).getFluid());
                        fluidMachine.getInputTank().setAmount(nbt.getLong("inputAmount"));
                    }

                    if (nbt.contains("outputFluid")) {
                        fluidMachine.getOutputTank().setFluid(FluidHooks.fluidFromCompound(nbt.getCompound("outputFluid")).getFluid());
                        fluidMachine.getOutputTank().setAmount(nbt.getLong("outputAmount"));
                    }

                    if (machineBlock instanceof OxygenDistributorBlockEntity oxygenDistributorMachine) {
                        if (nbt.contains("showOxygen")) {
                            oxygenDistributorMachine.setShowOxygen(nbt.getBoolean("showOxygen"));
                        }
                    }
                }
            }
        }
        return super.postPlacement(pos, world, player, stack, state);
    }
}
