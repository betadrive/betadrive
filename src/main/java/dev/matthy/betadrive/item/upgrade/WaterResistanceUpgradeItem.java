package dev.matthy.betadrive.item.upgrade;

import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.config.PlayerConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class WaterResistanceUpgradeItem extends UpgradeItem {
    public WaterResistanceUpgradeItem(Item.Properties settings) {
        super(settings);
    }
    @Override
    public boolean canRun(Level world, Player user, InteractionHand hand, PlayerConfig config) {
        return !config.waterResistant;
    }
    @Override
    public PlayerConfig modifyConfig(PlayerConfig config, Level world, Player user, InteractionHand hand) {
        config.waterResistant = true;
        if(world.isClientSide()) BetadriveClient.isWaterResistant = true;
        return config;
    }
}
