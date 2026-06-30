/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = SuperSargassoSea.MODID)
public class LocalPotions {
    public static final DeferredRegister<Potion> REGISTRY = DeferredRegister
        .create(Registries.POTION, SuperSargassoSea.MODID);

    public static final Holder<Potion> HEAD_EXPLOSION = REGISTRY.register(
        "head_explosion",
        registryName -> new Potion(
            registryName.getPath(),
            new MobEffectInstance[] { new MobEffectInstance(LocalMobEffects.HEAD_EXPLOSION, 600, 0) }));

    public static final Holder<Potion> LONG_HEAD_EXPLOSION = REGISTRY.register(
        "long_head_explosion",
        registryName -> new Potion(
            registryName.getPath(),
            new MobEffectInstance[] { new MobEffectInstance(LocalMobEffects.HEAD_EXPLOSION, 1200, 0) }));

    public static final Holder<Potion> STRONG_HEAD_EXPLOSION = REGISTRY.register(
        "strong_head_explosion",
        registryName -> new Potion(
            registryName.getPath(),
            new MobEffectInstance[] { new MobEffectInstance(LocalMobEffects.HEAD_EXPLOSION, 600, 1) }));

    public static final Holder<Potion> EXTRA_STRONG_HEAD_EXPLOSION = REGISTRY.register(
        "extra_strong_head_explosion",
        registryName -> new Potion(
            registryName.getPath(),
            new MobEffectInstance[] { new MobEffectInstance(LocalMobEffects.HEAD_EXPLOSION, 600, 2) }));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }

    @SubscribeEvent
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addMix(
            Potions.AWKWARD,
            Items.TNT,
            HEAD_EXPLOSION);
        builder.addMix(
            HEAD_EXPLOSION,
            Items.REDSTONE,
            LONG_HEAD_EXPLOSION);
        builder.addMix(
            HEAD_EXPLOSION,
            Items.GLOWSTONE,
            STRONG_HEAD_EXPLOSION);
        builder.addMix(
            STRONG_HEAD_EXPLOSION,
            Items.GLOWSTONE,
            EXTRA_STRONG_HEAD_EXPLOSION);
    }
}
