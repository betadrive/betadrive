package com.matthyfamily.betadrive.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class RobofistToolMaterial implements ToolMaterial {
    public static final RobofistToolMaterial INSTANCE = new RobofistToolMaterial();
    @Override
    public int getDurability() {
        return 2000;
    }
    @Override
    public float getMiningSpeedMultiplier() {
        return 1.0F;
    }
    @Override
    public float getAttackDamage() {
        return 20.0F;
    }
    @Override
    public int getMiningLevel() {
        return 1;
    }
    @Override
    public int getEnchantability() {
        return 0;
    }
    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(BetadriveItems.CIRCUIT);
    }
}
