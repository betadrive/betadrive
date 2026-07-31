package dev.matthy.betadrive.item;

import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.payload.BatteryPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class GeneratorItem extends Item {
    public GeneratorItem(Properties properties) {
        super(properties);
    }
    @Environment(EnvType.CLIENT)
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        if(!BetadriveClient.isAndroid) {
            user.sendOverlayMessage(Component.translatable("item.betadrive.use.not_android_dialog"));
            return InteractionResult.FAIL;
        }
        BetadriveClient.battery = Math.min(100,BetadriveClient.battery+0.05d);
        ClientPlayNetworking.send(new BatteryPayload(BetadriveClient.battery));
        return InteractionResult.SUCCESS;
    }
}
