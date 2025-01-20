package de.skyblocksquad;

import de.skyblocksquad.mixin.ParticleManagerAccessor;
import de.skyblocksquad.options.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class Keybinds {

    private static final KeyBinding particleLimitOverrideToggle = new KeyBinding("key.firework-show.particle_limit_override",
            GLFW.GLFW_KEY_UNKNOWN, "category.firework-show.vanilla_overrides");
    private static final KeyBinding smallExplosionBallSizeOverrideToggle = new KeyBinding("key.firework-show.small_explosion_ball.overrides",
            GLFW.GLFW_KEY_UNKNOWN, "category.firework-show.vanilla_overrides");
    private static final KeyBinding largeExplosionBallSizeOverrideToggle = new KeyBinding("key.firework-show.large_explosion_ball.overrides",
            GLFW.GLFW_KEY_UNKNOWN, "category.firework-show.vanilla_overrides");

    public static void registerKeybinds() {
        KeyBindingHelper.registerKeyBinding(particleLimitOverrideToggle);
        KeyBindingHelper.registerKeyBinding(smallExplosionBallSizeOverrideToggle);
        KeyBindingHelper.registerKeyBinding(largeExplosionBallSizeOverrideToggle);
    }

    public static void checkKeybinds(MinecraftClient client) {
        if (client.player == null) return;

        handleKeybind(client, particleLimitOverrideToggle,
                config -> config.particleLimitOverrideState,
                (config, newState) -> {
                    config.particleLimitOverrideState = newState;
                    ((ParticleManagerAccessor) MinecraftClient.getInstance().particleManager).getParticles().clear();
                });

        handleKeybind(client, smallExplosionBallSizeOverrideToggle,
                config -> config.smallExplosionBallOverrideState,
                (config, newState) -> config.smallExplosionBallOverrideState = newState);

        handleKeybind(client, largeExplosionBallSizeOverrideToggle,
                config -> config.largeExplosionBallOverrideState,
                (config, newState) -> config.largeExplosionBallOverrideState = newState);

    }

    private static void handleKeybind(MinecraftClient client, KeyBinding keyBinding,
                                      Function<ModConfig, Boolean> stateGetter, BiConsumer<ModConfig, Boolean> stateSetter) {
        while (keyBinding.wasPressed()) {
            ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

            boolean newState = !stateGetter.apply(config);
            stateSetter.accept(config, newState);
            AutoConfig.getConfigHolder(ModConfig.class).save();

            //noinspection DataFlowIssue
            client.player.sendMessage(
                    Text.translatable("keybind.firework-show.toggle.format",
                            Text.translatable(keyBinding.getTranslationKey()),
                            Text.translatable(newState ? "keybind.firework-show.toggle.enabled" : "keybind.firework-show.toggle.disabled")
                                    .formatted(newState ? Formatting.GREEN : Formatting.RED)),
                    true
            );
        }
    }

}