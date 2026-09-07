package com.outurnate.sargasso.worldgen;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RuinsFeature extends Feature<NoneFeatureConfiguration> {
    private record Corner(int x, int y) {
    }

    private static record Room(int x, int y, int w, int h) {
        private static final int MIN_SIZE = 3;

        private List<Room> divideW(RandomSource random) {
            int partition = random.nextInt(x + MIN_SIZE + 1, w - MIN_SIZE);
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
                new Room(x, y, partition, h),
                new Room(x + partition, y, w - partition, h));
        }

        private List<Room> divideH(RandomSource random) {
            int partition = random.nextInt(y + MIN_SIZE, h - MIN_SIZE);
            return List.of(
                new Room(x, y, w, partition),
                new Room(x, y + partition, w, h - partition));
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
                walls.add(new Wall(WallDirection.EAST_WEST, x + xc, y));
                walls.add(new Wall(WallDirection.EAST_WEST, x + xc, y + h));
            }
            for (int yc = 1; yc < h; ++yc) {
                walls.add(new Wall(WallDirection.NORTH_SOUTH, x, y + yc));
                walls.add(new Wall(WallDirection.NORTH_SOUTH, x + w, y + yc));
            }

            ArrayList<Corner> corners = new ArrayList<>();
            corners.add(new Corner(x, y));
            corners.add(new Corner(x + w, y));
            corners.add(new Corner(x, y + h));
            corners.add(new Corner(x + w, y + h));
            return Pair.of(corners, walls);
        }
    }

    private record Wall(WallDirection direction, int x, int y) {
    }

    private static enum WallDirection {
        NORTH_SOUTH, // y/h
        EAST_WEST // x/w
    }

    public RuinsFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
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

        for (Wall wall : allWalls) {
            placeWall(wall, context.level(), context.origin());
        }
        for (Corner corner : allCorners) {
            placeCorner(corner, context.level(), context.origin());
        }
        return true;
    }

    private void placeCorner(Corner corner, LevelWriter level, BlockPos origin) {
        this.setBlock(level, origin.offset(corner.x, 0, corner.y), Blocks.GREEN_CONCRETE.defaultBlockState());
    }

    private void placeWall(Wall wall, LevelWriter level, BlockPos origin) {
        this.setBlock(level, origin.offset(wall.x, 0, wall.y), switch (wall.direction) {
            case WallDirection.NORTH_SOUTH -> Blocks.RED_CONCRETE.defaultBlockState();
            case WallDirection.EAST_WEST -> Blocks.BLUE_CONCRETE.defaultBlockState();
        });
    }
}
