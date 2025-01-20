package de.skyblocksquad.mixin;

import de.skyblocksquad.options.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

    @ModifyArg(
            method = "method_18125(Lnet/minecraft/client/particle/ParticleTextureSheet;)Ljava/util/Queue;",
            at = @At(value = "INVOKE", target = "Lcom/google/common/collect/EvictingQueue;create(I)Lcom/google/common/collect/EvictingQueue;")
    )
    private static int overrideMaxParticleCount(int maxSize) {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        if (config.particleLimitOverrideState)
            return config.particleLimitOverride;

        return maxSize;
    }

}