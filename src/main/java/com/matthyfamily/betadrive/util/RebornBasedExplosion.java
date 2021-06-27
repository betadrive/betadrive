package com.matthyfamily.betadrive.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.apache.commons.lang3.time.StopWatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by modmuss50 on 12/03/2016.
 */
public class RebornBasedExplosion extends Explosion {

    @NotNull
    BlockPos center;

    @NotNull
    World world;

    @NotNull
    int radius;

    @Nullable
    LivingEntity livingBase;

    public RebornBasedExplosion(
            @NotNull
                    BlockPos center,
            @NotNull
                    World world,
            @NotNull
                    int radius) {
        super(world, null, null, null, center.getX(), center.getY(), center.getZ(), radius, false, DestructionType.DESTROY);
        this.center = center;
        this.world = world;
        this.radius = radius;
    }

    public void setLivingBase(
            @Nullable
                    LivingEntity livingBase) {
        this.livingBase = livingBase;
    }

    public
    @Nullable
    LivingEntity getLivingBase() {
        return livingBase;
    }

    public void explode() {
        StopWatch watch = new StopWatch();
        watch.start();
        for (int tx = -radius; tx < radius + 1; tx++) {
            for (int ty = -radius; ty < radius + 1; ty++) {
                for (int tz = -radius; tz < radius + 1; tz++) {
                    if (Math.sqrt(Math.pow(tx, 2) + Math.pow(ty, 2) + Math.pow(tz, 2)) <= radius - 2) {
                        BlockPos pos = center.add(tx, ty, tz);
                        BlockState state = world.getBlockState(pos);
                        Block block = state.getBlock();
                        if (block != Blocks.BEDROCK && !state.isAir()) {
                            block.onDestroyedByExplosion(world, pos, this);
                            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void collectBlocksAndDamageEntities() {
        explode();
    }

    @Override
    public void affectWorld(boolean spawnParticles) {
        explode();
    }

    @Override
    public
    @Nullable
    LivingEntity getCausingEntity() {
        return livingBase;
    }

    @Override
    public List<BlockPos> getAffectedBlocks() {
        List<BlockPos> poses = new ArrayList<>();
        for (int tx = -radius; tx < radius + 1; tx++) {
            for (int ty = -radius; ty < radius + 1; ty++) {
                for (int tz = -radius; tz < radius + 1; tz++) {
                    if (Math.sqrt(Math.pow(tx, 2) + Math.pow(ty, 2) + Math.pow(tz, 2)) <= radius - 2) {
                        BlockPos pos = center.add(tx, ty, tz);
                        BlockState state = world.getBlockState(pos);
                        Block block = state.getBlock();
                        if (block != Blocks.BEDROCK && !state.isAir()) {
                            poses.add(pos);
                        }
                    }
                }
            }
        }
        return poses;
    }
}