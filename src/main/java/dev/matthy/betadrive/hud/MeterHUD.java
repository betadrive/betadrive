package dev.matthy.betadrive.hud;

import dev.matthy.betadrive.android.AndroidPlayer;
import dev.matthy.betadrive.Betadrive;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.matthy.betadrive.config.ConfigFile;
import dev.matthy.betadrive.hud.texts.BatteryText;
import dev.matthy.betadrive.hud.texts.LevelText;
import dev.matthy.betadrive.hud.texts.SpeedText;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@Environment(EnvType.CLIENT)
public class MeterHUD extends HUDStat implements HudRenderCallback {
    public JSONObject cfg; // parsed, = saves/<world name>/betadrive.json
    private boolean ic;
    private MinecraftClient client; // = MinecraftClient.getInstance()
    private int cyclesDone = 0;
    private World world; // approx. = MinecraftClient.getInstance().world
    private int initial; // start time for converting
    public boolean cleared = false; // blue pill used? set with .clear()
    public String cfgJsonString = Betadrive.cfg.data;
    private AndroidPlayer androidPlayer; // user's AndroidPlayer
    public boolean finishedConverting = false; // are we done with the cliche animation?
    public String playerName; // user's IGN
    public void clear() {cleared = true; }
    public MeterHUD(boolean isCONVERTING, World worlda) {
        loadFromString();
        androidPlayer = new AndroidPlayer(MinecraftClient.getInstance().player);

        playerName = androidPlayer.getName();
        this.initial = (int) worlda.getTime();
        this.ic = isCONVERTING;
        this.world = worlda;
        client = MinecraftClient.getInstance();
        EVENT.register((ctx, ctr) -> this.onHudRender(ctx, ctr));
    }

    public void loadFromString() { // load cfg to this obj\
        cfgJsonString = Betadrive.cfg.read() == null ? ConfigFile.initData : Betadrive.cfg.read().toJSONString();
        cfg = (JSONObject) JSONValue.parse(cfgJsonString);
    }
    public boolean hasUsername(String ign) { // does the cfg file have a username?
        try { loadFromString(); } catch(Exception e) {}
        if(cfg == null) return false;
        return ((JSONArray) cfg.getOrDefault("dat", new JSONArray())).contains(ign);
    }
    public void transformationAnimation(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        cyclesDone = (int) world.getTime() - initial;
        if((hasUsername(androidPlayer.getName())) || !ic || cyclesDone == 400) return;
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

            client.player.sendMessage(Text.of("You feel unstoppable."), true); // shows where item name shows

            Betadrive.cfg.becomeAndroid(new AndroidPlayer(client.player)); // set cfg
            cleared = false; // disable prev. blue pills
            ic = false; // no longer converting (Is not Converting)
            Betadrive.mainHud = this; // set the hud to this object for use elsewhere
            finishedConverting = true; // we're done, reset the UI in InGameHudMixin et al.
            loadFromString();
        }
        client.player.getHungerManager().setFoodLevel(19); // fill hunger
    }

    public void hudAnimation(DrawContext drawContext, RenderTickCounter renderTickCounter) { // when you *are* an android and we're just rendering the HUD
        if(!hasUsername(playerName) || cleared || client.player == null) return; // checks to make sure you *are* an android, haven't taken the blue pill, and aren't null (somehow)
        RenderSystem.enableBlend();
        String hudText = HUDText.build(new SpeedText(), new LevelText(), new BatteryText());
        printText(hudText, 12, 12, 0xA9E2FB, drawContext);
    }
    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) { // go through each of the renders. if the first doesn't run, it will return so we don't need to worry about double-rendering 2 different UIs
        transformationAnimation(drawContext,renderTickCounter);
        hudAnimation(drawContext,renderTickCounter);
    }
}