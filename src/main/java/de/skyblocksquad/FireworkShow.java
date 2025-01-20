package de.skyblocksquad;

import de.skyblocksquad.options.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FireworkShow implements ClientModInitializer {

    public static final String MOD_ID = "sbs-firework-show";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);

        Keybinds.registerKeybinds();
        ClientTickEvents.END_CLIENT_TICK.register(Keybinds::checkKeybinds);
    }

}