package com.matthyfamily.betadrive.mixin;

import com.matthyfamily.betadrive.hud.MeterHUD;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public class InGameHudMixin {
    private boolean isHudRendering = false;
    /**
     * @author Samuel-Martineau
     * @reason Prevents the default status effects overlay from being rendered
     */
    @Overwrite()
    public void renderCrosshair(MatrixStack matrixStack) {
        if(!isHudRendering) {
            isHudRendering = true;
            MeterHUD meterHud = new MeterHUD(false, MinecraftClient.getInstance().world);
        }
    }
}
