package dev.matthy.betadrive.hud.texts;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.hud.HUDText;

public class BatteryText extends HUDText {

    public BatteryText() {
        super("BAT", (player, world) -> String.valueOf((int) BetadriveConfig.getBatteryLevel()));
    }
}
