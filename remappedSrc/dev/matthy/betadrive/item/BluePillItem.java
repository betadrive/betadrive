package dev.matthy.betadrive.item;

import dev.matthy.betadrive.android.AndroidPlayer;
import dev.matthy.betadrive.Betadrive;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BluePillItem extends Item {
    public BluePillItem(net.minecraft.item.Item.Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult use(World world, PlayerEntity playerEntity, Hand hand) {
        Betadrive.cfg.unBecomeAndroid(new AndroidPlayer(playerEntity));
        playerEntity.getStackInHand(hand).decrement(1);
        try{
            RedPillItem.getHud().clear();
        } catch(Exception e) { System.out.println("You are not an android yet."); }
        return ActionResult.CONSUME;
    }
}