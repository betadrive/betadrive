package dev.matthy.betadrive.hud;

import dev.matthy.betadrive.config.HUDConfig;

public class HUDTexts {
    public static void register(HUDText text) {
        HUDConfig.possibleStats.put(text.getLabel(), text);
    }
    public static void register(HUDText... texts) {
        for(HUDText text : texts) register(text);
    }

    public static void init() {
        for(HUDText text : HUDConfig.possibleStats.values()) text.registerItem();
    }
}
