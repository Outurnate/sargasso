/* (C)2026 */
package com.outurnate.sargasso.datagen;

import static java.util.Map.entry;
import static net.minecraft.client.data.models.BlockModelGenerators.NOP;
import static net.minecraft.client.data.models.BlockModelGenerators.ROTATION_HORIZONTAL_FACING;
import static net.minecraft.client.data.models.BlockModelGenerators.X_ROT_180;
import static net.minecraft.client.data.models.BlockModelGenerators.X_ROT_90;
import static net.minecraft.client.data.models.BlockModelGenerators.Y_ROT_180;
import static net.minecraft.client.data.models.BlockModelGenerators.Y_ROT_270;
import static net.minecraft.client.data.models.BlockModelGenerators.Y_ROT_90;
import static net.minecraft.client.data.models.BlockModelGenerators.condition;
import static net.minecraft.client.data.models.BlockModelGenerators.createSimpleBlock;
import static net.minecraft.client.data.models.BlockModelGenerators.plainModel;
import static net.minecraft.client.data.models.BlockModelGenerators.plainVariant;

import com.mojang.math.Transformation;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.block.ShockTherapistBlock;
import com.outurnate.sargasso.block.ShockTherapistBlock.Phase;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalItems;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.item.CompositeModel;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.joml.Vector3f;

public class LocalModelProvider extends ModelProvider {
    private static void createBottleWithContents(ItemModelGenerators itemModels, Item item) {
        Identifier model = itemModels.generateLayeredItem(
            item,
            TextureMapping.getItemTexture(Items.SPLASH_POTION),
            TextureMapping.getItemTexture(item));
        itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(model));
    }

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

    private MultiPartGenerator generateShockTherapist() {
        Map<AttachFace, VariantMutator> attachMutators = Map.ofEntries(
            entry(AttachFace.CEILING, X_ROT_180),
            entry(AttachFace.FLOOR, NOP),
            entry(AttachFace.WALL, X_ROT_90));
        Map<Direction, VariantMutator> horizontalMutators = Map.ofEntries(
            entry(Direction.EAST, Y_ROT_90),
            entry(Direction.SOUTH, Y_ROT_180),
            entry(Direction.WEST, Y_ROT_270),
            entry(Direction.NORTH, NOP));

        Identifier shock_therapist = SuperSargassoSea.ID("block/shock_therapist");
        Identifier shock_therapist_glow = SuperSargassoSea.ID("block/shock_therapist_glow");
        MultiPartGenerator generator = MultiPartGenerator.multiPart(LocalBlocks.SHOCK_THERAPIST.get());
        for (AttachFace attachFace : AttachFace.values()) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                generator.with(
                    condition()
                        .term(BlockStateProperties.ATTACH_FACE, attachFace)
                        .term(BlockStateProperties.HORIZONTAL_FACING, direction),
                    plainVariant(shock_therapist)
                        .with(attachMutators.get(attachFace))
                        .with(horizontalMutators.get(direction)));
                generator.with(
                    condition()
                        .term(ShockTherapistBlock.PHASE, Phase.DISCHARGING)
                        .term(BlockStateProperties.ATTACH_FACE, attachFace)
                        .term(BlockStateProperties.HORIZONTAL_FACING, direction),
                    plainVariant(shock_therapist_glow)
                        .with(attachMutators.get(attachFace))
                        .with(horizontalMutators.get(direction)));
            }
        }
        return generator;
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        createRotatedAndTextureRandomizedBlock(blockModels, LocalBlocks.FLOTSAM.get(), 4);
        createRotatedAndModelRandomizedBlock(
            blockModels,
            LocalBlocks.DEBRIS.get(),
            Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "block/debris"),
            3);

        blockModels.createTrivialCube(LocalBlocks.CREAMY_BEDROCK.get());
        blockModels.createParticleOnlyBlock(LocalBlocks.GLITCH.get(), Blocks.OBSIDIAN);
        blockModels.createParticleOnlyBlock(LocalBlocks.PORTAL.get(), Blocks.OBSIDIAN);
        Identifier toaster = SuperSargassoSea.ID("block/toaster");
        blockModels.blockStateOutput.accept(
            createSimpleBlock(
                LocalBlocks.TOASTER.get(),
                plainVariant(toaster)).with(ROTATION_HORIZONTAL_FACING));
        Identifier pylon = SuperSargassoSea.ID("block/pylon");
        blockModels.blockStateOutput.accept(
            createSimpleBlock(
                LocalBlocks.PYLON.get(),
                plainVariant(pylon)));

        blockModels.blockStateOutput.accept(generateShockTherapist());

        itemModels.itemModelOutput.accept(
            LocalItems.DEBRIS.get(),
            new CompositeModel.Unbaked(
                List.of(
                    new CuboidItemModelWrapper.Unbaked(
                        SuperSargassoSea.ID("block/debris"),
                        Optional.empty(),
                        Collections.emptyList()),
                    new CuboidItemModelWrapper.Unbaked(
                        SuperSargassoSea.ID("block/debris1"),
                        Optional.empty(),
                        Collections.emptyList()),
                    new CuboidItemModelWrapper.Unbaked(
                        SuperSargassoSea.ID("block/debris2"),
                        Optional.empty(),
                        Collections.emptyList())),
                Optional.of(
                    new Transformation(
                        new Vector3f(0.25F, 0.5F, 0.25F),
                        null,
                        new Vector3f(0.5F, 0.5F, 0.5F),
                        null))));
        itemModels.generateFlatItem(LocalItems.BEDROCK_SLOP.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.BREADROCK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.BEDROCK_CREAM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.STUDDED_LEATHER_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.STUDDED_LEATHER_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.STUDDED_LEATHER_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.STUDDED_LEATHER_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.BLACK_FOX_EARS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.TWO_COLOR_FOX_EARS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.COMICALLY_TALL_FOX_EARS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.ORANGE_FOX_EARS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.AA_BATTERY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.RECHARGABLE_AA_BATTERY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(
            LocalItems.STUDDED_LEATHER_UPGRADE_SMITHING_TEMPLATE.get(),
            ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.POTATO_BATTERY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.PERSONAL_VOLTMETER.get(), ModelTemplates.FLAT_ITEM);
        createBottleWithContents(itemModels, LocalItems.LIGHTNING_BOTTLE.get());
        itemModels.generateFlatItem(LocalItems.RECORD_UNCHECKED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.REDSTONE_EMP.get(), ModelTemplates.FLAT_ITEM);
        itemModels.declareCustomModelItem(LocalItems.FOX_EARS.get());
    }
}
