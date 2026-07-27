/* (C)2026 */
package com.outurnate.sargasso;

import com.outurnate.sargasso.registry.LocalDimensions;

import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;

public class Utils {
    public static int countBlocks(Level level, BlockPos center, int radius, Block block) {
        int count = 0;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = center.getX() - radius; x <= center.getX() + radius; ++x) {
            for (int y = center.getY() - radius; y <= center.getY() + radius; ++y) {
                for (int z = center.getZ() - radius; z <= center.getZ() + radius; ++z) {
                    pos.set(x, y, z);
                    if (level.getBlockState(pos).is(block)) {
                        ++count;
                    }
                }
            }
        }
        return count;
    }

    public static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }

        return a;
    }

    public static long nextLong(RandomSource randomSource, long max) {
        long r;
        long limit = Long.MAX_VALUE - (Long.MAX_VALUE % max);
        do {
            r = randomSource.nextLong() & Long.MAX_VALUE;
        } while (r >= limit);
        return r % max;
    }

    public static Vec3 randomVelInDirection(
        RandomSource rand,
        Direction direction,
        float minSpeed,
        float maxSpeed) {
        direction = direction.getOpposite();
        float speed = minSpeed + (rand.nextFloat() * (maxSpeed - minSpeed));
        float yRot = direction.toYRot() + ((rand.nextFloat() * 80.0F) - 40.0F);
        float xRot = direction.toYRot() + ((rand.nextFloat() * 80.0F) - 40.0F);
        float xd = -Mth.sin(yRot * Mth.DEG_TO_RAD) * Mth.cos(xRot * Mth.DEG_TO_RAD);
        float yd = -Mth.sin(xRot * Mth.DEG_TO_RAD);
        float zd = Mth.cos(yRot * Mth.DEG_TO_RAD) * Mth.cos(xRot * Mth.DEG_TO_RAD);
        return new Vec3(xd, yd, zd).normalize().scale(speed);
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
