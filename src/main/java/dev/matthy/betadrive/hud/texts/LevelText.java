package dev.matthy.betadrive.hud.texts;

import dev.matthy.betadrive.hud.HUDText;

public class LevelText extends HUDText {
    // XP levels (not XP points) from 0 to game's hardcoded max
    public LevelText() {
        super("LVL", (player, world) -> String.valueOf(player.experienceLevel));
    }
}
