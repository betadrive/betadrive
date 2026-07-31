package dev.matthy.betadrive.item.pill;

import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.hud.MeterHUD;
import dev.matthy.betadrive.hud.TransformationAnimation;
import dev.matthy.betadrive.payload.IsAndroidPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class BluePillItem extends Item { // betadrive:blue_pill. Turns player back into a human
    public BluePillItem(Item.Properties settings) {
        super(settings);
    }
    @Environment(EnvType.CLIENT)
    @Override
    public InteractionResult use(Level world, Player playerEntity, InteractionHand hand) {
        if(!world.isClientSide()) return InteractionResult.FAIL;
        if(MeterHUD.clearAnimation || !BetadriveClient.isAndroid) { // If clearAnimation is enabled, which is only caused thus far by the player already having taken the blue pill, then tell them they aren't an android
            BetadriveClient.isConverting = false;
            TransformationAnimation.getStartTimeFlag = true;
            playerEntity.sendOverlayMessage(Component.translatable("item.betadrive.use.not_android_dialog"));
            return InteractionResult.FAIL;
        }
        ClientPlayNetworking.send(new IsAndroidPayload(playerEntity.getStringUUID(), false));
        BetadriveClient.isConvertingBack = true;
        TransformationAnimation.startRevert = true;
        MeterHUD.clearAnimation = true;
        playerEntity.getMainHandItem().consume(1, playerEntity);
        return InteractionResult.CONSUME;
    }
}