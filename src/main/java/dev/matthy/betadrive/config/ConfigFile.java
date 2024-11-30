package dev.matthy.betadrive.config;

import dev.matthy.betadrive.Betadrive;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.WorldSavePath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ConfigFile {
    public static final String initData = "{\"dat\":[],\"props\":{}}";
    public String data = initData;
    public JSONObject cfg;
    public String savename;
    public void setSavename(String sn) {
        System.out.println(sn);
        savename = sn;
    }
    public void refreshConfig() {
        loadFromDisk();
        if(Betadrive.mainHud == null) return;
        Betadrive.mainHud.cfgJsonString = data;
        Betadrive.mainHud.cfg = cfg;
    }
    public void loadFromDisk() {
        data = readFileAsString(getPath());
        data = readFileAsString(getPath());
        cfg = (JSONObject) JSONValue.parse(data);
    }
    public static String getPath() { // get the path to the config file, usually ~/.minecraft/saves/<world name>/betadrive.json
        String r = "";
        try {
            return MinecraftClient.getInstance().getServer().getSavePath(WorldSavePath.ROOT).toString().replace("/.", "")+ "/betadrive.json";

        } catch(Exception e) {
            e.printStackTrace();
        }
        return r;
    }
    public String readFileAsString(String fileName)
    {
        try {
            data = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public boolean isExistant() { // does the file exist
        return new File(getPath()).exists();
    }
    public void create() { // make the cfg file on disk
        if(isExistant()) return;
        try {
            Files.write(Paths.get(getPath()), Arrays.asList(data.split("\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void overwrite() { // reset the cfg file
        if(!isExistant()) return;
        try {
            Files.write(Paths.get(getPath()), Arrays.asList(data.split("\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public JSONObject read() { // get cfg file as JSONObject
        create();
        try {
            return (JSONObject) JSONValue.parse(readFileAsString(getPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void append(String str) { // add to dat/android list
        JSONObject jsonObject = null;
        try {
            jsonObject = read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray array = ((JSONArray) jsonObject.get("dat"));
        array.add(str);
        jsonObject.put("dat",  array);
        try {
            Files.write(Paths.get(getPath()), Arrays.asList(jsonObject.toJSONString().split("\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshConfig();
    }
    public void setBatteryLevel(String ign, double batteryLevel) { // add to dat/android list
        JSONObject jsonObject = null;
        try {
            jsonObject = read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject object = ((JSONObject) jsonObject.get("props"));
        JSONObject playerProps = ((JSONObject) object.getOrDefault(ign, new JSONObject()));
        playerProps.put("battery", batteryLevel);
        object.put(ign, playerProps);
        jsonObject.put("props",  object);
        try {
            Files.write(Paths.get(getPath()), Arrays.asList(jsonObject.toJSONString().split("\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshConfig();
    }
    public double getBatteryLevel(String ign) {
        JSONObject readResult = read();
        if(readResult == null) readResult = new JSONObject();
        return (double) (((JSONObject) (((JSONObject) readResult.getOrDefault("props", new JSONObject())).getOrDefault(ign, new JSONObject()))).getOrDefault("battery", 100d));
    }
    public void remove(String str) { // remove from dat/android list
        JSONObject jsonObject = null;
        try {
            jsonObject = read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray array = ((JSONArray) jsonObject.get("dat"));
        array.remove(str);
        jsonObject.put("dat",  array);
        try {
            Files.write(Paths.get(getPath()), Arrays.asList(jsonObject.toJSONString().split("\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshConfig();
    }
}