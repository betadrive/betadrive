package dev.matthy.betadrive.hud;

import dev.matthy.betadrive.android.AndroidPlayer;
import dev.matthy.betadrive.Betadrive;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class MeterHUD extends HUDStat implements HudRenderCallback {
    private boolean ic;
    private MinecraftClient client;
    private int cyclesDone = 0;
    private World world;
    private int initial;
    private boolean cleared;
    private String cfgJsonString = Betadrive.cfg.read().toJSONString();
    public void clear() {cleared = true;}
    public MeterHUD(boolean isCONVERTING, World worlda) {
        this.initial = (int) worlda.getTime();
        this.ic = isCONVERTING;
        this.world = worlda;
        client = MinecraftClient.getInstance();
        EVENT.register((ctx,ctr) -> this.onHudRender(ctx,ctr));
    }
    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        try {
            client.player.getHungerManager().setFoodLevel(19);
            if((!cfgJsonString.contains(new AndroidPlayer(MinecraftClient.getInstance().player).getName())) && ic) {
                cyclesDone = (int) world.getTime() - initial;
                if(ic && !(cyclesDone == 400)) {
                    if(cyclesDone > 0  && cyclesDone < 160) {
                        RenderSystem.enableBlend();
                        printText("BIOLOGICAL PRESENCE DETECTED.", 36, 50, 0xA9E2FB, drawContext);
                        printText("RELEASING NANOBOTS TO CONVERT BIOLOGICAL PERSENCE", 36, 60, 0xA9E2FB, drawContext);
                        printText("TO ANDROID.", 36, 70, 0xA9E2FB, drawContext);
                    } else if(cyclesDone > 160 && cyclesDone < 280) {
                        printText("CONVERTING ALL BODY PARTS...", 36, 50, 0xA9E2FB, drawContext);
                        printText("...done.", 36, 60, 0xA9E2FB, drawContext);
                    } else if(cyclesDone > 280 && cyclesDone < 340) {
                        printText("CONVERSION PROCESSING", 36, 50, 0xA9E2FB, drawContext);
                        printText("BRAIN CONVERTING TO PROCESSING UNIT..", 36, 60, 0xA9E2FB, drawContext);
                    } else if(cyclesDone > 340 && cyclesDone < 360) {
                        printText("...done.", 36, 50, 0xA9E2FB, drawContext);
                        printText("Body conversion complete.", 36, 60, 0xA9E2FB, drawContext);

                        client.player.sendMessage(Text.of("You feel unstoppable."), true);

                        Betadrive.cfg.becomeAndroid(new AndroidPlayer(client.player));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(cfgJsonString.contains(MinecraftClient.getInstance().player.getName().getString()) && !cleared){
                if (client.player == null) {
                    System.out.println("player is null");
                    return;
                }
                RenderSystem.enableBlend();
                String averageSpeed = String.valueOf(Math.round(1000 * client.player.getMovementSpeed()));
                printText("[XP=" + client.player.totalExperience + " MPS=" + averageSpeed + "]", 12, 12, 0xA9E2FB, drawContext);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}