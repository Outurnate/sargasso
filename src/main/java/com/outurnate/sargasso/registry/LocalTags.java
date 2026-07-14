/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

public class LocalTags {
    public static final TagKey<Item> ALWAYS_LOST = TagKey
        .create(Registries.ITEM, SuperSargassoSea.ID("always_lost"));
    public static final TagKey<Item> BREAD = TagKey
        .create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "foods/bread"));
    public static final TagKey<Item> EQUIPMENT = TagKey
        .create(Registries.ITEM, SuperSargassoSea.ID("equipment"));
    public static final TagKey<Biome> LOST_EQUIPMENT = TagKey
        .create(Registries.BIOME, SuperSargassoSea.ID("lost_equipment"));
    public static final TagKey<Biome> LOST_BLOCKS = TagKey
        .create(Registries.BIOME, SuperSargassoSea.ID("lost_blocks"));
    public static final TagKey<Biome> LOST_ITEMS = TagKey
        .create(Registries.BIOME, SuperSargassoSea.ID("lost_items"));
    public static final TagKey<Item> FOX_TRUST_HAT = TagKey
        .create(Registries.ITEM, SuperSargassoSea.ID("fox_trust_hat"));
}
