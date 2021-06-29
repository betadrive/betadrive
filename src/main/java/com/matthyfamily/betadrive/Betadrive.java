package com.matthyfamily.betadrive;

import api.com.matthyfamily.betadrive.config.BetadriveConfig;
import com.matthyfamily.betadrive.biome.RobotDumpBiome;
import com.matthyfamily.betadrive.block.BetadriveBlocks;
import com.matthyfamily.betadrive.entity.BetadriveEntities;
import com.matthyfamily.betadrive.item.BetadriveItems;
import net.fabricmc.api.ModInitializer;

public class Betadrive implements ModInitializer {
    public static BetadriveConfig cfg = new BetadriveConfig();
    @Override
    public void onInitialize() {
        BetadriveBlocks.init();
        BetadriveItems.init();
        BetadriveEntities.init();
        RobotDumpBiome.register();
    }
}
