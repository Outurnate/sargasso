/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.datagen.loot.AdvancementLoot;
import com.outurnate.sargasso.datagen.loot.BlockLoot;
import com.outurnate.sargasso.datagen.loot.BookLoot;
import com.outurnate.sargasso.datagen.loot.CastleLoot;
import com.outurnate.sargasso.datagen.loot.EscherLoot;
import com.outurnate.sargasso.datagen.loot.OfficeLoot;
import com.outurnate.sargasso.datagen.loot.ShipwreckLoot;
import com.outurnate.sargasso.datagen.loot.StartingHouseLoot;
import com.outurnate.sargasso.datagen.loot.VillageLoot;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class LocalLootTableProvider extends LootTableProvider {
    public LocalLootTableProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(
            output,
            Set.of(),
            List.of(
                new SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(BookLoot::new, LootContextParamSets.CHEST),
                new SubProviderEntry(AdvancementLoot::new, LootContextParamSets.CHEST),
                new SubProviderEntry(CastleLoot::new, LootContextParamSets.CHEST),
                new SubProviderEntry(VillageLoot::new, LootContextParamSets.CHEST),
                new SubProviderEntry(OfficeLoot::new, LootContextParamSets.CHEST),
                new SubProviderEntry(EscherLoot::new, LootContextParamSets.CHEST),
                new SubProviderEntry(ShipwreckLoot::new, LootContextParamSets.CHEST),
                new SubProviderEntry(StartingHouseLoot::new, LootContextParamSets.CHEST)),
            lookupProvider);
    }
}
