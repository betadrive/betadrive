package dev.matthy.betadrive.hud;

import dev.matthy.betadrive.Betadrive;
import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.config.HUDConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class MeterHUD extends HUDStat {
    public static boolean clearAnimation = false; // Enabled only when Blue Pill is consumed thus far. Clears the HUD and disables it until the player re-converts into an android.
    public static Player player = Minecraft.getInstance().player; // Clientside player
    public static final ArrayList<HUDText> texts = new ArrayList<>(); // List of enabled instances of HUDText, modified by updateHudLabels
    private static double prevRotationX = 0;
    private static double prevRotationY = 0;
    private static final double dragFactor = 5;
    private static final double speedFactor = 0.15;
    private static final double dampingFactor = 0.9;
    private static double swayX = 0;
    private static double swayY = 0;
    public static void updateHudLabels() { // update which hud elements are enabled
        texts.clear(); // Clear previous texts data
        for(String key : HUDConfig.possibleStats.keySet()) { // For each possible key, check if it's enabled in the config (default to false if not present in config). If so, add it to texts for rendering
            if(BetadriveClient.whichToEnable.getOrDefault(key, false)) texts.add(HUDConfig.possibleStats.get(key));
        }
    }
    public static void hudAnimation(GuiGraphicsExtractor drawContext, boolean isGlitched) { // when you *are* an android, and we're just rendering the HUD
        if(!BetadriveClient.isAndroid || clearAnimation || BetadriveClient.isConverting) return; // Checks to make sure you *are* an android, haven't taken the blue pill, and aren't converting
        updateHudLabels(); // See MeterHUD.updateHudLabels
        if(texts.isEmpty()) return; // If no instances of HUDText are present/none enabled, don't render anything
        String hudText = HUDText.build(texts); // If there *are* any instances of HUDText in texts, then we can build them all together
        if(isGlitched) hudText = HUDText.randomString(hudText.length()); // Random text if glitched
        textPrinting(drawContext, hudText);
    }
    public static void textPrinting(GuiGraphicsExtractor drawContext, String text) { // when you *are* an android, and we're just rendering the HUD
        // Move the HUD with camera rotation motion
        double xRot = player.getRotationVector().x;
        double yRot = player.getRotationVector().y;
        // Smooth animation for less choppiness
        swayX = Mth.clamp(Mth.lerp(speedFactor, swayX, swayX+((xRot - prevRotationX) * dragFactor))*dampingFactor, -12, 12);
        swayY = Mth.clamp(Mth.lerp(speedFactor, swayY, swayY+((yRot - prevRotationY) * dragFactor))*dampingFactor, -12, 12);
        prevRotationX = xRot;
        prevRotationY = yRot;
        printText(text, (int) (12-swayY), (int) (12-swayX), 0xFFA9E2FB, drawContext); // Finally, render the built/combined text
    }
    public static void updateIfNeeded() {
        if(Betadrive.updateAndroidStatus) {
            BetadriveClient.isAndroid = BetadriveConfig.getAndroidStatus(BetadriveClient.playerUUID);
            Betadrive.updateAndroidStatus = false;
//            BetadriveClient.isConverting = false;
            clearAnimation = false;
        }
        if(Betadrive.updateBattery) {
            BetadriveClient.battery = BetadriveConfig.getBattery(BetadriveClient.playerUUID);
            Betadrive.updateBattery = false;
        }
    }
    public static void render(GuiGraphicsExtractor drawContext, DeltaTracker renderTickCounter) {
        if(BetadriveClient.playerUUID == null) BetadriveClient.playerUUID = player.getUUID();
        updateIfNeeded();
        if(BetadriveClient.isConverting) {
            TransformationAnimation.transformationAnimation(drawContext); // If we're converting, render the transformation animation (see: TransformationAnimation) instead
            return; // Exit early to make sure android UI isn't also rendered too soon with the conversion animation
        }
        if(MeterHUD.clearAnimation || !BetadriveClient.isAndroid) { // Checks if (a) player turned back into human (i.e. via Blue Pill), or (b) player is not an android and is not converting.
            if(!BetadriveClient.isConvertingBack) return; // If we didn't just take a blue pill/convert back to a human flag wasn't set, then exit early
            TransformationAnimation.revertAnimation(drawContext); // Otherwise, run the transforming *back to human* animation
        }
        hudAnimation(drawContext, player.isUnderWater() && !BetadriveClient.isWaterResistant); // Finally, render the android-only HUD animation
    }
}