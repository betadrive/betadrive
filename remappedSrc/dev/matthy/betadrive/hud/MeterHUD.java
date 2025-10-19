package dev.matthy.betadrive.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.matthy.betadrive.Betadrive;
import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.android.AndroidPlayer;
import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.hud.texts.BatteryText;
import dev.matthy.betadrive.hud.texts.LevelText;
import dev.matthy.betadrive.hud.texts.SpeedText;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class MeterHUD extends HUDStat implements HudRenderCallback {
    private boolean ic; // is converting
    private final MinecraftClient client; // = MinecraftClient.getInstance()
    private final World world; // approx. = MinecraftClient.getInstance().world
    private final int initial; // start time for converting
    public boolean cleared = false; // blue pill used? set with .clear()
    public boolean finishedConverting = false; // are we done with the cliché animation?
    public UUID playerName; // user's UUID

    public void clear() {cleared = true; }
    public MeterHUD(boolean isCONVERTING, World world) {
        // user's AndroidPlayer
        AndroidPlayer androidPlayer = new AndroidPlayer(MinecraftClient.getInstance().player);
        playerName = androidPlayer.getPlayer().getUuid();
        this.initial = (int) world.getTime();
        this.ic = isCONVERTING;
        this.world = world;
        client = MinecraftClient.getInstance();
        EVENT.register(this);
    }
    public void transformationAnimation(DrawContext drawContext) { // text popup on screen that appears when taking red pill/converting to android
        int cyclesDone = (int) world.getTime() - initial; // ticks (?) since animation started
        if(Betadrive.isAndroid || !ic || cyclesDone == 400) return;
        if(cyclesDone > 0  && cyclesDone < 160) { // jank method for delays, each value *should* correspond to ticks?
            RenderSystem.enableBlend();
            printText("BIOLOGICAL PRESENCE DETECTED.", 36, 50, 0xA9E2FB, drawContext);
            printText("RELEASING NANOBOTS TO CONVERT BIOLOGICAL PRESENCE", 36, 60, 0xA9E2FB, drawContext);
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

            assert client.player != null;
            client.player.sendMessage(Text.of("You feel unstoppable."), true); // shows where item name shows

            BetadriveConfig.becomeAndroid(); // set cfg
            cleared = false; // disable prev. blue pills
            ic = false; // no longer converting (Is not Converting)
            BetadriveClient.mainHud = this; // set the hud to this object for use elsewhere
            finishedConverting = true; // we're done, reset the UI in InGameHudMixin et al.
        }
        assert client.player != null;
        client.player.getHungerManager().setFoodLevel(19); // fill hunger
    }


    public void hudAnimation(DrawContext drawContext) { // when you *are* an android, and we're just rendering the HUD
        if(!Betadrive.isAndroid || cleared || client.player == null) return; // checks to make sure you *are* an android, haven't taken the blue pill, and aren't null (somehow)
        RenderSystem.enableBlend();
        String hudText = HUDText.build(new SpeedText(), new LevelText(), new BatteryText());
        printText(hudText, 12, 12, 0xA9E2FB, drawContext);
    }
    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) { // go through each of the renders. if the first doesn't run, it will return so we don't need to worry about double-rendering 2 different UIs
        transformationAnimation(drawContext);
        hudAnimation(drawContext);
    }
}