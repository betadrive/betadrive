package dev.matthy.betadrive.hud.texts;

import dev.matthy.betadrive.Betadrive;
import dev.matthy.betadrive.hud.HUDText;
import net.minecraft.entity.player.PlayerEntity;

import java.util.function.Function;

public class BatteryText extends HUDText {

    public BatteryText() {
        super("BAT", (player) -> String.valueOf((int) Betadrive.cfg.getBatteryLevel(player.getName().getString())));
    }
}
