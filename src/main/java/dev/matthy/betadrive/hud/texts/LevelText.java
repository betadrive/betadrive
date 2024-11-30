package dev.matthy.betadrive.hud.texts;

import dev.matthy.betadrive.hud.HUDText;
import net.minecraft.entity.player.PlayerEntity;

import java.util.function.Function;

public class LevelText extends HUDText {

    public LevelText() {
        super("LVL", (player) -> String.valueOf(player.experienceLevel));
    }
}
