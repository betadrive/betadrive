package dev.matthy.betadrive.mixin;

import dev.matthy.betadrive.hud.MeterHUD;
import dev.matthy.betadrive.Betadrive;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public abstract class InGameHudMixin {
    @Shadow
    protected abstract PlayerEntity getCameraPlayer();
    public String saveName;
    private boolean isHudRendering = false;
    /**
     * @author Kate Matthy
     * @reason Add android HUD
     */
    @Overwrite
    private void renderCrosshair(DrawContext context, RenderTickCounter tickCounter) {
        if(Betadrive.mainHud == null || Betadrive.mainHud.cleared || (isHudRendering && !Betadrive.mainHud.finishedConverting)) return; // are we not an android, or is the hud already running?
        Betadrive.mainHud  = new MeterHUD(false, MinecraftClient.getInstance().world);
        isHudRendering = true; // don't make *another* mainHud object for no reason
        Betadrive.mainHud.finishedConverting = false;
    }
}