package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.repository.LocalTags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

public class LocalBlockTagsProvider extends BlockTagsProvider {
    public LocalBlockTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, lookupProvider, SuperSargassoSea.MODID);
    }

    @Override
    protected void addTags(Provider registries) {
        this.tag(LocalTags.SEA_CARVER_REPLACEABLES)
            .add(LocalBlocks.PETRIFIED_FLOTSAM.get());
        this.tag(BlockTags.ENDERMAN_HOLDABLE)
            .add(LocalBlocks.FLOTSAM.get())
            .add(LocalBlocks.PETRIFIED_FLOTSAM.get())
            .replace(false);
        this.tag(BlockTags.PORTALS)
            .add(LocalBlocks.PORTAL.get())
            .add(LocalBlocks.GLITCH.get())
            .replace(false);
        this.tag(BlockTags.WITHER_IMMUNE)
            .add(LocalBlocks.REINFORCED_STARMETAL_BLOCK.get())
            .add(LocalBlocks.GLITCH.get())
            .add(LocalBlocks.PORTAL.get())
            .replace(false);
        this.tag(BlockTags.create(Identifier.fromNamespaceAndPath("c", "ores")))
            .add(LocalBlocks.RICH_PETRIFIED_FLOTSAM.get())
            .replace(false);
        this.tag(BlockTags.create(Identifier.fromNamespaceAndPath("c", "villager_job_sites")))
            .add(LocalBlocks.SORTING_BIN.get())
            .replace(false);
    }
}
