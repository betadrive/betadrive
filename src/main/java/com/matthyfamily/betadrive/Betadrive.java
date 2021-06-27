package com.matthyfamily.betadrive;

import api.com.matthyfamily.betadrive.android.AndroidPlayer;
import api.com.matthyfamily.betadrive.config.BetadriveConfig;
import com.matthyfamily.betadrive.biome.RobotDumpBiome;
import com.matthyfamily.betadrive.block.BetadriveBlocks;
import com.matthyfamily.betadrive.item.BetadriveItems;
import net.fabricmc.api.ModInitializer;

public class Betadrive implements ModInitializer {
    private static AndroidPlayer you;
    private static int accumulatedPower;
    public static BetadriveConfig cfg = new BetadriveConfig();
    @Override
    public void onInitialize() {
        accumulatedPower = 0;
        BetadriveBlocks.init();
        BetadriveItems.init();
        RobotDumpBiome.register();
    }
    public static void setAP(int ap) {
        accumulatedPower = ap;
    }
    public static int getAcP() {
        return accumulatedPower;
    }
    public AndroidPlayer getAP() {
        return this.you;
    }
}
