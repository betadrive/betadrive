package dev.matthy.betadrive.android;

import net.minecraft.entity.player.PlayerEntity;

public class AndroidPlayer {
    private PlayerEntity player;
    private boolean isAndroid;
    private String name;
    public boolean isHudDone;
    private float battery;
    public AndroidPlayer(PlayerEntity playerEntity) {
        this.player = playerEntity;
        this.isAndroid = true;
        this.isHudDone = false;
        this.name = player.getName().getString();
        this.battery = 100;
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