/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.LocalBiomeTagsProvider;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilStructure;

public class LocalStructuresProvider {
    public static final ResourceKey<Structure> FOSSIL = ResourceKey
        .create(Registries.STRUCTURE, SuperSargassoSea.ID("fossil"));
    public static final ResourceKey<Structure> FORTRESS = ResourceKey
        .create(Registries.STRUCTURE, SuperSargassoSea.ID("fortress"));
    public static final ResourceKey<Structure> APOTHECARY = ResourceKey
        .create(Registries.STRUCTURE, SuperSargassoSea.ID("apothecary"));
    public static final ResourceKey<Structure> OFFICE = ResourceKey
        .create(Registries.STRUCTURE, SuperSargassoSea.ID("office"));
    public static final ResourceKey<Structure> ESCHER = ResourceKey
        .create(Registries.STRUCTURE, SuperSargassoSea.ID("escher"));

    public static void provide(BootstrapContext<Structure> bootstrap) {
        HolderGetter<Biome> biomeRegistry = bootstrap.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> structureTemplatePoolRegistry = bootstrap
            .lookup(Registries.TEMPLATE_POOL);
        bootstrap.register(
            FOSSIL,
            new NetherFossilStructure(
                new StructureSettings.Builder(
                    biomeRegistry.getOrThrow(LocalBiomeTagsProvider.SEA))
                        .generationStep(Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.NONE)
                        .build(),
                UniformHeight.of(VerticalAnchor.absolute(32), VerticalAnchor.belowTop(2))));
        bootstrap.register(
            FORTRESS,
            new JigsawStructure(
                new StructureSettings.Builder(
                    HolderSet.direct(biomeRegistry.getOrThrow(LocalBiomesProvider.PEAKS)))
                        .generationStep(Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.NONE)
                        .build(),
                structureTemplatePoolRegistry.getOrThrow(LocalStructureTemplatePoolsProvider.FORTRESS),
                7,
                ConstantHeight.of(VerticalAnchor.absolute(-42)),
                false,
                Types.WORLD_SURFACE_WG));
        bootstrap.register(
            APOTHECARY,
            new JigsawStructure(
                new StructureSettings.Builder(
                    biomeRegistry.getOrThrow(LocalBiomeTagsProvider.HAS_APOTHECARY))
                        .generationStep(Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                structureTemplatePoolRegistry.getOrThrow(LocalStructureTemplatePoolsProvider.APOTHECARY),
                1,
                ConstantHeight.ZERO,
                false,
                Types.WORLD_SURFACE_WG));
        bootstrap.register(
            OFFICE,
            new JigsawStructure(
                new StructureSettings.Builder(
                    HolderSet.direct(biomeRegistry.getOrThrow(LocalBiomesProvider.LOWLANDS)))
                        .generationStep(Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_BOX)
                        .spawnOverrides(
                            Map.of(
                                MobCategory.MONSTER,
                                new StructureSpawnOverride(
                                    StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                    WeightedList
                                        .of(new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 1, 1)))))
                        .build(),
                structureTemplatePoolRegistry.getOrThrow(LocalStructureTemplatePoolsProvider.OFFICE),
                Optional.<Identifier>empty(),
                8,
                ConstantHeight.ZERO,
                false,
                Optional.of(Types.WORLD_SURFACE_WG),
                new JigsawStructure.MaxDistance(100),
                List.of(),
                JigsawStructure.DEFAULT_DIMENSION_PADDING,
                JigsawStructure.DEFAULT_LIQUID_SETTINGS));
        bootstrap.register(
            ESCHER,
            new JigsawStructure(
                new StructureSettings.Builder(
                    HolderSet.direct(biomeRegistry.getOrThrow(LocalBiomesProvider.RARE)))
                        .generationStep(Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .spawnOverrides(
                            Map.of(
                                MobCategory.MONSTER,
                                new StructureSpawnOverride(
                                    StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                    WeightedList.of(
                                        new MobSpawnSettings.SpawnerData(EntityType.BREEZE, 1, 1)))))
                        .build(),
                structureTemplatePoolRegistry
                    .getOrThrow(LocalStructureTemplatePoolsProvider.ESCHER),
                Optional.<Identifier>empty(),
                20,
                ConstantHeight.of(VerticalAnchor.absolute(200)),
                false,
                Optional.empty(),
                new JigsawStructure.MaxDistance(110, 128),
                List.of(),
                JigsawStructure.DEFAULT_DIMENSION_PADDING,
                JigsawStructure.DEFAULT_LIQUID_SETTINGS));
    }
}
