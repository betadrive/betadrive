package dev.matthy.betadrive;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.matthy.betadrive.client.BetadriveClient;
import dev.matthy.betadrive.config.ConfigFile;
import dev.matthy.betadrive.config.PlayerConfig;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.UUID;

public class BetadriveConfig {
    private static final Gson gson = new Gson();
    public static void becomeAndroid(UUID uuid) { // make this AndroidPlayer an android in the cfg
        if(uuid == BetadriveClient.playerUUID) {
            BetadriveClient.battery = 100d;
            BetadriveClient.isAndroid = true;
            ClientPlayNetworking.send(new BatteryPayload(100d));
            ClientPlayNetworking.send(new IsAndroidPayload(uuid.toString(), BetadriveClient.isAndroid));
        }
        PlayerConfig config = getAndroidPlayerConfig(uuid);
        config.isAndroid = true;
        config.whichToEnable = new LinkedHashMap<>();
        config.whichToEnable.put("BAT", true);
        config.whichToEnable.put("HP", true);
        setAndroidPlayerConfig(uuid, config);
    }
    public static void unBecomeAndroid(UUID uuid) { // remove this AndroidPlayer from the android list in cfg
        if(uuid == BetadriveClient.playerUUID) {
            BetadriveClient.isAndroid = false;
            ClientPlayNetworking.send(new BatteryPayload(BetadriveClient.battery));
            ClientPlayNetworking.send(new IsAndroidPayload(uuid.toString(), BetadriveClient.isAndroid));
        }
        setAndroidStatus(uuid, false);
    }

    public static void fillBattery(UUID uuid) {
        if(uuid == BetadriveClient.playerUUID) {
            BetadriveClient.battery = 100f;
            ClientPlayNetworking.send(new BatteryPayload(BetadriveClient.battery));
        }
        setBattery(uuid, BetadriveClient.battery);
    }
    public static double getBatteryLevel() {
        return BetadriveClient.battery;
    }

    public static void setJSON(ConfigFile jsonObj) {
        try (FileWriter file = new FileWriter(Betadrive.filePath)) {
            file.write(gson.toJson(jsonObj.settingsMap));
        } catch (IOException e) {
            Betadrive.LOGGER.warn("Failed to save config to {}", Betadrive.filePath);
        }
    }
    public static ConfigFile getJSON() {
        ConfigFile configFile = new ConfigFile();
        try (FileReader reader = new FileReader(Betadrive.filePath)) {
            Gson gson = new Gson();
            Type hmType = new TypeToken<LinkedHashMap<String, PlayerConfig>>() {}.getType();
            LinkedHashMap<String, PlayerConfig> map = gson.fromJson(reader, hmType);
            if(map != null) configFile = new ConfigFile(map);

        } catch (IOException ignored) {
            configFile = new ConfigFile();
        }
        return configFile;
    }

    public static void setAndroidPlayerConfig(UUID uuid, PlayerConfig cfg) {
        ConfigFile jsonObj = getJSON();
        jsonObj.settingsMap.put(uuid.toString(), cfg);
        setJSON(jsonObj);
    }
    public static PlayerConfig getAndroidPlayerConfig(UUID uuid) {
        return PlayerConfig.fromJSON(getJSON(), uuid);
    }

    public static void setAndroidStatus(UUID uuid, boolean isAndroid) {
        PlayerConfig cfg = getAndroidPlayerConfig(uuid);
        cfg.isAndroid = isAndroid;
        setAndroidPlayerConfig(uuid, cfg);
    }
    public static boolean getAndroidStatus(UUID uuid) {
        return getAndroidPlayerConfig(uuid).isAndroid;
    }
    public static void setBattery(UUID uuid, double battery) {
        PlayerConfig cfg = getAndroidPlayerConfig(uuid);
        cfg.battery = battery;
        setAndroidPlayerConfig(uuid, cfg);
    }
    public static double getBattery(UUID uuid) {
        return getAndroidPlayerConfig(uuid).battery;
    }
}
