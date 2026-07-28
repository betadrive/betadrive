package dev.matthy.betadrive.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;

import java.util.Random;

public class RobofistItem extends Item { // betadrive:robofist. A weapon with random direction knockback and a lot of damage
    private final Random RANDOM = new Random();
    public RobofistItem(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, Item.Properties settings) {
        super(settings.sword(toolMaterial, attackDamage, attackSpeed));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity target, InteractionHand type) {
        target.knockback(5, RANDOM.nextFloat()*100, RANDOM.nextFloat()*100); // Random KB direction
        return super.interactLivingEntity(itemStack, player, target, type);
    }
}