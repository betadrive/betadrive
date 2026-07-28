package dev.matthy.betadrive;

import dev.matthy.betadrive.command.BetadriveCommands;
import dev.matthy.betadrive.config.PlayerConfig;
import dev.matthy.betadrive.hud.HUDTexts;
import dev.matthy.betadrive.hud.texts.*;
import dev.matthy.betadrive.item.BetadriveItems;
import dev.matthy.betadrive.payload.BatteryPayload;
import dev.matthy.betadrive.payload.HUDConfigPayload;
import dev.matthy.betadrive.payload.IsAndroidPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.level.storage.LevelResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class Betadrive implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger("betadrive");
    public static String filePath = null;
    public static boolean updateAndroidStatus = false;
    public static boolean updateBattery = false;
    @Override
    public void onInitialize() {
        HUDTexts.register(new BatteryText(), new HealthText(), new HungerText(), new LevelText(), new SpeedText()); // Built-in HUDText types
        BetadriveItems.init(); // Add items
        PayloadTypeRegistry.clientboundPlay().register(HUDConfigPayload.TYPE, HUDConfigPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(BatteryPayload.TYPE, BatteryPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(IsAndroidPayload.TYPE, IsAndroidPayload.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(HUDConfigPayload.TYPE, HUDConfigPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(BatteryPayload.TYPE, (payload, context) -> BetadriveConfig.setBattery(context.player().getUUID(), payload.battery()));
        ServerPlayNetworking.registerGlobalReceiver(IsAndroidPayload.TYPE, (payload, context) -> BetadriveConfig.setAndroidStatus(context.player().getUUID(), payload.isAndroid()));
        ServerPlayNetworking.registerGlobalReceiver(HUDConfigPayload.TYPE, (payload, context) -> {
            UUID uuid = UUID.fromString(payload.uuid());
            PlayerConfig playerConfig = BetadriveConfig.getAndroidPlayerConfig(uuid);
            playerConfig.whichToEnable.put(payload.property(), payload.active());
            BetadriveConfig.setAndroidPlayerConfig(uuid, playerConfig);
        });
        ServerPlayerEvents.JOIN.register((pe) -> {
            filePath = pe.level().getServer().getWorldPath(LevelResource.ROOT) + "/betadrive.json";
            BetadriveConfig.getAndroidPlayerConfig(pe.getUUID()).whichToEnable.forEach((prop, active) -> ServerPlayNetworking.send(pe, new HUDConfigPayload(pe.getStringUUID(), prop, active)));
        });

        BetadriveCommands.register();
    }
}
