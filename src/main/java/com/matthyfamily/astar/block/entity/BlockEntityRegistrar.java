package com.matthyfamily.astar.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockEntityRegistrar {
    public static BlockEntityType registerSimple(Identifier iddat, FabricBlockEntityTypeBuilder.Factory factory) {
        Block b = Registry.register(Registry.BLOCK, iddat, new Block(FabricBlockSettings.of(Material.TNT)));
        return Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                iddat,
                FabricBlockEntityTypeBuilder
                        .create(factory, b)
                        .build(null)
        );
    }
    public static BlockEntityType registerCustom(Identifier iddat, FabricBlockSettings settings, FabricBlockEntityTypeBuilder.Factory factory) {
        Block b = Registry.register(Registry.BLOCK, iddat, new Block(settings));
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, iddat, FabricBlockEntityTypeBuilder.create(factory, b).build(null));
    }
    public static BlockEntityType registerAdvanced(Identifier iddat, Block block, FabricBlockEntityTypeBuilder.Factory factory) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, iddat, FabricBlockEntityTypeBuilder.create(factory, block).build(null));
    }
}
