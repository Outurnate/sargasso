/* (C)2026 */
package com.outurnate.sargasso.datagen.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.LocalLootTableProvider;
import com.outurnate.sargasso.registry.LocalBlocks;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;

public class LocalStructureProcessorListProvider {
    public static final ResourceKey<StructureProcessorList> GLITCH = ResourceKey
        .create(Registries.PROCESSOR_LIST, SuperSargassoSea.ID("glitch"));
    public static final ResourceKey<StructureProcessorList> BOOKSHELF_LOOT = ResourceKey
        .create(Registries.PROCESSOR_LIST, SuperSargassoSea.ID("bookshelf_loot"));

    public static void provide(BootstrapContext<StructureProcessorList> bootstrap) {
        bootstrap.register(
            GLITCH,
            new StructureProcessorList(
                List.of(
                    new RuleProcessor(
                        List.of(
                            new ProcessorRule(
                                new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.4F),
                                AlwaysTrueTest.INSTANCE,
                                LocalBlocks.GLITCH.get().defaultBlockState()))))));
        bootstrap.register(
            BOOKSHELF_LOOT,
            new StructureProcessorList(
                List.of(
                    new RuleProcessor(
                        List.of(
                            new ProcessorRule(
                                new BlockMatchTest(Blocks.CHISELED_BOOKSHELF),
                                AlwaysTrueTest.INSTANCE,
                                PosAlwaysTrueTest.INSTANCE,
                                Blocks.DIRT.defaultBlockState(),
                                new AppendLoot(LocalLootTableProvider.BOOKS)))))));
    }
}
