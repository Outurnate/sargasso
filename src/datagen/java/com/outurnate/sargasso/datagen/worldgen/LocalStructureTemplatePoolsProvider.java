/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.util.StructureProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class LocalStructureTemplatePoolsProvider extends StructureProvider {
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
    public static final ResourceKey<StructureTemplatePool> VILLAGE_BUILDINGS_A = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_buildings_a"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_BUILDINGS_B = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_buildings_b"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_BUILDINGS_C = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_buildings_c"));
    public static final ResourceKey<StructureTemplatePool> VILLAGE_BUILDINGS_D = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_buildings_d"));
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
    public static final ResourceKey<StructureTemplatePool> VILLAGE_STANDS = ResourceKey
        .create(Registries.TEMPLATE_POOL, SuperSargassoSea.ID("village_stands"));

    @Override
    protected void provide(TemplatePoolBuilder bootstrap) {
        bootstrap.register(FORTRESS, "fortress/segment");
        bootstrap.register(
            FORTRESS_SEGMENT,
            entry("fortress/segment"),
            entry("fortress/end"));
        bootstrap.register(APOTHECARY, "apothecary");
        bootstrap.register(OFFICE, "office/base");
        bootstrap.register(
            OFFICE_FLOORS,
            OFFICE_TERMINATORS,
            LocalStructureProcessorListProvider.REMOVE_CHESTS,
            entry("office/floor", 10),
            entry("office/roof_1", 1),
            entry("office/roof_2", 1));
        bootstrap.register(
            OFFICE_FIRST_FLOOR,
            entry("office/floor", LocalStructureProcessorListProvider.REMOVE_CHESTS));
        bootstrap.register(
            OFFICE_TERMINATORS,
            entry("office/roof_1"),
            entry("office/roof_2"));
        bootstrap.register(
            OFFICE_ROADS,
            entry("office/road_1"),
            entry("office/road_2"),
            entry("office/road_3"));
        bootstrap.register(
            ESCHER_HORIZONTAL_TERMINATORS,
            entry("escher/end"),
            entry("escher/pool"),
            entry("escher/acropolis"));
        bootstrap.register(ESCHER_VERTICAL_TERMINATORS, "escher/vert_end");
        bootstrap.register(ESCHER_INVERTED_TERMINATORS, "escher/inverted_end");
        bootstrap.register(
            ESCHER_HORIZONTAL,
            ESCHER_HORIZONTAL_TERMINATORS,
            entry("escher/cross", 2),
            entry("escher/cube", 1),
            entry("escher/4", 1),
            entry("escher/3", 1),
            entry("escher/left", 1),
            entry("escher/right", 1),
            entry("escher/to_vert", 4),
            entry("escher/vert_to_hor", 4),
            entry("escher/acropolis", 2),
            entry("escher/pool", 2));
        bootstrap.register(
            ESCHER_VERTICAL,
            ESCHER_VERTICAL_TERMINATORS,
            entry("escher/to_vert", 1),
            entry("escher/vertical_3", 1),
            entry("escher/vertical_4", 1),
            entry("escher/cube", 1),
            entry("escher/vert_to_invert", 1),
            entry("escher/vert_to_hor", 1));
        bootstrap.register(
            ESCHER_INVERTED,
            ESCHER_INVERTED_TERMINATORS,
            entry("escher/vert_to_invert", 1),
            entry("escher/inverted_cross", 4),
            entry("escher/inverted_3", 1),
            entry("escher/inverted_4", 1),
            entry("escher/cube", 1),
            entry("escher/inverted_right", 8),
            entry("escher/inverted_left", 8));
        bootstrap.register(
            ESCHER,
            entry("escher/cube"),
            entry("escher/cross"));
        bootstrap.register(CASTLE, "castle");
        bootstrap.register(VILLAGE, "village/base");
        bootstrap.register(VILLAGE_QUARTERS, "village/quarter");
        bootstrap.register(
            VILLAGE_BUILDINGS_A,
            entry("village/building_1", LocalStructureProcessorListProvider.WOOLS),
            entry("village/building_2", LocalStructureProcessorListProvider.WOOLS.size()),
            entry("village/building_3", LocalStructureProcessorListProvider.WOOLS.size()),
            entry("village/building_4", LocalStructureProcessorListProvider.WOOLS.size()));
        bootstrap.register(
            VILLAGE_BUILDINGS_B,
            entry("village/building_5", LocalStructureProcessorListProvider.WOOLS.size()),
            entry("village/building_6", LocalStructureProcessorListProvider.WOOLS.size()),
            entry("village/building_7", LocalStructureProcessorListProvider.WOOLS),
            entry("village/building_8", LocalStructureProcessorListProvider.WOOLS.size()));
        bootstrap.register(
            VILLAGE_BUILDINGS_C,
            entry("village/building_9", LocalStructureProcessorListProvider.WOOLS.size()),
            entry("village/building_10", LocalStructureProcessorListProvider.WOOLS.size()),
            entry("village/building_11", LocalStructureProcessorListProvider.WOOLS.size()),
            entry("village/building_12", LocalStructureProcessorListProvider.WOOLS.size()));
        bootstrap.register(
            VILLAGE_BUILDINGS_D,
            entry("village/building_13", LocalStructureProcessorListProvider.WOOLS.size()),
            entry("village/building_14", LocalStructureProcessorListProvider.WOOLS.size()),
            entry("village/building_15", LocalStructureProcessorListProvider.WOOLS),
            entry("village/building_16", LocalStructureProcessorListProvider.WOOLS.size()),
            entry("village/building_17", LocalStructureProcessorListProvider.WOOLS.size()));
        bootstrap.register(VILLAGE_WALLS, "village/wall");
        bootstrap.register(VILLAGE_CORNERS, "village/wall_corner");
        bootstrap.register(VILLAGE_PATH_FOUNDATIONS, "village/path_foundation");
        bootstrap.register(
            VILLAGE_PATHS,
            entry("village/path_1"),
            entry("village/path_2"),
            entry("village/path_3"),
            entry("village/path_4"),
            entry("village/path_5"),
            entry("village/path_6"));
        bootstrap.register(
            VILLAGE_JUNKS,
            entry("village/junk_1"),
            entry("village/junk_2"),
            entry("village/junk_3"),
            entry("village/junk_4"),
            entry("village/junk_5"));
        bootstrap.register(
            VILLAGE_ALLEYS,
            entry("village/alley_1"),
            entry("village/alley_2"),
            entry("village/alley_3"),
            entry("village/alley_4"),
            entry("village/alley_5"),
            entry("village/alley_6"));
        bootstrap.register(VILLAGE_CENTER_FOUNDATIONS, "village/center_foundation");
        bootstrap.register(VILLAGE_CENTERS, "village/center_1");
        bootstrap.register(VILLAGE_JUNK_CORNERS, "village/junk_corner");
        bootstrap.register(VILLAGE_GRAVEL, "village/gravel");
        bootstrap.register(
            VILLAGE_STANDS,
            entry("village/stand_1", LocalStructureProcessorListProvider.WOOLS),
            entry("village/stand_2", LocalStructureProcessorListProvider.WOOLS),
            entry("village/stand_3", LocalStructureProcessorListProvider.WOOLS),
            entry("village/stand_4", LocalStructureProcessorListProvider.WOOLS),
            entry("village/stand_5", LocalStructureProcessorListProvider.WOOLS),
            entry("village/stand_6", LocalStructureProcessorListProvider.WOOLS),
            entry("village/stand_7", LocalStructureProcessorListProvider.WOOLS),
            entry("village/stand_8", LocalStructureProcessorListProvider.WOOLS),
            entry("village/stand_9", LocalStructureProcessorListProvider.WOOLS),
            entry("village/stand_10", LocalStructureProcessorListProvider.WOOLS),
            entry("village/stand_11", LocalStructureProcessorListProvider.WOOLS),
            entry("village/stand_12", LocalStructureProcessorListProvider.WOOLS));
    }
}
