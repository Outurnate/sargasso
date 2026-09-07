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
        public List<Room> divide(RandomSource random) {
            if (random.nextBoolean()) {
                int partition = random.nextInt(x, w);
                return List.of(
                    new Room(x, y, w - partition, h),
                    new Room(x + partition, y, partition, h));
            } else {
                int partition = random.nextInt(y, h);
                return List.of(
                    new Room(x, y, w, h - partition),
                    new Room(x, y + partition, w, partition));
            }
        }
    }

    private static List<Room> maybeDivide(RandomSource random, List<Room> rooms) {
        ArrayList<Room> outputRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (random.nextBoolean()) {
                outputRooms.addAll(room.divide(random));
            }
        }
        return outputRooms;
    }

    public RuinsFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        List<Room> rooms = List.of(new Room(0, 0, 8, 8));
        for (int i = 0; i < 3; ++i) {
            rooms = maybeDivide(context.random(), rooms);
        }
        for (Room room : rooms) {
            SuperSargassoSea.LOGGER.error(room.toString());
        }
        return true;
    }
}
