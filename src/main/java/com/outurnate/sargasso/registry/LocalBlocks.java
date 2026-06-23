/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.block.DebrisBlock;
import com.outurnate.sargasso.block.GlitchBlock;

import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class LocalBlocks {
    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister
        .createBlocks(SuperSargassoSea.MODID);

    public static final DeferredBlock<Block> FLOTSAM = REGISTRY.registerBlock(
        "flotsam",
        p -> new SandBlock(new ColorRGBA(MapColor.DIRT.col), p),
        p -> p
            .mapColor(MapColor.DIRT)
            .instrument(NoteBlockInstrument.SNARE)
            .strength(1.0F)
            .sound(SoundType.MUD));

    public static final DeferredBlock<Block> DEBRIS = REGISTRY.registerBlock(
        "debris",
        p -> new DebrisBlock(p),
        p -> p
            .mapColor(MapColor.DIRT)
            .replaceable()
            .noCollision()
            .instabreak()
            .noOcclusion()
            .offsetType(BlockBehaviour.OffsetType.XYZ)
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.MUD));

    public static final DeferredBlock<Block> CREAMY_BEDROCK = REGISTRY.registerBlock(
        "creamy_bedrock",
        p -> new Block(p),
        p -> p
            .mapColor(MapColor.SAND)
            .instrument(NoteBlockInstrument.BANJO)
            .strength(1.0F)
            .sound(SoundType.WOOL));

    public static final DeferredBlock<Block> GLITCH = REGISTRY.registerBlock(
        "glitch",
        p -> new GlitchBlock(p),
        p -> p
            .mapColor(MapColor.COLOR_BLACK)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .strength(-1.0F, 3600000.0F)
            .noLootTable()
            .isValidSpawn(Blocks::never)
            .noCollision()
            .noOcclusion()
            .sound(SoundType.AMETHYST));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
