package dev.matthy.betadrive.item.pill;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.hud.MeterHUD;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class RedPillItem extends Item { // betadrive:red_pill. Turns player into an android, the main focus of Betadrive
    public RedPillItem(Settings settings) {
        super(settings);
    }
    @Environment(EnvType.CLIENT)
    @Override
    public ActionResult use(World world, PlayerEntity playerEntity, Hand hand) {
        if(!world.isClient()) return ActionResult.PASS;
        if(BetadriveConfig.getAndroidStatus(playerEntity.getUuid())) {
            BetadriveClient.isAndroid = true;
            BetadriveClient.isConverting = false;
            playerEntity.sendMessage(Text.translatable("item.betadrive.use.already_android_dialog"), true);
            return ActionResult.FAIL;
        }
        if(BetadriveClient.isConverting) return ActionResult.FAIL;
        MeterHUD.clearAnimation = false;
        BetadriveClient.isConverting = true;
        BetadriveConfig.becomeAndroid(playerEntity.getUuid()); // set cfg
        playerEntity.getStackInHand(hand).decrement(1);
        return ActionResult.CONSUME;
    }
}