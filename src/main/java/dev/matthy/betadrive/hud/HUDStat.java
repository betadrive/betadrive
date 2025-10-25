package dev.matthy.betadrive.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HUDStat {
    static TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
    public static void printText(String text, int x, int y,int color, DrawContext ctx) { // draw `color`-colored string `text` at (`x`,`y`) using context `ctx`
        MutableText t = Text.literal(text);
        t.setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of("betadrive", "ascii")))); // use our font
        ctx.drawText(renderer,t,x,y, color,false);
    }
}