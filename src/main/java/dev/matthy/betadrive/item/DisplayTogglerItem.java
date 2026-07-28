package dev.matthy.betadrive.item;

import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.payload.HUDConfigPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class DisplayTogglerItem extends Item {
    private final String propertyToSet;
    public DisplayTogglerItem(Item.Properties settings, String propertyToSet) {
        super(settings);
        this.propertyToSet = propertyToSet;
    }
    @Environment(EnvType.CLIENT)
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        if(!world.isClientSide()) return InteractionResult.FAIL; // .use(...) runs twice; once for client, once for server. We remove the server-based use event so that we don't turn on then immediately turn off the HUD element.
        if(!BetadriveClient.isAndroid || BetadriveClient.isConverting) { // If the player is not an android, then tell them and don't toggle the HUD because it will do nothing for them
            user.sendOverlayMessage(Component.translatable("item.betadrive.use.not_android_dialog"));
            return InteractionResult.FAIL;
        }
        BetadriveClient.whichToEnable.put(this.propertyToSet, !BetadriveClient.whichToEnable.get(this.propertyToSet));
        ClientPlayNetworking.send(new HUDConfigPayload(user.getStringUUID(), this.propertyToSet, BetadriveClient.whichToEnable.get(this.propertyToSet)));
        return InteractionResult.SUCCESS;
    }


}
