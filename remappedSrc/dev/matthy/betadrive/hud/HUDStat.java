package dev.matthy.betadrive.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HUDStat {
    TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
    public void printText(String text, int x, int y,int color, DrawContext ctx) {
        MutableText t = Text.literal(text);
        t.setStyle(Style.EMPTY.withFont(Identifier.of("betadrive", "ascii")));
        ctx.drawText(renderer,t,x,y,color,false);
    }
}