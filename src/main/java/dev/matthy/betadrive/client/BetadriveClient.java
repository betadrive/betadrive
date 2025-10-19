package dev.matthy.betadrive.client;

import dev.matthy.betadrive.hud.MeterHUD;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class BetadriveClient implements ClientModInitializer {

    public static UUID playerUUID;
    @Override
    public void onInitializeClient() {
        HudElementRegistry.attachElementAfter(VanillaHudElements.CHAT, Identifier.of("betadrive", "after_chat"), MeterHUD::render);
    }
}
