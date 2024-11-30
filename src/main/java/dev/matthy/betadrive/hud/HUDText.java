package dev.matthy.betadrive.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Arrays;
import java.util.function.Function;


public class HUDText {
    private String statLabel;
    private String statValue;
    public HUDText(String label, Function<PlayerEntity,String> value) {
        statLabel = label;
        statValue = value.apply(MinecraftClient.getInstance().player);
    }
    @Override
    public String toString() {
        return statLabel + "=" + statValue;
    }
    public static String build(HUDText... args) {
        return "["+String.join(" ", Arrays.asList(args).stream().map(ele -> ele.toString()).toList())+"]";
    }
}
