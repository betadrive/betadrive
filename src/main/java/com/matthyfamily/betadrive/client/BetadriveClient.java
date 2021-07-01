package com.matthyfamily.betadrive.client;

import com.matthyfamily.astar.entity.EntityRegistrar;
import com.matthyfamily.betadrive.entity.BetadriveEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BetadriveClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //EntityRendererRegistry.INSTANCE.register(BetadriveEntities.ANDROID, (context) -> new AndroidEntityRenderer(context));
        EntityRegistrar.registerHostileBipedalEntityRenderer(BetadriveEntities.ANDROID, new Identifier("betadrive", "textures/entity/android/android.png"));
        EntityRegistrar.registerFriendlyBipedalEntityRenderer(BetadriveEntities.FRIENDLY_ANDROID, new Identifier("betadrive", "textures/entity/android/friendly_android.png"));
    }
}
