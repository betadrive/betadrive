package com.matthyfamily.betadrive.item;


import api.com.matthyfamily.betadrive.android.AndroidPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static com.matthyfamily.betadrive.Betadrive.cfg;

public class AbsorptionUpgrade extends Item {

    public AbsorptionUpgrade(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        try {
            if(!cfg.isExistant()) {
                cfg.create();
            }
            if(cfg.read().toJSONString().contains(new AndroidPlayer(playerEntity).getName())) {
                playerEntity.setAbsorptionAmount(playerEntity.getAbsorptionAmount()+4);
                playerEntity.sendMessage(Text.of("You feel... empowered. Although it just feels temporary."), true);
                playerEntity.getStackInHand(hand).decrement(1);
                return TypedActionResult.consume(playerEntity.getStackInHand(hand));
            } else {
                playerEntity.sendMessage(Text.of("It did nothing... maybe you aren't an Android?"), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TypedActionResult.consume(playerEntity.getStackInHand(hand));
    }
}
