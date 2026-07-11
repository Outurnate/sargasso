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
    public static record CalculatedWeights(int segment, int end, int maxDepth) {
        public CalculatedWeights(int maxDepth) {
            // first step, we need to figure out the probability of a segment vs an end
            // piece
            // let t be the chance that we'll get an end piece before reaching max depth
            double t = 0.999F;
            // let p be the probability that we'll roll an end jigsaw piece
            double p = 1.0 - Math.pow(1.0 - t, 1.0 / maxDepth);

            // the max weight allowed
            int totalWeight = 150;

            int endProbability = (int) (p * totalWeight);
            int segmentProbability = (int) totalWeight - endProbability;

            // datagen does something silly - it allocates objects in a loop
            // "weight" times. this saves some memory during datagen
            int factor = gcd(endProbability, segmentProbability);
            this(segmentProbability / factor, endProbability / factor, maxDepth);
        }

        private static int gcd(int a, int b) {
            a = Math.abs(a);
            b = Math.abs(b);

            while (b != 0) {
                int t = b;
                b = a % b;
                a = t;
            }

            return a;
        }
    }

    public static final ResourceKey<StructureTemplatePool> LIBRARY = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("library"));
    public static final ResourceKey<StructureTemplatePool> FORTRESS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("fortress"));
    public static final ResourceKey<StructureTemplatePool> FORTRESS_SEGMENT = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("fortress_segment"));
    public static final ResourceKey<StructureTemplatePool> APOTHECARY = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("apothecary"));
    public static final CalculatedWeights OFFICE_GENSETTINGS = new CalculatedWeights(20);
    public static final ResourceKey<StructureTemplatePool> OFFICE = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("office"));
    public static final ResourceKey<StructureTemplatePool> OFFICE_FLOORS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("office_floors"));
    public static final ResourceKey<StructureTemplatePool> OFFICE_FIRST_FLOOR = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("office_first_floor"));

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
                        OFFICE_GENSETTINGS.segment),
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":office_roof"),
                        OFFICE_GENSETTINGS.end)),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            OFFICE_FIRST_FLOOR,
            new StructureTemplatePool(
                empty,
                List.of(
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":office_floor"),
                        1)),
                StructureTemplatePool.Projection.RIGID));
    }
}
