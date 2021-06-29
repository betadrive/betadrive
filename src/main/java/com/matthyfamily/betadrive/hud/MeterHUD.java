package com.matthyfamily.betadrive.hud;

import api.com.matthyfamily.betadrive.android.AndroidPlayer;
import api.com.matthyfamily.betadrive.hud.HUDStat;
import com.matthyfamily.betadrive.Betadrive;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class MeterHUD extends HUDStat {
    private boolean ic;
    private MinecraftClient client;
    private int cyclesDone = 0;
    private World world;
    private int initial;
    private boolean cleared;
    public void clear() {cleared = true;}
    public MeterHUD(boolean isCONVERTING, World worlda) {
        this.initial = (int) worlda.getTime();
        this.ic = isCONVERTING;
        this.world = worlda;
        client = MinecraftClient.getInstance();
        HudRenderCallback.EVENT.register((__, ___) -> this.render());
    }

    private void render() {
        try {
            client.player.getHungerManager().setFoodLevel(19);
            if((!Betadrive.cfg.read().toJSONString().contains(new AndroidPlayer(MinecraftClient.getInstance().player).getName())) && ic) {
                cyclesDone = (int) world.getTime() - initial;
                if(ic && !(cyclesDone == 400)) {
                    if(cyclesDone > 0  && cyclesDone < 160) {
                            RenderSystem.enableBlend();
                            printText("BIOLOGICAL PRESENCE DETECTED.", 36, 50, 0xA9E2FB);
                            printText("RELEASING NANOBOTS TO CONVERT BIOLOGICAL PERSENCE", 36, 60, 0xA9E2FB);
                            printText("TO ANDROID.", 36, 70, 0xA9E2FB);
                    } else if(cyclesDone > 160 && cyclesDone < 280) {
                            printText("CONVERTING ALL BODY PARTS...", 36, 50, 0xA9E2FB);
                            printText("...done.", 36, 60, 0xA9E2FB);
                    } else if(cyclesDone > 280 && cyclesDone < 340) {
                            printText("CONVERSION PROCESSING", 36, 50, 0xA9E2FB);
                            printText("BRAIN CONVERTING TO PROCESSING UNIT..", 36, 60, 0xA9E2FB);
                    } else if(cyclesDone > 340 && cyclesDone < 360) {
                        printText("...done.", 36, 50, 0xA9E2FB);
                        printText("Body conversion complete.", 36, 60, 0xA9E2FB);

                        client.player.sendMessage(Text.of("You feel unstoppable."), true);

                        Betadrive.cfg.becomeAndroid(new AndroidPlayer(client.player));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(Betadrive.cfg.read().toJSONString().contains(MinecraftClient.getInstance().player.getName().asString()) && !cleared){
                if (client.player == null) {
                    System.out.println("player is null");
                    return;
                }
                RenderSystem.enableBlend();
                String averageSpeed = String.valueOf(Math.round(1000 * client.player.getMovementSpeed()));
                printText("[XP==" + client.player.totalExperience + " MPS==" + averageSpeed + "]", 12, 12, 0xA9E2FB);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
