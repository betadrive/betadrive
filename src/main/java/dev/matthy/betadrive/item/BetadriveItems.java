package dev.matthy.betadrive.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.function.Function;
/*
public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(FabricDocsReference.MOD_ID, "item_group"));
public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
		.icon(() -> new ItemStack(ModItems.GUIDITE_SWORD))
		.displayName(Text.translatable("itemGroup.fabric_docs_reference"))
		.build();

 */
public class BetadriveItems {
    public static ArrayList<Item> groupAddable = new ArrayList<Item>();
    public static Item CIRCUIT = register("circuit", Item::new, new Item.Settings()); // material, betadrive:circuit
    public static Item ADVANCED_CIRCUIT;
    public static Item BATTERY = register("battery", BatteryItem::new, new Item.Settings());
    public static Item RED_PILL = register("red_pill", RedPillItem::new, new Item.Settings()); // used to become an android, betadrive:red_pill
    public static Item BLUE_PILL = register("blue_pill", BluePillItem::new, new Item.Settings()); // used to not become an android, betadrive:blue_pill
    public static Item ROBOFIST = register("robofist", properties -> new RobofistItem(ToolMaterial.NETHERITE, 20, 4.0F,properties), new Item.Settings()); // weapon, betadrive:robofist
    public static final RegistryKey<ItemGroup> BETADRIVE_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of("betadrive", "item_group"));
    public static final ItemGroup BETADRIVE_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(CIRCUIT))
            .displayName(Text.translatable("itemGroup.betadrive"))
            .build();
    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) { // register items
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("betadrive", path));
        Item item = Items.register(registryKey, factory, settings);
        groupAddable.add(item);
        return item;
    }
    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings, boolean includeInGroup) { // register items
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("betadrive", path));
        Item item = Items.register(registryKey, factory, settings);
        if(includeInGroup) groupAddable.add(item);
        return item;
    }
    public static void init() {
        Registry.register(Registries.ITEM_GROUP, BETADRIVE_GROUP_KEY, BETADRIVE_ITEM_GROUP);
        ItemGroupEvents.modifyEntriesEvent(BETADRIVE_GROUP_KEY).register(itemGroup -> {
            for(int i=0; i<groupAddable.size(); i++)  itemGroup.add(groupAddable.get(i)); // add all the items put in groupAddable (via register override, includeInGroup=true, or not specified)
        });
    }
}