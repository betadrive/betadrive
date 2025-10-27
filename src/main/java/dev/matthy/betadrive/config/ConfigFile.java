package dev.matthy.betadrive.config;

import java.util.LinkedHashMap;

public class ConfigFile { // For Gson. Maps player UUID as a String to a PlayerConfig for serialization/deserialization
    public LinkedHashMap<String, PlayerConfig> settingsMap;
    public ConfigFile() {
         settingsMap = new LinkedHashMap<>();
    }
    public ConfigFile(LinkedHashMap<String, PlayerConfig> settingsMap) {
        this.settingsMap = settingsMap;
    }
}
