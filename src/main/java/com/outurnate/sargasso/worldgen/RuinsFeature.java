package com.outurnate.sargasso.worldgen;

import com.mojang.datafixers.util.Pair;
import com.outurnate.sargasso.SuperSargassoSea;

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

public class RuinsFeature extends Feature<NoneFeatureConfiguration> {
    private record Corner(int x, int z) {
    }

    private static record Room(int x, int z, int w, int h) {
        private static final int MIN_SIZE = 3;

        private List<Room> divideW(RandomSource random) {
            int partition = random.nextInt(0, w - MIN_SIZE);
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
            int partition = random.nextInt(0, h - MIN_SIZE);
            return List.of(
                new Room(x, z, w, partition),
                new Room(x, z + partition, w, h - partition));
        }

        public List<Room> divide(RandomSource random) {
            if (w <= MIN_SIZE && h <= MIN_SIZE) {
                return List.of(this);
            } else if (h <= MIN_SIZE) {
                return divideW(random);
            } else if (w <= MIN_SIZE) {
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

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        try {
            RandomSource random = context.random();
            List<Room> rooms = List.of(new Room(0, 0, 16, 16)); // actual dims +1 in x/z
            for (int i = 0; i < 3; ++i) {
                ArrayList<Room> newRooms = new ArrayList<>();
                for (Room room : rooms) {
                    if (random.nextBoolean()) {
                        newRooms.addAll(room.divide(random));
                    } else {
                        newRooms.add(room);
                    }
                }
                rooms = newRooms;
            }

            HashSet<Wall> allWalls = new HashSet<>();
            ArrayList<Corner> allCorners = new ArrayList<>();
            for (Room room : rooms) {
                Pair<List<Corner>, List<Wall>> elements = room.elements();
                allCorners.addAll(elements.getFirst());
                allWalls.addAll(elements.getSecond());
            }

            LevelWriter level = context.level();
            for (Wall wall : allWalls) {
                placeWall(wall, random, level, context.origin());
            }
            for (Corner corner : allCorners) {
                placeCorner(corner, level, context.origin());
            }
            return true;
        } catch (Exception e) {
            SuperSargassoSea.LOGGER.error(e.toString());
            throw e;
        }
    }

    private void placeCorner(Corner corner, LevelWriter level, BlockPos origin) {
        this.setBlock(level, origin.offset(corner.x, 0, corner.z), Blocks.GREEN_CONCRETE.defaultBlockState());
    }

    private void placeWall(Wall wall, RandomSource random, LevelWriter level, BlockPos origin) {
        for (BlockPos pos : wall.getPositions(origin, random)) {
            this.setBlock(level, pos, Blocks.GRAY_CONCRETE.defaultBlockState());
        }
    }
}
