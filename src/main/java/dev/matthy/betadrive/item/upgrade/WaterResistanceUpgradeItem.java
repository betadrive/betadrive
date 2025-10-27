package dev.matthy.betadrive.item.upgrade;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.config.PlayerConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class WaterResistanceUpgradeItem extends Item {
    public final Identifier WATER_RESISTANCE_UPGRADE_ITEM = Identifier.of("betadrive", "water_resistance_upgrade");
    public WaterResistanceUpgradeItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if(!BetadriveConfig.getAndroidStatus(user.getUuid())) {
            user.sendMessage(Text.translatable("item.betadrive.use.not_android_dialog"), true);
            return ActionResult.FAIL;
        }
        PlayerConfig cfg = BetadriveConfig.getAndroidPlayerConfig(user.getUuid());
        cfg.waterResistant = true;
        BetadriveConfig.setAndroidPlayerConfig(user.getUuid(), cfg);
        if(world.isClient()) {
            BetadriveClient.isWaterResistant = true;
        }
        user.getStackInHand(hand).decrement(1);
        return ActionResult.CONSUME;
    }
}
