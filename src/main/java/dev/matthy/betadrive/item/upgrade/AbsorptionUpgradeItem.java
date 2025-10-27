package dev.matthy.betadrive.item.upgrade;

import dev.matthy.betadrive.BetadriveConfig;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class AbsorptionUpgradeItem extends Item {
    public final Identifier ABSORPTION_UPGRADE_ITEM = Identifier.of("betadrive", "absorption_upgrade");
    public AbsorptionUpgradeItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if(!BetadriveConfig.getAndroidStatus(user.getUuid())) {
            user.sendMessage(Text.translatable("item.betadrive.use.not_android_dialog"), true);
            return ActionResult.FAIL;
        }
        double newAbsorptionAmount;
        try {
            newAbsorptionAmount = user.getAttributeInstance(EntityAttributes.MAX_HEALTH).getModifier(ABSORPTION_UPGRADE_ITEM).value()+2;
            newAbsorptionAmount = Math.min(newAbsorptionAmount,20);
        } catch (NullPointerException e) {
            user.getAttributeInstance(EntityAttributes.MAX_HEALTH).addPersistentModifier(new EntityAttributeModifier(ABSORPTION_UPGRADE_ITEM, 2, EntityAttributeModifier.Operation.ADD_VALUE));
            user.getStackInHand(hand).decrement(1);
            return ActionResult.CONSUME;
        }
        user.getAttributeInstance(EntityAttributes.MAX_HEALTH).overwritePersistentModifier(new EntityAttributeModifier(ABSORPTION_UPGRADE_ITEM, newAbsorptionAmount, EntityAttributeModifier.Operation.ADD_VALUE));
        user.getStackInHand(hand).decrement(1);
        return ActionResult.CONSUME;
    }
}
