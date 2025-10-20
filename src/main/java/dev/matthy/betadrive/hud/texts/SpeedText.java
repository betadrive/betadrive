package dev.matthy.betadrive.hud.texts;

import dev.matthy.betadrive.hud.HUDText;
import net.minecraft.util.math.Vec3d;

public class SpeedText extends HUDText {
    private static Vec3d prevPos = null;
    private static long prevTick = 0;
    private static double prevVel = 0;
    public SpeedText() {
        super("MPS", (player, world) -> { // this function rocks! really not bad! Does account for elytra/creative flight/minecart/boat/etc.
            Vec3d currPos = player.getEntityPos(); // getEntityPos updates once per tick
            if (prevPos == null) {
                prevPos = currPos;
                return "";
            }
            long currTick = world.getTime();
            double velocity = prevPos.distanceTo(currPos);
            if (prevTick == currTick) return String.format("%.3f", prevVel * 20);
            prevPos = currPos;
            prevVel = velocity;
            prevTick = currTick;
            return String.format("%.3f", velocity * 20);
        });
    }
}
