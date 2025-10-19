package dev.matthy.betadrive.item;

import dev.matthy.betadrive.BetadriveConfig;
import dev.matthy.betadrive.config.PlayerConfig;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DisplayTogglerItem extends Item {
    private String propertyToSet;
    public DisplayTogglerItem(Item.Settings settings, String propertyToSet) {
        super(settings);
        this.propertyToSet = propertyToSet;
    }
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if(world.isClient()) return ActionResult.SUCCESS; // .use(...) runs twice; once for client, once for server. We remove the client-based use event so that we don't turn on then immediately turn off the HUD element.
        PlayerConfig cfg = BetadriveConfig.getAndroidPlayerConfig(user.getUuid());
        if(propertyToSet.equals("battery")) cfg.hudSettings.enableBatteryText = !cfg.hudSettings.enableBatteryText;
        if(propertyToSet.equals("speed")) cfg.hudSettings.enableSpeedText = !cfg.hudSettings.enableSpeedText;
        if(propertyToSet.equals("level")) cfg.hudSettings.enableLevelText = !cfg.hudSettings.enableLevelText;
        BetadriveConfig.setAndroidPlayerConfig(user.getUuid(), cfg);
        return ActionResult.SUCCESS;
    }
}
