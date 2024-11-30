package dev.matthy.betadrive.item;

import dev.matthy.astar.item.ItemRegistrar;
import net.minecraft.item.Item;

public class BetadriveItems {
    public static Item CIRCUIT;
    public static Item ADVANCED_CIRCUIT;
    public static void init() {
        ItemRegistrar.registerAdvanced("betadrive:red_pill", new RedPillItem(new Item.Settings()));
        ItemRegistrar.registerAdvanced("betadrive:blue_pill", new BluePillItem(new Item.Settings()));

    }
}