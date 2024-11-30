package dev.matthy.betadrive.item;

import dev.matthy.betadrive.Betadrive;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Random;

public class BatteryItem extends Item { // betadrive:battery
    private Random RANDOM = new Random();

    public BatteryItem(Settings settings) {
        super(settings);
    }
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        Betadrive.cfg.setBatteryLevel(user.getName().getString(),100d);
        user.getStackInHand(hand).decrement(1);
        return ActionResult.CONSUME;
    }
}