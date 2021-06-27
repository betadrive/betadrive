package com.matthyfamily.betadrive.item;

import com.matthyfamily.betadrive.block.BetadriveBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BetadriveItems {
    public static void init() {
        Registry.register(Registry.ITEM, new Identifier("betadrive", "red_pill"), new RedPillItem(new Item.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "blue_pill"), new BluePillItem(new Item.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "absorption_upgrade"), new AbsorptionUpgrade(new Item.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "guidebook"), new GuidebookItem(new Item.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "electron_extractor"), new BlockItem(BetadriveBlocks.ELECTRON_EXTRACTOR_BLOCK, new BlockItem.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "electron_bomb"), new BlockItem(BetadriveBlocks.ELECTRON_BOMB_BLOCK, new BlockItem.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "circuit"), new Item(new Item.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "robot_waste"), new BlockItem(BetadriveBlocks.ROBOT_WASTE_BLOCK, new BlockItem.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "compacted_robot_waste"), new BlockItem(BetadriveBlocks.COMPACTED_ROBOT_WASTE_BLOCK, new BlockItem.Settings()));
        Registry.register(Registry.ITEM, new Identifier("betadrive", "metal_scrap"), new BlockItem(BetadriveBlocks.METAL_SCRAP, new BlockItem.Settings()));
    }
}
