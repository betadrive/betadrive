package com.matthyfamily.betadrive.block;

import com.matthyfamily.betadrive.Betadrive;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Tickable;

public class ElectronExtractorBlockEntity extends BlockEntity implements Tickable {
    private int energy = 0;
    public ElectronExtractorBlockEntity() {
        super(BetadriveBlocks.ELECTRON_EXTRACTOR_BLOCK_ENTITY);
    }

    @Override
    public void tick() {
        energy += 512;
    }
    public int getEnergy() {
        return energy;
    }
    public void subtractEnergy(int toSub) {
        energy -= toSub;
    }
}
