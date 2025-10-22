package dev.matthy.betadrive.hud.texts;

import dev.matthy.betadrive.hud.HUDText;

public class HungerText extends HUDText {
    public HungerText() {
        super("HGR", (player, world) -> String.valueOf(player.getHungerManager().getFoodLevel()));
    }
}
