package dev.matthy.betadrive.config;

import java.util.LinkedHashMap;
import java.util.UUID;

public class PlayerConfig { // Used to manage the save betadrive.json file
    public transient String uuid;
    public boolean isAndroid;
    public double battery;
    public LinkedHashMap<String, Boolean> whichToEnable;

    public PlayerConfig(UUID uuid) {
        this.uuid = uuid.toString();
        this.isAndroid = false;
        this.battery = 100;
        whichToEnable = new LinkedHashMap<>();
        whichToEnable.put("BAT", true);
        whichToEnable.put("HP", true);
    }
    public PlayerConfig(UUID uuid, boolean isAndroid) {
        this.uuid = uuid.toString();
        this.isAndroid = isAndroid;
        this.battery = 100;
        whichToEnable = new LinkedHashMap<>();
        whichToEnable.put("BAT", true);
        whichToEnable.put("HP", true);
    }
    public PlayerConfig(UUID uuid, boolean isAndroid, double battery) {
        this.uuid = uuid.toString();
        this.isAndroid = isAndroid;
        this.battery = battery;
        whichToEnable = new LinkedHashMap<>();
        whichToEnable.put("BAT", true);
        whichToEnable.put("HP", true);
    }
    public PlayerConfig(UUID uuid, boolean isAndroid, double battery, LinkedHashMap<String, Boolean> whichToEnable) {
        this.uuid = uuid.toString();
        this.isAndroid = isAndroid;
        this.battery = battery;
        this.whichToEnable = whichToEnable;
    }
    
    public static PlayerConfig fromJSON(ConfigFile configFile, UUID uuid) {
        return configFile.settingsMap.getOrDefault(uuid.toString(), new PlayerConfig(uuid));
    }
}
