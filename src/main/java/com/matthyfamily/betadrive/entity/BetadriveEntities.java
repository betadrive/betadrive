package com.matthyfamily.betadrive.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BetadriveEntities {
    public static EntityType<AndroidEntity> ANDROID;
    public static void init() {
        ANDROID = Registry.register(
                Registry.ENTITY_TYPE,
                new Identifier("betadrive", "android"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, AndroidEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).build()
        );
        FabricDefaultAttributeRegistry.register(ANDROID,
                ZombieEntity.createZombieAttributes()
        );
    }

}
