package dev.matthy.betadrive.item.pill;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.hud.MeterHUD;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
    @Environment(EnvType.CLIENT)
    @Override
    public ActionResult use(World world, PlayerEntity playerEntity, Hand hand) {
        if(MeterHUD.clearAnimation || !BetadriveConfig.getAndroidStatus(playerEntity.getUuid())) { // If clearAnimation is enabled, which is only caused thus far by the player already having taken the blue pill, then tell them they aren't an android
            BetadriveClient.isAndroid = false;
            BetadriveClient.isConverting = false;
            assert MinecraftClient.getInstance().player != null;
            playerEntity.sendMessage(Text.translatable("item.betadrive.use.not_android_dialog"), true);
            return ActionResult.FAIL;
        }
        BetadriveConfig.unBecomeAndroid(playerEntity.getUuid());
        MeterHUD.clearAnimation = true;
        playerEntity.getStackInHand(hand).decrement(1);
        return ActionResult.CONSUME;
    }
}