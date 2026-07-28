package dev.matthy.betadrive.hud;

import dev.matthy.betadrive.item.BetadriveItems;
import dev.matthy.betadrive.item.DisplayTogglerItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.BiFunction;


public class HUDText {
    private final String statLabel;
    private final String statName;
    private final String itemName;
    private final BiFunction<Player, Level, String> function; // Function that uses the player and the player's world for calculating any values. Client-side
    private static final Random RANDOM = new Random();
    public static final char[] randomLetterChoices = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()[]{}:;,../<>".toCharArray();
    public HUDText(String label, String name, BiFunction<Player, Level, String> value) {
        statLabel = label; // Used to label the stat, e.g. "HP" (health percent), "HGR" (hunger), "LVL" (XP level) as well as being used in the JSON config to check if this text is enabled for a player
        statName = name; // The full name of the stat (e.g. "battery" for the BAT label)
        itemName = statName+"_display_toggler"; // item name/ID
        function = value; // Function that takes the PlayerEntity and World and provides the value to use in the rendered text
    }
    public void registerItem() {
        BetadriveItems.register(itemName, (settings) -> new DisplayTogglerItem(settings, statLabel), new Item.Properties().stacksTo(1));
    }
    public static String randomString(int length) {
        StringBuilder randomStr = new StringBuilder();
        for(int i=0; i<length; i++) {
            randomStr.append(randomLetterChoices[RANDOM.nextInt(randomLetterChoices.length)]);
        }
        return randomStr.toString();
    }
    public String toGlitchedString() {
        return statLabel + "=" + randomString(3);
    }
    public String getLabel() { // Get just the label for storing if it's enabled in the config
        return statLabel;
    }

    @Override
    public String toString() { // Convert 1 HUDText to a string used for HUDText.build(...)
        return statLabel + "=" + function.apply(Minecraft.getInstance().player, Minecraft.getInstance().level); // KEY=VALUE, e.g. "HP=100%" @ 20 health, "XP=30" @ 30 levels, "BAT=50%" at half of max battery charge
    }
    public static String build(HUDText... args) { // Join together all the enabled texts with 2 spaces as the delimiter, surround with []
        return "["+String.join("  ", Arrays.stream(args).map(HUDText::toString).toList())+"]";
    }
    public static String build(ArrayList<HUDText> args) { // Join together all the enabled texts with 2 spaces as the delimiter, surround with []
        return "["+String.join("  ", args.stream().map(HUDText::toString).toList())+"]";
    }
    public static String buildGlitched(ArrayList<HUDText> args) { // Join together all the enabled texts with 2 spaces as the delimiter, surround with []
        return "["+String.join("  ", args.stream().map(HUDText::toGlitchedString).toList())+"]";
    }
}
