package com.matthyfamily.astar.item;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ItemRegistrar {
    public static Item registerSimple(String iddat) {
        return Registry.register(Registry.ITEM, iddat, new Item(new Item.Settings()));
    }
    public static Item registerCustom(String iddat, Item.Settings settings) {
        return Registry.register(Registry.ITEM, iddat, new Item(settings));
    }
    public static Item registerAdvanced(String iddat, Item item) {
        return Registry.register(Registry.ITEM, iddat, item);
    }
}
