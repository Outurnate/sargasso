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

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalItems;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class AdvancementLoot extends LootProvider {
	public static final ResourceKey<LootTable> ATLAS = ResourceKey.create(
			Registries.LOOT_TABLE,
			SuperSargassoSea.ID("atlas"));

	public AdvancementLoot(HolderLookup.Provider lookupProvider) {
		super(lookupProvider);
	}

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, Builder> output) {
		output.accept(
				ATLAS,
				LootTable.lootTable()
						.withPool(
								LootPool.lootPool()
										.setRolls(ConstantValue.exactly(1.0F))
										.add(
												LootItem.lootTableItem(LocalItems.ATLAS))));
	}
}
