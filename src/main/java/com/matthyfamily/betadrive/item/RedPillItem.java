package com.matthyfamily.betadrive.item;

import com.matthyfamily.betadrive.Betadrive;
import com.matthyfamily.betadrive.hud.MeterHUD;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RedPillItem extends Item {
    public RedPillItem(Settings settings) {
        super(settings);
    }
    private static MeterHUD meterHud;
    public static MeterHUD getHud() {
        return meterHud;
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        try {
            if(!Betadrive.cfg.data.contains(playerEntity.getName().asString())) {
                meterHud = new MeterHUD(true, world);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        playerEntity.getStackInHand(hand).decrement(1);
        return TypedActionResult.consume(playerEntity.getStackInHand(hand));
    }
}
