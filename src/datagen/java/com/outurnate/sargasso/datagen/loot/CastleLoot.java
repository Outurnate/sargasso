package com.outurnate.sargasso.datagen.loot;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class CastleLoot extends LootProvider {
    public static final ResourceKey<LootTable> CASTLE = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("chests/castle"));

    public static final ResourceKey<LootTable> CASTLE_BARREL = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("chests/castle_barrel"));

    public CastleLoot(HolderLookup.Provider lookupProvider) {
        super(lookupProvider);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, Builder> output) {
        LootPool.Builder baseCastlePool = LootPool.lootPool()
            .setRolls(UniformGenerator.between(1, 6))
            .add(
                LootItem.lootTableItem(Items.WHEAT).setWeight(7)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
            .add(
                LootItem.lootTableItem(Items.CARROT).setWeight(5)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
            .add(
                LootItem.lootTableItem(Items.POTATO).setWeight(5)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
            .add(
                LootItem.lootTableItem(Items.ARROW).setWeight(2)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 7))))
            .add(
                LootItem.lootTableItem(Items.STRING).setWeight(2)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 7))));
        output.accept(CASTLE, LootTable.lootTable().withPool(baseCastlePool));
        output.accept(
            CASTLE_BARREL,
            LootTable.lootTable()
                .withPool(baseCastlePool)
                .withPool(
                    LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1, 2))
                        .add(LootItem.lootTableItem(Items.CROSSBOW).setWeight(2))
                        .add(LootItem.lootTableItem(Items.TRIPWIRE_HOOK).setWeight(2))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2))));
    }
}
