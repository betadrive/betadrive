package dev.matthy.betadrive.client;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.hud.HUDTexts;
import dev.matthy.betadrive.hud.MeterHUD;
import dev.matthy.betadrive.hud.texts.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class BetadriveClient implements ClientModInitializer {

    public static UUID playerUUID;
    public static boolean isConverting = false;
    public static boolean isAndroid = false;
    public static boolean isWaterResistant = false;
    public static double battery = 100; // Client's battery %
    @Override
    public void onInitializeClient() {
        HudElementRegistry.attachElementAfter(VanillaHudElements.CHAT, Identifier.of("betadrive", "after_chat"), MeterHUD::render);
        ClientPlayConnectionEvents.JOIN.register((handler, packetSender, minecraftClient) -> { // Get if our player is an android or not
            assert minecraftClient.player != null; // Shouldn't be necessary but safer this way
            HUDTexts.register(new BatteryText(), new HealthText(), new HungerText(), new LevelText(), new SpeedText());
            isAndroid = BetadriveConfig.getAndroidStatus(minecraftClient.player.getUuid()); // Set the client flag for if we're an android to reduce reading the config file
            isWaterResistant = BetadriveConfig.getAndroidPlayerConfig(minecraftClient.player.getUuid()).waterResistant;
        });
        AttackEntityCallback.EVENT.register((player, w, h, e, hr) -> { // Lower battery by 0.01% (or if we're out of battery, lower hunger) if we attack something
            float result = (float) Math.max(0d, battery - 0.01d);
            battery = result;
            BetadriveConfig.setBattery(player.getUuid(), battery);
            if (result >= 0) return ActionResult.PASS;
            player.getHungerManager().setFoodLevel(Math.max(player.getHungerManager().getFoodLevel() - 1, 0));
            return ActionResult.PASS;
        });

        PlayerBlockBreakEvents.AFTER.register((w, player, bp, bs, be) -> {// Lower battery by 0.01% (or if we're out of battery, lower hunger) if we break a block
            double result = Math.max(0d, battery - 0.01d);
            battery = result;
            BetadriveConfig.setBattery(player.getUuid(), battery);
            if (result >= 0) return;
            player.getHungerManager().setFoodLevel(Math.max(player.getHungerManager().getFoodLevel() - 1, 0));
        });
    }
}
