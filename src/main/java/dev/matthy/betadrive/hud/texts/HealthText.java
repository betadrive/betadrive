package dev.matthy.betadrive.hud.texts;

import dev.matthy.betadrive.hud.HUDText;

public class HealthText extends HUDText {
    public HealthText() { // Health based on percent of base max health (20 half-hearts/HP = 100%)
        super("HP", "health", (player, world) -> (int) player.getHealth() * 5 + "%");
    }
}
