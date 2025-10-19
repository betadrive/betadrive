package dev.matthy.betadrive.item;

import dev.matthy.betadrive.Betadrive;
import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.hud.MeterHUD;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class RedPillItem extends Item { // betadrive:red_pill
    public RedPillItem(net.minecraft.item.Item.Settings settings) {
        super(settings);
    }
    @Environment(EnvType.CLIENT)
    @Override
    public ActionResult use(World world, PlayerEntity playerEntity, Hand hand) {
        try {
            if(!Betadrive.isAndroid) {
                BetadriveClient.mainHud = new MeterHUD(true, world);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        playerEntity.getStackInHand(hand).decrement(1);
        return ActionResult.CONSUME;
    }
}