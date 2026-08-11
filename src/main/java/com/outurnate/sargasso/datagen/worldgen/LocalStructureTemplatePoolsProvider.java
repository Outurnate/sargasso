/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.mojang.datafixers.util.Pair;
import com.outurnate.sargasso.SuperSargassoSea;

import java.util.Arrays;
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
    public static final ResourceKey<StructureTemplatePool> ESCHER_VERTICAL_TERMINATORS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("escher_vertical_terminators"));
    public static final ResourceKey<StructureTemplatePool> ESCHER_INVERTED_TERMINATORS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("escher_inverted_terminators"));
    public static final ResourceKey<StructureTemplatePool> ESCHER = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("escher"));

    @SafeVarargs
    private static StructureTemplatePool pool(
        Holder<StructureTemplatePool> fallback,
        Pair<String, Integer>... names) {
        return new StructureTemplatePool(
            fallback,
            Arrays.stream(names).map(
                name -> Pair.<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>of(
                    SinglePoolElement.single(SuperSargassoSea.MODID + ":" + name.getFirst()),
                    name.getSecond()))
                .toList(),
            StructureTemplatePool.Projection.RIGID);
    }

    private static StructureTemplatePool pool(
        Holder<StructureTemplatePool> fallback,
        String name) {
        return pool(fallback, Pair.of(name, 1));
    }

    public static void provide(BootstrapContext<StructureTemplatePool> bootstrap) {
        HolderGetter<StructureTemplatePool> structureTemplatePoolsRegistry = bootstrap
            .lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> empty = structureTemplatePoolsRegistry.getOrThrow(Pools.EMPTY);

        bootstrap.register(FORTRESS, pool(empty, "fortress"));
        bootstrap.register(
            FORTRESS_SEGMENT,
            pool(
                empty,
                Pair.of("fortress", 1),
                Pair.of("fortress_end", 1)));
        bootstrap.register(APOTHECARY, pool(empty, "apothecary"));
        bootstrap.register(OFFICE, pool(empty, "office_base"));
        bootstrap.register(
            OFFICE_FLOORS,
            pool(
                structureTemplatePoolsRegistry.getOrThrow(OFFICE_TERMINATORS),
                Pair.of("office_floor", 10),
                Pair.of("office_roof_1", 1),
                Pair.of("office_roof_2", 1)));
        bootstrap.register(OFFICE_FIRST_FLOOR, pool(empty, "office_floor"));
        bootstrap.register(
            OFFICE_TERMINATORS,
            pool(
                empty,
                Pair.of("office_roof_1", 1),
                Pair.of("office_roof_2", 1)));
        bootstrap.register(
            OFFICE_ROADS,
            pool(
                empty,
                Pair.of("office_road_1", 1),
                Pair.of("office_road_2", 1),
                Pair.of("office_road_3", 1)));
        bootstrap.register(ESCHER_HORIZONTAL_TERMINATORS, pool(empty, "escher_end"));
        bootstrap.register(ESCHER_VERTICAL_TERMINATORS, pool(empty, "escher_vert_end"));
        bootstrap.register(ESCHER_INVERTED_TERMINATORS, pool(empty, "escher_inverted_end"));
        bootstrap.register(
            ESCHER_HORIZONTAL,
            pool(
                structureTemplatePoolsRegistry.getOrThrow(ESCHER_HORIZONTAL_TERMINATORS),
                Pair.of("escher_cross", 4),
                Pair.of("escher_6", 1),
                Pair.of("escher_5", 1),
                Pair.of("escher_4", 1),
                Pair.of("escher_3", 1),
                Pair.of("escher_left", 4),
                Pair.of("escher_right", 4),
                Pair.of("escher_end", 4),
                Pair.of("escher_to_vert", 4),
                Pair.of("escher_vert_to_hor", 4)));
        bootstrap.register(
            ESCHER_VERTICAL,
            pool(
                structureTemplatePoolsRegistry.getOrThrow(ESCHER_VERTICAL_TERMINATORS),
                Pair.of("escher_to_vert", 4),
                Pair.of("escher_vertical_3", 1),
                Pair.of("escher_vertical_4", 1),
                Pair.of("escher_vertical_5", 1),
                Pair.of("escher_vertical_6", 1),
                Pair.of("escher_vert_to_invert", 4),
                Pair.of("escher_vert_to_hor", 4),
                Pair.of("escher_inverted_end", 4)));
        bootstrap.register(
            ESCHER_INVERTED,
            pool(
                structureTemplatePoolsRegistry.getOrThrow(ESCHER_INVERTED_TERMINATORS),
                Pair.of("escher_vert_to_invert", 4),
                Pair.of("escher_inverted_cross", 4),
                Pair.of("escher_inverted_3", 1),
                Pair.of("escher_inverted_4", 1),
                Pair.of("escher_inverted_5", 1),
                Pair.of("escher_inverted_6", 1),
                Pair.of("escher_inverted_right", 4),
                Pair.of("escher_inverted_left", 4),
                Pair.of("escher_inverted_end", 4)));
        bootstrap.register(ESCHER, pool(empty, "escher_cross"));
    }
}
