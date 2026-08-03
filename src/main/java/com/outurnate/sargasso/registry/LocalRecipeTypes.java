package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.recipe.ApplyCosmeticRecipe;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> REGISTRY = DeferredRegister
        .create(Registries.RECIPE_TYPE, SuperSargassoSea.MODID);

    public static final Supplier<RecipeType<?>> APPLY_COSMETIC = REGISTRY.register(
        "apply_cosmetic",
        RecipeType::<ApplyCosmeticRecipe>simple);

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
