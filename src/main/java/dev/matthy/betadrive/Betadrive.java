package dev.matthy.betadrive;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import dev.matthy.betadrive.hud.HUDTexts;
import dev.matthy.betadrive.hud.texts.*;
import dev.matthy.betadrive.item.BetadriveItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.argument.GameProfileArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.server.PlayerConfigEntry;
import net.minecraft.text.Text;
import net.minecraft.util.WorldSavePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

import static net.minecraft.server.command.CommandManager.*;

public class Betadrive implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger("betadrive");
    public static String filePath = null;
    public static boolean updateAndroidStatus = false;
    public static boolean updateBattery = false;
    @Override
    public void onInitialize() {
        HUDTexts.register(new BatteryText(), new HealthText(), new HungerText(), new LevelText(), new SpeedText());
        BetadriveItems.init(); // add items
        PayloadTypeRegistry.playC2S().register(BatteryPayload.ID, BatteryPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(IsAndroidPayload.ID, IsAndroidPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(BatteryPayload.ID, (payload, context) -> BetadriveConfig.setBattery(context.player().getUuid(), payload.battery()));
        ServerPlayNetworking.registerGlobalReceiver(IsAndroidPayload.ID, (payload, context) -> BetadriveConfig.setAndroidStatus(UUID.fromString(payload.uuid()), payload.isAndroid()));
        ServerPlayerEvents.JOIN.register((pe) -> filePath = pe.getEntityWorld().getServer().getSavePath(WorldSavePath.ROOT).toAbsolutePath() + "/betadrive.json");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("betadrive")
                .requires(source -> source.hasPermissionLevel(2))
                        .then(literal("android")
                        .then(literal("get")
                        .executes(context -> {
                            context.getSource().sendFeedback(() -> Text.literal("No player name provided!"), false);
                            return 0;
                        })
                        .then(argument("name", GameProfileArgumentType.gameProfile())
                        .executes(context -> {
                            final PlayerConfigEntry value = GameProfileArgumentType.getProfileArgument(context, "name").stream().findFirst().orElseThrow();
                            boolean isAndroid =  BetadriveConfig.getAndroidStatus(value.id());
                            context.getSource().sendFeedback(() -> Text.literal(String.format("%s is%s an android", value.name(), isAndroid ? "" : " not")), false);
                            return isAndroid ? 1 : 0;
                        })))
                .then(literal("set")
                        .executes(context -> {
                            context.getSource().sendFeedback(() -> Text.literal("No player name provided!"), false);
                            return 0;
                        })
                        .then(argument("name", GameProfileArgumentType.gameProfile())
                                .then(argument("androidStatus", BoolArgumentType.bool())
                                        .executes(context -> {
                                            final PlayerConfigEntry value = GameProfileArgumentType.getProfileArgument(context, "name").stream().findFirst().orElseThrow();
                                            boolean setTo = BoolArgumentType.getBool(context, "androidStatus");
                                            BetadriveConfig.setAndroidStatus(value.id(), setTo);
                                            Betadrive.updateAndroidStatus = true;
                                            context.getSource().sendFeedback(() -> Text.literal(String.format("%s is now%s an android", value.name(), setTo ? "" : " not")), false);
                                            return setTo ? 1 : 0;
                                        })
                                )
                        )
                )).then(literal("battery")
                        .then(literal("get")
                                .executes(context -> {
                                    context.getSource().sendFeedback(() -> Text.literal("No player name provided!"), false);
                                    return 0;
                                })
                                .then(argument("name", GameProfileArgumentType.gameProfile())
                                        .executes(context -> {
                                            final PlayerConfigEntry value = GameProfileArgumentType.getProfileArgument(context, "name").stream().findFirst().orElseThrow();
                                            double battery = BetadriveConfig.getBattery(value.id());
                                            context.getSource().sendFeedback(() -> Text.literal(String.format("%s at %.2f%% battery", value.name(), battery)), false);
                                            return (int) battery;
                                        })))
                        .then(literal("set")
                                .executes(context -> {
                                    context.getSource().sendFeedback(() -> Text.literal("No player name provided!"), false);
                                    return 0;
                                })
                                .then(argument("name", GameProfileArgumentType.gameProfile())
                                        .then(argument("percent", DoubleArgumentType.doubleArg())
                                                .executes(context -> {
                                                    final PlayerConfigEntry value = GameProfileArgumentType.getProfileArgument(context, "name").stream().findFirst().orElseThrow();
                                                    double setTo = DoubleArgumentType.getDouble(context, "percent");
                                                    BetadriveConfig.setBattery(value.id(), setTo);
                                                    Betadrive.updateBattery = true;
                                                    context.getSource().sendFeedback(() -> Text.literal(String.format("%s is now at %.2f%% battery", value.name(), setTo)), false);
                                                    return (int) setTo;
                                                })
                                        )
                                )
                        )
                )
        ));
    }
}
