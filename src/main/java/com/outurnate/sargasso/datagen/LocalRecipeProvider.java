/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.registry.LocalItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;

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
        this
            .shaped(RecipeCategory.FOOD, LocalItems.BREADROCK)
            .define('#', Items.WHEAT)
            .define('S', LocalItems.BEDROCK_SLOP.get())
            .pattern("###")
            .pattern("SSS")
            .unlockedBy("has_slop", this.has(LocalItems.BEDROCK_SLOP.get()))
            .save(this.output);
        this
            .shaped(RecipeCategory.BUILDING_BLOCKS, LocalItems.FLOTSAM)
            .define('J', LocalItems.DEBRIS.get())
            .pattern("JJ")
            .pattern("JJ")
            .unlockedBy("has_junk", this.has(LocalItems.DEBRIS.get()))
            .save(this.output);
        this
            .shapeless(RecipeCategory.TOOLS, LocalItems.TWO_COLOR_FOX_EARS)
            .requires(LocalItems.AA_BATTERY)
            .requires(LocalItems.BLACK_FOX_EARS)
            .unlockedBy("has_fox_ears", this.has(LocalItems.BLACK_FOX_EARS.get()))
            .save(this.output);
    }
}
