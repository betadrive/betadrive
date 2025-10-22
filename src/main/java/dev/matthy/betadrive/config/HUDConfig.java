package dev.matthy.betadrive.config;

import dev.matthy.betadrive.hud.HUDText;
import net.minidev.json.JSONObject;

import java.util.HashMap;

public class HUDConfig { // Toggle which HUD stats are active
    public HashMap<String, Boolean> whichToEnable = new HashMap<>();
    public static HashMap<String, HUDText> possibleStats = new HashMap<>();

    public HUDConfig() {
        whichToEnable.put("BAT", true);
        whichToEnable.put("HP", true);
    }
    public HUDConfig(HashMap<String, Boolean> whichToEnable) {
        this.whichToEnable = whichToEnable;
    }
    public JSONObject constructJSON() {
        JSONObject json = new JSONObject();
        for (String key : possibleStats.keySet()) {
            json.put(key, whichToEnable.getOrDefault(key, false));
        }
        return json;
    }
    public static HUDConfig fromJSON(JSONObject object) {
        HashMap<String, Boolean> whichToEnable = new HashMap<>();
        for(String key : object.keySet()) {
             whichToEnable.put(key, (boolean) object.get(key));
         }
        return new HUDConfig(whichToEnable);
    }
    public void enableDefaults() {
        whichToEnable.put("BAT", true);
        whichToEnable.put("HP", true);
    }
}
