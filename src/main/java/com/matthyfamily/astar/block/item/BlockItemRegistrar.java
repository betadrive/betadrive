package com.matthyfamily.astar.block.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.registry.Registry;

public class BlockItemRegistrar {
    public static BlockItem registerSimple(String iddat, Block block) {
        return Registry.register(Registry.ITEM, iddat, new BlockItem(block, new BlockItem.Settings()));
    }
    public static BlockItem registerCustom(String iddat, Block block, BlockItem.Settings settings) {
        return Registry.register(Registry.ITEM, iddat, new BlockItem(block, settings));
    }
    public static BlockItem registerAdvanced(String iddat, BlockItem blockItem) {
        return Registry.register(Registry.ITEM, iddat, blockItem);
    }
}
