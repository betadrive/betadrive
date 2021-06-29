package com.matthyfamily.betadrive.client;

import com.matthyfamily.betadrive.entity.AndroidEntityRenderer;
import com.matthyfamily.betadrive.entity.BetadriveEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class BetadriveClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(BetadriveEntities.ANDROID, (context) -> new AndroidEntityRenderer(context));
    }
}
