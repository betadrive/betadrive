package dev.matthy.betadrive.hud.texts;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.hud.HUDText;

public class BatteryText extends HUDText {

    public BatteryText() { // Battery rounded to percent, in format %d%% (e.g. 99%, 5%, 100%)
        super("BAT", "battery", (player, world) -> (int) (BetadriveConfig.getBatteryLevel() + 0.5) +"%");
    }
}
