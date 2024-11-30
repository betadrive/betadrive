package dev.matthy.betadrive;

import dev.matthy.betadrive.android.AndroidPlayer;
import dev.matthy.betadrive.config.BetadriveConfig;
import dev.matthy.betadrive.hud.MeterHUD;
import dev.matthy.betadrive.item.BetadriveItems;
import net.fabricmc.api.ModInitializer;

public class Betadrive implements ModInitializer {

    public static BetadriveConfig cfg = new BetadriveConfig();
    public AndroidPlayer playerAndroid;
    public static MeterHUD mainHud;
    @Override
    public void onInitialize() {
        BetadriveItems.init(); // add items
    }
}
