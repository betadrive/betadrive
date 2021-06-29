package api.com.matthyfamily.betadrive.config;

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
    public String data = "{\"dat\":[],\"explosionSize\":32,\"chargePerTick\":512}";
    public String savename;
    public void setSavename(String sn) {
        System.out.println(sn);
        savename = sn;
    }
    public static String getPath() {
        String r = "";
        try {
            try {
                return (MinecraftClient.getInstance().getServer().getRunDirectory().getPath()+"\\saves\\"+
                        (
                                (MinecraftClient.getInstance().getServer().getSavePath(WorldSavePath.ROOT).toString())
                                        .replace(MinecraftClient.getInstance().getServer().getRunDirectory().getPath()+"\\saves\\", "")
                                        +"/betadrive")).replace("\\.\\", "\\").replace("\\./", "\\").replace("/.\\", "\\").replace("/./", "\\");
            } catch(Exception e) {
                return FabricLoader.getInstance().getGameDir().toString() + "\\betadrive";
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println(r);
        return r;
        /*if(MinecraftClient.getInstance().getServer() == null) {
            return MinecraftClient.getInstance().getServer().getRunDirectory().getPath()+"/saves/"+MinecraftClient.getInstance().world.getServer().getName()+"/betadrive";
        }  else {
            return MinecraftClient.getInstance().runDirectory.getPath()+"/config/betadrive";
        }*/
    }
    public String readFileAsString(String fileName)
    {
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public boolean isExistant() {
        if(new File(getPath()+".json").exists()) {
            return true;
        }
        return false;
    }
    public void create() {
        if(!isExistant()) {
            try {
                Files.write(Paths.get(getPath() + ".json"), Arrays.asList(data.split("\n")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void overwrite() {
        if(isExistant()) {
            try {
                Files.write(Paths.get(getPath() + ".json"), Arrays.asList(data.split("\n")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public JSONObject read() {
        create();
        try {
            return (JSONObject) JSONValue.parse(readFileAsString(getPath()+".json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void append(String str) {
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
            Files.write(Paths.get(getPath() + ".json"), Arrays.asList(jsonObject.toJSONString().split("\n")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
