package dev.matthy.betadrive.hud.texts;

import dev.matthy.betadrive.hud.HUDText;

public class HealthText extends HUDText {
    public HealthText() {
        super("HP", (player, world) -> (int) player.getHealth() * 5 + "%");
    }
}
