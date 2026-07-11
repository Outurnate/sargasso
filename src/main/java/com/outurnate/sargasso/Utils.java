/* (C)2026 */
package com.outurnate.sargasso;

import com.outurnate.sargasso.registry.LocalDimensions;

import java.util.Set;

import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Utils {
    public static long nextLong(RandomSource randomSource, long max) {
        long r;
        long limit = Long.MAX_VALUE - (Long.MAX_VALUE % max);
        do {
            r = randomSource.nextLong() & Long.MAX_VALUE;
        } while (r >= limit);
        return r % max;
    }

    // https://forums.minecraftforge.net/topic/74979-1144-rotate-voxel-shapes/
    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };

        int times = (to.ordinal() - from.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes(
                (minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes
                    .or(buffer[1], Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

    public static void sendToSea(ServerPlayer player) {
        ServerLevel sea = player.level().getServer().getLevel(LocalDimensions.SEA);
        RandomSource random = sea.getRandom();

        float radius = (float) Config.VOID_SPAWN_RADIUS.getAsDouble();
        float theta = random.nextFloat() * Mth.TWO_PI;
        int x = (int) (radius * Mth.cos(theta));
        int z = (int) (radius * Mth.sin(theta));
        int y = sea.getChunkSource()
            .getChunk(
                SectionPos.blockToSectionCoord(x),
                SectionPos.blockToSectionCoord(z),
                ChunkStatus.FULL,
                true)
            .getHeight(Types.WORLD_SURFACE, x, z);

        player.teleportTo(sea, x + 0.5, y + 1.0, z + 0.5, Set.of(), 0, 0, false);
        sea.playSound(null, player, SoundEvents.PORTAL_TRAVEL, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
