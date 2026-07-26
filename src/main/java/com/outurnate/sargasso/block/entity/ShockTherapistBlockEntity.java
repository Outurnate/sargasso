package com.outurnate.sargasso.block.entity;

import com.mojang.datafixers.util.Pair;
import com.outurnate.sargasso.entity.ElectricMine;
import com.outurnate.sargasso.registry.LocalBlockEntities;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ShockTherapistBlockEntity extends BlockEntity {
    public static record LerpVec3(Vec3 oldPos, Vec3 newPos) {
        public LerpVec3(Vec3 pos) {
            this(pos, pos);
        }

        public LerpVec3(Entity entity) {
            this(entity.getPosition(0.0F), entity.getPosition(1.0F));
        }

        public Vec3 pos(float partialTickTime) {
            return Mth.lerp(partialTickTime, oldPos, newPos);
        }

        public Vec3 pos() {
            return newPos;
        }
    }

    private static int findFarthest(List<ElectricMine> mines, Vec3 pos) {
        int result = 0;
        double dist = 0.0F;
        for (int i = 0; i < mines.size(); ++i) {
            double currentDist = mines.get(i).getPosition(0.0F).distanceTo(pos);
            if (currentDist > dist) {
                result = i;
                dist = currentDist;
            }
        }
        return result;
    }

    private static List<ElectricMine> naiveTSP(List<ElectricMine> mines, Vec3 pos) {
        ArrayList<ElectricMine> newMines = new ArrayList<>();
        while (mines.size() != 0) {
            ElectricMine currentMine = mines.remove(findFarthest(mines, pos));
            newMines.add(currentMine);
            pos = currentMine.getPosition(0.0F);
        }
        return newMines;
    }

    public List<Pair<LerpVec3, LerpVec3>> bolts;

    public ShockTherapistBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(LocalBlockEntities.SHOCK_THERAPIST.get(), worldPosition, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        double arcRadius = 8.0;
        AABB arcSpace = AABB.ofSize(pos.getCenter(), 2.0 * arcRadius, 2.0 * arcRadius, 2.0 * arcRadius);

        List<ElectricMine> mines = level.getEntities(
            EntityTypeTest.forClass(ElectricMine.class),
            arcSpace,
            e -> true);
        bolts = new ArrayList<>();
        if (mines.size() > 1) {
            Vec3 leftPos = new Vec3(
                pos.getX() + (3.0 / 16.0),
                pos.getY() + (6.0 / 16.0),
                pos.getZ() + (8.0 / 16.0));
            Vec3 rightPos = new Vec3(
                pos.getX() + (13.0 / 16.0),
                pos.getY() + (6.0 / 16.0),
                pos.getZ() + (8.0 / 16.0));
            LerpVec3 lastPos = new LerpVec3(leftPos);
            for (ElectricMine mine : naiveTSP(mines, lastPos.pos())) {
                LerpVec3 nextPos = new LerpVec3(mine);
                bolts.add(new Pair<LerpVec3, LerpVec3>(lastPos, nextPos));
                lastPos = nextPos;
            }
            bolts.add(new Pair<LerpVec3, LerpVec3>(lastPos, new LerpVec3(rightPos)));
        }

        if (level instanceof ServerLevel serverLevel) {
            ArrayList<Entity> struckEntities = new ArrayList<>();
            for (Pair<LerpVec3, LerpVec3> bolt : bolts) {
                struckEntities.addAll(
                    serverLevel.getEntities(
                        (Entity) null,
                        arcSpace.inflate(2.0),
                        e -> e.getBoundingBox().clip(bolt.getFirst().pos(), bolt.getSecond().pos())
                            .isPresent()));
            }
            for (Entity struckEntity : struckEntities) {
                // TODO custom damage source
                struckEntity.hurtServer(serverLevel, serverLevel.damageSources().lightningBolt(), 1.0F);
            }
        }

        if ((level.getGameTime() % (20 * 10)) == 0) {
            RandomSource random = level.getRandom();
            int num = random.nextInt(5, 10);
            for (int i = 0; i < num; ++i) {
                Entity electricMine = new ElectricMine(level);
                electricMine.setPos(pos.getCenter().add(0.0, 1.0, 0.0));

                float yRot = random.nextFloat() * 360.0F;
                float xRot = 225.0F;
                float speed = 0.2F;
                float xd = -Mth.sin(yRot * Mth.DEG_TO_RAD) * Mth.cos(xRot * Mth.DEG_TO_RAD);
                float yd = -Mth.sin(xRot * Mth.DEG_TO_RAD);
                float zd = Mth.cos(yRot * Mth.DEG_TO_RAD) * Mth.cos(xRot * Mth.DEG_TO_RAD);
                Vec3 movement = new Vec3(xd, yd, zd).normalize().scale(speed);
                electricMine.setDeltaMovement(movement);
                level.addFreshEntity(electricMine);
            }
        }
    }
}
