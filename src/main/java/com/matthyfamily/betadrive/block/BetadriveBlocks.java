package com.matthyfamily.betadrive.block;

import com.matthyfamily.astar.block.BlockRegistrar;
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
        ROBOT_WASTE_BLOCK = BlockRegistrar.registerSimple("betadrive:robot_waste");
        COMPACTED_ROBOT_WASTE_BLOCK = BlockRegistrar.registerSimple("betadrive:compacted_robot_waste");
        METAL_SCRAP = BlockRegistrar.registerSimple("betadrive:metal_scrap");
    }
}
