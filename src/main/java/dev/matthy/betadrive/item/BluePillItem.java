package dev.matthy.betadrive.item;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.hud.MeterHUD;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BluePillItem extends Item { // betadrive:blue_pill
    public BluePillItem(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult use(World world, PlayerEntity playerEntity, Hand hand) {
        BetadriveConfig.unBecomeAndroid();
        try{
            MeterHUD.clearAnimation = true;
            playerEntity.getStackInHand(hand).decrement(1);
        } catch(Exception e) {
            assert MinecraftClient.getInstance().player != null;
            MinecraftClient.getInstance().player.sendMessage(Text.of("You are not an android yet."), true); }
        return ActionResult.CONSUME;
    }
}