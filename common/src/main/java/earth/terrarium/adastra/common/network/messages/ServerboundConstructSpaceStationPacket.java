package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.common.compat.cadmus.CadmusIntegration;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.handlers.LaunchingDimensionHandler;
import earth.terrarium.adastra.common.handlers.SpaceStationHandler;
import earth.terrarium.adastra.common.network.CodecPacketType;
import earth.terrarium.adastra.common.recipes.SpaceStationRecipe;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public record ServerboundConstructSpaceStationPacket(
    ResourceKey<Level> dimension, Component name) implements Packet<ServerboundConstructSpaceStationPacket> {

    public static final ServerboundPacketType<ServerboundConstructSpaceStationPacket> TYPE = new Type();

    @Override
    public PacketType<ServerboundConstructSpaceStationPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ServerboundConstructSpaceStationPacket> implements ServerboundPacketType<ServerboundConstructSpaceStationPacket> {

        public Type() {
            super(
                ServerboundConstructSpaceStationPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "construct_space_station"),
                ObjectByteCodec.create(
                    ExtraByteCodecs.DIMENSION.fieldOf(ServerboundConstructSpaceStationPacket::dimension),
                    ExtraByteCodecs.COMPONENT.fieldOf(ServerboundConstructSpaceStationPacket::name),
                    ServerboundConstructSpaceStationPacket::new
                )
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundConstructSpaceStationPacket packet) {
            return player -> {
                if (!(player.level() instanceof ServerLevel serverLevel)) return;
                if (!(player instanceof ServerPlayer serverPlayer)) return;
                var planet = PlanetApi.API.getPlanet(packet.dimension);
                if (planet == null) return;

                ServerLevel targetLevel = serverLevel.getServer().getLevel(planet.orbitIfPresent());
                if (targetLevel == null) return;

                // Shouldn't be able to construct space stations outside of orbit.
                if (!PlanetApi.API.isSpace(targetLevel)) return;
                if (!ModUtils.canTeleportToPlanet(player, planet)) return;

                if (CadmusIntegration.cadmusLoaded() && CadmusIntegration.isClaimed(targetLevel, player.chunkPosition())) {
                    return;
                }

                SpaceStationRecipe recipe = SpaceStationRecipe.getSpaceStation(targetLevel, targetLevel.dimension()).orElse(null);
                if (recipe == null) return;
                if (SpaceStationHandler.isInSpaceStation(serverPlayer, targetLevel)) return;
                if (!SpaceStationRecipe.hasIngredients(serverPlayer, targetLevel, recipe)) return;
                SpaceStationRecipe.consumeIngredients(serverPlayer, targetLevel);

                var pos = player.chunkPosition();

                // Construct space station structure from structure nbt file
                StructureTemplate structure = targetLevel.getStructureManager().getOrCreate(recipe.structure());
                BlockPos stationPos = BlockPos.containing((pos.getMiddleBlockX() - (structure.getSize().getX() / 2.0f)), AdAstraConfig.atmosphereLeave / 2.0F, (pos.getMiddleBlockZ() - (structure.getSize().getZ() / 2.0f)));
                targetLevel.getChunkSource().addRegionTicket(TicketType.PORTAL, new ChunkPos(stationPos), 1, stationPos);
                structure.placeInWorld(targetLevel, stationPos, stationPos, new StructurePlaceSettings(), targetLevel.random, 2);

                SpaceStationHandler.constructSpaceStation(serverPlayer, targetLevel, packet.name);

                LaunchingDimensionHandler.addSpawnLocation(player, serverLevel);
                BlockPos middleBlockPosition = pos.getMiddleBlockPosition(AdAstraConfig.atmosphereLeave);
                ModUtils.land(serverPlayer, targetLevel, new Vec3(middleBlockPosition.getX() - 0.5f, (middleBlockPosition.getY() / 2.0F) + 30.0F, middleBlockPosition.getZ() - 0.5f));

                // Cadmus claiming 3x3 chunks
                if (CadmusIntegration.cadmusLoaded()) {
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            CadmusIntegration.claim(serverPlayer, new ChunkPos(pos.x + i, pos.z + j));
                        }
                    }
                }
            };
        }
    }
}