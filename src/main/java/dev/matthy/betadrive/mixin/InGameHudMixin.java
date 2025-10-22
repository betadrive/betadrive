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
        if(MeterHUD.clearAnimation || (isHudRendering && BetadriveClient.isConverting) || !BetadriveClient.isAndroid) return; // Checks if (a) player turned back into human (i.e. via Blue Pill), (b) player is currently becoming an android, or (c) player is not an android and is not converting. If any of these are true, exit early
        assert MinecraftClient.getInstance().world != null;
        MeterHUD.render(context, tickCounter);
    }
}