/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.mojang.datafixers.util.Pair;
import com.outurnate.sargasso.SuperSargassoSea;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class LocalStructureTemplatePoolsProvider {
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
    public static final ResourceKey<StructureTemplatePool> OFFICE_FIRST_FLOOR = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("office_first_floor"));
    public static final ResourceKey<StructureTemplatePool> OFFICE_TERMINATORS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("office_terminators"));
    public static final ResourceKey<StructureTemplatePool> OFFICE_ROADS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("office_roads"));
    public static final ResourceKey<StructureTemplatePool> ESCHER_HORIZONTAL = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("escher_horizontal"));
    public static final ResourceKey<StructureTemplatePool> ESCHER_VERTICAL = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("escher_vertical"));
    public static final ResourceKey<StructureTemplatePool> ESCHER_INVERTED = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("escher_inverted"));
    public static final ResourceKey<StructureTemplatePool> ESCHER_HORIZONTAL_TERMINATORS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("escher_horizontal_terminators"));
    // TODO no vertical terminators
    public static final ResourceKey<StructureTemplatePool> ESCHER_INVERTED_TERMINATORS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("escher_inverted_terminators"));

    private static List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>> pool(
        String... names) {
        return Arrays.stream(names).map(
            name -> Pair.<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>of(
                SinglePoolElement.single(SuperSargassoSea.MODID + ":" + name),
                1))
            .toList();
    }

    public static void provide(BootstrapContext<StructureTemplatePool> bootstrap) {
        HolderGetter<StructureTemplatePool> structureTemplatePoolsRegistry = bootstrap
            .lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> empty = structureTemplatePoolsRegistry.getOrThrow(Pools.EMPTY);
        Holder<StructureTemplatePool> officeTerminators = structureTemplatePoolsRegistry
            .getOrThrow(OFFICE_TERMINATORS);

        bootstrap.register(
            FORTRESS,
            new StructureTemplatePool(
                empty,
                List.of(
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":fortress"),
                        1)),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            FORTRESS_SEGMENT,
            new StructureTemplatePool(
                empty,
                List.of(
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":fortress"),
                        1),
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":fortress_end"),
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
                officeTerminators,
                List.of(
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":office_floor"),
                        10),
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":office_roof_1"),
                        1),
                    Pair.of(
                        SinglePoolElement.single(SuperSargassoSea.MODID + ":office_roof_2"),
                        1)),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            OFFICE_FIRST_FLOOR,
            new StructureTemplatePool(
                empty,
                pool("office_floor"),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            OFFICE_TERMINATORS,
            new StructureTemplatePool(
                empty,
                pool("office_roof_1", "office_roof_2"),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            OFFICE_ROADS,
            new StructureTemplatePool(
                empty,
                pool("office_road_1", "office_road_2", "office_road_3"),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            ESCHER_HORIZONTAL_TERMINATORS,
            new StructureTemplatePool(
                empty,
                pool(
                    "escher_end"),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            ESCHER_INVERTED_TERMINATORS,
            new StructureTemplatePool(
                empty,
                pool(
                    "escher_inverted_end"),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            ESCHER_HORIZONTAL,
            new StructureTemplatePool(
                structureTemplatePoolsRegistry.getOrThrow(ESCHER_HORIZONTAL_TERMINATORS),
                pool(
                    "escher_cross",
                    "escher_6",
                    "escher_5",
                    "escher_4",
                    "escher_3",
                    "escher_left",
                    "escher_right",
                    "escher_end",
                    "escher_to_vert"),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            ESCHER_VERTICAL,
            new StructureTemplatePool(
                empty,
                pool(
                    "escher_to_vert",
                    "escher_vertical_6",
                    "escher_vert_to_invert"),
                StructureTemplatePool.Projection.RIGID));
        bootstrap.register(
            ESCHER_INVERTED,
            new StructureTemplatePool(
                structureTemplatePoolsRegistry.getOrThrow(ESCHER_INVERTED_TERMINATORS),
                pool(
                    "escher_vert_to_invert",
                    "escher_inverted_cross",
                    "escher_inverted_3",
                    "escher_inverted_4",
                    "escher_inverted_5",
                    "escher_inverted_6",
                    "escher_inverted_right",
                    "escher_inverted_left",
                    "escher_inverted_end"),
                StructureTemplatePool.Projection.RIGID));
    }
}
