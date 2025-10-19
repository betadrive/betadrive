package dev.matthy.betadrive;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.UUID;

public class BetadriveConfig {
    public static void becomeAndroid() { // make this AndroidPlayer an android in the cfg
        Betadrive.battery = 100f;
        Betadrive.isAndroid = true;

        ClientPlayNetworking.send(new BatteryPayload(Betadrive.battery));
        ClientPlayNetworking.send(new IsAndroidPayload(Betadrive.isAndroid));
    }
    public static void unBecomeAndroid() { // remove this AndroidPlayer from the android list in cfg
        Betadrive.isAndroid = false;

        ClientPlayNetworking.send(new BatteryPayload(Betadrive.battery));
        ClientPlayNetworking.send(new IsAndroidPayload(Betadrive.isAndroid));
    }

    public static void fillBattery(UUID uuid, World world) {
        Betadrive.battery = 100f;

        ClientPlayNetworking.send(new BatteryPayload(Betadrive.battery));
    }
    public static float getBatteryLevel() {
        return Betadrive.battery;
    }
}
