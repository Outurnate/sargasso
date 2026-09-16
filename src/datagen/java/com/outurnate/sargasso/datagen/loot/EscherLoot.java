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
import com.outurnate.sargasso.loot.FlimFlamLoreFunction;
import com.outurnate.sargasso.registry.LocalItems;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.minecraft.world.item.equipment.trim.TrimPatterns;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction.ModifierBuilder;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SetLoreFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction.Target;
import net.minecraft.world.level.storage.loot.functions.SetOminousBottleAmplifierFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class EscherLoot extends LootProvider {
	public static final ResourceKey<LootTable> NORMAL_MAIN = ResourceKey.create(
			Registries.LOOT_TABLE,
			SuperSargassoSea.ID("escher/normal"));
	public static final ResourceKey<LootTable> NORMAL_RARE = ResourceKey.create(
			Registries.LOOT_TABLE,
			SuperSargassoSea.ID("escher/normal_rare"));
	public static final ResourceKey<LootTable> NORMAL_COMMON = ResourceKey.create(
			Registries.LOOT_TABLE,
			SuperSargassoSea.ID("escher/normal_common"));
	public static final ResourceKey<LootTable> NORMAL_UNIQUE = ResourceKey.create(
			Registries.LOOT_TABLE,
			SuperSargassoSea.ID("escher/normal_unique"));

	public static final ResourceKey<LootTable> OMINOUS_MAIN = ResourceKey.create(
			Registries.LOOT_TABLE,
			SuperSargassoSea.ID("escher/ominous"));
	public static final ResourceKey<LootTable> OMINOUS_RARE = ResourceKey.create(
			Registries.LOOT_TABLE,
			SuperSargassoSea.ID("escher/ominous_rare"));
	public static final ResourceKey<LootTable> OMINOUS_COMMON = ResourceKey.create(
			Registries.LOOT_TABLE,
			SuperSargassoSea.ID("escher/ominous_common"));
	public static final ResourceKey<LootTable> OMINOUS_UNIQUE = ResourceKey.create(
			Registries.LOOT_TABLE,
			SuperSargassoSea.ID("escher/ominous_unique"));

	public static final TranslatableContents NAME_HAMMER_0 = n("hammer.0");

	public static final TranslatableContents NAME_HAMMER_1 = n("hammer.1");
	public static final TranslatableContents NAME_HAMMER_2 = n("hammer.2");
	public static final TranslatableContents LORE_FIZZY = l("fizzy_lifting");
	public static final String POTION_FIZZY = "fizzy_lifting";
	public static final TranslatableContents NAME_ROCKET_BOOTS = n("rocket_boots");
	public static final TranslatableContents LORE_SHRINK_HELM = l("george");
	public static final TranslatableContents NAME_SHRINK_HELM = n("george");

	public EscherLoot(Provider lookupProvider) {
		super(lookupProvider);
	}

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, Builder> output) {
		LootTable everlastingFood = LootTable.lootTable().withPool(
				LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(LocalItems.INFINITE_BEEF))
						.add(LootItem.lootTableItem(LocalItems.INFINITE_CHICKEN))
						.add(LootItem.lootTableItem(LocalItems.INFINITE_COD))
						.add(LootItem.lootTableItem(LocalItems.INFINITE_MUTTON))
						.add(LootItem.lootTableItem(LocalItems.INFINITE_PORK))
						.add(LootItem.lootTableItem(LocalItems.INFINITE_RABBIT))
						.add(LootItem.lootTableItem(LocalItems.INFINITE_SALMON)))
				.build();
		output.accept(
				NORMAL_RARE,
				LootTable.lootTable()
						.withPool(
								LootPool.lootPool()
										.setRolls(ConstantValue.exactly(1.0F))
										.add(
												LootItem.lootTableItem(Items.EMERALD).setWeight(3)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
										.add(
												LootItem.lootTableItem(Items.SHIELD).setWeight(3)
														.apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.5F, 1.0F))))
										.add(
												LootItem.lootTableItem(Items.BOW)
														.setWeight(3)
														.apply(
																EnchantWithLevelsFunction.enchantWithLevels(
																		this.lookupProvider,
																		UniformGenerator.between(5.0F, 15.0F)))
														.apply(FlimFlamLoreFunction.setFlimFlam()))
										.add(
												LootItem.lootTableItem(Items.CROSSBOW)
														.setWeight(2)
														.apply(
																EnchantWithLevelsFunction.enchantWithLevels(
																		this.lookupProvider,
																		UniformGenerator.between(5.0F, 20.0F)))
														.apply(FlimFlamLoreFunction.setFlimFlam()))
										.add(
												LootItem.lootTableItem(Items.IRON_AXE)
														.setWeight(2)
														.apply(
																EnchantWithLevelsFunction.enchantWithLevels(
																		this.lookupProvider,
																		UniformGenerator.between(0.0F, 10.0F)))
														.apply(FlimFlamLoreFunction.setFlimFlam()))
										.add(
												LootItem.lootTableItem(Items.IRON_CHESTPLATE)
														.setWeight(2)
														.apply(
																EnchantWithLevelsFunction.enchantWithLevels(
																		this.lookupProvider,
																		UniformGenerator.between(0.0F, 10.0F)))
														.apply(FlimFlamLoreFunction.setFlimFlam()))
										.add(NestedLootTable.inlineLootTable(everlastingFood).setWeight(2))
										.add(
												LootItem.lootTableItem(Items.BOOK)
														.setWeight(2)
														.apply(
																new EnchantRandomlyFunction.Builder()
																		.withOneOf(
																				HolderSet.direct(
																						enchantments.getOrThrow(Enchantments.SHARPNESS),
																						enchantments.getOrThrow(Enchantments.BANE_OF_ARTHROPODS),
																						enchantments.getOrThrow(Enchantments.EFFICIENCY),
																						enchantments.getOrThrow(Enchantments.FORTUNE),
																						enchantments.getOrThrow(Enchantments.SILK_TOUCH),
																						enchantments.getOrThrow(Enchantments.FEATHER_FALLING)))))
										.add(
												LootItem.lootTableItem(Items.BOOK)
														.setWeight(2)
														.apply(
																new EnchantRandomlyFunction.Builder()
																		.withOneOf(
																				HolderSet.direct(
																						enchantments.getOrThrow(Enchantments.KNOCKBACK),
																						enchantments.getOrThrow(Enchantments.LOYALTY),
																						enchantments.getOrThrow(Enchantments.SHARPNESS),
																						enchantments.getOrThrow(Enchantments.MENDING)))))
										.add(generateRocketBoots().setWeight(1))
										.add(
												LootItem.lootTableItem(Items.DIAMOND_AXE)
														.setWeight(1)
														.apply(
																EnchantWithLevelsFunction.enchantWithLevels(
																		this.lookupProvider,
																		UniformGenerator.between(5.0F, 15.0F)))
														.apply(FlimFlamLoreFunction.setFlimFlam()))));
		output.accept(
				NORMAL_COMMON,
				LootTable.lootTable()
						.withPool(
								LootPool.lootPool()
										.setRolls(ConstantValue.exactly(1.0F))
										.add(
												LootItem.lootTableItem(Items.ARROW).setWeight(4)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
										.add(
												LootItem.lootTableItem(Items.EMERALD).setWeight(4)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
										.add(
												LootItem.lootTableItem(LocalItems.SPIDER_BOTTLE.get()).setWeight(3)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
										.add(
												LootItem.lootTableItem(Items.IRON_INGOT).setWeight(3)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
										.add(generateFizzy().setWeight(3))
										.add(
												LootItem.lootTableItem(Items.OMINOUS_BOTTLE)
														.setWeight(2)
														.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
														.apply(
																SetOminousBottleAmplifierFunction
																		.setAmplifier(UniformGenerator.between(0.0F, 1.0F))))
										.add(
												LootItem.lootTableItem(Items.DIAMOND).setWeight(1).apply(
														SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));
		output.accept(
				NORMAL_UNIQUE,
				LootTable.lootTable()
						.withPool(
								LootPool.lootPool()
										.setRolls(ConstantValue.exactly(1.0F))
										.add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(4))
										.add(LootItem.lootTableItem(LocalItems.LIGHTNING_BOTTLE.get()).setWeight(1))));
		output.accept(
				NORMAL_MAIN,
				LootTable.lootTable()
						.withPool(
								LootPool.lootPool()
										.setRolls(ConstantValue.exactly(1.0F))
										.add(NestedLootTable.lootTableReference(NORMAL_RARE).setWeight(8))
										.add(NestedLootTable.lootTableReference(NORMAL_COMMON).setWeight(2)))
						.withPool(
								LootPool.lootPool()
										.setRolls(UniformGenerator.between(1.0F, 3.0F))
										.add(NestedLootTable.lootTableReference(NORMAL_COMMON)))
						.withPool(
								LootPool.lootPool()
										.setRolls(ConstantValue.exactly(1.0F))
										.when(LootItemRandomChanceCondition.randomChance(0.25F))
										.add(NestedLootTable.lootTableReference(NORMAL_UNIQUE))));
		output.accept(
				OMINOUS_RARE,
				LootTable.lootTable()
						.withPool(
								LootPool.lootPool()
										.setRolls(ConstantValue.exactly(1.0F))
										.add(LootItem.lootTableItem(Items.EMERALD_BLOCK).setWeight(5))
										.add(LootItem.lootTableItem(Items.IRON_BLOCK).setWeight(4))
										.add(
												LootItem.lootTableItem(Items.CROSSBOW)
														.setWeight(4)
														.apply(
																EnchantWithLevelsFunction.enchantWithLevels(
																		this.lookupProvider,
																		UniformGenerator.between(5.0F, 20.0F)))
														.apply(FlimFlamLoreFunction.setFlimFlam()))
										.add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(3))
										.add(
												LootItem.lootTableItem(Items.DIAMOND_AXE)
														.setWeight(3)
														.apply(
																EnchantWithLevelsFunction.enchantWithLevels(
																		this.lookupProvider,
																		UniformGenerator.between(10.0F, 20.0F)))
														.apply(FlimFlamLoreFunction.setFlimFlam()))
										.add(generateShrinkingHelm().setWeight(3))
										.add(
												LootItem.lootTableItem(Items.BOOK)
														.setWeight(2)
														.apply(
																new EnchantRandomlyFunction.Builder()
																		.withOneOf(
																				HolderSet.direct(
																						enchantments.getOrThrow(Enchantments.KNOCKBACK),
																						enchantments.getOrThrow(Enchantments.PUNCH),
																						enchantments.getOrThrow(Enchantments.SMITE),
																						enchantments.getOrThrow(Enchantments.LOOTING),
																						enchantments.getOrThrow(Enchantments.MULTISHOT)))))
										.add(LootItem.lootTableItem(Items.DIAMOND_BLOCK).setWeight(1))));
		output.accept(
				OMINOUS_COMMON,
				LootTable.lootTable()
						.withPool(
								LootPool.lootPool()
										.setRolls(ConstantValue.exactly(1.0F))
										.add(
												LootItem.lootTableItem(Items.EMERALD).setWeight(5)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 10.0F))))
										.add(
												LootItem.lootTableItem(Items.WIND_CHARGE).setWeight(4)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 12.0F))))
										.add(
												LootItem.lootTableItem(Items.TIPPED_ARROW)
														.setWeight(3)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 12.0F)))
														.apply(SetPotionFunction.setPotion(Potions.STRONG_SLOWNESS)))
										.add(
												LootItem.lootTableItem(Items.DIAMOND).setWeight(2)
														.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))))
										.add(
												LootItem.lootTableItem(Items.OMINOUS_BOTTLE)
														.setWeight(1)
														.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
														.apply(
																SetOminousBottleAmplifierFunction
																		.setAmplifier(UniformGenerator.between(2.0F, 4.0F))))));
		output.accept(
				OMINOUS_UNIQUE,
				LootTable.lootTable()
						.withPool(generateHammers()));
		output.accept(
				OMINOUS_MAIN,
				LootTable.lootTable()
						.withPool(
								LootPool.lootPool()
										.setRolls(ConstantValue.exactly(1.0F))
										.add(NestedLootTable.lootTableReference(OMINOUS_RARE).setWeight(8))
										.add(NestedLootTable.lootTableReference(OMINOUS_COMMON).setWeight(2)))
						.withPool(
								LootPool.lootPool()
										.setRolls(UniformGenerator.between(1.0F, 3.0F))
										.add(NestedLootTable.lootTableReference(OMINOUS_COMMON)))
						.withPool(
								LootPool.lootPool()
										.setRolls(ConstantValue.exactly(1.0F))
										.when(LootItemRandomChanceCondition.randomChance(0.75F))
										.add(NestedLootTable.lootTableReference(OMINOUS_UNIQUE))));
	}

	private LootItem.Builder<?> generateFizzy() {
		return generateLootItemCustomPotion(
				POTION_FIZZY,
				0,
				0,
				255,
				LORE_FIZZY,
				new MobEffectInstance(
						MobEffects.LEVITATION,
						4000,
						4));
	}

	private LootPool.Builder generateHammers() {
		TranslatableContents[] names = new TranslatableContents[] {
				NAME_HAMMER_0,
				NAME_HAMMER_1,
				NAME_HAMMER_2 };
		LootPool.Builder pool = LootPool.lootPool();
		pool.setRolls(UniformGenerator.between(0.0F, 1.0F));
		for (TranslatableContents name : names) {
			pool.add(
					LootItem.lootTableItem(LocalItems.HAMMER)
							.apply(SetNameFunction.setName(MutableComponent.create(name), Target.ITEM_NAME)));
			pool.add(
					LootItem.lootTableItem(LocalItems.HAMMER)
							.apply(SetNameFunction.setName(MutableComponent.create(name), Target.ITEM_NAME))
							.apply(
									EnchantWithLevelsFunction
											.enchantWithLevels(lookupProvider, UniformGenerator.between(15.0F, 30.0F))));
			pool.add(
					LootItem.lootTableItem(LocalItems.HAMMER)
							.apply(SetNameFunction.setName(MutableComponent.create(name), Target.ITEM_NAME))
							.apply(FlimFlamLoreFunction.setFlimFlam())
							.apply(
									EnchantWithLevelsFunction
											.enchantWithLevels(lookupProvider, UniformGenerator.between(15.0F, 30.0F))));
			pool.add(
					LootItem.lootTableItem(LocalItems.HAMMER)
							.apply(SetNameFunction.setName(MutableComponent.create(name), Target.ITEM_NAME))
							.apply(FlimFlamLoreFunction.setFlimFlam()));
		}
		return pool;
	}

	private LootItem.Builder<?> generateRocketBoots() {
		Identifier modifierIdentifier = Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "rocket");
		return LootItem.lootTableItem(Items.GOLDEN_BOOTS)
				.apply(
						SetNameFunction
								.setName(MutableComponent.create(NAME_ROCKET_BOOTS), Target.ITEM_NAME))
				.apply(
						SetComponentsFunction.setComponent(
								DataComponents.TRIM,
								new ArmorTrim(
										trimMaterialProvider.getOrThrow(TrimMaterials.REDSTONE),
										trimPatternProvider.getOrThrow(TrimPatterns.SNOUT))))
				.apply(
						SetAttributesFunction.setAttributes()
								.withModifier(
										new ModifierBuilder(
												modifierIdentifier,
												Attributes.JUMP_STRENGTH,
												AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
												ConstantValue.exactly(10.0F))
												.forSlot(EquipmentSlotGroup.ARMOR))
								.withModifier(
										new ModifierBuilder(
												modifierIdentifier,
												Attributes.FALL_DAMAGE_MULTIPLIER,
												AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
												ConstantValue.exactly(.2F))
												.forSlot(EquipmentSlotGroup.ARMOR)));
	}

	private LootItem.Builder<?> generateShrinkingHelm() {
		HolderGetter<Enchantment> enchantmentProvider = this.lookupProvider.lookupOrThrow(Registries.ENCHANTMENT);

		ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
		enchantments.set(enchantmentProvider.getOrThrow(Enchantments.PROTECTION), 4);
		enchantments.set(enchantmentProvider.getOrThrow(Enchantments.RESPIRATION), 3);
		enchantments.set(enchantmentProvider.getOrThrow(Enchantments.AQUA_AFFINITY), 1);
		enchantments.set(enchantmentProvider.getOrThrow(Enchantments.THORNS), 3);
		enchantments.set(enchantmentProvider.getOrThrow(Enchantments.BINDING_CURSE), 1);
		enchantments.set(enchantmentProvider.getOrThrow(Enchantments.UNBREAKING), 3);
		enchantments.set(enchantmentProvider.getOrThrow(Enchantments.MENDING), 1);

		Identifier modifierIdentifier = SuperSargassoSea.ID("george");
		return LootItem.lootTableItem(Items.DIAMOND_HELMET)
				.apply(
						SetComponentsFunction
								.setComponent(DataComponents.ENCHANTMENTS, enchantments.toImmutable()))
				.apply(
						SetComponentsFunction.setComponent(
								DataComponents.TOOLTIP_DISPLAY,
								TooltipDisplay.DEFAULT.withHidden(DataComponents.ATTRIBUTE_MODIFIERS, true)))
				.apply(
						SetComponentsFunction.setComponent(
								DataComponents.TRIM,
								new ArmorTrim(
										trimMaterialProvider.getOrThrow(TrimMaterials.NETHERITE),
										trimPatternProvider.getOrThrow(TrimPatterns.SILENCE))))
				.apply(SetLoreFunction.setLore().addLine(MutableComponent.create(LORE_SHRINK_HELM)))
				.apply(
						SetNameFunction
								.setName(MutableComponent.create(NAME_SHRINK_HELM), Target.ITEM_NAME))
				.apply(
						SetAttributesFunction.setAttributes()
								.withModifier(
										new ModifierBuilder(
												modifierIdentifier,
												Attributes.MAX_HEALTH,
												AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
												ConstantValue.exactly(-0.95F)).forSlot(EquipmentSlotGroup.ARMOR))
								.withModifier(
										new ModifierBuilder(
												modifierIdentifier,
												Attributes.SCALE,
												AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
												ConstantValue.exactly(-0.9F)).forSlot(EquipmentSlotGroup.ARMOR))
								.withModifier(
										new ModifierBuilder(
												modifierIdentifier,
												Attributes.MOVEMENT_SPEED,
												AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
												ConstantValue.exactly(-0.9F)).forSlot(EquipmentSlotGroup.ARMOR))
								.withModifier(
										new ModifierBuilder(
												modifierIdentifier,
												Attributes.JUMP_STRENGTH,
												AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
												ConstantValue.exactly(-0.5F)).forSlot(EquipmentSlotGroup.ARMOR)));
	}
}
