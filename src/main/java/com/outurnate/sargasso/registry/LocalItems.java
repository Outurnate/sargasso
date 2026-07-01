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
import net.minecraft.world.item.equipment.ArmorType;
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

    public static final DeferredItem<BlockItem> TOASTER = REGISTRY.registerSimpleBlockItem(
        "toaster",
        LocalBlocks.TOASTER);

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

    public static final DeferredItem<Item> STUDDED_LEATHER_HELMET = REGISTRY.registerItem(
        "studded_leather_helmet",
        props -> new Item(props.humanoidArmor(LocalArmorMaterials.STUDDED_LEATHER, ArmorType.HELMET)));
    public static final DeferredItem<Item> STUDDED_LEATHER_CHESTPLATE = REGISTRY.registerItem(
        "studded_leather_chestplate",
        props -> new Item(props.humanoidArmor(LocalArmorMaterials.STUDDED_LEATHER, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> STUDDED_LEATHER_LEGGINGS = REGISTRY.registerItem(
        "studded_leather_leggings",
        props -> new Item(props.humanoidArmor(LocalArmorMaterials.STUDDED_LEATHER, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> STUDDED_LEATHER_BOOTS = REGISTRY.registerItem(
        "studded_leather_boots",
        props -> new Item(props.humanoidArmor(LocalArmorMaterials.STUDDED_LEATHER, ArmorType.BOOTS)));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
