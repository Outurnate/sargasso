package com.outurnate.sargasso.block;

import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class SortingBinBlock extends Block {
    public static enum Type implements StringRepresentable {
        BOLTS,
        BUCKETS,
        COGS,
        WIRES,
        CLOCKSPRINGS,
        CIRCUITS;

        public static Type getRandom() {
            return switch (RandomSource.createThreadLocalInstance().nextInt(6)) {
                case 0 -> BOLTS;
                case 1 -> BUCKETS;
                case 2 -> COGS;
                case 3 -> WIRES;
                case 4 -> CLOCKSPRINGS;
                default -> CIRCUITS;
            };
        }

        @Override
        public String getSerializedName() {
            return switch (this) {
                case BOLTS -> "bolts";
                case BUCKETS -> "buckets";
                case COGS -> "cogs";
                case WIRES -> "wires";
                case CLOCKSPRINGS -> "clocksprings";
                case CIRCUITS -> "circuits";
            };
        }
    }

    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    public SortingBinBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any().setValue(TYPE, Type.BOLTS));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(TYPE, Type.getRandom());
    }
}
