package com.matthyfamily.betadrive.entity;

import com.matthyfamily.betadrive.Betadrive;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class AndroidEntity extends ZombieEntity {
    protected AndroidEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected boolean burnsInDaylight() {
        return false;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GENERIC_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GENERIC_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_CHAIN_PLACE;
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.BLOCK_CHAIN_STEP;
    }

    @Override
    public float getMovementSpeed() {
        return 0.5f;
    }
}
