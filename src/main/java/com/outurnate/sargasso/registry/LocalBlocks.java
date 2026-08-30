/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.block.BetaChestBlock;
import com.outurnate.sargasso.block.DebrisBlock;
import com.outurnate.sargasso.block.GlitchBlock;
import com.outurnate.sargasso.block.PortalBlock;
import com.outurnate.sargasso.block.ShockTherapistBlock;
import com.outurnate.sargasso.block.SortingBinBlock;
import com.outurnate.sargasso.block.ToasterBlock;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TransparentBlock;
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
        DebrisBlock::new,
        p -> p
            .mapColor(MapColor.DIRT)
            .noCollision()
            .instabreak()
            .noOcclusion()
            .offsetType(BlockBehaviour.OffsetType.XYZ)
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.MUD));

    public static final DeferredBlock<Block> CREAMY_BEDROCK = REGISTRY.registerBlock(
        "creamy_bedrock",
        Block::new,
        p -> p
            .mapColor(MapColor.SAND)
            .instrument(NoteBlockInstrument.BANJO)
            .strength(1.0F)
            .sound(SoundType.WOOL));

    public static final DeferredBlock<Block> GLITCH = REGISTRY.registerBlock(
        "glitch",
        GlitchBlock::new,
        p -> p
            .mapColor(MapColor.COLOR_BLACK)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .strength(-1.0F, 3600000.0F)
            .noLootTable()
            .isValidSpawn(Blocks::never)
            .noCollision()
            .noOcclusion()
            .sound(SoundType.AMETHYST));

    public static final DeferredBlock<Block> PORTAL = REGISTRY.registerBlock(
        "portal",
        PortalBlock::new,
        p -> p
            .mapColor(MapColor.COLOR_BLACK)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .strength(-1.0F, 3600000.0F)
            .noLootTable()
            .isValidSpawn(Blocks::never)
            .noCollision()
            .noOcclusion()
            .sound(SoundType.AMETHYST));

    public static final DeferredBlock<Block> TOASTER = REGISTRY.registerBlock(
        "toaster",
        ToasterBlock::new,
        p -> p
            .mapColor(MapColor.COLOR_GRAY)
            .instrument(NoteBlockInstrument.SNARE)
            .strength(1.0F)
            .sound(SoundType.ANVIL)
            .noOcclusion());

    public static final DeferredBlock<Block> PYLON = REGISTRY.registerBlock(
        "pylon",
        TransparentBlock::new,
        p -> p
            .mapColor(MapColor.COLOR_ORANGE)
            .instrument(NoteBlockInstrument.DIDGERIDOO)
            .strength(1.0F)
            .sound(SoundType.WOOL)
            .forceSolidOn()
            .noOcclusion());

    public static final DeferredBlock<Block> SHOCK_THERAPIST = REGISTRY.registerBlock(
        "shock_therapist",
        ShockTherapistBlock::new,
        p -> p
            .mapColor(MapColor.COLOR_YELLOW)
            .instrument(NoteBlockInstrument.HARP)
            .strength(1.0F)
            .sound(SoundType.IRON)
            .forceSolidOn()
            .noOcclusion()
            .lightLevel(
                state -> state.getValue(ShockTherapistBlock.PHASE) == ShockTherapistBlock.Phase.DISCHARGING
                    ? 15
                    : 0));

    public static final DeferredBlock<Block> STARMETAL_BLOCK = REGISTRY.registerBlock(
        "starmetal_block",
        Block::new,
        p -> p
            .mapColor(MapColor.COLOR_GRAY)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .strength(1.0F)
            .sound(SoundType.IRON));

    public static final DeferredBlock<Block> REINFORCED_STARMETAL_BLOCK = REGISTRY.registerBlock(
        "reinforced_starmetal_block",
        Block::new,
        p -> p
            .mapColor(MapColor.COLOR_GRAY)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .strength(-1.0F, 3600000.0F)
            .sound(SoundType.IRON)
            .noLootTable()
            .isValidSpawn(Blocks::never));

    public static final DeferredBlock<Block> PETRIFIED_FLOTSAM = REGISTRY.registerBlock(
        "petrified_flotsam",
        Block::new,
        p -> p
            .mapColor(MapColor.COLOR_BROWN)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .strength(1.0F)
            .sound(SoundType.STONE));

    public static final DeferredBlock<Block> RICH_PETRIFIED_FLOTSAM = REGISTRY.registerBlock(
        "rich_petrified_flotsam",
        Block::new,
        p -> p
            .mapColor(MapColor.COLOR_BROWN)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .strength(1.0F)
            .sound(SoundType.STONE));

    public static final DeferredBlock<Block> SORTING_BIN = REGISTRY.registerBlock(
        "sorting_bin",
        SortingBinBlock::new,
        p -> p
            .mapColor(MapColor.COLOR_BLACK)
            .instrument(NoteBlockInstrument.BANJO)
            .strength(1.0F)
            .sound(SoundType.STONE));

    public static final DeferredBlock<Block> ALPHA_GRASS = REGISTRY.registerBlock(
        "alpha_grass",
        p -> new Block(p),
        p -> p
            .mapColor(MapColor.GRASS).strength(0.6F).sound(SoundType.GRASS));

    public static final DeferredBlock<Block> BETA_CHEST = REGISTRY.registerBlock(
        "beta_chest",
        p -> new BetaChestBlock(
            () -> LocalBlockEntities.BETA_CHEST.get(),
            SoundEvents.CHEST_OPEN,
            SoundEvents.CHEST_CLOSE,
            p),
        p -> p.mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F)
            .sound(SoundType.WOOD).ignitedByLava());

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
