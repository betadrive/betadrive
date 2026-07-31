package dev.matthy.betadrive.client;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.config.HUDConfig;
import dev.matthy.betadrive.hud.MeterHUD;
import dev.matthy.betadrive.payload.HUDConfigPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionResult;

import java.util.LinkedHashMap;
import java.util.UUID;

public class BetadriveClient implements ClientModInitializer {

    public static UUID playerUUID;
    public static boolean isConverting = false;
    public static boolean isConvertingBack = false;
    public static boolean isAndroid = false;
    public static boolean isWaterResistant = false;
    public static double battery = 100; // Client's battery %
    public static LinkedHashMap<String, Boolean> whichToEnable = new LinkedHashMap<>() {{
        for(String key : HUDConfig.possibleStats.keySet()) put(key, false);
    }};
    @Override
    public void onInitializeClient() {
        HudElementRegistry.attachElementAfter(VanillaHudElements.CROSSHAIR, Identifier.fromNamespaceAndPath("betadrive", "after_crosshair"), MeterHUD::render);
        ClientPlayConnectionEvents.JOIN.register((handler, packetSender, minecraftClient) -> { // Get if our player is an android or not
            assert minecraftClient.player != null; // Shouldn't be necessary but safer this way
            isAndroid = BetadriveConfig.getAndroidStatus(minecraftClient.player.getUUID()); // Set the client flag for if we're an android to reduce reading the config file
            isWaterResistant = BetadriveConfig.getAndroidPlayerConfig(minecraftClient.player.getUUID()).waterResistant;
        });
        AttackEntityCallback.EVENT.register((player, w, h, e, hr) -> { // Lower battery by 0.01% (or if we're out of battery, lower hunger) if we attack something
            float result = (float) Math.max(0d, battery - 0.01d);
            battery = result;
            BetadriveConfig.setBattery(player.getUUID(), battery);
            if (result > 0) return InteractionResult.PASS;
            player.getFoodData().setFoodLevel(Math.max(player.getFoodData().getFoodLevel() - 1, 0));
            return InteractionResult.PASS;
        });

        PlayerBlockBreakEvents.AFTER.register((w, player, bp, bs, be) -> { // Lower battery by 0.01% (or if we're out of battery, lower hunger) if we break a block
            double result = Math.max(0d, battery - 0.01d);
            battery = result;
            BetadriveConfig.setBattery(player.getUUID(), battery);
            if (result > 0) return;
            player.getFoodData().setFoodLevel(Math.max(player.getFoodData().getFoodLevel() - 1, 0));
        });
        ClientPlayNetworking.registerGlobalReceiver(HUDConfigPayload.TYPE, (payload, context) -> {
            whichToEnable.put(payload.property(), payload.active());
        });

    }
}
