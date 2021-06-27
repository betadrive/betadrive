package com.matthyfamily.betadrive.item;

import com.matthyfamily.betadrive.gui.GuidebookGui;
import com.matthyfamily.betadrive.gui.GuidebookScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GuidebookItem extends Item {
    public GuidebookItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        MinecraftClient.getInstance().openScreen(new GuidebookScreen(new GuidebookGui()));
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }
}
