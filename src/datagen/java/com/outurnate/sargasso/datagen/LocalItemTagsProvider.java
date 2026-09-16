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
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.repository.LocalTags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jspecify.annotations.NonNull;

public class LocalItemTagsProvider extends ItemTagsProvider {
	public static final TagKey<Item> ENCHANTABLE_LOYALTY = ItemTags
			.create(Identifier.withDefaultNamespace("enchantable/loyalty"));

	public LocalItemTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
		super(output, lookupProvider, SuperSargassoSea.MODID);
	}

	@Override
	protected void addTags(@NonNull Provider registries) {
		this.tag(LocalTags.ALWAYS_LOST)
				.add(Items.BARRIER)
				.add(Items.STRUCTURE_VOID)
				.add(Items.LIGHT)
				.add(Items.COMMAND_BLOCK)
				.add(Items.CHAIN_COMMAND_BLOCK)
				.add(Items.REPEATING_COMMAND_BLOCK)
				.add(Items.STRUCTURE_BLOCK)
				.add(Items.JIGSAW)
				.add(Items.TEST_INSTANCE_BLOCK)
				.add(Items.TEST_BLOCK)
				.add(Items.KNOWLEDGE_BOOK)
				.add(Items.DEBUG_STICK)
				.add(Items.BEDROCK)
				.add(Items.REINFORCED_DEEPSLATE)
				.add(Items.BUDDING_AMETHYST)
				.add(Items.CHORUS_PLANT)
				.add(Items.DIRT_PATH)
				.add(Items.END_PORTAL_FRAME)
				.add(Items.FARMLAND)
				.add(Items.FROGSPAWN)
				.add(Items.INFESTED_CHISELED_STONE_BRICKS)
				.add(Items.INFESTED_COBBLESTONE)
				.add(Items.INFESTED_CRACKED_STONE_BRICKS)
				.add(Items.INFESTED_DEEPSLATE)
				.add(Items.INFESTED_MOSSY_STONE_BRICKS)
				.add(Items.INFESTED_STONE)
				.add(Items.INFESTED_STONE_BRICKS)
				.add(Items.SPAWNER)
				.add(Items.TRIAL_SPAWNER)
				.add(Items.VAULT)
				.add(LocalItems.DEBRIS.get());
		this.tag(LocalTags.BREAD)
				.add(LocalItems.BREADROCK.get())
				.replace(false);
		this.tag(LocalTags.EQUIPMENT)
				.addTag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "armors")))
				.addTag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "tools")));
		this.tag(LocalTags.FOX_TRUST_HAT)
				.add(LocalItems.FOX_EARS.get())
				.add(LocalItems.COMICALLY_TALL_FOX_EARS.get());
		this.tag(ItemTags.HEAD_ARMOR)
				.add(LocalItems.STUDDED_LEATHER_HELMET.get())
				.replace(false);
		this.tag(ItemTags.CHEST_ARMOR)
				.add(LocalItems.STUDDED_LEATHER_CHESTPLATE.get())
				.replace(false);
		this.tag(ItemTags.LEG_ARMOR)
				.add(LocalItems.STUDDED_LEATHER_LEGGINGS.get())
				.replace(false);
		this.tag(ItemTags.FOOT_ARMOR)
				.add(LocalItems.STUDDED_LEATHER_BOOTS.get())
				.replace(false);
		this.tag(ItemTags.FREEZE_IMMUNE_WEARABLES)
				.add(LocalItems.STUDDED_LEATHER_BOOTS.get())
				.add(LocalItems.STUDDED_LEATHER_LEGGINGS.get())
				.add(LocalItems.STUDDED_LEATHER_CHESTPLATE.get())
				.add(LocalItems.STUDDED_LEATHER_HELMET.get())
				.replace(false);
		this.tag(ItemTags.GAZE_DISGUISE_EQUIPMENT)
				.add(LocalItems.PYLON.get())
				.replace(false);
		this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "enchantable/knockback")))
				.add(LocalItems.HAMMER.get())
				.replace(false);
		this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "foods/cooked_beef")))
				.add(LocalItems.INFINITE_BEEF.get())
				.replace(false);
		this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "foods/cooked_chicken")))
				.add(LocalItems.INFINITE_CHICKEN.get())
				.replace(false);
		this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "foods/cooked_cod")))
				.add(LocalItems.INFINITE_COD.get())
				.replace(false);
		this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "foods/cooked_mutton")))
				.add(LocalItems.INFINITE_MUTTON.get())
				.replace(false);
		this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "foods/cooked_porkchop")))
				.add(LocalItems.INFINITE_PORK.get())
				.replace(false);
		this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "foods/cooked_rabbit")))
				.add(LocalItems.INFINITE_RABBIT.get())
				.replace(false);
		this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "foods/cooked_salmon")))
				.add(LocalItems.INFINITE_SALMON.get())
				.replace(false);
		this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "ingots")))
				.add(LocalItems.STARMETAL_INGOT.get())
				.replace(false);
		this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "music_discs")))
				.add(LocalItems.RECORD_UNCHECKED.get())
				.replace(false);
		this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "ores")))
				.add(LocalItems.RICH_PETRIFIED_FLOTSAM.get())
				.replace(false);
		this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "tools/melee_weapon")))
				.add(LocalItems.HAMMER.get())
				.replace(false);
		this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "tools/ranged_weapon")))
				.add(LocalItems.HAMMER.get())
				.replace(false);
		this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "villager_job_sites")))
				.add(LocalItems.SORTING_BIN.get())
				.replace(false);
		this.tag(ItemTags.MELEE_WEAPON_ENCHANTABLE)
				.add(LocalItems.HAMMER.get())
				.replace(false);
		this.tag(ENCHANTABLE_LOYALTY)
				.addTag(ItemTags.TRIDENT_ENCHANTABLE)
				.add(LocalItems.HAMMER.get())
				.replace(false);
	}
}
