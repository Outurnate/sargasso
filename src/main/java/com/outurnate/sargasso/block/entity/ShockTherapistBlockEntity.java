package com.outurnate.sargasso.block.entity;

import com.mojang.datafixers.util.Pair;
import com.outurnate.sargasso.entity.ElectricMine;
import com.outurnate.sargasso.registry.LocalBlockEntities;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
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
    private static int findNearest(List<ElectricMine> mines, Vec3 pos) {
        int result = 0;
        double dist = 3_000_000.0F;
        for (int i = 0; i < mines.size(); ++i) {
            double currentDist = Math.abs(mines.get(i).getPosition(0.0F).subtract(pos).length());
            if (currentDist < dist) {
                result = i;
                dist = currentDist;
            }
        }
        return result;
    }

    private static List<ElectricMine> rearrange(List<ElectricMine> mines, int firstIndex, int lastIndex) {
        ArrayList<ElectricMine> newMines = new ArrayList<>();
        newMines.add(mines.get(firstIndex));
        for (int i = 0; i < mines.size(); ++i) {
            if (i != firstIndex && i != lastIndex) {
                newMines.add(mines.get(i));
            }
        }
        newMines.add(mines.get(lastIndex));
        return newMines;
    }

    public List<Pair<Vec3, Vec3>> bolts;

    public ShockTherapistBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(LocalBlockEntities.SHOCK_THERAPIST.get(), worldPosition, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        double arcRadius = 8.0;

        List<ElectricMine> mines = level.getEntities(
            EntityTypeTest.forClass(ElectricMine.class),
            AABB.ofSize(pos.getCenter(), 2.0 * arcRadius, 2.0 * arcRadius, 2.0 * arcRadius),
            e -> true);
        bolts = new ArrayList<>();
        if (mines.size() > 1) {
            Vec3 leftPos = new Vec3(3.0, 6.0, 8.0);
            Vec3 rightPos = new Vec3(13.0, 6.0, 8.0);
            int leftStart = findNearest(mines, leftPos);
            int rightStart = findNearest(mines, rightPos);
            if (leftStart == rightStart) {
                leftStart = 0;
                rightStart = mines.size() - 1;
            }
            mines = rearrange(mines, leftStart, rightStart);
            Vec3 lastPos = leftPos;
            for (ElectricMine mine : mines) {
                Vec3 nextPos = mine.getPosition(0.0F); // TODO delayed eval
                bolts.add(new Pair<Vec3, Vec3>(lastPos, nextPos));
                lastPos = nextPos;
            }
            bolts.add(new Pair<Vec3, Vec3>(lastPos, rightPos));
        }

        if ((level.getGameTime() % (20 * 10)) == 0) {
            RandomSource random = level.getRandom();
            int num = random.nextInt(5, 10);
            num = 1;
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
