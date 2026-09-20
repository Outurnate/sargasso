/*
 * This class is distributed as part of the Super Sargasso Sea mod.
 * Complete source on GitHub:
 * https://github.com/Outurnate/sargasso
 *
 * Super Sargasso Sea is free software and distributed
 * under the MIT License: https://opensource.org/license/mit
 *
 * © 2026 the authors of the Super Sargasso Sea mod
 */
package com.outurnate.sargasso.repository;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class LocalTags {
	public static final TagKey<Item> ALWAYS_LOST = TagKey
			.create(Registries.ITEM, SuperSargassoSea.ID("always_lost"));
	public static final TagKey<Item> BREAD = TagKey
			.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "foods/bread"));
	public static final TagKey<Item> EQUIPMENT = TagKey
			.create(Registries.ITEM, SuperSargassoSea.ID("equipment"));
	public static final TagKey<Item> COPPER_JUNK = TagKey
			.create(Registries.ITEM, SuperSargassoSea.ID("copper_junk"));
	public static final TagKey<Item> IRON_JUNK = TagKey
			.create(Registries.ITEM, SuperSargassoSea.ID("iron_junk"));
	public static final TagKey<Item> GOLD_JUNK = TagKey
			.create(Registries.ITEM, SuperSargassoSea.ID("gold_junk"));
	public static final TagKey<Biome> LOST_EQUIPMENT = TagKey
			.create(Registries.BIOME, SuperSargassoSea.ID("lost_equipment"));
	public static final TagKey<Biome> LOST_BLOCKS = TagKey
			.create(Registries.BIOME, SuperSargassoSea.ID("lost_blocks"));
	public static final TagKey<Biome> LOST_ITEMS = TagKey
			.create(Registries.BIOME, SuperSargassoSea.ID("lost_items"));
	public static final TagKey<Item> FOX_TRUST_HAT = TagKey
			.create(Registries.ITEM, SuperSargassoSea.ID("fox_trust_hat"));
	public static final TagKey<EntityType<?>> CAN_WEAR_PYLON = TagKey
			.create(Registries.ENTITY_TYPE, SuperSargassoSea.ID("can_wear_pylon"));
	public static final TagKey<Block> SEA_CARVER_REPLACEABLES = TagKey
			.create(Registries.BLOCK, SuperSargassoSea.ID("sea_carver_replaceables"));
}
