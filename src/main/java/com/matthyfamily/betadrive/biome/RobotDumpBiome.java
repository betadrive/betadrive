package com.matthyfamily.betadrive.biome;

import com.matthyfamily.betadrive.block.BetadriveBlocks;
import com.matthyfamily.betadrive.entity.BetadriveEntities;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class RobotDumpBiome {
    private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> DUMP_SURFACE_BUILDER = SurfaceBuilder.DEFAULT
            .withConfig(new TernarySurfaceConfig(
                    BetadriveBlocks.ROBOT_WASTE_BLOCK.getDefaultState(),
                    BetadriveBlocks.COMPACTED_ROBOT_WASTE_BLOCK.getDefaultState(),
                    BetadriveBlocks.METAL_SCRAP.getDefaultState()));

    private static final Biome ROBOT_DUMP = createRobotDump();

    private static Biome createRobotDump() {
        // We specify what entities spawn and what features generate in the biome.
        // Aside from some structures, trees, rocks, plants and
        //   custom entities, these are mostly the same for each biome.
        // Vanilla configured features for biomes are defined in DefaultBiomeFeatures.

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(BetadriveEntities.ANDROID, 80, 1, 3));

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        generationSettings.surfaceBuilder(DUMP_SURFACE_BUILDER);
        DefaultBiomeFeatures.addDefaultUndergroundStructures(generationSettings);
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addDefaultLakes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return (new Biome.Builder())
                .precipitation(Biome.Precipitation.SNOW)
                .category(Biome.Category.NONE)
                .depth(0.125F)
                .scale(0.05F)
                .temperature(0.8F)
                .downfall(0.4F)
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x808080)
                        .waterFogColor(0x0808080)
                        .fogColor(0xe0e0c0)
                        .skyColor(0xe0e0c0)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }
    public static final RegistryKey<Biome> DUMP_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier("betadrive", "robot_dump"));
    public static void register() {
        Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier("betadrive", "robot_waste_b"), DUMP_SURFACE_BUILDER);
        Registry.register(BuiltinRegistries.BIOME, DUMP_KEY.getValue(), ROBOT_DUMP);
        OverworldBiomes.addContinentalBiome(DUMP_KEY, OverworldClimate.TEMPERATE, 2D);
        OverworldBiomes.addContinentalBiome(DUMP_KEY, OverworldClimate.COOL, 2D);
    }
}
