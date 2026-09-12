package com.outurnate.sargasso.datagen.loot;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.loot.FlimFlamLoreFunction;
import com.outurnate.sargasso.registry.LocalItems;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction.ModifierBuilder;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction.Target;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class VillageLoot extends LootProvider {
    public static final ResourceKey<LootTable> BONE = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("village/bone"));

    public static final ResourceKey<LootTable> POT = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("village/pot")); // TODO update NBT

    public static final ResourceKey<LootTable> CHEST = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("village/chest")); // TODO update NBT

    public static final TranslatableContents NAME_LIAR_PANTS = n("liar_pants");

    public VillageLoot(Provider lookupProvider) {
        super(lookupProvider);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, Builder> output) {
        output.accept(
            BONE,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                    .add(LootItem.lootTableItem(Items.BONE))));
        output.accept(
            POT,
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .setRolls(UniformGenerator.between(3, 7))
                    .add(LootItem.lootTableItem(Items.BONE).setWeight(1))
                    .add(LootItem.lootTableItem(Items.BOWL).setWeight(2))
                    .add(LootItem.lootTableItem(Items.LEATHER).setWeight(5))
                    .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(1))
                    .add(LootItem.lootTableItem(Items.STICK).setWeight(15))
                    .add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(1))
                    .add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(7))
                    .add(LootItem.lootTableItem(Items.COPPER_NUGGET).setWeight(5))
                    .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(5))
                    .add(LootItem.lootTableItem(Items.COBBLESTONE).setWeight(15))
                    .add(generateTerribleTool(Items.WOODEN_AXE).setWeight(1))
                    .add(generateTerribleTool(Items.WOODEN_HOE).setWeight(1))
                    .add(generateTerribleTool(Items.WOODEN_PICKAXE).setWeight(1))
                    .add(generateTerribleTool(Items.WOODEN_SHOVEL).setWeight(1))
                    .add(generateTerribleTool(Items.WOODEN_SPEAR).setWeight(1))
                    .add(generateTerribleTool(Items.WOODEN_SWORD).setWeight(1))
                    .add(generateLiarsPants().setWeight(1))));
        output.accept(
            CHEST,
            LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .setRolls(UniformGenerator.between(3, 7))
                    .add(LootItem.lootTableItem(Items.BONE).setWeight(1))
                    .add(LootItem.lootTableItem(Items.BOWL).setWeight(2))
                    .add(LootItem.lootTableItem(Items.LEATHER).setWeight(5))
                    .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(1))
                    .add(LootItem.lootTableItem(Items.STICK).setWeight(15))
                    .add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(1))
                    .add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(7))
                    .add(LootItem.lootTableItem(Items.COPPER_NUGGET).setWeight(5))
                    .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(5))
                    .add(LootItem.lootTableItem(Items.COBBLESTONE).setWeight(15))
                    .add(generateTerribleTool(Items.WOODEN_AXE).setWeight(1))
                    .add(generateTerribleTool(Items.WOODEN_HOE).setWeight(1))
                    .add(generateTerribleTool(Items.WOODEN_PICKAXE).setWeight(1))
                    .add(generateTerribleTool(Items.WOODEN_SHOVEL).setWeight(1))
                    .add(generateTerribleTool(Items.WOODEN_SPEAR).setWeight(1))
                    .add(generateTerribleTool(Items.WOODEN_SWORD).setWeight(1))
                    .add(generateLiarsPants().setWeight(1))
                    .add(
                        LootItem.lootTableItem(LocalItems.STUDDED_LEATHER_UPGRADE_SMITHING_TEMPLATE)
                            .setWeight(5))));
    }

    private LootItem.Builder<?> generateLiarsPants() {
        Identifier modifierIdentifier = Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "liar");
        return LootItem.lootTableItem(Items.LEATHER_LEGGINGS)
            .apply(
                SetNameFunction
                    .setName(MutableComponent.create(NAME_LIAR_PANTS), Target.ITEM_NAME))
            .apply(
                SetAttributesFunction.setAttributes()
                    .withModifier(
                        new ModifierBuilder(
                            modifierIdentifier,
                            Attributes.BURNING_TIME,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                            ConstantValue.exactly(1.0F))
                                .forSlot(EquipmentSlotGroup.ARMOR)));
    }

    private LootItem.Builder<?> generateTerribleTool(ItemLike item) {
        return LootItem.lootTableItem(item)
            .apply(FlimFlamLoreFunction.setFlimFlam())
            .apply(
                EnchantWithLevelsFunction
                    .enchantWithLevels(this.lookupProvider, ConstantValue.exactly(1.0F)))
            .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.2F)));
    }
}
