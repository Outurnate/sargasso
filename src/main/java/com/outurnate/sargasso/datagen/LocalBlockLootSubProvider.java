/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.loot.LostItemFunction;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalItems;

import java.util.Set;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class LocalBlockLootSubProvider extends BlockLootSubProvider {
    protected LocalBlockLootSubProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
    }

    @Override
    protected void generate() {
        this.add(LocalBlocks.DEBRIS.get(), this.createSingleItemTable(LocalItems.JUNK.get()));
        this.add(
            LocalBlocks.CREAMY_BEDROCK.get(),
            this.createSingleItemTable(LocalItems.BEDROCK_SLOP.get(), UniformGenerator.between(1.0F, 3.0F)));
        this.add(
            LocalBlocks.FLOTSAM.get(),
            this.createSilkTouchDispatchTable(
                LocalBlocks.FLOTSAM.get(),
                LootItem.lootTableItem(LocalItems.JUNK.get())
                    .apply(LostItemFunction.createBuilder())));
        this.add(
            LocalBlocks.GLITCH.get(),
            LootTable.lootTable());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return LocalBlocks.REGISTRY.getEntries().stream().map(e -> (Block) e.value()).toList();
    }
}
