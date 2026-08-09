/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class LocalStructureSetsProvider {
    public static final ResourceKey<StructureSet> FOSSIL = ResourceKey
        .create(Registries.STRUCTURE_SET, SuperSargassoSea.ID("fossil"));
    public static final ResourceKey<StructureSet> FORTRESS = ResourceKey
        .create(Registries.STRUCTURE_SET, SuperSargassoSea.ID("fortress"));
    public static final ResourceKey<StructureSet> APOTHECARY = ResourceKey
        .create(Registries.STRUCTURE_SET, SuperSargassoSea.ID("apothecary"));
    public static final ResourceKey<StructureSet> OFFICE = ResourceKey
        .create(Registries.STRUCTURE_SET, SuperSargassoSea.ID("office"));

    public static void provide(BootstrapContext<StructureSet> bootstrap) {
        HolderGetter<Structure> structureRegistry = bootstrap.lookup(Registries.STRUCTURE);
        bootstrap.register(
            FOSSIL,
            new StructureSet(
                structureRegistry.getOrThrow(LocalStructuresProvider.FOSSIL),
                new RandomSpreadStructurePlacement(
                    20,
                    8,
                    RandomSpreadType.LINEAR,
                    14353921)));
        bootstrap.register(
            FORTRESS,
            new StructureSet(
                structureRegistry.getOrThrow(LocalStructuresProvider.FORTRESS),
                new RandomSpreadStructurePlacement(
                    20,
                    15,
                    RandomSpreadType.LINEAR,
                    5487487)));
        bootstrap.register(
            APOTHECARY,
            new StructureSet(
                structureRegistry.getOrThrow(LocalStructuresProvider.APOTHECARY),
                new RandomSpreadStructurePlacement(
                    20,
                    15,
                    RandomSpreadType.LINEAR,
                    432488)));
        bootstrap.register(
            OFFICE,
            new StructureSet(
                structureRegistry.getOrThrow(LocalStructuresProvider.OFFICE),
                new RandomSpreadStructurePlacement(
                    40,
                    30,
                    RandomSpreadType.LINEAR,
                    345789)));
    }
}
