/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class LocalRecipeProvider extends RecipeProvider {

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new LocalRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "Super Sargasso Sea Recipes";
        }
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
        shapeless(RecipeCategory.TOOLS, LocalItems.TWO_COLOR_FOX_EARS)
            .requires(LocalItems.AA_BATTERY)
            .requires(LocalItems.BLACK_FOX_EARS)
            .unlockedBy("has_fox_ears", this.has(LocalItems.BLACK_FOX_EARS.get()))
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
            .define('C', Items.COPPER_NUGGET)
            .define('R', Items.REDSTONE)
            .define('I', Items.IRON_NUGGET)
            .pattern("IGI")
            .pattern("III")
            .pattern("CRC")
            .unlockedBy("has_potato_battery", this.has(LocalItems.POTATO_BATTERY.get()))
            .save(this.output);
        dyedItem(LocalItems.FOX_EARS.get(), "dyed_armor");
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
