package com.matthyfamily.betadrive.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class RobofistItem extends SwordItem {
    public RobofistItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        entity.takeKnockback(5, RANDOM.nextFloat()*100, RANDOM.nextFloat()*100);

        return super.useOnEntity(stack, user, entity, hand);
    }
}
