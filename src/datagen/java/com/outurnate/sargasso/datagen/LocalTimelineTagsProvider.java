/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.worldgen.LocalTimelinesProvider;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TimelineTagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TimelineTags;
import net.minecraft.world.timeline.Timeline;

public class LocalTimelineTagsProvider extends TimelineTagsProvider {
    public static final TagKey<Timeline> IN_SEA = TagKey
        .create(Registries.TIMELINE, Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "in_sea"));

    public LocalTimelineTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(IN_SEA).addTag(TimelineTags.UNIVERSAL).add(LocalTimelinesProvider.DAY);
    }
}
