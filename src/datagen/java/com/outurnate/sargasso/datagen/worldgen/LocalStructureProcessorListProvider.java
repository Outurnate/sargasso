/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.LocalLootTableProvider;

import java.util.List;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.CappedProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;

public class LocalStructureProcessorListProvider {
    private static record WoolRule(ResourceKey<StructureProcessorList> key, StructureProcessorList instance) {
        public WoolRule(Block wool) {
            this(
                ResourceKey.create(
                    Registries.PROCESSOR_LIST,
                    SuperSargassoSea.ID("red_wool_to_" + BuiltInRegistries.BLOCK.getKey(wool).getPath())),
                new StructureProcessorList(
                    List.of(
                        new RuleProcessor(
                            List.of(
                                new ProcessorRule(
                                    new BlockStateMatchTest(Blocks.RED_WOOL.defaultBlockState()),
                                    AlwaysTrueTest.INSTANCE,
                                    wool.defaultBlockState()))))));
        }

        public static List<WoolRule> createWools() {
            return List.of(
                new WoolRule(Blocks.GRAY_WOOL),
                new WoolRule(Blocks.BLACK_WOOL),
                new WoolRule(Blocks.BROWN_WOOL),
                new WoolRule(Blocks.ORANGE_WOOL),
                new WoolRule(Blocks.YELLOW_WOOL),
                new WoolRule(Blocks.GREEN_WOOL),
                new WoolRule(Blocks.CYAN_WOOL),
                new WoolRule(Blocks.BLUE_WOOL),
                new WoolRule(Blocks.PURPLE_WOOL),
                new WoolRule(Blocks.MAGENTA_WOOL),
                new WoolRule(Blocks.PINK_WOOL),
                new WoolRule(Blocks.RED_WOOL));
        }
    }

    public static final ResourceKey<StructureProcessorList> REMOVE_CHESTS = ResourceKey
        .create(Registries.PROCESSOR_LIST, SuperSargassoSea.ID("remove_chests"));
    public static final List<ResourceKey<StructureProcessorList>> WOOLS = WoolRule.createWools().stream()
        .map(rule -> rule.key).toList();
    public static final ResourceKey<StructureProcessorList> BONE_SAND = ResourceKey
        .create(Registries.PROCESSOR_LIST, SuperSargassoSea.ID("bone_sand"));

    protected static String getItemName(ItemLike itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath();
    }

    public static void provide(BootstrapContext<StructureProcessorList> bootstrap) {
        bootstrap.register(
            REMOVE_CHESTS,
            new StructureProcessorList(
                List.of(
                    new BlockRotProcessor(
                        HolderSet.direct(BuiltInRegistries.BLOCK.wrapAsHolder(Blocks.CHEST)),
                        0.1F))));
        for (WoolRule rule : WoolRule.createWools()) {
            bootstrap.register(rule.key, rule.instance);
        }
        bootstrap.register(
            BONE_SAND,
            new StructureProcessorList(
                List.of(
                    new CappedProcessor(
                        new RuleProcessor(
                            List.of(
                                new ProcessorRule(
                                    new BlockMatchTest(Blocks.SAND),
                                    AlwaysTrueTest.INSTANCE,
                                    PosAlwaysTrueTest.INSTANCE,
                                    Blocks.SUSPICIOUS_SAND.defaultBlockState(),
                                    new AppendLoot(LocalLootTableProvider.BONE)))),
                        ConstantInt.of(10)))));
    }
}
