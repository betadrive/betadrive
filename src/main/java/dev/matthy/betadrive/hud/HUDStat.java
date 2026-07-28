package dev.matthy.betadrive.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;

public class HUDStat {
    public static Style betadriveFont = Style.EMPTY.withFont(new FontDescription.Resource(Identifier.fromNamespaceAndPath("betadrive", "ascii")));
    public static void printText(String text, int x, int y, int color, GuiGraphicsExtractor ctx) { // Draw `color`-colored string `text` at (`x`,`y`) using context `ctx`
        Component t = Component.literal(text).setStyle(betadriveFont);
        ctx.text(Minecraft.getInstance().font, t, x, y, color, false); // Draw the text on the screen
    }
}