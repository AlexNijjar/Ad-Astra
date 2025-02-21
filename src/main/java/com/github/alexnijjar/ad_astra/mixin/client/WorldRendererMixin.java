package com.github.alexnijjar.ad_astra.mixin.client;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.client.AdAstraClient;
import com.github.alexnijjar.ad_astra.client.resourcepack.SkyRenderer;
import com.github.alexnijjar.ad_astra.client.resourcepack.SkyRenderer.WeatherEffects;
import com.github.alexnijjar.ad_astra.registry.ModParticleTypes;
import com.github.alexnijjar.ad_astra.util.ModUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

	@Shadow
	@Final
	private MinecraftClient client;

	// Cancel the portal sound when the player falls out of orbit.
	@Inject(method = "processWorldEvent", at = @At("HEAD"), cancellable = true)
	public void adastra_processWorldEvent(PlayerEntity source, int eventId, BlockPos pos, int data, CallbackInfo ci) {
		if (eventId == WorldEvents.TRAVEL_THROUGH_PORTAL) {
			MinecraftClient client = MinecraftClient.getInstance();
			ClientPlayerEntity player = client.player;
			// Don't player the portal sound if the player teleported to the new world.
			if (((int) player.getPos().getY()) == AdAstra.CONFIG.rocket.atmosphereLeave) {
				ci.cancel();
			}
		}
	}

	// Venus rain.
	@Inject(method = "tickRainSplashing", at = @At("HEAD"), cancellable = true)
	public void adastra_tickRainSplashing(Camera camera, CallbackInfo info) {
		MinecraftClient client = MinecraftClient.getInstance();
		RegistryKey<World> world = client.world.getRegistryKey();
		boolean isVenus = false;
		for (SkyRenderer skyRenderer : AdAstraClient.skyRenderers) {
			if (world.equals(skyRenderer.dimension()) && skyRenderer.weatherEffects().equals(WeatherEffects.VENUS)) {
				isVenus = true;
				break;
			}
		}
		
		if (!isVenus) {
			return;
		}		
		
				WorldRendererAccessor worldRenderer = (WorldRendererAccessor) (Object) this;
	
				float f = client.world.getRainGradient(1.0f) / (MinecraftClient.isFancyGraphicsOrBetter() ? 1.0f : 2.0f);
				if (f <= 0.0f) {
					return;
				}
				Random random = new Random((long) worldRenderer.getTicks() * 312987231);
				ClientWorld worldView = client.world;
				BlockPos blockPos = new BlockPos(camera.getPos());
				Vec3i blockPos2 = null;
				int i = (int) (100.0f * f * f) / (client.options.particles == ParticlesMode.DECREASED ? 2 : 1);
				for (int j = 0; j < i; ++j) {
					int k = random.nextInt(21) - 10;
					int l = random.nextInt(21) - 10;
					BlockPos blockPos3 = worldView.getTopPosition(Heightmap.Type.MOTION_BLOCKING, blockPos.add(k, 0, l));
					Biome biome = worldView.getBiome(blockPos3).value();
					if (blockPos3.getY() <= worldView.getBottomY() || blockPos3.getY() > blockPos.getY() + 10 || blockPos3.getY() < blockPos.getY() - 10 || biome.getPrecipitation() != Biome.Precipitation.RAIN || !biome.doesNotSnow(blockPos3))
						continue;
					blockPos2 = blockPos3.down();
					if (client.options.particles == ParticlesMode.MINIMAL)
						break;
					double d = random.nextDouble();
					double e = random.nextDouble();
					BlockState blockState = worldView.getBlockState((BlockPos) blockPos2);
					FluidState fluidState = worldView.getFluidState((BlockPos) blockPos2);
					VoxelShape voxelShape = blockState.getCollisionShape(worldView, (BlockPos) blockPos2);
					double g = voxelShape.getEndingCoord(Direction.Axis.Y, d, e);
					double h = fluidState.getHeight(worldView, (BlockPos) blockPos2);
					double m = Math.max(g, h);
					DefaultParticleType particleEffect = fluidState.isIn(FluidTags.LAVA) || blockState.isOf(Blocks.MAGMA_BLOCK) || CampfireBlock.isLitCampfire(blockState) ? ParticleTypes.SMOKE : ModParticleTypes.VENUS_RAIN;
					client.world.addParticle(particleEffect, (double) blockPos2.getX() + d, (double) blockPos2.getY() + m, (double) blockPos2.getZ() + e, 0.0, 0.0, 0.0);
				}
				worldRenderer.setField_20793(worldRenderer.getField_20793() + 1);
				if (blockPos2 != null && random.nextInt(3) < worldRenderer.getField_20793()) {
					worldRenderer.setField_20793(0);
					if (blockPos2.getY() > blockPos.getY() + 1 && worldView.getTopPosition(Heightmap.Type.MOTION_BLOCKING, blockPos).getY() > MathHelper.floor(blockPos.getY())) {
						client.world.playSound((BlockPos) blockPos2, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1f, 0.5f, false);
					} else {
						client.world.playSound((BlockPos) blockPos2, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2f, 1.0f, false);
					}
				}
		info.cancel();
	}
}