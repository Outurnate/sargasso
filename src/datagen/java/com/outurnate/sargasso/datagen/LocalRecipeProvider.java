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

import com.google.common.collect.ImmutableList;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.recipe.ApplyCosmeticRecipe;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.repository.LocalTags;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.CustomCraftingRecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.NonNull;

public class LocalRecipeProvider extends RecipeProvider {
	public static class Runner extends RecipeProvider.Runner {
		public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(output, lookupProvider);
		}

		@Override
		public @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider provider, @NonNull RecipeOutput output) {
			return new LocalRecipeProvider(provider, output);
		}

		@Override
		public @NonNull String getName() {
			return "Super Sargasso Sea Recipes";
		}
	}

	private static String getHasName(TagKey<Item> baseBlock) {
		return "has_" + getItemName(baseBlock);
	}

	private static String getItemName(TagKey<Item> itemLike) {
		return itemLike.location().getPath();
	}

	protected LocalRecipeProvider(Provider registries, RecipeOutput output) {
		super(registries, output);
	}

	@Override
	protected void buildRecipes() {
		shaped(RecipeCategory.FOOD, LocalItems.BREADROCK)
				.define('#', Items.WHEAT)
				.define('S', LocalItems.BEDROCK_SLOP.get())
				.pattern("###")
				.pattern("SSS")
				.unlockedBy("has_slop", this.has(LocalItems.BEDROCK_SLOP.get()))
				.save(this.output);
		shaped(RecipeCategory.BUILDING_BLOCKS, LocalItems.FLOTSAM)
				.define('J', LocalItems.DEBRIS.get())
				.pattern("JJ")
				.pattern("JJ")
				.unlockedBy("has_junk", this.has(LocalItems.DEBRIS.get()))
				.save(this.output);
		studdedLeatherSmithing(
				Items.LEATHER_HELMET,
				RecipeCategory.COMBAT,
				LocalItems.STUDDED_LEATHER_HELMET.get());
		studdedLeatherSmithing(
				Items.LEATHER_CHESTPLATE,
				RecipeCategory.COMBAT,
				LocalItems.STUDDED_LEATHER_CHESTPLATE.get());
		studdedLeatherSmithing(
				Items.LEATHER_LEGGINGS,
				RecipeCategory.COMBAT,
				LocalItems.STUDDED_LEATHER_LEGGINGS.get());
		studdedLeatherSmithing(
				Items.LEATHER_BOOTS,
				RecipeCategory.COMBAT,
				LocalItems.STUDDED_LEATHER_BOOTS.get());
		shaped(RecipeCategory.TOOLS, LocalItems.PERSONAL_VOLTMETER)
				.define('G', Items.GLASS_PANE)
				.define('C', LocalItems.LOOSE_WIRE.get())
				.define('R', Items.REDSTONE)
				.define('I', Items.IRON_NUGGET)
				.pattern("IGI")
				.pattern("III")
				.pattern("CRC")
				.unlockedBy("has_potato_battery", this.has(LocalItems.POTATO_BATTERY.get()))
				.save(this.output);
		shapeless(RecipeCategory.MISC, LocalItems.COMICALLY_TALL_FOX_EARS)
				.requires(LocalItems.FOX_EARS)
				.requires(Items.RED_MUSHROOM)
				.unlockedBy(getHasName(LocalItems.FOX_EARS), this.has(LocalItems.FOX_EARS))
				.save(this.output);
		dyedItem(LocalItems.FOX_EARS.get(), "dyed_fox_ears");
		cosmetic(Ingredient.of(LocalItems.FOX_EARS.get()), LocalItems.AA_BATTERY.get(), "dyed_fox_ears");
		cosmetic(
				Ingredient.of(this.items.getOrThrow(ItemTags.HEAD_ARMOR)),
				LocalItems.FOX_EARS.get(),
				"armored_fox_ears");
		cosmetic(
				Ingredient.of(this.items.getOrThrow(ItemTags.HEAD_ARMOR)),
				LocalItems.COMICALLY_TALL_FOX_EARS.get(),
				"armored_fox_ears");
		shapeless(RecipeCategory.FOOD, LocalItems.POTATO_BATTERY)
				.requires(Items.POTATO)
				.requires(LocalItems.LOOSE_WIRE.get())
				.requires(LocalItems.QUARTER.get())
				.unlockedBy(getHasName(LocalItems.QUARTER), this.has(LocalItems.QUARTER))
				.save(this.output);
		shaped(RecipeCategory.DECORATIONS, LocalItems.STARMETAL_BLOCK, 2)
				.define('I', LocalItems.STARMETAL_INGOT)
				.pattern(" I ")
				.pattern("I I")
				.pattern(" I ")
				.unlockedBy(getHasName(LocalItems.STARMETAL_INGOT), this.has(LocalItems.STARMETAL_INGOT))
				.save(this.output);
		oreBlasting(
				LocalTags.COPPER_JUNK,
				RecipeCategory.MISC,
				CookingBookCategory.MISC,
				Items.COPPER_NUGGET,
				0.1F,
				100,
				"copper_junk");
		oreBlasting(
				LocalTags.IRON_JUNK,
				RecipeCategory.MISC,
				CookingBookCategory.MISC,
				Items.IRON_NUGGET,
				0.1F,
				100,
				"iron_junk");
		oreBlasting(
				LocalTags.GOLD_JUNK,
				RecipeCategory.MISC,
				CookingBookCategory.MISC,
				Items.GOLD_NUGGET,
				0.1F,
				100,
				"gold_junk");
		oreBlasting(
				ImmutableList.of(LocalItems.STARMETAL_SCRAP),
				RecipeCategory.MISC,
				CookingBookCategory.MISC,
				LocalItems.STARMETAL_INGOT,
				0.1F,
				100,
				"starmetal_recycle");
		shaped(RecipeCategory.COMBAT, LocalItems.HAMMER, 1)
				.define('I', LocalItems.STARMETAL_INGOT)
				.define('B', LocalItems.STARMETAL_BLOCK)
				.define('H', Items.HEAVY_CORE)
				.pattern("BHB")
				.pattern(" I ")
				.pattern(" I ")
				.unlockedBy(getHasName(Items.HEAVY_CORE), this.has(Items.HEAVY_CORE))
				.save(this.output);
		shaped(RecipeCategory.DECORATIONS, LocalItems.PETRIFIED_FLOTSAM, 1)
				.define('F', LocalItems.FLOTSAM)
				.pattern("FFF")
				.pattern("FFF")
				.pattern("FFF")
				.unlockedBy(getHasName(LocalItems.FLOTSAM), this.has(LocalItems.FLOTSAM))
				.save(this.output);
	}

	private void cosmetic(Ingredient target, Item cosmetic, String group) {
		CustomCraftingRecipeBuilder.customCrafting(
				RecipeCategory.MISC,
				(commonInfo, bookInfo) -> new ApplyCosmeticRecipe(
						commonInfo,
						bookInfo,
						target,
						Ingredient.of(cosmetic)))
				.unlockedBy(getHasName(cosmetic), this.has(cosmetic))
				.group(group)
				.save(
						this.output,
						SuperSargassoSea.MODID + ":" + "apply_" + getItemName(cosmetic));
	}

	@Override
	protected void dyedItem(@NonNull Item target, @NonNull String group) {
		CustomCraftingRecipeBuilder.customCrafting(
				RecipeCategory.MISC,
				(commonInfo, bookInfo) -> new DyeRecipe(
						commonInfo,
						bookInfo,
						Ingredient.of(target),
						this.tag(ItemTags.DYES),
						new ItemStackTemplate(target)))
				.unlockedBy(getHasName(target), this.has(target))
				.group(group)
				.save(this.output, SuperSargassoSea.MODID + ":" + getItemName(target) + "_dyed");
	}

	private String getSimpleRecipeNameModded(ItemLike itemLike) {
		return SuperSargassoSea.MODID + ":" + getItemName(itemLike);
	}

	@Override
	protected void nineBlockStorageRecipes(
			@NonNull RecipeCategory unpackedFormCategory,
			@NonNull ItemLike unpackedForm,
			@NonNull RecipeCategory packedFormCategory,
			@NonNull ItemLike packedForm) {
		this.nineBlockStorageRecipes(
				unpackedFormCategory,
				unpackedForm,
				packedFormCategory,
				packedForm,
				getSimpleRecipeNameModded(packedForm),
				null,
				getSimpleRecipeNameModded(unpackedForm),
				null);
	}

	private void oreBlasting(TagKey<Item> smeltables, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, ItemLike result, float experience, int cookingTime, String group) {
		this.oreCooking(BlastingRecipe::new, smeltables, craftingCategory, cookingCategory, result, experience, cookingTime, group, "_from_blasting");
	}

	@Override
	protected <T extends AbstractCookingRecipe> void oreCooking(
			AbstractCookingRecipe.@NonNull Factory<T> factory,
			List<ItemLike> smeltables,
			@NonNull RecipeCategory craftingCategory,
			@NonNull CookingBookCategory cookingCategory,
			@NonNull ItemLike result,
			float experience,
			int cookingTime,
			@NonNull String group,
			@NonNull String fromDesc) {
		for (ItemLike item : smeltables) {
			SimpleCookingRecipeBuilder
					.generic(
							Ingredient.of(item),
							craftingCategory,
							cookingCategory,
							result,
							experience,
							cookingTime,
							factory)
					.group(group)
					.unlockedBy(getHasName(item), this.has(item))
					.save(
							this.output,
							SuperSargassoSea.MODID + ":" + getItemName(result) + fromDesc + "_" + getItemName(item));
		}
	}

	private <T extends AbstractCookingRecipe> void oreCooking(AbstractCookingRecipe.Factory<T> factory, TagKey<Item> smeltables, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, ItemLike result, float experience, int cookingTime, String group, String fromDesc) {
		SimpleCookingRecipeBuilder.generic(Ingredient.of(registries.getOrThrow(smeltables)), craftingCategory, cookingCategory, result, experience, cookingTime, factory).group(group).unlockedBy(getHasName(smeltables), this.has(smeltables)).save(this.output, SuperSargassoSea.MODID + ":" + getItemName(result) + fromDesc + "_" + getItemName(smeltables));
	}

	private void studdedLeatherSmithing(Item base, RecipeCategory category, Item result) {
		SmithingTransformRecipeBuilder.smithing(
				Ingredient.of(LocalItems.STUDDED_LEATHER_UPGRADE_SMITHING_TEMPLATE),
				Ingredient.of(base),
				Ingredient.of(Items.IRON_INGOT),
				category,
				result)
				.unlocks("has_studded_upgrade", this.has(LocalItems.STUDDED_LEATHER_UPGRADE_SMITHING_TEMPLATE))
				.save(this.output, SuperSargassoSea.ID(getItemName(result) + "_smithing").toString());
	}
}
