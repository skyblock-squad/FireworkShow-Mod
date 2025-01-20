package de.skyblocksquad.options;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import de.skyblocksquad.mixin.ParticleManagerAccessor;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ModMenuConfig implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.translatable("title.firework-show.config"));

            ConfigCategory overrides = builder.getOrCreateCategory(Text.translatable("category.firework-show.vanilla_overrides"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            overrides.addEntry(entryBuilder.startIntField(Text.translatable("key.firework-show.particle_limit_override"),
                            config.particleLimitOverride)
                    .setDefaultValue(ParticleManagerAccessor.getMaxParticleCount())
                    .setTooltip(Text.translatable("option.firework-show.particle_limit_override.tooltip"))
                    .setSaveConsumer(newValue -> {
                        config.particleLimitOverride = newValue;
                        AutoConfig.getConfigHolder(ModConfig.class).save();
                        ((ParticleManagerAccessor) MinecraftClient.getInstance().particleManager).getParticles().clear();
                    })
                    .build());

            SubCategoryBuilder smallExplosionBallOverrides = entryBuilder.startSubCategory(Text.translatable("key.firework-show.small_explosion_ball.overrides"));

            smallExplosionBallOverrides.add(entryBuilder.startDoubleField(Text.translatable("option.firework-show.small_explosion_ball_size"),
                            config.smallExplosionBallSizeOverride)
                    .setDefaultValue(0.25F)
                    .setTooltip(Text.translatable("option.firework-show.small_explosion_ball_size.tooltip"))
                    .setSaveConsumer(newValue -> {
                        config.smallExplosionBallSizeOverride = newValue;
                        AutoConfig.getConfigHolder(ModConfig.class).save();
                    })
                    .build());

            smallExplosionBallOverrides.add(entryBuilder.startIntField(Text.translatable("option.firework-show.small_explosion_ball_amount"),
                            config.smallExplosionBallAmountOverride)
                    .setDefaultValue(2)
                    .setTooltip(Text.translatable("option.firework-show.small_explosion_ball_amount.tooltip"))
                    .setSaveConsumer(newValue -> {
                        config.smallExplosionBallAmountOverride = newValue;
                        AutoConfig.getConfigHolder(ModConfig.class).save();
                    })
                    .build());

            overrides.addEntry(smallExplosionBallOverrides.build());

            SubCategoryBuilder largeExplosionBallOverrides = entryBuilder.startSubCategory(Text.translatable("key.firework-show.large_explosion_ball.overrides"));

            largeExplosionBallOverrides.add(entryBuilder.startDoubleField(Text.translatable("option.firework-show.large_explosion_ball_size"),
                            config.largeExplosionBallSizeOverride)
                    .setDefaultValue(0.5F)
                    .setTooltip(Text.translatable("option.firework-show.large_explosion_ball_size.tooltip"))
                    .setSaveConsumer(newValue -> {
                        config.largeExplosionBallSizeOverride = newValue;
                        AutoConfig.getConfigHolder(ModConfig.class).save();
                    })
                    .build());

            largeExplosionBallOverrides.add(entryBuilder.startIntField(Text.translatable("option.firework-show.large_explosion_ball_amount"),
                            config.largeExplosionBallAmountOverride)
                    .setDefaultValue(4)
                    .setTooltip(Text.translatable("option.firework-show.large_explosion_ball_amount.tooltip"))
                    .setSaveConsumer(newValue -> {
                        config.largeExplosionBallAmountOverride = newValue;
                        AutoConfig.getConfigHolder(ModConfig.class).save();
                    })
                    .build());

            overrides.addEntry(largeExplosionBallOverrides.build());

            return builder.build();
        };
    }

}