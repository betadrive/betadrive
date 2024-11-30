package dev.matthy.astar.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ItemRegistrar {
    public static Item registerSimple(String iddat) {
        return Registry.register(Registries.ITEM, iddat, new Item(new Item.Settings()));
    }
    public static Item registerCustom(String iddat, Item.Settings settings) {
        return Registry.register(Registries.ITEM, iddat, new Item(settings));
    }
    public static Item registerAdvanced(String iddat, Item item) {
        return Registry.register(Registries.ITEM, iddat, item);
    }
}