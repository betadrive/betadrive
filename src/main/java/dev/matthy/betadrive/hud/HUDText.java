package dev.matthy.betadrive.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;


public class HUDText {
    private final String statLabel;
    private final BiFunction<PlayerEntity, World,String> function;
    public HUDText(String label, BiFunction<PlayerEntity, World,String> value) {
        statLabel = label;
        function = value;
    }
    public String getLabel() {
        return statLabel;
    }

    @Override
    public String toString() {
        return statLabel + "=" + function.apply(MinecraftClient.getInstance().player, MinecraftClient.getInstance().world);
    }
    public static String build(HUDText... args) {
        return "["+String.join("  ", Arrays.stream(args).map(HUDText::toString).toList())+"]";
    }
    public static String build(ArrayList<HUDText> args) {
        return "["+String.join("  ", args.stream().map(HUDText::toString).toList())+"]";
    }
}
