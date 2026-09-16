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
package com.outurnate.sargasso.datagen.loot;

import com.outurnate.sargasso.loot.LostItemFunction;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalItems;
import java.util.Set;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jspecify.annotations.NonNull;

public class BlockLoot extends BlockLootSubProvider {
	public BlockLoot(HolderLookup.Provider lookupProvider) {
		super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
	}

	private LootTable.Builder createFlotsamOreDrops(Block block) {
		LootTable nonSilkTouch = LootTable.lootTable().withPool(
				LootPool.lootPool()
						.setRolls(UniformGenerator.between(1.0F, 3.0F))
						.add(createOreEntry(block, LocalItems.LEAKY_BUCKET, 2.0F))
						.add(createOreEntry(block, LocalItems.RUSTED_BOLT, 4.0F))
						.add(createOreEntry(block, LocalItems.BROKEN_COG, 3.0F))
						.add(createOreEntry(block, LocalItems.LOOSE_WIRE, 5.0F))
						.add(createOreEntry(block, LocalItems.CIRCUIT_BOARD, 1.0F))
						.add(createOreEntry(block, LocalItems.CLOCKSPRING, 1.0F)))
				.build();
		return LootTable.lootTable()
				.withPool(
						LootPool.lootPool()
								.setRolls(ConstantValue.exactly(1.0F))
								.add(
										LootItem.lootTableItem(block)
												.when(this.hasSilkTouch())
												.otherwise(NestedLootTable.inlineLootTable(nonSilkTouch))));
	}

	private LootPoolEntryContainer.Builder<?> createOreEntry(Block block, ItemLike ore, float max) {
		HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries
				.lookupOrThrow(Registries.ENCHANTMENT);
		return this.applyExplosionDecay(
				block,
				LootItem.lootTableItem(ore)
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, max)))
						.apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))));
	}

	@Override
	protected void generate() {
		this.add(LocalBlocks.DEBRIS.get(), this.createSingleItemTable(LocalItems.DEBRIS.get()));
		this.add(LocalBlocks.TOASTER.get(), this.createSingleItemTable(LocalItems.TOASTER.get()));
		this.add(LocalBlocks.SORTING_BIN.get(), this.createSingleItemTable(LocalItems.SORTING_BIN.get()));
		this.add(LocalBlocks.BETA_CHEST.get(), this.createSingleItemTable(Items.CHEST));
		this.add(
				LocalBlocks.BETA_MOSSY_COBBLE.get(),
				this.createSingleItemTable(Blocks.MOSSY_COBBLESTONE));
		this.add(
				LocalBlocks.ALPHA_GRASS.get(),
				this.createSilkTouchDispatchTable(
						Blocks.GRASS_BLOCK,
						LootItem.lootTableItem(Blocks.DIRT)));
		this.add(
				LocalBlocks.STARMETAL_BLOCK.get(),
				this.createSilkTouchDispatchTable(
						LocalBlocks.STARMETAL_BLOCK.get(),
						LootItem.lootTableItem(LocalItems.STARMETAL_SCRAP.get())));
		this.add(LocalBlocks.PYLON.get(), this.createSingleItemTable(LocalItems.PYLON.get()));
		this.add(
				LocalBlocks.SHOCK_THERAPIST.get(),
				this.createSingleItemTable(LocalItems.SHOCK_THERAPIST.get()));
		this.add(
				LocalBlocks.CREAMY_BEDROCK.get(),
				this.createSingleItemTable(
						LocalItems.BEDROCK_SLOP.get(),
						UniformGenerator.between(1.0F, 3.0F)));
		this.add(
				LocalBlocks.FLOTSAM.get(),
				this.createSilkTouchDispatchTable(
						LocalBlocks.FLOTSAM.get(),
						LootItem.lootTableItem(LocalItems.DEBRIS.get())
								.apply(LostItemFunction.createBuilder())));
		this.add(
				LocalBlocks.PETRIFIED_FLOTSAM.get(),
				this.createSingleItemTable(LocalItems.PETRIFIED_FLOTSAM.get()));
		this.add(
				LocalBlocks.RICH_PETRIFIED_FLOTSAM.get(),
				this.createFlotsamOreDrops(LocalBlocks.RICH_PETRIFIED_FLOTSAM.get()));
	}

	@Override
	protected @NonNull Iterable<Block> getKnownBlocks() {
		return LocalBlocks.REGISTRY.getEntries().stream().map(e -> (Block) e.value()).toList();
	}
}
