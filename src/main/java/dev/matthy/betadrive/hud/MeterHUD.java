package dev.matthy.betadrive.hud;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.config.HUDConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

import java.util.ArrayList;

import static dev.matthy.betadrive.hud.TransformationAnimation.transformationAnimation;

@Environment(EnvType.CLIENT)
public class MeterHUD extends HUDStat {
    public static boolean clearAnimation = false; // Enabled only when Blue Pill is consumed thus far. Clears the HUD and disables it until the player re-converts into an android.
    private static ArrayList<HUDText> texts = new ArrayList<>();

    public static void updateHudLabels() { // update which hud elements are enabled
        java.util.LinkedHashMap<String, Boolean> whichToEnable = BetadriveConfig.getAndroidPlayerConfig(BetadriveClient.playerUUID).whichToEnable;
        texts.clear();
        for(String key : HUDConfig.possibleStats.keySet()) {
            if(whichToEnable.getOrDefault(key, false)) texts.add(HUDConfig.possibleStats.get(key));
        }
    }
    public static void hudAnimation(DrawContext drawContext) { // when you *are* an android, and we're just rendering the HUD
        if(!BetadriveClient.isAndroid || clearAnimation || BetadriveClient.isConverting) return; // checks to make sure you *are* an android, haven't taken the blue pill, and aren't converting)
        updateHudLabels();
        if(texts.isEmpty()) return;
        String hudText = HUDText.build(texts);
        printText(hudText, 12, 12, 0xFFA9E2FB, drawContext);
    }
    public static void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if(BetadriveClient.playerUUID == null) BetadriveClient.playerUUID = MinecraftClient.getInstance().player.getUuid();
        if(BetadriveClient.isConverting) transformationAnimation(drawContext, renderTickCounter);
        hudAnimation(drawContext);
    }
}