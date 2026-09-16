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
import com.outurnate.sargasso.registry.LocalPotions;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.functions.SetStewEffectFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ShipwreckLoot extends LootProvider {
	public static final ResourceKey<LootTable> CHEST = ResourceKey.create(
			Registries.LOOT_TABLE,
			SuperSargassoSea.ID("shipwreck/chest"));
	public static final ResourceKey<LootTable> DISPENSER = ResourceKey.create(
			Registries.LOOT_TABLE,
			SuperSargassoSea.ID("shipwreck/dispenser"));
	public static final ResourceKey<LootTable> BARREL = ResourceKey.create(
			Registries.LOOT_TABLE,
			SuperSargassoSea.ID("shipwreck/barrel"));

	public static final TranslatableContents LORE_BHJ = l("bhj");
	public static final String POTION_BHJ = "bhj";

	private static LootPool.Builder generalSupplies() {
		return LootPool.lootPool()
				.add(
						LootItem.lootTableItem(Items.PAPER).setWeight(8)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 12.0F))))
				.add(
						LootItem.lootTableItem(Items.POTATO).setWeight(7)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
				.add(
						LootItem.lootTableItem(Items.MOSS_BLOCK).setWeight(7)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
				.add(
						LootItem.lootTableItem(Items.POISONOUS_POTATO).setWeight(7)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
				.add(
						LootItem.lootTableItem(Items.CARROT).setWeight(7)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F))))
				.add(
						LootItem.lootTableItem(Items.WHEAT).setWeight(7)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 21.0F))))
				.add(
						LootItem.lootTableItem(Items.SUSPICIOUS_STEW)
								.setWeight(10)
								.apply(
										SetStewEffectFunction.stewEffect()
												.withEffect(MobEffects.NIGHT_VISION, UniformGenerator.between(7.0F, 10.0F))
												.withEffect(MobEffects.JUMP_BOOST, UniformGenerator.between(7.0F, 10.0F))
												.withEffect(MobEffects.WEAKNESS, UniformGenerator.between(6.0F, 8.0F))
												.withEffect(MobEffects.BLINDNESS, UniformGenerator.between(5.0F, 7.0F))
												.withEffect(MobEffects.POISON, UniformGenerator.between(10.0F, 20.0F))
												.withEffect(MobEffects.SATURATION, UniformGenerator.between(7.0F, 10.0F))));
	}

	public ShipwreckLoot(Provider lookupProvider) {
		super(lookupProvider);
	}

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, Builder> output) {
		output.accept(
				CHEST,
				LootTable.lootTable()
						.withPool(
								generalSupplies()
										.setRolls(UniformGenerator.between(3.0F, 10.0F))
										.add(
												LootItem.lootTableItem(Items.COAL).setWeight(6)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
										.add(
												LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(5)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 24.0F))))
										.add(
												LootItem.lootTableItem(Blocks.PUMPKIN).setWeight(2)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
										.add(
												LootItem.lootTableItem(Blocks.BAMBOO).setWeight(2)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
										.add(
												LootItem.lootTableItem(Items.GUNPOWDER).setWeight(3)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
										.add(
												LootItem.lootTableItem(Blocks.TNT)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
										.add(
												LootItem.lootTableItem(LocalItems.STUDDED_LEATHER_HELMET).setWeight(3).apply(
														EnchantRandomlyFunction.randomApplicableEnchantment(this.lookupProvider)))
										.add(
												LootItem.lootTableItem(LocalItems.STUDDED_LEATHER_CHESTPLATE)
														.setWeight(3)
														.apply(
																EnchantRandomlyFunction.randomApplicableEnchantment(this.lookupProvider)))
										.add(
												LootItem.lootTableItem(LocalItems.STUDDED_LEATHER_LEGGINGS).setWeight(3).apply(
														EnchantRandomlyFunction.randomApplicableEnchantment(this.lookupProvider)))
										.add(
												LootItem.lootTableItem(LocalItems.STUDDED_LEATHER_BOOTS).setWeight(3).apply(
														EnchantRandomlyFunction.randomApplicableEnchantment(this.lookupProvider)))
										.add(
												LootItem.lootTableItem(Items.POTION)
														.apply(SetPotionFunction.setPotion(LocalPotions.HEAD_EXPLOSION))
														.setWeight(1))
										.add(LootItem.lootTableItem(LocalItems.BEDROCK_CREAM).setWeight(10))
										.add(
												generateLootItemCustomPotion(
														POTION_BHJ,
														255,
														60,
														0,
														LORE_BHJ,
														new MobEffectInstance(
																MobEffects.SPEED,
																36000,
																2),
														new MobEffectInstance(
																MobEffects.STRENGTH,
																36000,
																3),
														new MobEffectInstance(
																MobEffects.REGENERATION,
																36000,
																2),
														new MobEffectInstance(
																MobEffects.SLOWNESS,
																36000,
																3),
														new MobEffectInstance(
																MobEffects.WEAKNESS,
																36000,
																2),
														new MobEffectInstance(
																MobEffects.POISON,
																36000,
																1))
														.setWeight(1))));
		output.accept(
				DISPENSER,
				LootTable.lootTable()
						.withPool(
								LootPool.lootPool()
										.setRolls(UniformGenerator.between(3.0F, 10.0F))
										.add(
												LootItem.lootTableItem(Items.FIRE_CHARGE).setWeight(7).apply(
														SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))));
		output.accept(
				BARREL,
				LootTable.lootTable()
						.withPool(
								generalSupplies()
										.setRolls(UniformGenerator.between(2.0F, 12.0F))));
	}
}
