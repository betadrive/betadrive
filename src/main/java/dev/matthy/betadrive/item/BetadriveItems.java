package dev.matthy.betadrive.item;

import dev.matthy.betadrive.hud.HUDTexts;
import dev.matthy.betadrive.item.pill.BluePillItem;
import dev.matthy.betadrive.item.pill.RedPillItem;
import dev.matthy.betadrive.item.upgrade.AbsorptionUpgradeItem;
import dev.matthy.betadrive.item.upgrade.WaterResistanceUpgradeItem;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;

import java.util.ArrayList;
import java.util.function.Function;

public class BetadriveItems { // Stores and registers all Betadrive items
    public static ArrayList<Item> groupAddable = new ArrayList<>();
    public static Item CIRCUIT = register("circuit", Item::new, new Item.Properties()); // material, betadrive:circuit
    public static Item ADVANCED_CIRCUIT = register("advanced_circuit", Item::new, new Item.Properties()); // material, betadrive:advanced_circuit
    public static Item BATTERY = register("battery", BatteryItem::new, new Item.Properties()); // betadrive:battery
    public static Item RED_PILL = register("red_pill", RedPillItem::new, new Item.Properties()); // used to become an android, betadrive:red_pill
    public static Item BLUE_PILL = register("blue_pill", BluePillItem::new, new Item.Properties()); // used to not become an android, betadrive:blue_pill
    public static Item ROBOFIST = register("robofist", properties -> new RobofistItem(ToolMaterial.DIAMOND, 20, 4.0F, properties), new Item.Properties()); // weapon, betadrive:robofist
    public static Item ABSORPTION_UPGRADE = register("absorption_upgrade", AbsorptionUpgradeItem::new, new Item.Properties());
    public static Item WATER_RESISTANCE_UPGRADE  = register("water_resistance_upgrade", WaterResistanceUpgradeItem::new, new Item.Properties());
    public static final ResourceKey<CreativeModeTab> BETADRIVE_GROUP_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath("betadrive", "creative_tab")
    );
    public static CreativeModeTab BETADRIVE_ITEM_GROUP = null;
    public static final CreativeModeTab.Builder BETADRIVE_ITEM_GROUP_BUILDER = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(CIRCUIT))
            .title(Component.translatable("itemGroup.betadrive"));
    public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        return register(name, itemFactory, settings, true);
    }
    public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings, boolean includeInGroup) { // register items
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("betadrive", name));
        Item item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        if(includeInGroup) groupAddable.add(item);
        return item;
    }
    public static void init() {
        BETADRIVE_ITEM_GROUP = BETADRIVE_ITEM_GROUP_BUILDER.displayItems((params, output) -> {
            for (Item item : groupAddable) output.accept(item); // add all the items put in groupAddable (via register override, includeInGroup=true, or not specified)
        }).build();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, BETADRIVE_GROUP_KEY, BETADRIVE_ITEM_GROUP); // "Betadrive" item group
        HUDTexts.init(); // get display togglers in the list too
    }
}