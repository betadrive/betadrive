package com.matthyfamily.betadrive.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.minecraft.text.LiteralText;

public class GuidebookGui extends LightweightGuiDescription {
    public GuidebookGui() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(512, 240);
        WLabel label = new WLabel(new LiteralText("Welcome to the Betadrive mod. Betadrive is a mod that lets you become an android."), 0x222222);
        root.add(label, 0, 1, 2, 1);
        WLabel label2 = new WLabel(new LiteralText("To do so, use your favorite recipe viewer mod and"), 0x222222);
        root.add(label2, 0, 2, 2, 1);
        WLabel label3 = new WLabel(new LiteralText("find the Red Pill recipe. Once you have done this, "), 0x222222);
        root.add(label3, 0, 3, 2, 1);
        WLabel label4 = new WLabel(new LiteralText("right click the Red Pill to take the pill."), 0x222222);
        root.add(label4, 0, 4, 2, 1);
        WLabel label5 = new WLabel(new LiteralText("After a bit of loading, you will now be an android."), 0x222222);
        root.add(label5, 0, 5, 2, 1);
        WLabel label6 = new WLabel(new LiteralText("This means you have capabilities past those of a"), 0x222222);
        root.add(label6, 0, 6, 2, 1);
        WLabel label7 = new WLabel(new LiteralText("puny human, and you can upgrade yourself without "), 0x222222);
        root.add(label7, 0, 7, 2, 1);
        WLabel label8 = new WLabel(new LiteralText("any pain. For example, you can craft an Absorption Upgrade "), 0x222222);
        root.add(label8, 0, 8, 2, 1);
        WLabel label9 = new WLabel(new LiteralText("and have 2 extra hearts temporarily. Join the android legion today! "), 0x222222);
        root.add(label9, 0, 9, 2, 1);
        WLabel label10 = new WLabel(new LiteralText("This guide was brought to you by [DATA EXPUNGED]."), 0x222222);
        root.add(label10, 0, 10, 2, 1);
        WLabel label11 = new WLabel(new LiteralText("To clear yourself of being an android, take the "), 0x222222);
        root.add(label11, 0, 11, 2, 1);
        WLabel label12 = new WLabel(new LiteralText("[REDACTED]"), 0x0000FF);
        root.add(label12, 0, 12, 2, 1);
        WLabel label13 = new WLabel(new LiteralText("pill to become a puny human again."), 0x222222);
        root.add(label13, 0, 13, 2, 1);

        root.validate(this);
    }
}
