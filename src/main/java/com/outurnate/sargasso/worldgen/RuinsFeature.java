package com.outurnate.sargasso.worldgen;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class RuinsFeature extends Feature<NoneFeatureConfiguration> {
    private static record Room(int x, int y, int w, int h) {
        private List<Room> divideW(RandomSource random) {
            int partition = random.nextInt(x, w);
            return List.of(
                new Room(x, y, w - partition, h),
                new Room(x + partition - 1, y, partition, h));
        }

        private List<Room> divideH(RandomSource random) {
            int partition = random.nextInt(y, h);
            return List.of(
                new Room(x, y, w, h - partition),
                new Room(x, y + partition - 1, w, partition));
        }

        public List<Room> divide(RandomSource random) {
            if (w == 1 && h == 1) {
                return List.of(this);
            } else if (h == 1) {
                return divideW(random);
            } else if (w == 1) {
                return divideH(random);
            } else {
                if (random.nextBoolean()) {
                    return divideW(random);
                } else {
                    return divideH(random);
                }
            }
        }
    }

    public RuinsFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        RandomSource random = context.random();
        List<Room> rooms = List.of(new Room(0, 0, 8, 8));
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
        }
        return true;
    }
}
