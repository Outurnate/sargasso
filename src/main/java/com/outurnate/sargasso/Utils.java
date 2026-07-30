/* (C)2026 */
package com.outurnate.sargasso;

import com.outurnate.sargasso.registry.LocalDimensions;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Utils {
    public static record VelocityHeading(Vec3 velocity, float yrot, float xrot) {
    }

    public static Map<AttachFace, Map<Direction, VoxelShape>> attachedHorizontalMap(
        double minX,
        double minY,
        double minZ,
        double maxX,
        double maxY,
        double maxZ) {
        double fminY = 1.0 - maxY;
        double fmaxY = 1.0 - minY;

        EnumMap<Direction, VoxelShape> wallMap = new EnumMap<>(Direction.class);
        wallMap.put(Direction.NORTH, Shapes.box(minX, minZ, fminY, maxX, maxZ, fmaxY));
        wallMap.put(Direction.EAST, Shapes.box(minY, minZ, minX, maxY, maxZ, maxX));
        wallMap.put(Direction.SOUTH, Shapes.box(minX, minZ, minY, maxX, maxZ, maxY));
        wallMap.put(Direction.WEST, Shapes.box(fminY, minZ, minX, fmaxY, maxZ, maxX));

        EnumMap<AttachFace, Map<Direction, VoxelShape>> map = new EnumMap<>(AttachFace.class);
        map.put(AttachFace.FLOOR, horizontalMap(minX, minY, minZ, maxX, maxY, maxZ));
        map.put(AttachFace.CEILING, horizontalMap(minX, fminY, minZ, maxX, fmaxY, maxZ));
        map.put(AttachFace.WALL, wallMap);

        return map;
    }

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

    public static Map<Direction, Vec3> horizontalMap(
        double x,
        double y,
        double z) {
        double fz = 1.0 - z;
        EnumMap<Direction, Vec3> map = new EnumMap<>(Direction.class);
        map.put(Direction.NORTH, new Vec3(x, y, z));
        map.put(Direction.EAST, new Vec3(fz, y, x));
        map.put(Direction.SOUTH, new Vec3(x, y, fz));
        map.put(Direction.WEST, new Vec3(z, y, x));
        return map;
    }

    // TODO assumes centered in X direction
    public static Map<Direction, VoxelShape> horizontalMap(
        double minX,
        double minY,
        double minZ,
        double maxX,
        double maxY,
        double maxZ) {
        double fminZ = 1.0 - maxZ;
        double fmaxZ = 1.0 - minZ;
        EnumMap<Direction, VoxelShape> map = new EnumMap<>(Direction.class);
        map.put(Direction.NORTH, Shapes.box(minX, minY, minZ, maxX, maxY, maxZ));
        map.put(Direction.EAST, Shapes.box(fminZ, minY, minX, fmaxZ, maxY, maxX));
        map.put(Direction.SOUTH, Shapes.box(minX, minY, fminZ, maxX, maxY, fmaxZ));
        map.put(Direction.WEST, Shapes.box(minZ, minY, minX, maxZ, maxY, maxX));
        return map;
    }

    public static long nextLong(RandomSource randomSource, long max) {
        long r;
        long limit = Long.MAX_VALUE - (Long.MAX_VALUE % max);
        do {
            r = randomSource.nextLong() & Long.MAX_VALUE;
        } while (r >= limit);
        return r % max;
    }

    public static <T> Function<BlockState, T> propLookup(
        EnumProperty<Direction> directionProp,
        EnumProperty<AttachFace> attachProp,
        Map<AttachFace, Map<Direction, T>> map) {
        return state -> map.get(state.getValue(attachProp)).get(state.getValue(directionProp));
    }

    public static VelocityHeading randomVelInDirection(
        RandomSource rand,
        Direction direction,
        float minSpeed,
        float maxSpeed) {
        float uncertainty = 45.0F;
        float speed = minSpeed + (rand.nextFloat() * (maxSpeed - minSpeed));
        Vec3 movement = new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ())
            .normalize()
            .add(
                rand.triangle(0.0, Mth.DEG_TO_RAD * uncertainty),
                rand.triangle(0.0, Mth.DEG_TO_RAD * uncertainty),
                rand.triangle(0.0, Mth.DEG_TO_RAD * uncertainty))
            .scale(speed);
        double sd = movement.horizontalDistance();
        double yrot = (float) (Mth.atan2(movement.x, movement.z) * 180.0F / (float) Math.PI);
        double xrot = (float) (Mth.atan2(movement.y, sd) * 180.0F / (float) Math.PI);
        return new VelocityHeading(movement, (float) yrot, (float) xrot);
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
