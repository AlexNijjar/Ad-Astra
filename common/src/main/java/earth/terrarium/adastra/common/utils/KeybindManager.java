package earth.terrarium.adastra.common.utils;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class KeybindManager {

    private static final Map<UUID, KeybindManager> PLAYER_KEYS = new HashMap<>();

    private boolean jumpDown;
    private boolean sprintDown;
    private boolean sneakDown;
    private boolean suitFlightEnabled;
    private boolean suitHoverEnabled;

    public KeybindManager() {
    }

    public KeybindManager(boolean jumpDown, boolean sprintDown, boolean sneakDown, boolean suitFlightEnabled, boolean suitHoverEnabled) {
        this.jumpDown = jumpDown;
        this.sprintDown = sprintDown;
        this.sneakDown = sneakDown;
        this.suitFlightEnabled = suitFlightEnabled;
        this.suitHoverEnabled = suitHoverEnabled;
    }

    public static boolean jumpDown(Player player) {
        return jumpDown(player.getUUID());
    }

    public static boolean jumpDown(UUID player) {
        return PLAYER_KEYS.getOrDefault(player, new KeybindManager()).jumpDown;
    }

    public static boolean sprintDown(Player player) {
        return sprintDown(player.getUUID());
    }

    public static boolean sprintDown(UUID player) {
        return PLAYER_KEYS.getOrDefault(player, new KeybindManager()).sprintDown;
    }

    public static boolean sneakDown(Player player) {
        return sneakDown(player.getUUID());
    }

    public static boolean sneakDown(UUID player) {
        return PLAYER_KEYS.getOrDefault(player, new KeybindManager()).sneakDown;
    }

    public static boolean suitFlightEnabled(Player player) {
        return suitFlightEnabled(player.getUUID());
    }

    public static boolean suitFlightEnabled(UUID player) {
        return PLAYER_KEYS.getOrDefault(player, new KeybindManager()).suitFlightEnabled;
    }

    public static boolean suitHoverEnabled(Player player) {
        return suitHoverEnabled(player.getUUID());
    }

    public static boolean suitHoverEnabled(UUID player) {
        return PLAYER_KEYS.getOrDefault(player, new KeybindManager()).suitHoverEnabled;
    }

    public static void set(Player player, boolean jumpDown, boolean sprintDown, boolean sneakDown, boolean suitFlightEnabled, boolean suitHoverEnabled) {
        set(player.getUUID(), jumpDown, sprintDown, sneakDown, suitFlightEnabled, suitHoverEnabled);
    }

    public static void set(UUID player, boolean jumpDown, boolean sprintDown, boolean sneakDown, boolean suitFlightEnabled, boolean suitHoverEnabled) {
        PLAYER_KEYS.put(player, new KeybindManager(jumpDown, sprintDown, sneakDown, suitFlightEnabled, suitHoverEnabled));
    }
}