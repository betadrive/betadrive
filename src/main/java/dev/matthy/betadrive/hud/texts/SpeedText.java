package dev.matthy.betadrive.hud.texts;

import dev.matthy.betadrive.hud.HUDText;
import net.minecraft.world.phys.Vec3;

public class SpeedText extends HUDText {
    public SpeedText() {
        super("MPS", "speed", (player, world) -> { // Player's speed in meters (or blocks) per second
            Vec3 position = player.position(); // Get current position
            Vec3 lastPosition = player.oldPosition(); // Get position from 1 tick ago
            return String.valueOf(Math.floor(position.distanceTo(lastPosition)*2000)/100); // Get distance between those 2 positions, multiply by ideal ticks per second (20) and round to hundredth. This assumes no lag, but that shouldn't matter since m/s is not some essential statistic that every player *needs*
        });
    }
}
