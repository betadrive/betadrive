package dev.matthy.betadrive.hud.texts;

import dev.matthy.betadrive.hud.HUDText;

public class HungerText extends HUDText {
    public HungerText() { // Hunger percent based on max default hunger (0<=getFoodLevel()<=20)
        super("HGR", "hunger", (player, world) -> player.getHungerManager().getFoodLevel() * 5 + "%");
    }
}
