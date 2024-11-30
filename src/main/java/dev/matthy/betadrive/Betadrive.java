package dev.matthy.betadrive;

import dev.matthy.betadrive.android.AndroidPlayer;
import dev.matthy.betadrive.config.BetadriveConfig;
import dev.matthy.betadrive.hud.MeterHUD;
import dev.matthy.betadrive.item.BetadriveItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.util.ActionResult;

public class Betadrive implements ModInitializer {

    public static BetadriveConfig cfg = new BetadriveConfig();
    public AndroidPlayer playerAndroid;
    public static MeterHUD mainHud;
    @Override
    public void onInitialize() {
        BetadriveItems.init(); // add items
        AttackEntityCallback.EVENT.register((player, w, h, e, hr) -> {
            double result = Math.max(0d,cfg.getBatteryLevel(player.getName().getString())-0.01d);
            cfg.setBatteryLevel(player.getName().getString(),result);
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        });
        PlayerBlockBreakEvents.AFTER.register((w, player, p, s, e) -> {
            double result = Math.max(0d,cfg.getBatteryLevel(player.getName().getString())-0.01d);
            cfg.setBatteryLevel(player.getName().getString(),result);
            if(result > 0d) return;
            player.getHungerManager().setFoodLevel(Math.max(player.getHungerManager().getFoodLevel()-1,0));
        });
        ServerPlayerEvents.AFTER_RESPAWN.register((player, d, b) -> {
            if(cfg.getBatteryLevel(player.getName().getString()) > 0) return;
            cfg.setBatteryLevel(player.getName().getString(), 30d);
        });
    }
}
