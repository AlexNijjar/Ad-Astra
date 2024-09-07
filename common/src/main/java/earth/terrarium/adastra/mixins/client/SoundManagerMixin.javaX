package earth.terrarium.adastra.mixins.client;

import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.client.utils.ClientData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public abstract class SoundManagerMixin {

    @Shadow
    @Final
    private SoundEngine soundEngine;

    @Inject(method = "play", at = @At("HEAD"), cancellable = true)
    private void adastra$play(SoundInstance sound, CallbackInfo ci) {
        if (adastra$play(sound, 0)) {
            ci.cancel();
        }
    }

    @Inject(method = "playDelayed", at = @At("HEAD"), cancellable = true)
    private void adastra$playDelayed(SoundInstance sound, int delay, CallbackInfo ci) {
        if (adastra$play(sound, delay)) {
            ci.cancel();
        }
    }

    @Unique
    private boolean adastra$play(SoundInstance sound, int delay) {
        var level = Minecraft.getInstance().level;
        if (level == null) return false;
        if (!AdAstraConfigClient.spaceMuffler) return false;
        SoundSource source = sound.getSource();
        if (source == SoundSource.MASTER) return false;
        if (!PlanetApi.API.isSpace(level)) return false;
        if (sound instanceof TickableSoundInstance) return false;
        if (source != SoundSource.MUSIC && ClientData.getLocalData() != null && ClientData.getLocalData().oxygen()) {
            return false;
        }

        Minecraft.getInstance().execute(() -> {
            float volume = source == SoundSource.MUSIC || source == SoundSource.RECORDS ? 1 : 0.1f;
            SoundInstance newSound = new SimpleSoundInstance(sound.getLocation(), source,
                volume, 0.1f, level.random,
                sound.isLooping(), delay,
                sound.getAttenuation(), sound.getX(),
                sound.getY(), sound.getZ(), sound.isRelative()
            );

            if (delay == 0) {
                soundEngine.play(newSound);
            } else {
                soundEngine.playDelayed(newSound, delay);
            }
        });
        return true;
    }
}
