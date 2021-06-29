package com.matthyfamily.betadrive.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class BetadriveBlocks {
    public static Block ROBOT_WASTE_BLOCK;
    public static Block COMPACTED_ROBOT_WASTE_BLOCK;
    public static Block METAL_SCRAP;
    public static void init() {
        ROBOT_WASTE_BLOCK = Registry.register(Registry.BLOCK, "betadrive:robot_waste", new Block(FabricBlockSettings.of(Material.METAL)));
        COMPACTED_ROBOT_WASTE_BLOCK = Registry.register(Registry.BLOCK, "betadrive:compacted_robot_waste", new Block(FabricBlockSettings.of(Material.METAL)));
        METAL_SCRAP = Registry.register(Registry.BLOCK, "betadrive:metal_scrap", new Block(FabricBlockSettings.of(Material.METAL)));
    }
}
