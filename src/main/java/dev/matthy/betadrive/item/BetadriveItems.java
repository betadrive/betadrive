package dev.matthy.betadrive.item;

import dev.matthy.betadrive.item.pill.BluePillItem;
import dev.matthy.betadrive.item.pill.RedPillItem;
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

public class BetadriveItems { // Stores and registers all Betadrive items
    public static ArrayList<Item> groupAddable = new ArrayList<>();
    public static Item CIRCUIT = register("circuit", Item::new, new Item.Settings()); // material, betadrive:circuit
    public static Item ADVANCED_CIRCUIT = register("advanced_circuit", Item::new, new Item.Settings()); // material, betadrive:advanced_circuit
    public static Item BATTERY_DISPLAY_TOGGLER = register("battery_display_toggler", (settings) -> new DisplayTogglerItem(settings, "BAT"), new Item.Settings().maxCount(1));
    public static Item LEVEL_DISPLAY_TOGGLER = register("level_display_toggler", (settings) -> new DisplayTogglerItem(settings, "LVL"), new Item.Settings().maxCount(1));
    public static Item SPEED_DISPLAY_TOGGLER = register("speed_display_toggler", (settings) -> new DisplayTogglerItem(settings, "MPS"), new Item.Settings().maxCount(1));
    public static Item HEALTH_DISPLAY_TOGGLER = register("health_display_toggler", (settings) -> new DisplayTogglerItem(settings, "HP"), new Item.Settings().maxCount(1));
    public static Item HUNGER_DISPLAY_TOGGLER = register("hunger_display_toggler", (settings) -> new DisplayTogglerItem(settings, "HGR"), new Item.Settings().maxCount(1));
    public static Item BATTERY = register("battery", BatteryItem::new, new Item.Settings()); // betadrive:battery
    public static Item RED_PILL = register("red_pill", RedPillItem::new, new Item.Settings()); // used to become an android, betadrive:red_pill
    public static Item BLUE_PILL = register("blue_pill", BluePillItem::new, new Item.Settings()); // used to not become an android, betadrive:blue_pill
    public static Item ROBOFIST = register("robofist", properties -> new RobofistItem(ToolMaterial.NETHERITE, 20, 4.0F,properties), new Item.Settings()); // weapon, betadrive:robofist
    public static Item ABSORPTION_UPGRADE = register("absorption_upgrade", AbsorptionUpgradeItem::new, new Item.Settings());
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
            for (Item item : groupAddable)
                itemGroup.add(item); // add all the items put in groupAddable (via register override, includeInGroup=true, or not specified)
        });
    }
}