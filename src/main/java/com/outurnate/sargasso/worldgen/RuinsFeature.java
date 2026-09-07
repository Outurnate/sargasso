package com.outurnate.sargasso.worldgen;

import com.mojang.datafixers.util.Pair;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalBlocks;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class RuinsFeature extends Feature<NoneFeatureConfiguration> {
    private record Corner(int x, int z) {
    }

    private static record Room(int x, int z, int w, int h) {
        private static final int MIN_SIZE = 3;

        private static boolean isDimDivisible(int dim) {
            return dim > (MIN_SIZE * 2);
        }

        private List<Room> divideW(RandomSource random) {
            int partition = random.nextInt(MIN_SIZE, w - MIN_SIZE);
            // x = 0
            // w = 8
            // p = 2
            // 1:
            // x = 0
            // w = 2
            // 2:
            // x = 2
            // w = 6
            return List.of(
                new Room(x, z, partition, h),
                new Room(x + partition, z, w - partition, h));
        }

        private List<Room> divideH(RandomSource random) {
            int partition = random.nextInt(MIN_SIZE, h - MIN_SIZE);
            return List.of(
                new Room(x, z, w, partition),
                new Room(x, z + partition, w, h - partition));
        }

        public List<Room> divide(RandomSource random) {
            if (!isDimDivisible(w) && !isDimDivisible(h)) {
                return List.of(this);
            } else if (!isDimDivisible(h)) {
                return divideW(random);
            } else if (!isDimDivisible(w)) {
                return divideH(random);
            } else {
                if (random.nextBoolean()) {
                    return divideW(random);
                } else {
                    return divideH(random);
                }
            }
        }

        public Pair<List<Corner>, List<Wall>> elements() {
            ArrayList<Wall> walls = new ArrayList<>();
            for (int xc = 1; xc < w; ++xc) {
                walls.add(new Wall(WallDirection.EAST_WEST, x + xc, z));
                walls.add(new Wall(WallDirection.EAST_WEST, x + xc, z + h));
            }
            for (int zc = 1; zc < h; ++zc) {
                walls.add(new Wall(WallDirection.NORTH_SOUTH, x, z + zc));
                walls.add(new Wall(WallDirection.NORTH_SOUTH, x + w, z + zc));
            }

            ArrayList<Corner> corners = new ArrayList<>();
            corners.add(new Corner(x, z));
            corners.add(new Corner(x + w, z));
            corners.add(new Corner(x, z + h));
            corners.add(new Corner(x + w, z + h));
            return Pair.of(corners, walls);
        }

        public Room shrink(int i) {
            return new Room(x, z, w - i, h - i);
        }
    }

    private record Wall(WallDirection direction, int x, int z) {
        private static final WeightedList<Function<WallDirection, List<Vec3i>>> OFFSETS = WeightedList.of(
            new Weighted<>(
                direction -> List.of(
                    BlockPos.ZERO,
                    BlockPos.ZERO.above(1),
                    BlockPos.ZERO.above(2)),
                20),
            new Weighted<>(
                direction -> List.of(
                    BlockPos.ZERO,
                    BlockPos.ZERO.offset(direction.left())),
                1),
            new Weighted<>(
                direction -> List.of(
                    BlockPos.ZERO,
                    BlockPos.ZERO.offset(direction.right())),
                1),
            new Weighted<>(
                direction -> List.of(
                    BlockPos.ZERO,
                    BlockPos.ZERO.offset(direction.left()),
                    BlockPos.ZERO.above(1)),
                10),
            new Weighted<>(
                direction -> List.of(
                    BlockPos.ZERO,
                    BlockPos.ZERO.offset(direction.right()),
                    BlockPos.ZERO.above(1)),
                10),
            new Weighted<>(
                direction -> List.of(
                    BlockPos.ZERO,
                    BlockPos.ZERO.above(1)),
                1),
            new Weighted<>(
                direction -> List.of(
                    BlockPos.ZERO,
                    BlockPos.ZERO.offset(direction.right()),
                    BlockPos.ZERO.offset(direction.left())),
                1));

        public List<BlockPos> getPositions(BlockPos origin, RandomSource random) {
            BlockPos localOrigin = origin.offset(x, 0, z);
            return OFFSETS.getRandom(random).get().apply(direction).stream()
                .map(offset -> localOrigin.offset(offset)).toList();
        }
    }

    private static enum WallDirection {
        NORTH_SOUTH, // z/h
        EAST_WEST; // x/w

        public Vec3i left() {
            return switch (this) {
                case WallDirection.NORTH_SOUTH -> new Vec3i(1, 0, 0);
                case WallDirection.EAST_WEST -> new Vec3i(0, 0, 1);
            };
        }

        public Vec3i right() {
            return switch (this) {
                case WallDirection.NORTH_SOUTH -> new Vec3i(-1, 0, 0);
                case WallDirection.EAST_WEST -> new Vec3i(0, 0, -1);
            };
        }
    }

    public RuinsFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    private List<Room> generateFootprint(RandomSource random) {
        float chanceOfSplit = 0.4F;
        if (random.nextFloat() < chanceOfSplit) {
            return List.of(
                new Room(
                    0,
                    0,
                    random.nextInt(Room.MIN_SIZE * 2, Room.MIN_SIZE * 4),
                    random.nextInt(Room.MIN_SIZE * 2, Room.MIN_SIZE * 4)),
                new Room(
                    Room.MIN_SIZE * 4,
                    Room.MIN_SIZE * 4,
                    random.nextInt(Room.MIN_SIZE * 4, Room.MIN_SIZE * 8),
                    random.nextInt(Room.MIN_SIZE * 4, Room.MIN_SIZE * 8)));
        } else {
            return List.of(
                new Room(
                    0,
                    0,
                    random.nextInt(Room.MIN_SIZE * 2, 20),
                    random.nextInt(Room.MIN_SIZE * 2, 20)));
        }
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        try {
            RandomSource random = context.random();
            placeRooms(generateFootprint(random), random, context.level(), context.origin());
            return true;
        } catch (Exception e) {
            SuperSargassoSea.LOGGER.error(e.toString());
            SuperSargassoSea.LOGGER.error(ExceptionUtils.getStackTrace(e));
            throw e;
        }
    }

    private void placeCorner(Corner corner, LevelWriter level, BlockPos origin) {
        for (int i = 0; i < 5; ++i) {
            this.setBlock(
                level,
                origin.offset(corner.x, i - 2, corner.z),
                LocalBlocks.STARMETAL_BLOCK.get().defaultBlockState());
        }
    }

    private void placeRooms(List<Room> bases, RandomSource random, LevelWriter level, BlockPos origin) {
        HashSet<Wall> allWalls = new HashSet<>();
        ArrayList<Corner> allCorners = new ArrayList<>();

        for (Room base : bases) {
            List<Room> rooms = List.of(base.shrink(1)); // actual dims +1 in x/z, so shrink 1
            float divisionChance = 0.8F;
            for (int i = 0; i < 3; ++i) {
                ArrayList<Room> newRooms = new ArrayList<>();
                for (Room room : rooms) {
                    if (random.nextFloat() < (divisionChance / i + 1)) {
                        newRooms.addAll(room.divide(random));
                    } else {
                        newRooms.add(room);
                    }
                }
                rooms = newRooms;
            }

            for (Room room : rooms) {
                Pair<List<Corner>, List<Wall>> elements = room.elements();
                allCorners.addAll(elements.getFirst());
                allWalls.addAll(elements.getSecond());
            }

            // floor
            for (int x = 0; x < base.w; ++x) {
                for (int z = 0; z < base.h; ++z) {
                    setBlock(
                        level,
                        origin.offset(base.x + x, 0, base.z + z),
                        Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState());
                }
            }
        }

        BlockPos wallOrigin = origin.above(1);
        for (Wall wall : allWalls) {
            placeWall(wall, random, level, wallOrigin);
        }

        for (Corner corner : allCorners) {
            placeCorner(corner, level, wallOrigin);
        }
    }

    private void placeWall(Wall wall, RandomSource random, LevelWriter level, BlockPos origin) {
        for (BlockPos pos : wall.getPositions(origin, random)) {
            this.setBlock(level, pos, Blocks.GRAY_CONCRETE.defaultBlockState());
        }
    }
}
