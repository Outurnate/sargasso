/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.LocalBiomeTagsProvider;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilStructure;

public class LocalStructuresProvider {
    public static final ResourceKey<Structure> FOSSIL = ResourceKey
        .create(Registries.STRUCTURE, SuperSargassoSea.ID("fossil"));
    public static final ResourceKey<Structure> LIBRARY = ResourceKey
        .create(Registries.STRUCTURE, SuperSargassoSea.ID("library"));
    public static final ResourceKey<Structure> FORTRESS = ResourceKey
        .create(Registries.STRUCTURE, SuperSargassoSea.ID("fortress"));
    public static final ResourceKey<Structure> APOTHECARY = ResourceKey
        .create(Registries.STRUCTURE, SuperSargassoSea.ID("apothecary"));
    public static final ResourceKey<Structure> OFFICE = ResourceKey
        .create(Registries.STRUCTURE, SuperSargassoSea.ID("office"));

    public static void provide(BootstrapContext<Structure> bootstrap) {
        HolderGetter<Biome> biomeRegistry = bootstrap.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> structureTemplatePoolRegistry = bootstrap
            .lookup(Registries.TEMPLATE_POOL);
        bootstrap.register(
            FOSSIL,
            new NetherFossilStructure(
                new StructureSettings.Builder(
                    HolderSet.direct(biomeRegistry.getOrThrow(LocalBiomesProvider.SEA)))
                        .generationStep(Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.NONE)
                        .build(),
                UniformHeight.of(VerticalAnchor.absolute(32), VerticalAnchor.belowTop(2))));
        bootstrap.register(
            LIBRARY,
            new JigsawStructure(
                new StructureSettings.Builder(
                    HolderSet.direct(biomeRegistry.getOrThrow(LocalBiomesProvider.SEA)))
                        .generationStep(Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                structureTemplatePoolRegistry.getOrThrow(LocalStructureTemplatePoolsProvider.LIBRARY),
                1,
                ConstantHeight.ZERO,
                false,
                Types.WORLD_SURFACE_WG));
        bootstrap.register(
            FORTRESS,
            new JigsawStructure(
                new StructureSettings.Builder(
                    HolderSet.direct(biomeRegistry.getOrThrow(LocalBiomesProvider.SEA)))
                        .generationStep(Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.NONE)
                        .build(),
                structureTemplatePoolRegistry.getOrThrow(LocalStructureTemplatePoolsProvider.FORTRESS),
                7,
                ConstantHeight.of(VerticalAnchor.belowTop(46)),
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
                    HolderSet.direct(biomeRegistry.getOrThrow(LocalBiomesProvider.SEA)))
                        .generationStep(Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                structureTemplatePoolRegistry.getOrThrow(LocalStructureTemplatePoolsProvider.OFFICE),
                7,
                ConstantHeight.ZERO,
                false,
                Types.WORLD_SURFACE_WG));
    }
}
