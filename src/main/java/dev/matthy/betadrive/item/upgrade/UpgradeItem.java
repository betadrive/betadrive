package dev.matthy.betadrive.item.upgrade;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.config.PlayerConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class UpgradeItem extends Item {
    public UpgradeItem(Item.Properties settings) {
        super(settings);
    }

    public boolean canRun(Level world, Player user, InteractionHand hand, PlayerConfig config) {
        if(!config.isAndroid) user.sendOverlayMessage(Component.translatable("item.betadrive.use.not_android_dialog"));
        return config.isAndroid;
    }
    public PlayerConfig modifyConfig(PlayerConfig cfg, Level world, Player user, InteractionHand hand) {
        return cfg;
    }
    public void postUse(Level world, Player user, InteractionHand hand) {}

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        PlayerConfig cfg = BetadriveConfig.getAndroidPlayerConfig(user.getUUID());
        if(!canRun(world, user, hand, cfg)) return InteractionResult.FAIL; // If the ActionResult is a fail, then follow that logic and return fail early
        BetadriveConfig.setAndroidPlayerConfig(user.getUUID(), modifyConfig(cfg, world, user, hand)); //  Update config
        postUse(world, user, hand);
        return InteractionResult.CONSUME; // return that we consumed
    }
}
