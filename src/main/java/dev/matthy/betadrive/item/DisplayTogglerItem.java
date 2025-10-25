package dev.matthy.betadrive.item;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.config.PlayerConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DisplayTogglerItem extends Item {
    private final String propertyToSet;
    public DisplayTogglerItem(Item.Settings settings, String propertyToSet) {
        super(settings);
        this.propertyToSet = propertyToSet;
    }
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) return ActionResult.SUCCESS; // .use(...) runs twice; once for client, once for server. We remove the server-based use event so that we don't turn on then immediately turn off the HUD element.
        if(!BetadriveConfig.getAndroidStatus(user.getUuid())) { // If the player is not an android, then tell them and don't toggle the HUD because it will do nothing for them
            user.sendMessage(Text.translatable("item.betadrive.use.not_android_dialog"), true);
            return ActionResult.FAIL;
        }
        PlayerConfig cfg = BetadriveConfig.getAndroidPlayerConfig(user.getUuid());
        cfg.whichToEnable.put(propertyToSet, !cfg.whichToEnable.getOrDefault(propertyToSet, false));
        BetadriveConfig.setAndroidPlayerConfig(user.getUuid(), cfg);
        return ActionResult.SUCCESS;
    }
}
