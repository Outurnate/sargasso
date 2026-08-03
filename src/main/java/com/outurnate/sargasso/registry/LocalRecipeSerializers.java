package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.recipe.ApplyCosmeticRecipe;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister
        .create(Registries.RECIPE_SERIALIZER, SuperSargassoSea.MODID);

    public static final Supplier<RecipeSerializer<ApplyCosmeticRecipe>> APPLY_COSMETIC = REGISTRY.register(
        "apply_cosmetic",
        () -> new RecipeSerializer<>(ApplyCosmeticRecipe.MAP_CODEC, ApplyCosmeticRecipe.STREAM_CODEC));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}