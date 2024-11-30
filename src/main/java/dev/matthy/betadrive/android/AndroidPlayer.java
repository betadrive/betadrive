package dev.matthy.betadrive.android;

import net.minecraft.entity.player.PlayerEntity;

public class AndroidPlayer {
    private PlayerEntity player;
    private boolean isAndroid;
    private String name;
    public boolean isHudDone;
    public AndroidPlayer(PlayerEntity playerEntity) {
        this.player = playerEntity;
        this.isAndroid = true;
        this.isHudDone = false;
        this.name = player.getName().getString();
    }
    public boolean isAndroid() {
        return this.isAndroid;
    }
    public PlayerEntity getPlayer() {
        return this.player;
    }
    public String getName() {
        return player.getName().getString();
    }
}