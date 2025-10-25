package dev.matthy.betadrive.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.Random;

public class RobofistItem extends Item { // betadrive:robofist. A weapon with random direction knockback and a lot of damage
    private final Random RANDOM = new Random();
    public RobofistItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, net.minecraft.item.Item.Settings settings) {
        super(settings.sword(toolMaterial, attackDamage, attackSpeed));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) { // On right click
        entity.takeKnockback(5, RANDOM.nextFloat()*100, RANDOM.nextFloat()*100); // Random KB direction

        return super.useOnEntity(stack, user, entity, hand);
    }
}