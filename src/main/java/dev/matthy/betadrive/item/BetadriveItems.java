package dev.matthy.betadrive.item;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class BetadriveItems {
    public static Item CIRCUIT = register("circuit", Item::new, new Item.Settings()); // material, betadrive:circuit
    public static Item ADVANCED_CIRCUIT;
    public static Item BATTERY = register("battery", BatteryItem::new, new Item.Settings());
    public static Item RED_PILL = register("red_pill", RedPillItem::new, new Item.Settings()); // used to become an android, betadrive:red_pill
    public static Item BLUE_PILL = register("blue_pill", BluePillItem::new, new Item.Settings()); // used to not become an android, betadrive:blue_pill
    public static Item ROBOFIST = register("robofist", properties -> new RobofistItem(ToolMaterial.NETHERITE, 20, 4.0F,properties), new Item.Settings()); // weapon, betadrive:robofist
    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) { // register items
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("betadrive", path));
        return Items.register(registryKey, factory, settings);
    }
    public static void init() {}
}