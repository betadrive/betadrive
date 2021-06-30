package com.matthyfamily.astar.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;

public class BlockRegistrar {
    public static Block registerSimple(String iddat) {
        return Registry.register(Registry.BLOCK, iddat, new Block(FabricBlockSettings.of(Material.TNT)));
    }
    public static Block registerCustom(String iddat, FabricBlockSettings settings) {
        return Registry.register(Registry.BLOCK, iddat, new Block(settings));
    }
    public static Block registerAdvanced(String iddat, Block block) {
        return Registry.register(Registry.BLOCK, iddat, block);
    }
}
