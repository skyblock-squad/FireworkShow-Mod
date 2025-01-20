package de.skyblocksquad.options;

import de.skyblocksquad.FireworkShow;
import de.skyblocksquad.mixin.ParticleManagerAccessor;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = FireworkShow.MOD_ID)
public class ModConfig implements ConfigData {

    public int particleLimitOverride = ParticleManagerAccessor.getMaxParticleCount();
    public boolean particleLimitOverrideState = true;

    public double smallExplosionBallSizeOverride = 0.25F;
    public int smallExplosionBallAmountOverride = 2;
    public boolean smallExplosionBallOverrideState = true;

    public double largeExplosionBallSizeOverride = 0.5F;
    public int largeExplosionBallAmountOverride = 4;
    public boolean largeExplosionBallOverrideState = true;

}