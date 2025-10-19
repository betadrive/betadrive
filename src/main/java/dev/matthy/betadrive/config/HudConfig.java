package dev.matthy.betadrive.config;

import net.minidev.json.JSONObject;

public class HudConfig {
    public boolean enableBatteryText = true;
    public boolean enableLevelText = true;
    public boolean enableSpeedText = true;

    public HudConfig() {
        enableBatteryText = true;
        enableLevelText = true;
        enableSpeedText = true;
    }
    public HudConfig(boolean enableBatteryText, boolean enableLevelText, boolean enableSpeedText) {
        this.enableBatteryText = enableBatteryText;
        this.enableLevelText = enableLevelText;
        this.enableSpeedText = enableSpeedText;
    }
    public JSONObject constructJSON() {
        JSONObject json = new JSONObject();
        json.put("battery", enableBatteryText);
        json.put("level", enableLevelText);
        json.put("speed", enableSpeedText);
        return json;
    }
    public static HudConfig fromJSON(JSONObject object) {
        return new HudConfig((boolean) object.get("battery"), (boolean) object.get("level"), (boolean) object.get("speed"));
    }
}
