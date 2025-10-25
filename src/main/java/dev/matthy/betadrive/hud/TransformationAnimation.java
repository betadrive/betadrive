package dev.matthy.betadrive.hud;

import dev.matthy.betadrive.client.BetadriveClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.util.HashMap;

import static dev.matthy.betadrive.hud.HUDStat.printText;

/* from en_us lang:
  "hud.betadrive.transformation_dialog_1_1": "Biological presence detected... (%s%%)",
  "hud.betadrive.transformation_dialog_1_2": "releasing nanobots. (%s%%)",
  "hud.betadrive.transformation_dialog_2_1": "Converting neurons... (%s%%)",
  "hud.betadrive.transformation_dialog_done": "...done. (%s%%)",
  "hud.betadrive.transformation_dialog_3_1": "Reconfiguring neural pathways... (%s%%)",
  "hud.betadrive.transformation_dialog_4_1": "Finalizing transformation... (%s%%)"
 */
@Environment(EnvType.CLIENT)
public class TransformationAnimation {
    public static long startTime = 0; // Start of in-game tick counter relative to when the animation started
    public static double animationSpeedMultiplier = 20; // Duration of the entire animation in seconds
    public static HashMap<Integer, String> animationCues = new HashMap<>()
    {{
        put(0, "hud.betadrive.transformation_dialog_1_1");
        put(60, "hud.betadrive.transformation_dialog_1_2");
        put(140, "hud.betadrive.transformation_dialog_2_1");
        put(200, "hud.betadrive.transformation_dialog_done");
        put(220, "hud.betadrive.transformation_dialog_3_1");
        put(300, "hud.betadrive.transformation_dialog_done");
        put(320, "hud.betadrive.transformation_dialog_4_1");
        put(380, "hud.betadrive.transformation_dialog_4_1");
    }};
    public static int[] animationStarts = {0, 60, 140, 200, 220, 300, 320, 380};
    public static boolean getStartTimeFlag = true; // See "if(getStartTimeFlag) { ... }"
    public static int closestUnder(int num) {
        int closestUnderValue = animationStarts[0];
        for(int value : animationStarts) {
            if(value > closestUnderValue && value < num) closestUnderValue = value;
        }
        return closestUnderValue;
    }
//    public static int closestAbove(int num) {
//        int closestAboveValue = animationStarts[0];
//        for(int value : animationStarts) {
//            if(value < closestAboveValue && value > num) closestAboveValue = value;
//        }
//        return closestAboveValue;
//    }

    public static void transformationAnimation(DrawContext drawContext, RenderTickCounter renderTickCounter) { // text popup on screen that appears when taking red pill/converting to android
        if(getStartTimeFlag) { // If we haven't gotten the start time of the animation, set it and don't run this again for this animation
            startTime = Util.getMeasuringTimeMs()/50;
            getStartTimeFlag = false;
        }
        int cyclesDone = (int) (Util.getMeasuringTimeMs()/50 - startTime); // Number of ticks since animation started
        if(!BetadriveClient.isConverting || cyclesDone >= animationSpeedMultiplier*21) return; // If we're actually not converting or the animation is done, then exit early
        String langKey = animationCues.get(closestUnder(cyclesDone));
        Text translated = net.minecraft.text.Text.translatable(langKey, cyclesDone/animationSpeedMultiplier*5);
        printText(translated.getString(), 36, 50, 0xFFA9E2FB, drawContext);
//        if(cyclesDone > 0  && cyclesDone < animationSpeedMultiplier*8) { // 1st text label (0-8 seconds)
//            printText("BIOLOGICAL PRESENCE DETECTED.", 36, 50, 0xFFA9E2FB, drawContext);
//            printText("RELEASING NANOBOTS TO CONVERT BIOLOGICAL PRESENCE", 36, 60, 0xFFA9E2FB, drawContext);
//            printText("TO ANDROID.", 36, 70, 0xFFA9E2FB, drawContext);
//        } else if(cyclesDone > animationSpeedMultiplier*8 && cyclesDone < animationSpeedMultiplier*14) { // 2nd text label (8-14 seconds)
//            printText("CONVERTING ALL BODY PARTS...", 36, 50, 0xFFA9E2FB, drawContext);
//            printText("...done.", 36, 60, 0xFFA9E2FB, drawContext);
//        } else if(cyclesDone > animationSpeedMultiplier*14 && cyclesDone < animationSpeedMultiplier*17) { // 3rd text label (14-17 seconds)
//            printText("CONVERSION PROCESSING", 36, 50, 0xFFA9E2FB, drawContext);
//            printText("BRAIN CONVERTING TO PROCESSING UNIT..", 36, 60, 0xFFA9E2FB, drawContext);
//        } else if(cyclesDone > animationSpeedMultiplier*17 && cyclesDone < animationSpeedMultiplier*20) { // 4th text label (17-20 seconds)
//            printText("...done.", 36, 50, 0xFFA9E2FB, drawContext);
//            printText("Body conversion complete.", 36, 60, 0xFFA9E2FB, drawContext);
        if(cyclesDone >= animationSpeedMultiplier*20) { // Reset for next red pill animation if needed
            BetadriveClient.isConverting=false; // disable isConverting
            BetadriveClient.isAndroid=true; // enable android flag for client
            getStartTimeFlag = true;
            startTime = 0;
        }
    }
}
