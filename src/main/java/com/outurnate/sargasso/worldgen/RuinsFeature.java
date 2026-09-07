package com.outurnate.sargasso.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RuinsFeature extends Feature<NoneFeatureConfiguration> {
    private static record Room(int x, int y, int w, int h) {
        private static final int MIN_SIZE = 3;

        private List<Room> divideW(RandomSource random) {
            int partition = random.nextInt(x, w);
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
            int partition = random.nextInt(y, h);
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

        public List<Wall> walls() {
            ArrayList<Wall> walls = new ArrayList<>();
            for (int xc = x + 1; xc < w - 1; ++xc) {
                walls.add(new Wall(WallDirection.EAST_WEST, xc, y));
                walls.add(new Wall(WallDirection.EAST_WEST, xc, y + h - 1));
            }
            for (int yc = y + 1; yc < h - 1; ++yc) {
                walls.add(new Wall(WallDirection.NORTH_SOUTH, x, yc));
                walls.add(new Wall(WallDirection.NORTH_SOUTH, x + w - 1, yc));
            }
            walls.add(new Wall(WallDirection.CORNER, x, y));
            walls.add(new Wall(WallDirection.CORNER, x + w - 1, y));
            walls.add(new Wall(WallDirection.CORNER, x, y + h - 1));
            walls.add(new Wall(WallDirection.CORNER, x + w - 1, y + h - 1));
            return walls;
        }
    }

    private record Wall(WallDirection direction, int x, int y) {
    }

    private static enum WallDirection {
        NORTH_SOUTH, // y/h
        EAST_WEST, // x/w
        CORNER
    }

    public RuinsFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        RandomSource random = context.random();
        List<Room> rooms = List.of(new Room(0, 0, 16, 16));
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
        for (Room room : rooms) {
            SuperSargassoSea.LOGGER.error(room.toString());
            for (Wall wall : room.walls()) {
                SuperSargassoSea.LOGGER.error(wall.toString());
                placeWall(wall, context.level(), context.origin());
            }
        }
        return true;
    }

    private void placeWall(Wall wall, LevelWriter level, BlockPos origin) {
        this.setBlock(level, origin.offset(wall.x, 0, wall.y), switch (wall.direction) {
            case WallDirection.NORTH_SOUTH -> Blocks.RED_CONCRETE.defaultBlockState();
            case WallDirection.EAST_WEST -> Blocks.BLUE_CONCRETE.defaultBlockState();
            case WallDirection.CORNER -> Blocks.GREEN_CONCRETE.defaultBlockState();
        });
    }
}
