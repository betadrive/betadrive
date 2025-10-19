package dev.matthy.betadrive;

import dev.matthy.betadrive.config.PlayerConfig;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.World;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class BetadriveConfig {
    public static void becomeAndroid(UUID uuid) { // make this AndroidPlayer an android in the cfg
        Betadrive.battery = 100d;
        Betadrive.isAndroid = true;

        ClientPlayNetworking.send(new BatteryPayload(Betadrive.battery));
        ClientPlayNetworking.send(new IsAndroidPayload(uuid.toString(), Betadrive.isAndroid));

        setAndroidStatus(uuid, true);
    }
    public static void unBecomeAndroid(UUID uuid) { // remove this AndroidPlayer from the android list in cfg
        Betadrive.isAndroid = false;

        ClientPlayNetworking.send(new BatteryPayload(Betadrive.battery));
        ClientPlayNetworking.send(new IsAndroidPayload(uuid.toString(), Betadrive.isAndroid));

        setAndroidStatus(uuid, false);
    }

    public static void fillBattery(UUID uuid, World world) {
        Betadrive.battery = 100f;

        ClientPlayNetworking.send(new BatteryPayload(Betadrive.battery));
    }
    public static double getBatteryLevel() {
        return Betadrive.battery;
    }

    public static void setJSON(JSONObject jsonObj) {
        try (FileWriter file = new FileWriter(Betadrive.filePath)) {
            file.write(jsonObj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static JSONObject getJSON() {
        JSONObject jsonObj = new JSONObject();
        try (FileReader reader = new FileReader(Betadrive.filePath)) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            if(obj instanceof JSONObject) {
                jsonObj = (JSONObject) obj;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public static void setAndroidPlayerConfig(UUID uuid, PlayerConfig cfg) {
        JSONObject jsonObj = getJSON();
        jsonObj.put(uuid.toString(), cfg.constructJSON());
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
