package dev.matthy.betadrive.hud.texts;

import dev.matthy.betadrive.hud.HUDText;

public class LevelText extends HUDText {

    public LevelText() {
        super("LVL", (player, world) -> String.valueOf(player.experienceLevel));
    }
}
