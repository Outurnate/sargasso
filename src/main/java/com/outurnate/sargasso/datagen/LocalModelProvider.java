/* (C)2026 */
package com.outurnate.sargasso.datagen;

import static java.util.Map.entry;
import static net.minecraft.client.data.models.BlockModelGenerators.NOP;
import static net.minecraft.client.data.models.BlockModelGenerators.ROTATION_HORIZONTAL_FACING;
import static net.minecraft.client.data.models.BlockModelGenerators.X_ROT_180;
import static net.minecraft.client.data.models.BlockModelGenerators.X_ROT_270;
import static net.minecraft.client.data.models.BlockModelGenerators.X_ROT_90;
import static net.minecraft.client.data.models.BlockModelGenerators.Y_ROT_180;
import static net.minecraft.client.data.models.BlockModelGenerators.Y_ROT_270;
import static net.minecraft.client.data.models.BlockModelGenerators.Y_ROT_90;
import static net.minecraft.client.data.models.BlockModelGenerators.condition;
import static net.minecraft.client.data.models.BlockModelGenerators.createSimpleBlock;
import static net.minecraft.client.data.models.BlockModelGenerators.plainModel;
import static net.minecraft.client.data.models.BlockModelGenerators.plainVariant;
import static net.minecraft.client.data.models.BlockModelGenerators.variants;

import com.mojang.math.Quadrant;
import com.mojang.math.Transformation;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.block.ShockTherapistBlock;
import com.outurnate.sargasso.block.ShockTherapistBlock.Phase;
import com.outurnate.sargasso.client.FromCosmeticItemTintSource;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalItems;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.item.CompositeModel;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
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
    private static final VariantMutator Z_ROT_90 = VariantMutator.Z_ROT.withValue(Quadrant.R90);

    private static final VariantMutator Z_ROT_180 = VariantMutator.Z_ROT.withValue(Quadrant.R180);

    private static final VariantMutator Z_ROT_270 = VariantMutator.Z_ROT.withValue(Quadrant.R270);

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

    public LocalModelProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, SuperSargassoSea.MODID);
    }

    private void createThreeAxisRotate(Block block, BlockModelGenerators blockModelGenerators) {
        Variant normal = plainModel(TexturedModel.CUBE.create(block, blockModelGenerators.modelOutput));
        VariantMutator[] mutators = new VariantMutator[] {
            X_ROT_90,
            X_ROT_180,
            X_ROT_270,
            Y_ROT_90,
            Y_ROT_180,
            Y_ROT_270,
            Z_ROT_90,
            Z_ROT_180,
            Z_ROT_270
        };
        List<Variant> mutated = new ArrayList<>();
        mutated.add(normal);
        for (VariantMutator mutator : mutators) {
            mutated.add(normal.with(mutator));
        }
        blockModelGenerators.blockStateOutput
            .accept(MultiVariantGenerator.dispatch(block, variants(mutated.toArray(Variant[]::new))));
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
        createThreeAxisRotate(LocalBlocks.FLOTSAM.get(), blockModels);
        createRotatedAndModelRandomizedBlock(
            blockModels,
            LocalBlocks.DEBRIS.get(),
            Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "block/debris"),
            3);

        blockModels.createTrivialCube(LocalBlocks.CREAMY_BEDROCK.get());
        blockModels.createTrivialCube(LocalBlocks.STARMETAL_BLOCK.get());
        blockModels.createTrivialCube(LocalBlocks.REINFORCED_STARMETAL_BLOCK.get());
        blockModels.createTrivialCube(LocalBlocks.PETRIFIED_FLOTSAM.get());
        blockModels.createTrivialCube(LocalBlocks.RICH_PETRIFIED_FLOTSAM.get());
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
        itemModels.generateFlatItem(LocalItems.QUARTER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.KEY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.KEY_OMINOUS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.STARMETAL_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.CIRCUIT_BOARD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.BROKEN_COG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.CLOCKSPRING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.RUSTED_BOLT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.LOOSE_WIRE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.LEAKY_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.STARMETAL_SCRAP.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(LocalItems.ATLAS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.declareCustomModelItem(LocalItems.HAMMER.get());

        Item foxEars = LocalItems.FOX_EARS.get();
        int foxOrange = ARGB.color(0xC3, 0x58, 0x17);
        int white = ARGB.color(255, 255, 255);
        int black = ARGB.color(0, 0, 0);
        int gray = ARGB.color(127, 127, 127);
        itemModels.itemModelOutput.accept(
            foxEars,
            ItemModelUtils.tintedModel(
                ModelLocationUtils.getModelLocation(foxEars),
                new FromCosmeticItemTintSource(foxOrange, Map.of(LocalItems.AA_BATTERY.get(), black)),
                new FromCosmeticItemTintSource(foxOrange, Map.of(LocalItems.AA_BATTERY.get(), foxOrange)),
                new Constant(white)));

        Item comicallyTallFoxEars = LocalItems.COMICALLY_TALL_FOX_EARS.get();
        itemModels.itemModelOutput.accept(
            comicallyTallFoxEars,
            ItemModelUtils.tintedModel(
                ModelLocationUtils.getModelLocation(comicallyTallFoxEars),
                new Constant(black),
                new Constant(black),
                new Constant(gray)));
    }
}
