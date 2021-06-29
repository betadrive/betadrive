package com.matthyfamily.betadrive.item;

import com.matthyfamily.betadrive.block.BetadriveBlocks;
import com.matthyfamily.betadrive.entity.BetadriveEntities;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BetadriveItems {
    public static Item CIRCUIT;
    public static Item ADVANCED_CIRCUIT;
    public static void init() {
        Registry.register(Registry.ITEM, new Identifier("betadrive", "red_pill"), new RedPillItem(new Item.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "blue_pill"), new BluePillItem(new Item.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "absorption_upgrade"), new AbsorptionUpgrade(new Item.Settings()));
        CIRCUIT = Registry.register(Registry.ITEM, new Identifier("betadrive", "circuit"), new Item(new Item.Settings()));
        ADVANCED_CIRCUIT = Registry.register(Registry.ITEM, new Identifier("betadrive", "advanced_circuit"), new Item(new Item.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "robot_waste"), new BlockItem(BetadriveBlocks.ROBOT_WASTE_BLOCK, new BlockItem.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "compacted_robot_waste"), new BlockItem(BetadriveBlocks.COMPACTED_ROBOT_WASTE_BLOCK, new BlockItem.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "metal_scrap"), new BlockItem(BetadriveBlocks.METAL_SCRAP, new BlockItem.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "robofist"), new RobofistItem(RobofistToolMaterial.INSTANCE, 1, 0.25F, new Item.Settings()));

    }
}
