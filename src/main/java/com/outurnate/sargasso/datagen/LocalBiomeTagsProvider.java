/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.worldgen.LocalBiomesProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class LocalBiomeTagsProvider extends BiomeTagsProvider {
    public static final TagKey<Biome> HAS_APOTHECARY = TagKey
        .create(Registries.BIOME, SuperSargassoSea.ID("has_structure/apothecary"));

    public LocalBiomeTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, lookupProvider, SuperSargassoSea.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.tag(HAS_APOTHECARY)
            .add(Biomes.SWAMP);
        this.tag(BiomeTags.HAS_PILLAGER_OUTPOST)
            .add(LocalBiomesProvider.SEA)
            .replace(false);
        this.tag(BiomeTags.HAS_RUINED_PORTAL_DESERT)
            .add(LocalBiomesProvider.SEA)
            .replace(false);
    }
}
