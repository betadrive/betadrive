package dev.matthy.betadrive.item.pill;

import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.hud.MeterHUD;
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

public class RedPillItem extends Item { // betadrive:red_pill. Turns player into an android, the main focus of Betadrive
    public RedPillItem(Item.Properties settings) {
        super(settings);
    }
    @Environment(EnvType.CLIENT)
    @Override
    public InteractionResult use(Level level, Player player, final InteractionHand hand) {
        if(!level.isClientSide()) return InteractionResult.FAIL;
        if(BetadriveClient.isAndroid) {
            BetadriveClient.isConverting = false;
            player.sendOverlayMessage(Component.translatable("item.betadrive.use.already_android_dialog"));
            return InteractionResult.FAIL;
        }
        if(BetadriveClient.isConverting) return InteractionResult.FAIL;
        MeterHUD.clearAnimation = false;
        BetadriveClient.isConverting = true;
        ClientPlayNetworking.send(new IsAndroidPayload(player.getStringUUID(), true));
        player.getMainHandItem().consume(1, player);
        return InteractionResult.CONSUME;
    }
}