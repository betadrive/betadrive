package dev.matthy.betadrive.client;

import dev.matthy.betadrive.hud.MeterHUD;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.util.Identifier;

public class BetadriveClient implements ClientModInitializer {

    public static MeterHUD mainHud;

    @Override
    public void onInitializeClient() {
        HudElementRegistry.attachElementAfter(VanillaHudElements.CHAT, Identifier.of("betadrive", "after_chat"), MeterHUD::render);
    }
}
