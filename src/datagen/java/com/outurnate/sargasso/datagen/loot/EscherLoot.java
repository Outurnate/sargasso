package com.outurnate.sargasso.datagen.loot;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.loot.FlimFlamLoreFunction;
import com.outurnate.sargasso.registry.LocalItems;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.minecraft.world.item.equipment.trim.TrimPatterns;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction.ModifierBuilder;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetLoreFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction.Target;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class EscherLoot extends LootProvider {
    public static final ResourceKey<LootTable> ESCHER = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("chests/escher"));

    public static final ResourceKey<LootTable> ESCHER_OMINOUS = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("chests/escher_ominous"));

    public static final TranslatableContents NAME_HAMMER_0 = n("hammer.0");

    public static final TranslatableContents NAME_HAMMER_1 = n("hammer.1");
    public static final TranslatableContents NAME_HAMMER_2 = n("hammer.2");
    public static final TranslatableContents LORE_FIZZY = l("fizzy_lifting");
    public static final String POTION_FIZZY = "fizzy_lifting";
    public static final TranslatableContents NAME_ROCKET_BOOTS = n("rocket_boots");
    public static final TranslatableContents LORE_SHRINK_HELM = l("george");
    public static final TranslatableContents NAME_SHRINK_HELM = n("george");

    public EscherLoot(Provider lookupProvider) {
        super(lookupProvider);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, Builder> output) {
        output.accept(
            ESCHER,
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .add(LootItem.lootTableItem(LocalItems.LIGHTNING_BOTTLE).setWeight(1))
                    .add(LootItem.lootTableItem(LocalItems.SPIDER_BOTTLE).setWeight(1))));

        output.accept(
            ESCHER_OMINOUS,
            LootTable.lootTable()
                .withPool(generateHammers())
                .withPool(generateStrangeArmors()));
    }

    private LootPool.Builder generateHammers() {
        TranslatableContents[] names = new TranslatableContents[] {
            NAME_HAMMER_0,
            NAME_HAMMER_1,
            NAME_HAMMER_2 };
        LootPool.Builder pool = LootPool.lootPool();
        pool.setRolls(UniformGenerator.between(0.0F, 1.0F));
        for (TranslatableContents name : names) {
            pool.add(
                LootItem.lootTableItem(LocalItems.HAMMER)
                    .apply(SetNameFunction.setName(MutableComponent.create(name), Target.ITEM_NAME)));
            pool.add(
                LootItem.lootTableItem(LocalItems.HAMMER)
                    .apply(SetNameFunction.setName(MutableComponent.create(name), Target.ITEM_NAME))
                    .apply(
                        EnchantWithLevelsFunction
                            .enchantWithLevels(lookupProvider, UniformGenerator.between(15.0F, 30.0F))));
            pool.add(
                LootItem.lootTableItem(LocalItems.HAMMER)
                    .apply(SetNameFunction.setName(MutableComponent.create(name), Target.ITEM_NAME))
                    .apply(FlimFlamLoreFunction.setFlimFlam())
                    .apply(
                        EnchantWithLevelsFunction
                            .enchantWithLevels(lookupProvider, UniformGenerator.between(15.0F, 30.0F))));
            pool.add(
                LootItem.lootTableItem(LocalItems.HAMMER)
                    .apply(SetNameFunction.setName(MutableComponent.create(name), Target.ITEM_NAME))
                    .apply(FlimFlamLoreFunction.setFlimFlam()));
        }
        return pool;
    }

    private LootItem.Builder<?> generateRocketBoots() {
        Identifier modifierIdentifier = Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "rocket");
        return LootItem.lootTableItem(Items.GOLDEN_BOOTS)
            .apply(
                SetNameFunction
                    .setName(MutableComponent.create(NAME_ROCKET_BOOTS), Target.ITEM_NAME))
            .apply(
                SetComponentsFunction.setComponent(
                    DataComponents.TRIM,
                    new ArmorTrim(
                        trimMaterialProvider.getOrThrow(TrimMaterials.REDSTONE),
                        trimPatternProvider.getOrThrow(TrimPatterns.SNOUT))))
            .apply(
                SetAttributesFunction.setAttributes()
                    .withModifier(
                        new ModifierBuilder(
                            modifierIdentifier,
                            Attributes.JUMP_STRENGTH,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                            ConstantValue.exactly(10.0F))
                                .forSlot(EquipmentSlotGroup.ARMOR))
                    .withModifier(
                        new ModifierBuilder(
                            modifierIdentifier,
                            Attributes.FALL_DAMAGE_MULTIPLIER,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                            ConstantValue.exactly(.2F))
                                .forSlot(EquipmentSlotGroup.ARMOR)));
    }

    private LootItem.Builder<?> generateShrinkingHelm() {
        HolderGetter<Enchantment> enchantmentProvider = this.lookupProvider.lookup(Registries.ENCHANTMENT)
            .get();

        ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        enchantments.set(enchantmentProvider.getOrThrow(Enchantments.PROTECTION), 4);
        enchantments.set(enchantmentProvider.getOrThrow(Enchantments.RESPIRATION), 3);
        enchantments.set(enchantmentProvider.getOrThrow(Enchantments.AQUA_AFFINITY), 1);
        enchantments.set(enchantmentProvider.getOrThrow(Enchantments.THORNS), 3);
        enchantments.set(enchantmentProvider.getOrThrow(Enchantments.BINDING_CURSE), 1);
        enchantments.set(enchantmentProvider.getOrThrow(Enchantments.UNBREAKING), 3);
        enchantments.set(enchantmentProvider.getOrThrow(Enchantments.MENDING), 1);

        Identifier modifierIdentifier = SuperSargassoSea.ID("george");
        return LootItem.lootTableItem(Items.DIAMOND_HELMET)
            .apply(
                SetComponentsFunction
                    .setComponent(DataComponents.ENCHANTMENTS, enchantments.toImmutable()))
            .apply(
                SetComponentsFunction.setComponent(
                    DataComponents.TOOLTIP_DISPLAY,
                    TooltipDisplay.DEFAULT.withHidden(DataComponents.ATTRIBUTE_MODIFIERS, true)))
            .apply(
                SetComponentsFunction.setComponent(
                    DataComponents.TRIM,
                    new ArmorTrim(
                        trimMaterialProvider.getOrThrow(TrimMaterials.NETHERITE),
                        trimPatternProvider.getOrThrow(TrimPatterns.SILENCE))))
            .apply(SetLoreFunction.setLore().addLine(MutableComponent.create(LORE_SHRINK_HELM)))
            .apply(
                SetNameFunction
                    .setName(MutableComponent.create(NAME_SHRINK_HELM), Target.ITEM_NAME))
            .apply(
                SetAttributesFunction.setAttributes()
                    .withModifier(
                        new ModifierBuilder(
                            modifierIdentifier,
                            Attributes.MAX_HEALTH,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                            ConstantValue.exactly(-0.95F)).forSlot(EquipmentSlotGroup.ARMOR))
                    .withModifier(
                        new ModifierBuilder(
                            modifierIdentifier,
                            Attributes.SCALE,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                            ConstantValue.exactly(-0.9F)).forSlot(EquipmentSlotGroup.ARMOR))
                    .withModifier(
                        new ModifierBuilder(
                            modifierIdentifier,
                            Attributes.MOVEMENT_SPEED,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                            ConstantValue.exactly(-0.9F)).forSlot(EquipmentSlotGroup.ARMOR))
                    .withModifier(
                        new ModifierBuilder(
                            modifierIdentifier,
                            Attributes.JUMP_STRENGTH,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                            ConstantValue.exactly(-0.5F)).forSlot(EquipmentSlotGroup.ARMOR)));
    }

    private LootPool.Builder generateStrangeArmors() {
        return LootPool.lootPool()
            .setRolls(UniformGenerator.between(0, 1))
            .add(
                generateLootItemCustomPotion(
                    POTION_FIZZY,
                    0,
                    0,
                    255,
                    LORE_FIZZY,
                    new MobEffectInstance(
                        MobEffects.LEVITATION,
                        4000,
                        4)))
            .add(generateShrinkingHelm())
            .add(generateRocketBoots());
    }
}
