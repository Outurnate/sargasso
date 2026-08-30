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
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

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
    public static final ResourceKey<StructureTemplatePool> CASTLE = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("castle"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_QUARTERS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_quarters"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_BUILDINGS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_buildings"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_WALLS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_walls"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_CORNERS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_corners"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_PATH_FOUNDATIONS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_path_foundations"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_PATHS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_paths"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_JUNKS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_junks"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_ALLEYS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_alleys"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_CENTER_FOUNDATIONS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_center_foundations"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_CENTERS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_centers"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_JUNK_CORNERS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_junk_corners"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_GRAVEL = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_gravel"));

    @SafeVarargs
    private static StructureTemplatePool pool(
        Holder<StructureTemplatePool> fallback,
        Holder<StructureProcessorList> processors,
        Pair<String, Integer>... names) {
        return new StructureTemplatePool(
            fallback,
            Arrays.stream(names).map(
                name -> Pair.<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>of(
                    SinglePoolElement.single(SuperSargassoSea.MODID + ":" + name.getFirst(), processors),
                    name.getSecond()))
                .toList(),
            StructureTemplatePool.Projection.RIGID);
    }

    private static StructureTemplatePool pool(
        Holder<StructureTemplatePool> fallback,
        Holder<StructureProcessorList> processors,
        String name) {
        return pool(fallback, processors, Pair.of(name, 1));
    }

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

    @SuppressWarnings("unchecked")
    private static StructureTemplatePool pool(
        Holder<StructureTemplatePool> fallback,
        String... names) {
        return pool(
            fallback,
            (Pair<String, Integer>[]) Arrays.stream(names).map((name) -> Pair.of(name, 1))
                .toArray(size -> (Pair<String, Integer>[]) new Pair[size]));
    }

    private static StructureTemplatePool pool(
        Holder<StructureTemplatePool> fallback,
        String name) {
        return pool(fallback, Pair.of(name, 1));
    }

    public static void provide(BootstrapContext<StructureTemplatePool> bootstrap) {
        HolderGetter<StructureTemplatePool> structureTemplatePoolsRegistry = bootstrap
            .lookup(Registries.TEMPLATE_POOL);
        HolderGetter<StructureProcessorList> structureProcessorRegistry = bootstrap
            .lookup(Registries.PROCESSOR_LIST);
        Holder<StructureTemplatePool> empty = structureTemplatePoolsRegistry.getOrThrow(Pools.EMPTY);

        bootstrap.register(FORTRESS, pool(empty, "fortress/segment"));
        bootstrap.register(
            FORTRESS_SEGMENT,
            pool(
                empty,
                Pair.of("fortress/segment", 1),
                Pair.of("fortress/end", 1)));
        bootstrap.register(APOTHECARY, pool(empty, "apothecary"));
        bootstrap.register(OFFICE, pool(empty, "office/base"));
        bootstrap.register(
            OFFICE_FLOORS,
            pool(
                structureTemplatePoolsRegistry.getOrThrow(OFFICE_TERMINATORS),
                structureProcessorRegistry.getOrThrow(LocalStructureProcessorListProvider.REMOVE_CHESTS),
                Pair.of("office/floor", 10),
                Pair.of("office/roof_1", 1),
                Pair.of("office/roof_2", 1)));
        bootstrap.register(
            OFFICE_FIRST_FLOOR,
            pool(
                empty,
                structureProcessorRegistry.getOrThrow(LocalStructureProcessorListProvider.REMOVE_CHESTS),
                "office/floor"));
        bootstrap.register(
            OFFICE_TERMINATORS,
            pool(
                empty,
                "office/roof_1",
                "office/roof_2"));
        bootstrap.register(
            OFFICE_ROADS,
            pool(
                empty,
                "office/road_1",
                "office/road_2",
                "office/road_3"));
        bootstrap.register(
            ESCHER_HORIZONTAL_TERMINATORS,
            pool(
                empty,
                Pair.of("escher/end", 1),
                Pair.of("escher/pool", 1),
                Pair.of("escher/acropolis", 1)));
        bootstrap.register(ESCHER_VERTICAL_TERMINATORS, pool(empty, "escher/vert_end"));
        bootstrap.register(ESCHER_INVERTED_TERMINATORS, pool(empty, "escher/inverted_end"));
        bootstrap.register(
            ESCHER_HORIZONTAL,
            pool(
                structureTemplatePoolsRegistry.getOrThrow(ESCHER_HORIZONTAL_TERMINATORS),
                Pair.of("escher/cross", 2),
                Pair.of("escher/cube", 1),
                Pair.of("escher/4", 1),
                Pair.of("escher/3", 1),
                Pair.of("escher/left", 1),
                Pair.of("escher/right", 1),
                Pair.of("escher/to_vert", 4),
                Pair.of("escher/vert_to_hor", 4),
                Pair.of("escher/acropolis", 2),
                Pair.of("escher/pool", 2)));
        bootstrap.register(
            ESCHER_VERTICAL,
            pool(
                structureTemplatePoolsRegistry.getOrThrow(ESCHER_VERTICAL_TERMINATORS),
                Pair.of("escher/to_vert", 1),
                Pair.of("escher/vertical_3", 1),
                Pair.of("escher/vertical_4", 1),
                Pair.of("escher/cube", 1),
                Pair.of("escher/vert_to_invert", 1),
                Pair.of("escher/vert_to_hor", 1)));
        bootstrap.register(
            ESCHER_INVERTED,
            pool(
                structureTemplatePoolsRegistry.getOrThrow(ESCHER_INVERTED_TERMINATORS),
                Pair.of("escher/vert_to_invert", 1),
                Pair.of("escher/inverted_cross", 4),
                Pair.of("escher/inverted_3", 1),
                Pair.of("escher/inverted_4", 1),
                Pair.of("escher/cube", 1),
                Pair.of("escher/inverted_right", 8),
                Pair.of("escher/inverted_left", 8)));
        bootstrap.register(
            ESCHER,
            pool(
                empty,
                "escher/cube",
                "escher/cross"));
        bootstrap.register(CASTLE, pool(empty, "castle"));
        bootstrap.register(VILLAGE, pool(empty, "village/base"));
        bootstrap.register(VILLAGE_QUARTERS, pool(empty, "village/quarter"));
        bootstrap.register(
            VILLAGE_BUILDINGS,
            pool(
                empty,
                "village/building_1",
                "village/building_2",
                "village/building_3",
                "village/building_4",
                "village/building_5",
                "village/building_6"));
        bootstrap.register(VILLAGE_WALLS, pool(empty, "village/wall"));
        bootstrap.register(VILLAGE_CORNERS, pool(empty, "village/wall_corner"));
        bootstrap.register(VILLAGE_PATH_FOUNDATIONS, pool(empty, "village/path_foundation"));
        bootstrap.register(
            VILLAGE_PATHS,
            pool(
                empty,
                "village/path_1",
                "village/path_2",
                "village/path_3",
                "village/path_4",
                "village/path_5",
                "village/path_6"));
        bootstrap.register(
            VILLAGE_JUNKS,
            pool(
                empty,
                "village/junk_1",
                "village/junk_2",
                "village/junk_3",
                "village/junk_4",
                "village/junk_5"));
        bootstrap.register(
            VILLAGE_ALLEYS,
            pool(
                empty,
                "village/alley_1",
                "village/alley_2",
                "village/alley_3",
                "village/alley_4",
                "village/alley_5",
                "village/alley_6"));
        bootstrap.register(VILLAGE_CENTER_FOUNDATIONS, pool(empty, "village/center_foundation"));
        bootstrap.register(
            VILLAGE_CENTERS,
            pool(
                empty,
                "village/center_1"));
        bootstrap.register(VILLAGE_JUNK_CORNERS, pool(empty, "village/junk_corner"));
        bootstrap.register(VILLAGE_GRAVEL, pool(empty, "village/gravel"));
    }
}
