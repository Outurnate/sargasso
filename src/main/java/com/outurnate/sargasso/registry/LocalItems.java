/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.item.BedrockCreamItem;
import com.outurnate.sargasso.item.LightningBottleItem;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalItems {
    public static final DeferredRegister.Items REGISTRY = DeferredRegister
        .createItems(SuperSargassoSea.MODID);

    public static final DeferredItem<BlockItem> FLOTSAM = REGISTRY.registerSimpleBlockItem(
        "flotsam",
        LocalBlocks.FLOTSAM);

    public static final DeferredItem<BlockItem> DEBRIS = REGISTRY.registerSimpleBlockItem(
        "debris",
        LocalBlocks.DEBRIS);

    public static final DeferredItem<Item> JUNK = REGISTRY.registerSimpleItem(
        "junk",
        p -> p);

    public static final DeferredItem<Item> BEDROCK_SLOP = REGISTRY.registerSimpleItem(
        "bedrock_slop",
        p -> p.food(
            new FoodProperties.Builder()
                .alwaysEdible()
                .nutrition(1)
                .saturationModifier(18.0F)
                .build(),
            Consumable.builder()
                .onConsume(
                    new ApplyStatusEffectsConsumeEffect(
                        new MobEffectInstance(
                            MobEffects.SLOWNESS,
                            1200,
                            1)))
                .build()));

    public static final DeferredItem<Item> BREADROCK = REGISTRY.registerSimpleItem(
        "breadrock",
        p -> p.food(
            new FoodProperties.Builder()
                .alwaysEdible()
                .nutrition(2)
                .saturationModifier(0.2F)
                .build(),
            Consumable.builder()
                .onConsume(
                    new ApplyStatusEffectsConsumeEffect(
                        new MobEffectInstance(
                            MobEffects.SLOWNESS,
                            1200,
                            0)))
                .build()));

    public static final DeferredItem<Item> BEDROCK_CREAM = REGISTRY.registerItem(
        "bedrock_cream",
        BedrockCreamItem::new,
        p -> p);

    public static final DeferredItem<Item> LIGHTNING_BOTTLE = REGISTRY.registerItem(
        "lightning_bottle",
        LightningBottleItem::new,
        p -> p
            .useCooldown(2.0F));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
