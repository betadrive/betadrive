package api.com.matthyfamily.betadrive.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;

public class HUDStat {
    private boolean cleared;

    public void clear() {
        cleared = true;
    }
    public void unclear() {
        cleared = false;
    }
    public void printText(String text, int x, int y,int color) {
        LiteralText t = new LiteralText(text);
        t.setStyle(Style.EMPTY.withFont(new Identifier("betadrive", "ascii")));
        MinecraftClient.getInstance().textRenderer.draw(new MatrixStack(), t, x, y, color);
    }
}
