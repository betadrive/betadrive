package dev.matthy.betadrive.mixin;

import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.hud.MeterHUD;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public abstract class InGameHudMixin {

    @Unique
    private boolean isHudRendering = false;
    /**
     * @author Kate Matthy
     * @reason Add android HUD
     */
    @Overwrite
    private void renderCrosshair(DrawContext context, RenderTickCounter tickCounter) {
        if(BetadriveClient.mainHud == null || BetadriveClient.mainHud.cleared || (isHudRendering && !BetadriveClient.mainHud.finishedConverting)) return; // are we not an android, or is the hud already running?
        assert MinecraftClient.getInstance().world != null;
        BetadriveClient.mainHud  = new MeterHUD(false, MinecraftClient.getInstance().world);
        isHudRendering = true; // don't make *another* mainHud object for no reason
        BetadriveClient.mainHud.finishedConverting = false;
    }
}