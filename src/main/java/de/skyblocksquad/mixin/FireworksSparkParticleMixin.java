package de.skyblocksquad.mixin;

import de.skyblocksquad.options.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.particle.FireworksSparkParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(FireworksSparkParticle.FireworkParticle.class)
public class FireworksSparkParticleMixin {

    @ModifyArgs(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/FireworksSparkParticle$FireworkParticle;explodeBall(DILit/unimi/dsi/fastutil/ints/IntList;Lit/unimi/dsi/fastutil/ints/IntList;ZZ)V", ordinal = 0)
    )
    private void overrideSmallExplosionBall(Args args) {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        if (!config.smallExplosionBallOverrideState) return;

        args.set(0, config.smallExplosionBallSizeOverride);
        args.set(1, config.smallExplosionBallAmountOverride);
    }

    @ModifyArgs(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/FireworksSparkParticle$FireworkParticle;explodeBall(DILit/unimi/dsi/fastutil/ints/IntList;Lit/unimi/dsi/fastutil/ints/IntList;ZZ)V", ordinal = 1)
    )
    private void overrideLargeExplosionBall(Args args) {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        if (!config.largeExplosionBallOverrideState) return;

        args.set(0, config.largeExplosionBallSizeOverride);
        args.set(1, config.largeExplosionBallAmountOverride);
    }

}