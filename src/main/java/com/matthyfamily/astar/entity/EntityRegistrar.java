package com.matthyfamily.astar.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegistrar {
    public static ZombieEntityRenderer createBipedalEntityRenderer(EntityRendererFactory.Context ctx, Identifier id) {
        return new ZombieEntityRenderer(ctx) {
            @Override
            public Identifier getTexture(ZombieEntity entity) {
                return id;
            }
        };
    }
    @Environment(EnvType.CLIENT)
    public static void registerHostileBipedalEntityRenderer(EntityType<HostileSimpleEntity> entityType, Identifier textureId) {
        EntityRendererRegistry.INSTANCE.register(entityType, (context) -> createBipedalEntityRenderer(context, textureId));
    }
    @Environment(EnvType.CLIENT)
    public static void registerFriendlyBipedalEntityRenderer(EntityType<FriendlySimpleEntity> entityType, Identifier textureId) {
        EntityRendererRegistry.INSTANCE.register(entityType, (context) -> createBipedalEntityRenderer(context, textureId));
    }

    public static EntityType<FriendlySimpleEntity> registerFriendlyBipedalEntity(Identifier id) {
        EntityType<FriendlySimpleEntity> tmpA = Registry.register(
                Registry.ENTITY_TYPE,
                id,
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FriendlySimpleEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).build()
        );
        FabricDefaultAttributeRegistry.register(tmpA,
                ZombieEntity.createZombieAttributes()
        );
        return tmpA;
    }
    public static EntityType<HostileSimpleEntity> registerHostileBipedalEntity(Identifier id) {
        EntityType<HostileSimpleEntity> tmpA = Registry.register(
                Registry.ENTITY_TYPE,
                id,
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HostileSimpleEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).build()
        );
        FabricDefaultAttributeRegistry.register(tmpA,
                ZombieEntity.createZombieAttributes()
        );
        return tmpA;
    }
}
