package dev.matthy.betadrive.hud;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.config.HUDConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Util;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class MeterHUD extends HUDStat {
    public static boolean clearAnimation = false; // Enabled only when Blue Pill is consumed thus far. Clears the HUD and disables it until the player re-converts into an android.
    public static long startTime = 0; // Start of in-game tick counter relative to when the animation started
    public static double animationSpeedMultiplier = 20; // Duration of the entire animation in seconds
    static boolean getStartTimeFlag = true; // See "if(getStartTimeFlag) { ... }"
    private static ArrayList<HUDText> texts = new ArrayList<>();

    public static void transformationAnimation(DrawContext drawContext, RenderTickCounter renderTickCounter) { // text popup on screen that appears when taking red pill/converting to android
        if(getStartTimeFlag) { // If we haven't gotten the start time of the animation, set it and don't run this again for this animation
            startTime = Util.getMeasuringTimeMs()/50;
            getStartTimeFlag = false;
        }
        int cyclesDone = (int) (Util.getMeasuringTimeMs()/50 - startTime); // Number of ticks since animation started
        if(!BetadriveClient.isConverting || cyclesDone >= animationSpeedMultiplier*21) return; // If we're actually not converting or the animation is done, then exit early
        if(cyclesDone > 0  && cyclesDone < animationSpeedMultiplier*8) { // 1st text label (0-8 seconds)
            printText("BIOLOGICAL PRESENCE DETECTED.", 36, 50, 0xFFA9E2FB, drawContext);
            printText("RELEASING NANOBOTS TO CONVERT BIOLOGICAL PRESENCE", 36, 60, 0xFFA9E2FB, drawContext);
            printText("TO ANDROID.", 36, 70, 0xFFA9E2FB, drawContext);
        } else if(cyclesDone > animationSpeedMultiplier*8 && cyclesDone < animationSpeedMultiplier*14) { // 2nd text label (8-14 seconds)
            printText("CONVERTING ALL BODY PARTS...", 36, 50, 0xFFA9E2FB, drawContext);
            printText("...done.", 36, 60, 0xFFA9E2FB, drawContext);
        } else if(cyclesDone > animationSpeedMultiplier*14 && cyclesDone < animationSpeedMultiplier*17) { // 3rd text label (14-17 seconds)
            printText("CONVERSION PROCESSING", 36, 50, 0xFFA9E2FB, drawContext);
            printText("BRAIN CONVERTING TO PROCESSING UNIT..", 36, 60, 0xFFA9E2FB, drawContext);
        } else if(cyclesDone > animationSpeedMultiplier*17 && cyclesDone < animationSpeedMultiplier*20) { // 4th text label (17-20 seconds)
            printText("...done.", 36, 50, 0xFFA9E2FB, drawContext);
            printText("Body conversion complete.", 36, 60, 0xFFA9E2FB, drawContext);
            BetadriveClient.isConverting=false; // disable isConverting
            BetadriveClient.isAndroid=true; // enable android flag for client
        } else if(cyclesDone >= animationSpeedMultiplier*20) { // Reset for next red pill animation if needed
            getStartTimeFlag = true;
            startTime = 0;
        }
    }

    public static void updateHudLabels() { // update which hud elements are enabled
        HUDConfig cfg = BetadriveConfig.getAndroidPlayerConfig(BetadriveClient.playerUUID).hudSettings;
        texts.clear();
        for(String key : HUDConfig.possibleStats.keySet()) {
            if(cfg.whichToEnable.getOrDefault(key, false)) texts.add(HUDConfig.possibleStats.get(key));
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