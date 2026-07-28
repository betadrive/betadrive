package dev.matthy.betadrive.item.upgrade;

import dev.matthy.betadrive.config.PlayerConfig;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;


public class AbsorptionUpgradeItem extends UpgradeItem {
    public final Identifier ABSORPTION_UPGRADE_ID = Identifier.fromNamespaceAndPath("betadrive", "absorption_upgrade");
    public AbsorptionUpgradeItem(Item.Properties settings) {
        super(settings);
    }
    @Override
    public boolean canRun(Level world, Player user, InteractionHand hand, PlayerConfig config) {
        if(user.getAttribute(Attributes.MAX_HEALTH) == null || user.getAttribute(Attributes.MAX_HEALTH).getModifier(ABSORPTION_UPGRADE_ID) == null) return true;
        return user.getAttribute(Attributes.MAX_HEALTH).getModifier(ABSORPTION_UPGRADE_ID).amount() < 20;
    }
    @Override
    public void postUse(Level world, Player user, InteractionHand hand) {
        double newAbsorptionAmount;
        try {
            newAbsorptionAmount = user.getAttribute(Attributes.MAX_HEALTH).getModifier(ABSORPTION_UPGRADE_ID).amount()+2;
        } catch (NullPointerException ignored) {
            user.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(ABSORPTION_UPGRADE_ID, 2, AttributeModifier.Operation.ADD_VALUE));
            return;
        }
        user.getAttribute(Attributes.MAX_HEALTH).addOrReplacePermanentModifier(new AttributeModifier(ABSORPTION_UPGRADE_ID, newAbsorptionAmount, AttributeModifier.Operation.ADD_VALUE));
    }
}
