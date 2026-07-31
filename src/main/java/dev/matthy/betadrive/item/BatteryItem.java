package dev.matthy.betadrive.item;

import dev.matthy.betadrive.BetadriveConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class BatteryItem extends Item { // betadrive:battery. Fills android battery and hunger

    public BatteryItem(Item.Properties settings) {
        super(settings);
    }
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        BetadriveConfig.fillBattery(user.getUUID());
        user.getFoodData().setFoodLevel(20);
        user.getFoodData().setSaturation(20);
        user.getMainHandItem().consume(1, user);
        return InteractionResult.CONSUME;
    }
}