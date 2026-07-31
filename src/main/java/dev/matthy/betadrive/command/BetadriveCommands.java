package dev.matthy.betadrive.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import dev.matthy.betadrive.Betadrive;
import dev.matthy.betadrive.BetadriveConfig;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.server.players.NameAndId;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class BetadriveCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("betadrive") // /betadrive
                .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_MODERATOR))
                .then(literal("android") // /betadrive android
                        .then(literal("get") // /betadrive android get --> runs // /betadrive android get @s
                                .executes(context -> {
                                    ServerPlayer value = context.getSource().getPlayer();
                                    boolean isAndroid =  BetadriveConfig.getAndroidStatus(value.getUUID());
                                    context.getSource().sendSuccess(() -> Component.literal(String.format("%s is%s an android", value.getPlainTextName(), isAndroid ? "" : " not")), false);
                                    return isAndroid ? 1 : 0;
                                })
                                .then(argument("name", GameProfileArgument.gameProfile()) // /betadrive android get [username] --> returns "[username] is an android" if user is android, and "[username] is not an android" if not
                                        .executes(context -> {
                                            final NameAndId value = GameProfileArgument.getGameProfiles(context, "name").stream().findFirst().orElseThrow();
                                            boolean isAndroid =  BetadriveConfig.getAndroidStatus(value.id());
                                            if(isAndroid) context.getSource().sendSuccess(() -> Component.translatable("commands.betadrive.playerIsAndroid", value.name()), false);
                                            else context.getSource().sendSuccess(() -> Component.translatable("commands.betadrive.playerIsNotAndroid", value.name()), false);
                                            return isAndroid ? 1 : 0;
                                        })))
                        .then(literal("set")
                                .executes(context -> { // /betadrive android set
                                    context.getSource().sendFailure(Component.literal("No player name provided!"));
                                    return 0;
                                })
                                .then(argument("androidStatus", BoolArgumentType.bool()) // /betadrive android set [status] --> runs /betadrive android set @s
                                        .executes(context -> {
                                            final ServerPlayer value = context.getSource().getPlayer(); // Get sender
                                            boolean setTo = BoolArgumentType.getBool(context, "androidStatus"); // Get sender's android status parameter
                                            BetadriveConfig.setAndroidStatus(value.getUUID(), setTo); // Set the status using the sender's UUID since [name] was not provided
                                            Betadrive.updateAndroidStatus = true; // Make everyone's android status update on clientside
                                            if(setTo) context.getSource().sendSuccess(() -> Component.translatable("commands.betadrive.playerAssignAndroid", value.getPlainTextName()), false); // Say that [username] is an android now, if we're setting them to be one
                                            else context.getSource().sendSuccess(() -> Component.translatable("commands.betadrive.playerAssignNotAndroid", value.getPlainTextName()), false); //  Otherwise, say that [username] is no longer an android
                                            return setTo ? 1 : 0; // Return 1 if setting to android, 0 if not
                                        })
                                )
                                .then(argument("name", GameProfileArgument.gameProfile()) // betadrive android set [name]
                                        .then(argument("androidStatus", BoolArgumentType.bool())  // betadrive android set [name] [status]
                                                .executes(context -> {
                                                    final NameAndId value = GameProfileArgument.getGameProfiles(context, "name").stream().findFirst().orElseThrow(); // This time, we take the parameter and get the first in the stream from the selector. This makes it only accept 1 player
                                                    boolean setTo = BoolArgumentType.getBool(context, "androidStatus"); // Get what we're setting [name]'s status to
                                                    BetadriveConfig.setAndroidStatus(value.id(), setTo); // Set the status
                                                    Betadrive.updateAndroidStatus = true; // Tell every client to update their clientside status for HUD purposes et al.
                                                    if(setTo) context.getSource().sendSuccess(() -> Component.translatable("commands.betadrive.playerAssignAndroid", value.name()), false); // Respond that we've set [name] to an android if that's what they wanted
                                                    else context.getSource().sendSuccess(() -> Component.translatable("commands.betadrive.playerAssignNotAndroid", value.name()), false); // Or if they wanted them to be human, say that
                                                    return setTo ? 1 : 0; // Return 1 if setting to android, 0 if not
                                                })
                                        )
                                )
                        )).then(literal("battery") // /betadrive battery
                        .then(literal("get") // /betadrive battery get --> runs /betadrive battery get @s
                                .executes(context -> {
                                    final ServerPlayer value = context.getSource().getPlayer(); // Get the runner
                                    double battery = BetadriveConfig.getBattery(value.getUUID()); // Get their battery from save config
                                    context.getSource().sendSuccess(() -> Component.translatable("commands.betadrive.playerBatteryGet", value.getPlainTextName(), battery), false); // Send their battery to player
                                    return (int) battery; // Return floor-ed battery since we can only return ints
                                })
                                .then(argument("name", GameProfileArgument.gameProfile()) // /betadrive battery get [name]
                                        .executes(context -> {
                                            final NameAndId value = GameProfileArgument.getGameProfiles(context, "name").stream().findFirst().orElseThrow(); // Get the user from [name]
                                            double battery = BetadriveConfig.getBattery(value.id()); // Get their battery from save config
                                            context.getSource().sendSuccess(() -> Component .translatable("commands.betadrive.playerBatteryGet", value.name(), battery), false); // Localized response of their battery
                                            return (int) battery; // Return floor-ed battery
                                        })))
                        .then(literal("set") // /betadrive battery set
                                .executes(context -> {
                                    context.getSource().sendSuccess(() -> Component.literal("No player name & charge provided!"), false);
                                    return 0;
                                })
                                .then(argument("percent", DoubleArgumentType.doubleArg()) // /betadrive battery set [percent] --> runs /betadrive battery set @s [percent]
                                        .executes(context -> {
                                            final ServerPlayer value = context.getSource().getPlayer(); // Get the runner
                                            double setTo = DoubleArgumentType.getDouble(context, "percent"); // Get what they want their battery set to
                                            BetadriveConfig.setBattery(value.getUUID(), setTo); // Set the battery
                                            Betadrive.updateBattery = true; // Make clients update their battery for HUD purposes et al.
                                            context.getSource().sendSuccess(() -> Component.translatable("commands.betadrive.playerBatterySet", value.getPlainTextName(), setTo), false); // Send what we set [user] to
                                            return (int) setTo; // Return floor-ed battery
                                        })
                                )
                                .then(argument("name", GameProfileArgument.gameProfile()) // /betadrive battery set [name] [percent]
                                        .then(argument("percent", DoubleArgumentType.doubleArg())
                                                .executes(context -> {
                                                    final NameAndId value = GameProfileArgument.getGameProfiles(context, "name").stream().findFirst().orElseThrow(); // Get specific user
                                                    double setTo = DoubleArgumentType.getDouble(context, "percent"); // Get their battery set value
                                                    BetadriveConfig.setBattery(value.id(), setTo); // Set the battery for that user
                                                    Betadrive.updateBattery = true; // Tell clients to update battery on clientside
                                                    context.getSource().sendSuccess(() -> Component.translatable("commands.betadrive.playerBatterySet", value.name(), setTo), false); // Respond that we've succeeded
                                                    return (int) setTo; // Return floor-ed battery
                                                })
                                        )
                                )
                        )
                )
        ));
    }
}
