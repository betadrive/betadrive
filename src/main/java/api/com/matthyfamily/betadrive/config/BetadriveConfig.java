package api.com.matthyfamily.betadrive.config;

import api.com.matthyfamily.betadrive.android.AndroidPlayer;
import net.minecraft.client.MinecraftClient;

public class BetadriveConfig extends ConfigFile {
    public void becomeAndroid(AndroidPlayer ap) {
        if(!isExistant()) {
            create();
        }
        try {
            System.out.println(!read().toJSONString().contains(ap.getName()));
            if(!read().toJSONString().contains(ap.getName())) {
                append(ap.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void unBecomeAndroid(AndroidPlayer ap) {
        if(!isExistant()) {
            create();
        }
        try {
            if(read().toJSONString().contains(ap.getName())) {
                this.overwrite();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public AndroidPlayer getAPFromIGN(String name) {
        return new AndroidPlayer(MinecraftClient.getInstance().getServer().getPlayerManager().getPlayer(name));
    }
}
