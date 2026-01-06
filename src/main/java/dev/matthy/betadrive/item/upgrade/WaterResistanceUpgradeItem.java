package dev.matthy.betadrive.item.upgrade;

import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.config.PlayerConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class WaterResistanceUpgradeItem extends UpgradeItem {
    public WaterResistanceUpgradeItem(Settings settings) {
        super(settings);
    }
    @Override
    public boolean canRun(World world, PlayerEntity user, Hand hand, PlayerConfig config) {
        return !config.waterResistant;
    }
    @Override
    public PlayerConfig modifyConfig(PlayerConfig config, World world, PlayerEntity user, Hand hand) {
        config.waterResistant = true;
        if(world.isClient()) BetadriveClient.isWaterResistant = true;
        return config;
    }
}
