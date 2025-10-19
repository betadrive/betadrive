package dev.matthy.betadrive;

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
        Betadrive.battery = 100f;
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
    public static float getBatteryLevel() {
        return Betadrive.battery;
    }

    public static void setAndroidStatus(UUID uuid, boolean isAndroid) {
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
        jsonObj.put(uuid.toString(), isAndroid);
        try (FileWriter file = new FileWriter(Betadrive.filePath)) {
            file.write(jsonObj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean getAndroidStatus(UUID uuid) {
        try (FileReader reader = new FileReader(Betadrive.filePath)) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            if(obj instanceof JSONObject) {
                JSONObject jsonObj = (JSONObject) obj;
                return (boolean) jsonObj.get(uuid.toString());
            }
        } catch (IOException | ParseException e) {}
        return false;
    }
}
