package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.network.CodecPacketType;
import earth.terrarium.adastra.common.utils.KeybindManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public record ServerboundSyncKeybindPacket(boolean jumping, boolean sprinting, boolean sneaking,
                                           boolean suitFlightEnabled, boolean suitHoverEnabled) implements Packet<ServerboundSyncKeybindPacket> {

    public static final ServerboundPacketType<ServerboundSyncKeybindPacket> TYPE = new Type();

    @Override
    public PacketType<ServerboundSyncKeybindPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ServerboundSyncKeybindPacket> implements ServerboundPacketType<ServerboundSyncKeybindPacket> {

        public Type() {
            super(
                ServerboundSyncKeybindPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "sync_keybinds"),
                ObjectByteCodec.create(
                    ByteCodec.BOOLEAN.fieldOf(ServerboundSyncKeybindPacket::jumping),
                    ByteCodec.BOOLEAN.fieldOf(ServerboundSyncKeybindPacket::sprinting),
                    ByteCodec.BOOLEAN.fieldOf(ServerboundSyncKeybindPacket::sneaking),
                    ByteCodec.BOOLEAN.fieldOf(ServerboundSyncKeybindPacket::suitFlightEnabled),
                    ByteCodec.BOOLEAN.fieldOf(ServerboundSyncKeybindPacket::suitHoverEnabled),
                    ServerboundSyncKeybindPacket::new
                )
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundSyncKeybindPacket packet) {
            return player -> KeybindManager.set(player, packet.jumping(), packet.sprinting(), packet.sneaking, packet.suitFlightEnabled(), packet.suitHoverEnabled());
        }
    }
}
