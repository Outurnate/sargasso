/* (C)2026 */
package com.outurnate.sargasso.datagen;

import static net.minecraft.client.data.models.BlockModelGenerators.Y_ROT_180;
import static net.minecraft.client.data.models.BlockModelGenerators.Y_ROT_270;
import static net.minecraft.client.data.models.BlockModelGenerators.Y_ROT_90;
import static net.minecraft.client.data.models.BlockModelGenerators.createSimpleBlock;
import static net.minecraft.client.data.models.BlockModelGenerators.plainModel;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalItems;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class LocalModelProvider extends ModelProvider {
    private static void createRotatedAndModelRandomizedBlock(
        BlockModelGenerators blockModels,
        Block block,
        Identifier baseModel,
        int numVariants) {
        ArrayList<Variant> allVariants = new ArrayList<Variant>();
        for (int i = 0; i < numVariants; ++i) {
            String suffix = i == 0 ? "" : String.valueOf(i);
            Variant base = plainModel(baseModel.withSuffix(suffix));
            allVariants.add(base);
            allVariants.add(base.with(Y_ROT_90));
            allVariants.add(base.with(Y_ROT_180));
            allVariants.add(base.with(Y_ROT_270));
        }
        blockModels.blockStateOutput.accept(
            createSimpleBlock(
                block,
                new MultiVariant(
                    WeightedList.of(allVariants.stream().map(v -> new Weighted<>(v, 1)).toList()))));
    }

    private static void createRotatedAndTextureRandomizedBlock(
        BlockModelGenerators blockModels,
        Block block,
        int numVariants) {
        ArrayList<Variant> allVariants = new ArrayList<Variant>();
        for (int i = 0; i < numVariants; ++i) {
            String suffix = i == 0 ? "" : String.valueOf(i);
            TexturedModel model = TexturedModel.CUBE.get(block);
            Material allMaterial = model.getMapping().get(TextureSlot.ALL);
            TextureMapping mapping = model.getMapping().copyAndUpdate(
                TextureSlot.ALL,
                new Material(allMaterial.sprite().withSuffix(suffix), allMaterial.forceTranslucent()));
            Variant base = plainModel(
                model.getTemplate().createWithSuffix(block, suffix, mapping, blockModels.modelOutput));
            allVariants.add(base);
            allVariants.add(base.with(Y_ROT_90));
            allVariants.add(base.with(Y_ROT_180));
            allVariants.add(base.with(Y_ROT_270));
        }
        blockModels.blockStateOutput.accept(
            createSimpleBlock(
                block,
                new MultiVariant(
                    WeightedList.of(allVariants.stream().map(v -> new Weighted<>(v, 1)).toList()))));
    }

    public LocalModelProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, SuperSargassoSea.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        createRotatedAndTextureRandomizedBlock(blockModels, LocalBlocks.FLOTSAM.get(), 4);
        createRotatedAndModelRandomizedBlock(
            blockModels,
            LocalBlocks.DEBRIS.get(),
            Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "block/debris"),
            2);

        blockModels.createTrivialCube(LocalBlocks.CREAMY_BEDROCK.get());
        blockModels.createParticleOnlyBlock(LocalBlocks.GLITCH.get(), Blocks.OBSIDIAN);

        itemModels.generateFlatItem(LocalItems.JUNK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.BEDROCK_SLOP.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.BREADROCK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.BEDROCK_CREAM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.LIGHTNING_BOTTLE.get(), ModelTemplates.FLAT_ITEM);
    }
}
