package com.matthyfamily.betadrive.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Vec3i;

public class ElectronBombBlockEntity extends BlockEntity implements Tickable {
    private static int chargingEnergy;
    public ElectronBombBlockEntity() {
        super(BetadriveBlocks.ELECTRON_BOMB_BLOCK_ENTITY);
        chargingEnergy = 0;
    }

    @Override
    public void tick() {
        try {
            if (MinecraftClient.getInstance().world.getBlockEntity(pos.subtract(new Vec3i(0, 1, 0))) instanceof ElectronExtractorBlockEntity) {
                if (((ElectronExtractorBlockEntity) world.getBlockEntity(pos.subtract(new Vec3i(0, 1, 0)))).getEnergy() > 10000) {
                    ((ElectronExtractorBlockEntity) world.getBlockEntity(pos.subtract(new Vec3i(0, 1, 0)))).subtractEnergy(10000);
                    this.chargingEnergy += 10000;
                }
            }
        } catch (Exception e) {

        }
    }
    public static int getCE() {
        return chargingEnergy;
    }

}
