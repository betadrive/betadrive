package com.matthyfamily.betadrive.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public class AndroidEntityRenderer extends ZombieBaseEntityRenderer {

    public AndroidEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new ZombieEntityModel(1f, false), new ZombieEntityModel(1f, false), new ZombieEntityModel(1f, false));
    }

    @Override
    public Identifier getTexture(ZombieEntity entity) {
        return new Identifier("betadrive", "textures/entity/android/android.png");
    }
}