package dev.matthy.betadrive.hud.texts;

import dev.matthy.betadrive.hud.HUDText;
import net.minecraft.util.math.Vec3d;

public class SpeedText extends HUDText {
    public SpeedText() {
        super("MPS", (player, world) -> {
            Vec3d position = player.getEntityPos(); // get current position
            Vec3d lastPosition = player.getLastRenderPos(); // get position from 1 tick ago
            return String.valueOf(Math.floor(position.distanceTo(lastPosition)*2000)/100); // get distance between those 2 positions, multiply by ideal ticks per second and round to hundredth
        });
    }
}
