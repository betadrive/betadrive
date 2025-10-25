package dev.matthy.betadrive.item;

import dev.matthy.betadrive.BetadriveConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BatteryItem extends Item { // betadrive:battery

    public BatteryItem(Settings settings) {
        super(settings);
    }
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        BetadriveConfig.fillBattery(user.getUuid());
        user.getHungerManager().setFoodLevel(20);
        user.getHungerManager().setSaturationLevel(20);
        user.getStackInHand(hand).decrement(1);
        return ActionResult.CONSUME;
    }
}