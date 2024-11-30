package dev.matthy.betadrive.hud.texts;

import dev.matthy.betadrive.hud.HUDText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class SpeedText extends HUDText {
    public SpeedText() {
        super("MPS", (player) -> { // this function sucks! really bad! Doesn't account for elytra/creative flight/minecart/boat/etc.
            Vec3d origin = new Vec3d(0,0,0);
            if((int) (player.getVelocity().distanceTo(origin)*1000) == 78) return "0"; // origin is a Vec3d = (0,0,0), checks if you're not moving
            int speed = Math.round(1000 * player.getMovementSpeed()); // only shows if running or not, but even if you're not moving it shows as 100
            if(speed == 100) return "4.317"; // walking
            if(speed == 130) return "5.612"; // running
            return "0"; // probably shouldn't happen!
        });
    }
}
