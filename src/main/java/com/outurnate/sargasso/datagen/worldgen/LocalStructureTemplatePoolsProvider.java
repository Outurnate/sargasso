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
    public static final ResourceKey<StructureTemplatePool> FORTRESS_END = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("fortress_end"));
    public static final ResourceKey<StructureTemplatePool> APOTHECARY = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("apothecary"));

    public static void provide(BootstrapContext<StructureTemplatePool> bootstrap) {
        HolderGetter<StructureTemplatePool> structureTemplatePoolsRegistry = bootstrap
            .lookup(Registries.TEMPLATE_POOL);
        HolderGetter<StructureProcessorList> structureProcessorListRegistry = bootstrap
            .lookup(Registries.PROCESSOR_LIST);
        Holder<StructureTemplatePool> empty = structureTemplatePoolsRegistry.getOrThrow(Pools.EMPTY);
        Holder<StructureProcessorList> glitch = structureProcessorListRegistry
            .getOrThrow(LocalStructureProcessorListProvider.GLITCH);

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
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":fortress", glitch),
                        1)),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            FORTRESS_END,
            new StructureTemplatePool(
                empty,
                List.of(
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":fortress", glitch),
                        1),
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":fortress_end", glitch),
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
    }
}
