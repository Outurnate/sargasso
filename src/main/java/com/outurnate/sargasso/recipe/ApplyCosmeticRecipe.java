package com.outurnate.sargasso.recipe;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalDataComponentTypes;
import com.outurnate.sargasso.registry.LocalRecipeSerializers;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class ApplyCosmeticRecipe extends CustomRecipe {
    public static final MapCodec<ApplyCosmeticRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
        i -> i.group(
            Ingredient.CODEC.fieldOf("source").forGetter(o -> o.sourceItem),
            Ingredient.CODEC.fieldOf("cosmetic").forGetter(o -> o.cosmeticItem))
            .apply(i, ApplyCosmeticRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ApplyCosmeticRecipe> STREAM_CODEC = StreamCodec
        .composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.sourceItem,
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.cosmeticItem,
            ApplyCosmeticRecipe::new);

    private final Ingredient sourceItem;
    private final Ingredient cosmeticItem;

    public ApplyCosmeticRecipe(Ingredient sourceItem, Ingredient cosmeticItem) {
        this.sourceItem = sourceItem;
        this.cosmeticItem = cosmeticItem;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        Pair<ItemStack, ItemStack> inputs = getItemsToCombine(input);
        ItemStack source = inputs.getFirst();
        ItemStack cosmetic = inputs.getSecond();

        SuperSargassoSea.LOGGER.error(cosmetic.toString());
        DataComponentPatch components = DataComponentPatch.builder()
            .set(
                LocalDataComponentTypes.COSMETIC_ITEM.get(),
                new ItemStackTemplate(cosmetic.getItem(), cosmetic.getComponentsPatch()))
            .build();
        source.applyComponents(components);
        source.setCount(1);
        SuperSargassoSea.LOGGER.error(source.toString());
        return source;
    }

    private @Nullable Pair<ItemStack, ItemStack> getItemsToCombine(CraftingInput input) {
        if (input.ingredientCount() != 2) {
            return null;
        } else {
            ItemStack source = null;
            ItemStack cosmetic = null;

            for (int i = 0; i < input.size(); i++) {
                ItemStack itemStack = input.getItem(i);
                if (sourceItem.test(itemStack)) {
                    source = itemStack;
                    if (cosmetic != null) {
                        return Pair.of(source, cosmetic);
                    }
                }
                if (cosmeticItem.test(itemStack)) {
                    cosmetic = itemStack;
                    if (source != null) {
                        return Pair.of(source, cosmetic);
                    }
                }
            }

            return null;
        }
    }

    @Override
    public RecipeSerializer<ApplyCosmeticRecipe> getSerializer() {
        return LocalRecipeSerializers.APPLY_COSMETIC.get();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return this.getItemsToCombine(input) != null;
    }
}
