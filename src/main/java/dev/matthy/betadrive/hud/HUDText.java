package dev.matthy.betadrive.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;


public class HUDText {
    private final String statLabel;
    private final BiFunction<PlayerEntity, World,String> function;
    public HUDText(String label, BiFunction<PlayerEntity, World,String> value) {
        statLabel = label; // Used to label the stat, e.g. "HP" (health percent), "HGR" (hunger), "LVL" (XP level) as well as being used in the JSON config to check if this text is enabled for a player
        function = value; // Function that takes the PlayerEntity and World and provides the value to use in the rendered text
    }
    public String getLabel() { // Get just the label for storing if it's enabled in the config
        return statLabel;
    }

    @Override
    public String toString() { // Convert 1 HUDText to a string used for HUDText.build(...)
        return statLabel + "=" + function.apply(MinecraftClient.getInstance().player, MinecraftClient.getInstance().world); // KEY=VALUE, e.g. "HP=100%" @ 20 health, "XP=30" @ 30 levels, "BAT=50%" at half of max battery charge
    }
    public static String build(HUDText... args) { // Join together all the enabled texts with 2 spaces as the delimiter, surround with []
        return "["+String.join("  ", Arrays.stream(args).map(HUDText::toString).toList())+"]";
    }
    public static String build(ArrayList<HUDText> args) { // Join together all the enabled texts with 2 spaces as the delimiter, surround with []
        return "["+String.join("  ", args.stream().map(HUDText::toString).toList())+"]";
    }
}
