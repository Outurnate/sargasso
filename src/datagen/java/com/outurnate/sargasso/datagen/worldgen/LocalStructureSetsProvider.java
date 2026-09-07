/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import static net.minecraft.world.level.levelgen.structure.StructureSet.entry;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class LocalStructureSetsProvider {

    public static final ResourceKey<StructureSet> APOTHECARY = ResourceKey
        .create(Registries.STRUCTURE_SET, SuperSargassoSea.ID("apothecary"));
    public static final ResourceKey<StructureSet> SKY = ResourceKey
        .create(Registries.STRUCTURE_SET, SuperSargassoSea.ID("sky"));
    public static final ResourceKey<StructureSet> SURFACE = ResourceKey
        .create(Registries.STRUCTURE_SET, SuperSargassoSea.ID("surface"));

    public static void provide(BootstrapContext<StructureSet> bootstrap) {
        HolderGetter<Structure> structureRegistry = bootstrap.lookup(Registries.STRUCTURE);
        bootstrap.register(
            SURFACE,
            new StructureSet(
                List.of(
                    entry(structureRegistry.getOrThrow(LocalStructuresProvider.FOSSIL), 1),
                    entry(structureRegistry.getOrThrow(LocalStructuresProvider.FORTRESS), 1),
                    entry(structureRegistry.getOrThrow(LocalStructuresProvider.OFFICE), 1),
                    entry(structureRegistry.getOrThrow(LocalStructuresProvider.CASTLE), 1),
                    entry(structureRegistry.getOrThrow(LocalStructuresProvider.VILLAGE), 1),
                    entry(structureRegistry.getOrThrow(LocalStructuresProvider.STARTING_HOUSE), 1)),
                new RandomSpreadStructurePlacement(
                    20,
                    8,
                    RandomSpreadType.LINEAR,
                    14353921)));
        bootstrap.register(
            SKY,
            new StructureSet(
                structureRegistry.getOrThrow(LocalStructuresProvider.ESCHER),
                new RandomSpreadStructurePlacement(
                    20,
                    8,
                    RandomSpreadType.LINEAR,
                    424864)));
        bootstrap.register(
            APOTHECARY,
            new StructureSet(
                structureRegistry.getOrThrow(LocalStructuresProvider.APOTHECARY),
                new RandomSpreadStructurePlacement(
                    20,
                    15,
                    RandomSpreadType.LINEAR,
                    432488)));
    }
}
