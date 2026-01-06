package dev.matthy.betadrive.item.upgrade;

import dev.matthy.betadrive.config.PlayerConfig;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;


public class AbsorptionUpgradeItem extends UpgradeItem {
    public final Identifier ABSORPTION_UPGRADE_ID = Identifier.of("betadrive", "absorption_upgrade");
    public AbsorptionUpgradeItem(Settings settings) {
        super(settings);
    }
    @Override
    public boolean canRun(World world, PlayerEntity user, Hand hand, PlayerConfig config) {
        if(user.getAttributeInstance(EntityAttributes.MAX_HEALTH) == null) return true;
        return user.getAttributeInstance(EntityAttributes.MAX_HEALTH).getModifier(ABSORPTION_UPGRADE_ID).value() < 20;
    }
    @Override
    public void postUse(World world, PlayerEntity user, Hand hand) {
        double newAbsorptionAmount;
        try {
            newAbsorptionAmount = user.getAttributeInstance(EntityAttributes.MAX_HEALTH).getModifier(ABSORPTION_UPGRADE_ID).value()+2;
        } catch (NullPointerException ignored) {
            user.getAttributeInstance(EntityAttributes.MAX_HEALTH).addPersistentModifier(new EntityAttributeModifier(ABSORPTION_UPGRADE_ID, 2, EntityAttributeModifier.Operation.ADD_VALUE));
            return;
        }
        user.getAttributeInstance(EntityAttributes.MAX_HEALTH).overwritePersistentModifier(new EntityAttributeModifier(ABSORPTION_UPGRADE_ID, newAbsorptionAmount, EntityAttributeModifier.Operation.ADD_VALUE));
    }
}
