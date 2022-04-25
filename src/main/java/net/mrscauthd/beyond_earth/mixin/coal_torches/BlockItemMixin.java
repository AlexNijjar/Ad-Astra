package net.mrscauthd.beyond_earth.mixin.coal_torches;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.registry.ModBlocks;
import net.mrscauthd.beyond_earth.util.ModUtils;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    private static void playFireExtinguish(BlockPos pos, World world) {
        world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
    }

    @Inject(at = @At(value = "TAIL"), method = "place")
    public void place(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> info) {
        // Extinguish fire items in dimensions with no oxygen.
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        if (!ModUtils.dimensionHasOxygen(false, world.getRegistryKey())) {
            BlockState blockstate = world.getBlockState(context.getBlockPos());
            Block block = blockstate.getBlock();

            boolean playSound = false;

            // Wall Torch.
            if (block instanceof WallTorchBlock) {
                world.setBlockState(pos, ModBlocks.WALL_COAL_TORCH.getDefaultState().with(WallTorchBlock.FACING, blockstate.get(WallTorchBlock.FACING)), 3);
                playSound = true;
            }

            // Torch.
            else if (block instanceof TorchBlock) {
                if (!block.equals(Blocks.REDSTONE_TORCH) && !block.equals(Blocks.REDSTONE_WALL_TORCH)) {
                    world.setBlockState(pos, ModBlocks.COAL_TORCH.getDefaultState(), 3);
                    playSound = true;
                }
            }

            // Lantern.
            else if (block instanceof LanternBlock) {
                world.setBlockState(pos, ModBlocks.COAL_LANTERN.getDefaultState().with(LanternBlock.HANGING, blockstate.get(LanternBlock.HANGING)), 3);
                playSound = true;
            }

            // Campfire.
            else if (block instanceof CampfireBlock && blockstate.get(CampfireBlock.LIT)) {
                world.setBlockState(pos, blockstate.with(CampfireBlock.LIT, false), 3);
                playSound = true;
            }

            if (playSound) {
                playFireExtinguish(pos, world);
            }
        }
    }
}