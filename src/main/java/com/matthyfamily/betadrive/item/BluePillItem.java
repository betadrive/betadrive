package com.matthyfamily.betadrive.item;

import api.com.matthyfamily.betadrive.android.AndroidPlayer;
import com.matthyfamily.betadrive.Betadrive;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BluePillItem extends Item {
    public BluePillItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        Betadrive.cfg.unBecomeAndroid(new AndroidPlayer(playerEntity));
        playerEntity.getStackInHand(hand).decrement(1);
        try{
            RedPillItem.getHud().clear();
        } catch(Exception e) { System.out.println("You are not an android yet."); }
        return TypedActionResult.consume(playerEntity.getStackInHand(hand));
    }
}
