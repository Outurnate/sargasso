package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalConsumeEffects {
    public static final DeferredRegister<ConsumeEffect.Type<?>> REGISTRY = DeferredRegister
        .create(Registries.CONSUME_EFFECT_TYPE, SuperSargassoSea.MODID);

    // https://docs.neoforged.net/docs/items/consumables/#consumeeffect
    /*
     * public static final Supplier<ConsumeEffect.Type<AddGeneratorConsumeEffect>>
     * ADD_GENERATOR = REGISTRY .register( "add_generator", () -> null);
     */

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
