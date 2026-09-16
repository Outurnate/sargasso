/*
 * This class is distributed as part of the Super Sargasso Sea mod.
 * Complete source on GitHub:
 * https://github.com/Outurnate/sargasso
 *
 * Super Sargasso Sea is free software and distributed
 * under the MIT License: https://opensource.org/license/mit
 *
 * © 2026 the authors of the Super Sargasso Sea mod
 */
package com.outurnate.sargasso;

import com.outurnate.sargasso.repository.LocalDimensions;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class Utils {
	public record VelocityHeading(Vec3 velocity, float yrot, float xrot) {
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
		//noinspection SuspiciousNameCombination
		wallMap.put(Direction.EAST, Shapes.box(minY, minZ, minX, maxY, maxZ, maxX));
		wallMap.put(Direction.SOUTH, Shapes.box(minX, minZ, minY, maxX, maxZ, maxY));
		//noinspection SuspiciousNameCombination
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

	@SuppressWarnings("unchecked")
	public static <E extends BlockEntity, A extends BlockEntity> @Nullable BlockEntityTicker<A> createTickerHelper(
			BlockEntityType<A> type,
			BlockEntityType<E> checkedType,
			BlockEntityTicker<? super E> ticker) {
		return checkedType == type ? (BlockEntityTicker<A>) ticker : null;
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

	// 0-180 only
	public static VelocityHeading randomVelInDirection(
			RandomSource rand,
			Direction direction,
			double minSpeed,
			double maxSpeed,
			double minAngleDeg,
			double maxAngleDeg) {
		double speed = minSpeed + (rand.nextFloat() * (maxSpeed - minSpeed));

		Vec3 n = Vec3.atLowerCornerOf(direction.getUnitVec3i());
		Vec3 u, v;
		switch (direction) {
			case EAST, WEST -> {
				u = new Vec3(0, 1, 0);
				v = new Vec3(0, 0, 1);
			}
			case UP, DOWN -> {
				u = new Vec3(1, 0, 0);
				v = new Vec3(0, 0, 1);
			}
			case NORTH, SOUTH -> {
				u = new Vec3(1, 0, 0);
				v = new Vec3(0, 1, 0);
			}
			default -> throw new IllegalStateException();
		}

		double minRad = Math.toRadians(minAngleDeg);
		double maxRad = Math.toRadians(maxAngleDeg);

		// Uniform over the spherical shell between min and max angles
		double cosMin = Math.cos(minRad);
		double cosMax = Math.cos(maxRad);

		double cosPhi = Mth.lerp(rand.nextDouble(), cosMin, cosMax);
		double sinPhi = Math.sqrt(1.0 - cosPhi * cosPhi);
		double theta = rand.nextDouble() * (Math.PI * 2.0);

		Vec3 movement = n.scale(cosPhi)
				.add(u.scale(Math.cos(theta) * sinPhi))
				.add(v.scale(Math.sin(theta) * sinPhi))
				.scale(speed);
		double sd = movement.horizontalDistance();
		@SuppressWarnings("SuspiciousNameCombination")
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

		player.resetFallDistance();
		player.teleportTo(sea, x + 0.5, y + 1.0, z + 0.5, Set.of(), 0, 0, false);
		sea.playSound(null, player, SoundEvents.PORTAL_TRAVEL, SoundSource.PLAYERS, 0.75F, 1.0F);
	}
}
