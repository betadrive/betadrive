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
    public static void printText(String text, int x, int y,int color, DrawContext ctx) { // Draw `color`-colored string `text` at (`x`,`y`) using context `ctx`
        MutableText t = Text.literal(text); // Get a MutableText from the raw string
        t.setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of("betadrive", "ascii")))); // Use our font (see src/main/resources/assets/betadrive/font, src/main/resources/assets/betadrive/textures/font)
        ctx.drawText(renderer,t,x,y, color,false); // Draw the text on the screen
    }

    public static void printText(Text text, int x, int y,int color, DrawContext ctx) { // Draw `color`-colored localized string `text` at (`x`,`y`) using context `ctx`. The only change from printText(String text, ...) is that this is more direct for i18n
        ((MutableText) text).setStyle(Style.EMPTY.withFont(new StyleSpriteSource.Font(Identifier.of("betadrive", "ascii")))); // Use our font (see src/main/resources/assets/betadrive/font, src/main/resources/assets/betadrive/textures/font)
        ctx.drawText(renderer, text,x,y, color,false); // Draw the text on the screen
    }
}