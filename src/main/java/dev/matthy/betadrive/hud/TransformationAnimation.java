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

@Environment(EnvType.CLIENT)
public class TransformationAnimation {
    public static long startTime = 0; // Start of in-game tick counter relative to when the animation started
    public static double animationSpeedMultiplier = 20; // Duration of the entire animation in seconds
    public static HashMap<Integer, String> animationCues = new HashMap<>() // K = integer start time in ticks, V = lang key for i18n
    {{ // Store default values:
        put(0, "hud.betadrive.transformation_dialog_1_1"); // 0-3 s
        put(60, "hud.betadrive.transformation_dialog_1_2"); // 3-7 s
        put(140, "hud.betadrive.transformation_dialog_2_1"); // 7-10 s
        put(200, "hud.betadrive.transformation_dialog_done"); // 10-11 s
        put(220, "hud.betadrive.transformation_dialog_3_1"); // 11-15 s
        put(300, "hud.betadrive.transformation_dialog_done"); // 15-16 s
        put(320, "hud.betadrive.transformation_dialog_4_1"); // 16-19 s
        put(380, "hud.betadrive.transformation_dialog_4_1"); // 19-20 s
    }};
    public static int[] animationStarts = animationCues.keySet().stream().mapToInt(Integer::intValue).toArray(); // This line of methods just converts the keys of animationCues to an int array. See: https://stackoverflow.com/a/37364352
    public static boolean getStartTimeFlag = true; // See "if(getStartTimeFlag) { ... }"
    public static int closestUnder(int num) { // Closest value to num that is below num listed in animationStarts. Used to get which animation keyframe we're on
        int closestUnderValue = animationStarts[0];
        for(int value : animationStarts)
            if(value > closestUnderValue && value < num) closestUnderValue = value;
        return closestUnderValue;
    }
    public static void transformationAnimation(DrawContext drawContext, RenderTickCounter renderTickCounter) { // text popup on screen that appears when taking red pill/converting to android
        if(getStartTimeFlag) { // If we haven't gotten the start time of the animation, set it and don't run this again for this animation
            startTime = Util.getMeasuringTimeMs()/50; // Get in ticks
            getStartTimeFlag = false;
        }
        int cyclesDone = (int) (Util.getMeasuringTimeMs()/50 - startTime); // Number of ticks since animation started
        if(!BetadriveClient.isConverting || cyclesDone >= animationSpeedMultiplier*21) return; // If we're actually not converting or the animation is done, then exit early
        String langKey = animationCues.get(closestUnder(cyclesDone));
        Text translated = net.minecraft.text.Text.translatable(langKey, cyclesDone/animationSpeedMultiplier*5);
        printText(translated.getString(), 36, 50, 0xFFA9E2FB, drawContext);
        if(cyclesDone >= animationSpeedMultiplier*20) { // Reset for next red pill animation if needed
            BetadriveClient.isConverting=false; // disable isConverting
            BetadriveClient.isAndroid=true; // enable android flag for client
            getStartTimeFlag = true; // Reset the getStartTimeFlag for if user takes blue pill then red pill again
            startTime = 0; // Again, just reset the start time if the user takes blue pill then red pill again. This is likely unnecessary since getStartTimeFlag will get the new time before startTime has a chance to be used w/o being reset
        }
    }
}
