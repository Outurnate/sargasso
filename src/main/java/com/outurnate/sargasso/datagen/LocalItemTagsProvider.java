/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.registry.LocalTags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

public class LocalItemTagsProvider extends ItemTagsProvider {
    public LocalItemTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, lookupProvider, SuperSargassoSea.MODID);
    }

    @Override
    protected void addTags(Provider registries) {
        this.tag(LocalTags.ALWAYS_LOST)
            .add(Items.BARRIER)
            .add(Items.STRUCTURE_VOID)
            .add(Items.LIGHT)
            .add(Items.COMMAND_BLOCK)
            .add(Items.CHAIN_COMMAND_BLOCK)
            .add(Items.REPEATING_COMMAND_BLOCK)
            .add(Items.STRUCTURE_BLOCK)
            .add(Items.JIGSAW)
            .add(Items.TEST_INSTANCE_BLOCK)
            .add(Items.TEST_BLOCK)
            .add(Items.KNOWLEDGE_BOOK)
            .add(Items.DEBUG_STICK)
            .add(Items.BEDROCK)
            .add(Items.REINFORCED_DEEPSLATE)
            .add(Items.BUDDING_AMETHYST)
            .add(Items.CHORUS_PLANT)
            .add(Items.DIRT_PATH)
            .add(Items.END_PORTAL_FRAME)
            .add(Items.FARMLAND)
            .add(Items.FROGSPAWN)
            .add(Items.INFESTED_CHISELED_STONE_BRICKS)
            .add(Items.INFESTED_COBBLESTONE)
            .add(Items.INFESTED_CRACKED_STONE_BRICKS)
            .add(Items.INFESTED_DEEPSLATE)
            .add(Items.INFESTED_MOSSY_STONE_BRICKS)
            .add(Items.INFESTED_STONE)
            .add(Items.INFESTED_STONE_BRICKS)
            .add(Items.SPAWNER)
            .add(Items.TRIAL_SPAWNER)
            .add(Items.VAULT)
            .add(LocalItems.DEBRIS.get());
        this.tag(LocalTags.BREAD)
            .add(LocalItems.BREADROCK.get())
            .replace(false);
        this.tag(LocalTags.EQUIPMENT)
            .addTag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "armors")))
            .addTag(ItemTags.create(Identifier.fromNamespaceAndPath("c", "tools")));
        this.tag(LocalTags.FOX_TRUST_HAT)
            .add(LocalItems.BLACK_FOX_EARS.get())
            .add(LocalItems.TWO_COLOR_FOX_EARS.get())
            .add(LocalItems.COMICALLY_TALL_FOX_EARS.get())
            .add(LocalItems.ORANGE_FOX_EARS.get());
    }
}
