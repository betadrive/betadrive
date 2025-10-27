package dev.matthy.betadrive.hud;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.config.HUDConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;

import static dev.matthy.betadrive.hud.TransformationAnimation.transformationAnimation;

@Environment(EnvType.CLIENT)
public class MeterHUD extends HUDStat {
    public static boolean clearAnimation = false; // Enabled only when Blue Pill is consumed thus far. Clears the HUD and disables it until the player re-converts into an android.
    public static PlayerEntity player = MinecraftClient.getInstance().player; // Clientside player
    private static final ArrayList<HUDText> texts = new ArrayList<>(); // List of enabled instances of HUDText, modified by updateHudLabels

    public static void updateHudLabels() { // update which hud elements are enabled
        java.util.LinkedHashMap<String, Boolean> whichToEnable = BetadriveConfig.getAndroidPlayerConfig(BetadriveClient.playerUUID).whichToEnable;
        texts.clear(); // Clear previous texts data
        for(String key : HUDConfig.possibleStats.keySet()) { // For each possible key, check if it's enabled in the config (default to false if not present in config). If so, add it to texts for rendering
            if(whichToEnable.getOrDefault(key, false)) texts.add(HUDConfig.possibleStats.get(key));
        }
    }
    public static void hudAnimation(DrawContext drawContext, boolean isGlitched) { // when you *are* an android, and we're just rendering the HUD
        if(!BetadriveClient.isAndroid || clearAnimation || BetadriveClient.isConverting) return; // checks to make sure you *are* an android, haven't taken the blue pill, and aren't converting)
        updateHudLabels(); // See MeterHUD.updateHudLabels
        if(texts.isEmpty()) return; // If no instances of HUDText are present/none enabled, don't render anything
        String hudText = HUDText.build(texts); // If there *are* any instances of HUDText in texts, then we can build them all together
        if(isGlitched) hudText = HUDText.randomString(hudText.length());
        printText(hudText, 12, 12, 0xFFA9E2FB, drawContext); // Finally, render the built/combined text
    }
    public static void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if(BetadriveClient.playerUUID == null) BetadriveClient.playerUUID = player.getUuid();
        if(BetadriveClient.isConverting) {
            transformationAnimation(drawContext, renderTickCounter); // If we're converting, render the transformation animation (see: TransformationAnimation) instead
            return; // Exit early to make sure android UI isn't also rendered too soon with the conversion animation
        }
        boolean glitchedFlag = false;
        if(player.isSubmergedInWater() && !BetadriveClient.isWaterResistant) glitchedFlag = true;
        hudAnimation(drawContext, glitchedFlag); // Finally, render the android-only HUD animation
    }
}