package com.matthyfamily.betadrive.item;

import com.matthyfamily.astar.block.item.BlockItemRegistrar;
import com.matthyfamily.astar.item.ItemRegistrar;
import com.matthyfamily.betadrive.block.BetadriveBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BetadriveItems {
    public static Item CIRCUIT;
    public static Item ADVANCED_CIRCUIT;
    public static void init() {
        ItemRegistrar.registerAdvanced("betadrive:red_pill", new RedPillItem(new Item.Settings()));
        ItemRegistrar.registerAdvanced("betadrive:blue_pill", new BluePillItem(new Item.Settings()));
        ItemRegistrar.registerAdvanced("betadrive:absorption_upgrade", new AbsorptionUpgrade(new Item.Settings()));
        CIRCUIT = ItemRegistrar.registerSimple("betadrive:circuit");
        ADVANCED_CIRCUIT = ItemRegistrar.registerSimple("betadrive:advanced_circuit");
        BlockItemRegistrar.registerSimple("betadrive:robot_waste", BetadriveBlocks.ROBOT_WASTE_BLOCK);
        BlockItemRegistrar.registerSimple("betadrive:compacted_robot_waste", BetadriveBlocks.COMPACTED_ROBOT_WASTE_BLOCK);
        BlockItemRegistrar.registerSimple("betadrive:metal_scrap", BetadriveBlocks.METAL_SCRAP);
        ItemRegistrar.registerAdvanced("betadrive:robofist", new RobofistItem(RobofistToolMaterial.INSTANCE, 1, 0.25F, new Item.Settings()));

    }
}
