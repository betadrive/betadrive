package dev.matthy.betadrive;

import dev.matthy.betadrive.item.BetadriveItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.WorldSavePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class Betadrive implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger("betadrive");
    public static String filePath = null;
    @Override
    public void onInitialize() {
        BetadriveItems.init(); // add items
        PayloadTypeRegistry.playC2S().register(BatteryPayload.ID, BatteryPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(IsAndroidPayload.ID, IsAndroidPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(BatteryPayload.ID, (payload, context) -> BetadriveConfig.setBattery(context.player().getUuid(), payload.battery()));
        ServerPlayNetworking.registerGlobalReceiver(IsAndroidPayload.ID, (payload, context) -> BetadriveConfig.setAndroidStatus(UUID.fromString(payload.uuid()), payload.isAndroid()));
        ServerPlayerEvents.JOIN.register((pe) -> filePath = pe.getEntityWorld().getServer().getSavePath(WorldSavePath.ROOT).toAbsolutePath() + "/betadrive.json");
    }
}
