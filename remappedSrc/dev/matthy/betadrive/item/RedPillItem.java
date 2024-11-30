package dev.matthy.betadrive.item;

import dev.matthy.betadrive.Betadrive;
import dev.matthy.betadrive.hud.MeterHUD;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class RedPillItem extends Item {
    public RedPillItem(net.minecraft.item.Item.Settings settings) {
        super(settings);
    }
    private static MeterHUD meterHud;
    public static MeterHUD getHud() {
        return meterHud;
    }
    @Override
    public ActionResult use(World world, PlayerEntity playerEntity, Hand hand) {
        try {
            if(!Betadrive.cfg.data.contains(playerEntity.getName().getString())) {
                meterHud = new MeterHUD(true, world);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        playerEntity.getStackInHand(hand).decrement(1);
        return ActionResult.CONSUME;
    }
}