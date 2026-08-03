package com.outurnate.sargasso.recipe;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.outurnate.sargasso.registry.LocalDataComponentTypes;
import com.outurnate.sargasso.registry.LocalRecipeSerializers;
import java.util.List;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class ApplyCosmeticRecipe extends CustomRecipe {
    public static final MapCodec<ApplyCosmeticRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
        i -> i.group(
            Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
            CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
            Ingredient.CODEC.fieldOf("source").forGetter(o -> o.sourceItem),
            Ingredient.CODEC.fieldOf("cosmetic").forGetter(o -> o.cosmeticItem))
            .apply(i, ApplyCosmeticRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ApplyCosmeticRecipe> STREAM_CODEC = StreamCodec
        .composite(
            Recipe.CommonInfo.STREAM_CODEC,
            o -> o.commonInfo,
            CraftingRecipe.CraftingBookInfo.STREAM_CODEC,
            o -> o.bookInfo,
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.sourceItem,
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.cosmeticItem,
            ApplyCosmeticRecipe::new);

    private static ItemStackTemplate apply(Ingredient sourceItem, Ingredient cosmeticItem) {
        ItemStack itemStack = apply(
            sourceItem.getValues().get(0).value().getDefaultInstance(),
            cosmeticItem.getValues().get(0).value().getDefaultInstance());
        return new ItemStackTemplate(itemStack.getItem(), itemStack.getComponentsPatch());
    }

    private static ItemStack apply(ItemStack source, ItemStack cosmetic) {
        DataComponentPatch components = DataComponentPatch.builder()
            .set(
                LocalDataComponentTypes.COSMETIC_ITEM.get(),
                new ItemStackTemplate(cosmetic.getItem(), cosmetic.getComponentsPatch()))
            .build();
        source.applyComponents(components);
        source.setCount(1);
        return source;
    }

    private final Recipe.CommonInfo commonInfo;
    private final CraftingRecipe.CraftingBookInfo bookInfo;
    private final Ingredient sourceItem;
    private final Ingredient cosmeticItem;

    public ApplyCosmeticRecipe(
        Recipe.CommonInfo commonInfo,
        CraftingRecipe.CraftingBookInfo bookInfo,
        Ingredient sourceItem,
        Ingredient cosmeticItem) {
        this.commonInfo = commonInfo;
        this.bookInfo = bookInfo;
        this.sourceItem = sourceItem;
        this.cosmeticItem = cosmeticItem;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        Pair<ItemStack, ItemStack> inputs = getItemsToCombine(input);
        if (inputs != null) {
            return apply(inputs.getFirst(), inputs.getSecond());
        }

        return ItemStack.EMPTY;
    }

    @Override
    public final CraftingBookCategory category() {
        return this.bookInfo.category();
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
            new ShapelessCraftingRecipeDisplay(
                List.of(
                    this.sourceItem.display(),
                    this.cosmeticItem.display()),
                new SlotDisplay.ItemStackSlotDisplay(apply(this.sourceItem, this.cosmeticItem)),
                new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)));
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
    public final String group() {
        return this.bookInfo.group();
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return this.getItemsToCombine(input) != null;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(List.of(this.sourceItem, this.cosmeticItem));
    }

    @Override
    public final boolean showNotification() {
        return this.commonInfo.showNotification();
    }
}
