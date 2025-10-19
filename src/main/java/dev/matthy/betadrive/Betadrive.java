package dev.matthy.betadrive;

import dev.matthy.betadrive.item.BetadriveItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.ActionResult;
import net.minecraft.util.WorldSavePath;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import net.minidev.json.reader.JsonWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.UUID;

public class Betadrive implements ModInitializer {
    public static float battery = 100;
    public static boolean isAndroid = false;
    public static boolean isConverting = false;
    public static String filePath = null;
    @Override
    public void onInitialize() {
        BetadriveItems.init(); // add items

        PayloadTypeRegistry.playC2S().register(BatteryPayload.ID, BatteryPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(IsAndroidPayload.ID, IsAndroidPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(BatteryPayload.ID, (payload, context) -> battery = payload.battery());
        ServerPlayNetworking.registerGlobalReceiver(IsAndroidPayload.ID, (payload, context) -> {
            BetadriveConfig.setAndroidStatus(UUID.fromString(payload.uuid()), payload.isAndroid());
        });
        ServerPlayerEvents.JOIN.register((pe) -> {
            filePath = pe.getEntityWorld().getServer().getSavePath(WorldSavePath.ROOT).toAbsolutePath().toString()+"/betadrive.json";
        });
        ServerPlayerEvents.JOIN.register((playerEntity) -> isAndroid = BetadriveConfig.getAndroidStatus(playerEntity.getUuid()));
        AttackEntityCallback.EVENT.register((player, w, h, e, hr) -> {
            float result = (float) Math.max(0d, battery - 0.01d);
            battery = result;
            if (result >= 0) return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
            player.getHungerManager().setFoodLevel(Math.max(player.getHungerManager().getFoodLevel() - 1, 0));
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        });

        PlayerBlockBreakEvents.AFTER.register((w, player, bp, bs, be) -> {
            float result = (float) Math.max(0d, battery - 0.01d);
            battery = result;
            if (result >= 0) return;
            player.getHungerManager().setFoodLevel(Math.max(player.getHungerManager().getFoodLevel() - 1, 0));
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((player, d, b) -> battery = (float) Math.max(30d, battery));

//        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("betadrive")
//                        .requires(source -> source.hasPermissionLevel(2))
//                        .then(literal("android")
//                                .then(literal("add").then(CommandManager.argument("username", StringArgumentType.greedyString()).executes(context -> {
//                                    final String username = StringArgumentType.getString(context, "username");
//                                    Betadrive.cfg.becomeAndroid(username);
//                                    context.getSource().sendFeedback(() -> Text.literal("Transformed %s into an android".formatted(username)), false);
//                                    return 1;
//                                }))).then(literal("remove").then(CommandManager.argument("username",StringArgumentType.greedyString())).executes(context -> {
//                                    final String username = StringArgumentType.getString(context, "username");
//                                    Betadrive.cfg.unBecomeAndroid(username);
//                                    context.getSource().sendFeedback(() -> Text.literal("Transformed %s into an human".formatted(username)), false);
//                                    return 1;
//                                })).executes(context -> {
//                                    context.getSource().sendFeedback(() -> Text.literal("o/"), false);
//                                    return 1;
//                                }))));
    }
}
