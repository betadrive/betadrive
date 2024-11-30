package dev.matthy.betadrive.config;

import dev.matthy.betadrive.Betadrive;
import dev.matthy.betadrive.android.AndroidPlayer;
import net.minecraft.client.MinecraftClient;
import org.json.simple.JSONArray;

public class BetadriveConfig extends ConfigFile {
    public void becomeAndroid(AndroidPlayer ap) { // make this AndroidPlayer an android in the cfg
        create();
        try {
            if(!hasUsername(ap.getName())) { // is it in there already?
                append(ap.getName()); // no? then append it!
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        refreshConfig(); // reload the config where it's being used
        Betadrive.mainHud.cfgJsonString = this.data;
    }
    public void unBecomeAndroid(AndroidPlayer ap) { // remove this AndroidPlayer from the android list in cfg
        create();
        try {
            if(hasUsername(ap.getName())) {// is it in there already?
                remove(ap.getName()); // yes? then remove it!

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        refreshConfig();
    }
    public AndroidPlayer getAPFromIGN(String name) { // username string --> AndroidPlayer object
        return new AndroidPlayer(MinecraftClient.getInstance().getServer().getPlayerManager().getPlayer(name));
    }
    public boolean hasUsername(String ign) { // do we have the username in the cfg?
        if(cfg == null) return false; // safety
        return ((JSONArray) cfg.getOrDefault("dat", new JSONArray())).contains(ign); // parse it & check
    }
}
