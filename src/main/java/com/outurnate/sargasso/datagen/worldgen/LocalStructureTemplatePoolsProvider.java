/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.mojang.datafixers.util.Pair;
import com.outurnate.sargasso.SuperSargassoSea;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class LocalStructureTemplatePoolsProvider {
    public static final ResourceKey<StructureTemplatePool> LIBRARY = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("library"));
    public static final ResourceKey<StructureTemplatePool> FORTRESS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("fortress"));
    public static final ResourceKey<StructureTemplatePool> FORTRESS_SEGMENT = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("fortress_segment"));
    public static final ResourceKey<StructureTemplatePool> APOTHECARY = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("apothecary"));
    public static final ResourceKey<StructureTemplatePool> OFFICE = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("office"));
    public static final ResourceKey<StructureTemplatePool> OFFICE_FLOORS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("office_floors"));

    public static void provide(BootstrapContext<StructureTemplatePool> bootstrap) {
        HolderGetter<StructureTemplatePool> structureTemplatePoolsRegistry = bootstrap
            .lookup(Registries.TEMPLATE_POOL);
        HolderGetter<StructureProcessorList> structureProcessorListRegistry = bootstrap
            .lookup(Registries.PROCESSOR_LIST);
        Holder<StructureTemplatePool> empty = structureTemplatePoolsRegistry.getOrThrow(Pools.EMPTY);
        Holder<StructureProcessorList> heavy_glitch = structureProcessorListRegistry
            .getOrThrow(LocalStructureProcessorListProvider.HEAVY_GLITCH);
        Holder<StructureProcessorList> medium_glitch = structureProcessorListRegistry
            .getOrThrow(LocalStructureProcessorListProvider.MEDIUM_GLITCH);
        Holder<StructureProcessorList> mild_glitch = structureProcessorListRegistry
            .getOrThrow(LocalStructureProcessorListProvider.MILD_GLITCH);

        bootstrap.register(
            LIBRARY,
            new StructureTemplatePool(
                empty,
                List.of(
                    Pair.of(SinglePoolElement.single(SuperSargassoSea.MODID + ":bookshelf_test"), 1)),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            FORTRESS,
            new StructureTemplatePool(
                empty,
                List.of(
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":fortress", heavy_glitch),
                        1)),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            FORTRESS_SEGMENT,
            new StructureTemplatePool(
                empty,
                List.of(
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":fortress", medium_glitch),
                        1),
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":fortress_end", medium_glitch),
                        1),
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":fortress", mild_glitch),
                        1),
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":fortress_end", mild_glitch),
                        1)),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            APOTHECARY,
            new StructureTemplatePool(
                empty,
                List.of(
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":apothecary"),
                        1)),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            OFFICE,
            new StructureTemplatePool(
                empty,
                List.of(
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":office_base"),
                        1)),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            OFFICE_FLOORS,
            new StructureTemplatePool(
                empty,
                List.of(
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":office_floor"),
                        10),
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":office_roof"),
                        1)),
                StructureTemplatePool.Projection.RIGID));
    }
}
