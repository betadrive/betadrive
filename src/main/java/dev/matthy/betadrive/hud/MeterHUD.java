package dev.matthy.betadrive.hud;

import dev.matthy.betadrive.Betadrive;
import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.config.HudConfig;
import dev.matthy.betadrive.hud.texts.BatteryText;
import dev.matthy.betadrive.hud.texts.LevelText;
import dev.matthy.betadrive.hud.texts.SpeedText;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Util;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class MeterHUD extends HUDStat {
    /*private boolean ic; // is converting
    private final MinecraftClient client; // = MinecraftClient.getInstance()
    private final World world; // approx. = MinecraftClient.getInstance().world
    private final int initial; // start time for converting
    public boolean cleared = false; // blue pill used? set with .clear()
    public boolean finishedConverting = false; // are we done with the cliché animation?
    public UUID playerName; // user's UUID*/
    public static boolean clearAnimation = false;
    public static long startTime = 0;
    static boolean getStartTimeFlag = true;
    public static boolean transformationAnimation(DrawContext drawContext, RenderTickCounter renderTickCounter) { // text popup on screen that appears when taking red pill/converting to android
        if(getStartTimeFlag) {
            startTime = Util.getMeasuringTimeMs()/50;
            getStartTimeFlag = false;
        }
        int cyclesDone = (int) (Util.getMeasuringTimeMs()/50 - startTime);
        if(!Betadrive.isConverting || cyclesDone >= 400) return false;
        if(cyclesDone > 0  && cyclesDone < 160) { // jank method for delays, each value *should* correspond to ticks?
            printText("BIOLOGICAL PRESENCE DETECTED.", 36, 50, 0xFFA9E2FB, drawContext);
            printText("RELEASING NANOBOTS TO CONVERT BIOLOGICAL PRESENCE", 36, 60, 0xFFA9E2FB, drawContext);
            printText("TO ANDROID.", 36, 70, 0xFFA9E2FB, drawContext);
        } else if(cyclesDone > 160 && cyclesDone < 280) {
            printText("CONVERTING ALL BODY PARTS...", 36, 50, 0xFFA9E2FB, drawContext);
            printText("...done.", 36, 60, 0xFFA9E2FB, drawContext);
        } else if(cyclesDone > 280 && cyclesDone < 340) {
            printText("CONVERSION PROCESSING", 36, 50, 0xFFA9E2FB, drawContext);
            printText("BRAIN CONVERTING TO PROCESSING UNIT..", 36, 60, 0xFFA9E2FB, drawContext);
        } else if(cyclesDone > 340 && cyclesDone < 360) {
            printText("...done.", 36, 50, 0xFFA9E2FB, drawContext);
            printText("Body conversion complete.", 36, 60, 0xFFA9E2FB, drawContext);
            Betadrive.isConverting=false; // disable isConverting
            Betadrive.isAndroid=true;
        } else if(cyclesDone > 360) {
            getStartTimeFlag = true;
            startTime = 0;
        }
        return true;
    }


    public static void hudAnimation(DrawContext drawContext) { // when you *are* an android, and we're just rendering the HUD
        if(!Betadrive.isAndroid || clearAnimation || Betadrive.isConverting) return; // checks to make sure you *are* an android, haven't taken the blue pill, and aren't converting)
        HudConfig cfg = BetadriveConfig.getAndroidPlayerConfig(BetadriveClient.playerUUID).hudSettings;
        ArrayList<HUDText> texts = new ArrayList<>();

        if(cfg.enableSpeedText) texts.add(new SpeedText());
        if(cfg.enableLevelText) texts.add(new LevelText());
        if(cfg.enableBatteryText) texts.add(new BatteryText());

        if(texts.isEmpty()) return;

        String hudText = HUDText.build(texts);
        printText(hudText, 12, 12, 0xFFA9E2FB, drawContext);
    }
    public static void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if(BetadriveClient.playerUUID == null) BetadriveClient.playerUUID = MinecraftClient.getInstance().player.getUuid();
        if(Betadrive.isConverting) transformationAnimation(drawContext, renderTickCounter);
        hudAnimation(drawContext);
    }
}