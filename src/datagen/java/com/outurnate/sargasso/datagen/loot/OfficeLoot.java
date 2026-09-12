package com.outurnate.sargasso.datagen.loot;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalItems;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class OfficeLoot extends LootProvider {
    public static final ResourceKey<LootTable> OFFICE = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("chests/office"));

    public OfficeLoot(Provider lookupProvider) {
        super(lookupProvider);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, Builder> output) {
        output.accept(
            OFFICE,
            LootTable.lootTable()
                // crap items pool
                .withPool(
                    LootPool.lootPool()
                        .setRolls(UniformGenerator.between(10, 14))
                        .add(LootItem.lootTableItem(Items.BOWL).setWeight(2))
                        .add(
                            LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                        .add(
                            LootItem.lootTableItem(Items.COPPER_NUGGET).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 20))))
                        .add(
                            LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 7))))
                        .add(
                            LootItem.lootTableItem(Items.PAPER).setWeight(15)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 20))))
                        .add(
                            LootItem.lootTableItem(LocalItems.QUARTER.get()).setWeight(7)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
                        .add(LootItem.lootTableItem(LocalItems.DEBRIS.get()).setWeight(7))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
                // good items pool
                .withPool(
                    LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1, 2))
                        .add(LootItem.lootTableItem(LocalItems.AA_BATTERY.get()).setWeight(16))
                        .add(LootItem.lootTableItem(LocalItems.RECHARGABLE_AA_BATTERY.get()).setWeight(2))
                        .add(LootItem.lootTableItem(LocalItems.RECORD_UNCHECKED.get()).setWeight(2))));
    }
}
