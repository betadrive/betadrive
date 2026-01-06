package dev.matthy.betadrive.item.upgrade;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.config.PlayerConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class UpgradeItem extends Item {
    public UpgradeItem(Item.Settings settings) {
        super(settings);
    }

    public boolean canRun(World world, PlayerEntity user, Hand hand, PlayerConfig config) {
        if(!config.isAndroid) user.sendMessage(Text.translatable("item.betadrive.use.not_android_dialog"), true);
        return config.isAndroid;
    }
    public PlayerConfig modifyConfig(PlayerConfig cfg, World world, PlayerEntity user, Hand hand) {
        return cfg;
    }
    public void postUse(World world, PlayerEntity user, Hand hand) {}

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        PlayerConfig cfg = BetadriveConfig.getAndroidPlayerConfig(user.getUuid());
        if(!canRun(world, user, hand, cfg)) return ActionResult.FAIL; // If the ActionResult is a fail, then follow that logic and return fail early
        BetadriveConfig.setAndroidPlayerConfig(user.getUuid(), modifyConfig(cfg, world, user, hand)); //  Update config
        postUse(world, user, hand);
        user.getStackInHand(hand).decrement(1); // consume
        return ActionResult.CONSUME; // return that we consumed
    }
}
