package com.matthyfamily.betadrive.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class BetadriveBlocks {
    public static BlockEntityType<ElectronExtractorBlockEntity> ELECTRON_EXTRACTOR_BLOCK_ENTITY;
    public static ElectronExtractorBlock ELECTRON_EXTRACTOR_BLOCK;
    public static BlockEntityType<ElectronBombBlockEntity> ELECTRON_BOMB_BLOCK_ENTITY;
    public static ElectronBombBlock ELECTRON_BOMB_BLOCK;
    public static Block ROBOT_WASTE_BLOCK;
    public static Block COMPACTED_ROBOT_WASTE_BLOCK;
    public static Block METAL_SCRAP;
    public static void init() {
        ELECTRON_EXTRACTOR_BLOCK = Registry.register(Registry.BLOCK, "betadrive:electron_extractor", new ElectronExtractorBlock(FabricBlockSettings.of(Material.METAL)));
        ELECTRON_EXTRACTOR_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "betadrive:electron_extractor", BlockEntityType.Builder.create(ElectronExtractorBlockEntity::new, ELECTRON_EXTRACTOR_BLOCK).build(null));
        ELECTRON_BOMB_BLOCK = Registry.register(Registry.BLOCK, "betadrive:electron_bomb", new ElectronBombBlock(FabricBlockSettings.of(Material.METAL)));
        ELECTRON_BOMB_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "betadrive:electron_bomb", BlockEntityType.Builder.create(ElectronBombBlockEntity::new, ELECTRON_BOMB_BLOCK).build(null));
        ROBOT_WASTE_BLOCK = Registry.register(Registry.BLOCK, "betadrive:robot_waste", new Block(FabricBlockSettings.of(Material.METAL)));
        COMPACTED_ROBOT_WASTE_BLOCK = Registry.register(Registry.BLOCK, "betadrive:compacted_robot_waste", new Block(FabricBlockSettings.of(Material.METAL)));
        METAL_SCRAP = Registry.register(Registry.BLOCK, "betadrive:metal_scrap", new Block(FabricBlockSettings.of(Material.METAL)));
    }
}
