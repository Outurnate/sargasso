package com.outurnate.sargasso.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.outurnate.sargasso.block.entity.ShockTherapistBlockEntity;
import com.outurnate.sargasso.registry.LocalBlockEntities;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class ShockTherapistBlock extends Block implements EntityBlock {
    public static enum Phase implements StringRepresentable {
        IDLE,
        CHARGING,
        DISCHARGING;

        @Override
        public String getSerializedName() {
            return switch (this) {
                case IDLE -> "idle";
                case CHARGING -> "charging";
                case DISCHARGING -> "discharging";
            };
        }

        public Phase next() {
            return switch (this) {
                case IDLE -> CHARGING;
                case CHARGING -> DISCHARGING;
                case DISCHARGING -> IDLE;
            };
        }
    }

    public static final EnumProperty<Phase> PHASE = EnumProperty.create("phase", Phase.class);
    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<AttachFace> ATTACH_FACE = BlockStateProperties.ATTACH_FACE;
    public static final MapCodec<ShockTherapistBlock> CODEC = RecordCodecBuilder
        .mapCodec(i -> i.group(propertiesCodec()).apply(i, ShockTherapistBlock::new));
    private static final Map<Direction, VoxelShape> WALL_SHAPES = Map.of(
        Direction.NORTH,
        Shapes.box(0.125, 0.25, 0.5, 0.875, 0.75, 1.0),
        Direction.EAST,
        Shapes.box(0.0, 0.25, 0.125, 0.5, 0.75, 0.875),
        Direction.SOUTH,
        Shapes.box(0.125, 0.25, 0.0, 0.875, 0.75, 0.5),
        Direction.WEST,
        Shapes.box(0.5, 0.25, 0.125, 1.0, 0.75, 0.875));
    private static final Map<Direction, VoxelShape> FLOOR_SHAPE = Map.of(
        Direction.NORTH,
        Shapes.box(0.125, 0.0, 0.25, 0.875, 0.5, 0.75),
        Direction.EAST,
        Shapes.box(0.25, 0.0, 0.125, 0.75, 0.5, 0.875),
        Direction.SOUTH,
        Shapes.box(0.125, 0.0, 0.25, 0.875, 0.5, 0.75),
        Direction.WEST,
        Shapes.box(0.25, 0.0, 0.125, 0.75, 0.5, 0.875));
    private static final Map<Direction, VoxelShape> CEILING_SHAPE = Map.of(
        Direction.NORTH,
        Shapes.box(0.125, 0.5, 0.25, 0.875, 1.0, 0.75),
        Direction.EAST,
        Shapes.box(0.125, 0.25, 0.0, 0.875, 0.75, 0.5),
        Direction.SOUTH,
        Shapes.box(0.125, 0.5, 0.25, 0.875, 1.0, 0.75),
        Direction.WEST,
        Shapes.box(0.125, 0.25, 0.0, 0.875, 0.75, 0.5));

    @SuppressWarnings("unchecked")
    private static <E extends BlockEntity, A extends BlockEntity> @Nullable BlockEntityTicker<A> createTickerHelper(
        BlockEntityType<A> type,
        BlockEntityType<E> checkedType,
        BlockEntityTicker<? super E> ticker) {
        return checkedType == type ? (BlockEntityTicker<A>) ticker : null;
    }

    public ShockTherapistBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
            stateDefinition.any()
                .setValue(PHASE, Phase.IDLE)
                .setValue(ATTACH_FACE, AttachFace.FLOOR)
                .setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<ShockTherapistBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, ATTACH_FACE, PHASE);
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected VoxelShape getShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context) {
        return (switch (state.getValue(ATTACH_FACE)) {
            case CEILING -> CEILING_SHAPE;
            case FLOOR -> FLOOR_SHAPE;
            case WALL -> WALL_SHAPES;
        }).get(state.getValue(HORIZONTAL_FACING));
        // return Shapes.box(0.125, 0.0, 0.25, 0.875, 0.5, 0.875);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        AttachFace attachFace = switch (clickedFace) {
            case Direction.DOWN -> AttachFace.CEILING;
            case Direction.UP -> AttachFace.FLOOR;
            case Direction.NORTH -> AttachFace.WALL;
            case Direction.SOUTH -> AttachFace.WALL;
            case Direction.EAST -> AttachFace.WALL;
            case Direction.WEST -> AttachFace.WALL;
        };
        Direction horizontalFacing = switch (attachFace) {
            case AttachFace.CEILING -> context.getHorizontalDirection().getOpposite();
            case AttachFace.FLOOR -> context.getHorizontalDirection().getOpposite();
            case AttachFace.WALL -> clickedFace;
        };
        return this.defaultBlockState()
            .setValue(HORIZONTAL_FACING, horizontalFacing)
            .setValue(ATTACH_FACE, attachFace);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
        Level level,
        BlockState state,
        BlockEntityType<T> type) {
        return createTickerHelper(
            type,
            LocalBlockEntities.SHOCK_THERAPIST.get(),
            (levelInner, pos, stateInner, blockEntity) -> blockEntity.tick(levelInner, pos, stateInner));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new ShockTherapistBlockEntity(worldPosition, blockState);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }
}
