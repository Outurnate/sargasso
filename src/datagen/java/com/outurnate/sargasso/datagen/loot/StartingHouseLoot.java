package com.outurnate.sargasso.datagen.loot;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class StartingHouseLoot extends LootProvider {
    public static final ResourceKey<LootTable> CHEST = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("chests/startinghouse"));

    public StartingHouseLoot(Provider lookupProvider) {
        super(lookupProvider);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, Builder> output) {
        output.accept(
            CHEST,
            LootTable.lootTable()
                .withPool(
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.STONE_AXE))
                        .add(LootItem.lootTableItem(Items.WOODEN_AXE).setWeight(3)))
                .withPool(
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.STONE_PICKAXE))
                        .add(LootItem.lootTableItem(Items.WOODEN_PICKAXE).setWeight(3)))
                .withPool(
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(3.0F))
                        .add(
                            LootItem.lootTableItem(Items.APPLE).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(
                            LootItem.lootTableItem(Items.BREAD).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(
                            LootItem.lootTableItem(Items.SALMON).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))))
                .withPool(
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(4.0F))
                        .add(
                            LootItem.lootTableItem(Items.STICK).setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 12.0F))))
                        .add(
                            LootItem.lootTableItem(Blocks.OAK_PLANKS).setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 12.0F))))
                        .add(
                            LootItem.lootTableItem(Blocks.OAK_LOG).setWeight(3).apply(
                                SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))));
    }
}
