package dev.matthy.betadrive.config;

import net.minidev.json.JSONObject;

import java.util.UUID;

public class PlayerConfig { // Used to manage the save betadrive.json file
    public String uuid;
    public boolean isAndroid;
    public double battery;
    public HUDConfig hudSettings;
    public PlayerConfig(UUID uuid) {
        this.uuid = uuid.toString();
        this.isAndroid = false;
        this.battery = 100;
        this.hudSettings = new HUDConfig();
    }
    public PlayerConfig(UUID uuid, boolean isAndroid) {
        this.uuid = uuid.toString();
        this.isAndroid = isAndroid;
        this.battery = 100;
        this.hudSettings = new HUDConfig();
    }
    public PlayerConfig(UUID uuid, boolean isAndroid, double battery) {
        this.uuid = uuid.toString();
        this.isAndroid = isAndroid;
        this.battery = battery;
        this.hudSettings = new HUDConfig();
    }
    public PlayerConfig(UUID uuid, boolean isAndroid, double battery, HUDConfig hudSettings) {
        this.uuid = uuid.toString();
        this.isAndroid = isAndroid;
        this.battery = battery;
        this.hudSettings = hudSettings;
    }
    
    public JSONObject constructJSON() {
        JSONObject object = new JSONObject();
        object.put("isAndroid", isAndroid);
        object.put("battery", battery);
        object.put("hudToggles", hudSettings.constructJSON());
        return object;
    }
    
    public static PlayerConfig fromJSON(JSONObject object, UUID uuid) {
        if(!object.containsKey(uuid.toString())) return new PlayerConfig(uuid);
        JSONObject ourObject = (JSONObject) object.get(uuid.toString());
        return new PlayerConfig(uuid, (Boolean) ourObject.get("isAndroid"), (double) ourObject.get("battery"), HUDConfig.fromJSON((JSONObject) ourObject.get("hudToggles")));
    }
}
