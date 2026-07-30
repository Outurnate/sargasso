package com.outurnate.sargasso.block.entity;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.outurnate.sargasso.Utils;
import com.outurnate.sargasso.block.ShockTherapistBlock;
import com.outurnate.sargasso.block.ShockTherapistBlock.Phase;
import com.outurnate.sargasso.entity.ElectricMine;
import com.outurnate.sargasso.registry.LocalBlockEntities;
import com.outurnate.sargasso.registry.LocalBlocks;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ShockTherapistBlockEntity extends BlockEntity {
    public static record LerpVec3(Vec3 oldPos, Vec3 newPos) {
        @SuppressWarnings("null")
        public static final Codec<LerpVec3> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                Vec3.CODEC.fieldOf("old_pos").forGetter(LerpVec3::oldPos),
                Vec3.CODEC.fieldOf("new_pos").forGetter(LerpVec3::newPos)).apply(instance, LerpVec3::new));

        public LerpVec3(Vec3 pos) {
            this(pos, pos);
        }

        public LerpVec3(Entity entity, Vec3 offset) {
            this(entity.getPosition(0.0F).add(offset), entity.getPosition(1.0F).add(offset));
        }

        public Vec3 pos(float partialTickTime) {
            return Mth.lerp(partialTickTime, oldPos, newPos);
        }

        public Vec3 pos() {
            return newPos;
        }
    }

    @SuppressWarnings("null")
    private static final Codec<Pair<LerpVec3, LerpVec3>> LERP_PAIR_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            LerpVec3.CODEC.fieldOf("first").forGetter(Pair::getFirst),
            LerpVec3.CODEC.fieldOf("second").forGetter(Pair::getSecond)).apply(instance, Pair::of));

    private static final Codec<List<Pair<LerpVec3, LerpVec3>>> BOLTS_CODEC = LERP_PAIR_CODEC.listOf();

    private static final int IDLE_TICKS = 10 * 20;
    private static final int CHARGE_TICKS = 3 * 20;
    private static final int DISCHARGE_TICKS = 5 * 20;

    private static final double ARCPOS_X = 3.0 / 16.0;
    private static final double ARCPOS_Y = 6.0 / 16.0;
    private static final double ARCPOS_Z = 8.0 / 16.0;
    private static final double ARCPOS_SHIFT = 10.0 / 16.0;

    private static final Map<AttachFace, Map<Direction, Vec3>> LEFTPOS_MAP = Map.of(
        AttachFace.CEILING,
        Utils.horizontalMap(ARCPOS_X, 1.0 - ARCPOS_Y, ARCPOS_Z),
        AttachFace.WALL,
        Utils.horizontalMap(ARCPOS_X, ARCPOS_Z, ARCPOS_Y),
        AttachFace.FLOOR,
        Utils.horizontalMap(ARCPOS_X, ARCPOS_Y, ARCPOS_Z));
    private static final Map<AttachFace, Map<Direction, Vec3>> RIGHTPOS_MAP = Map.of(
        AttachFace.CEILING,
        Utils.horizontalMap(ARCPOS_X, 1.0 - ARCPOS_Y, ARCPOS_Z),
        AttachFace.WALL,
        Utils.horizontalMap(ARCPOS_X, ARCPOS_Z, ARCPOS_Y),
        AttachFace.FLOOR,
        Utils.horizontalMap(ARCPOS_X + ARCPOS_SHIFT, ARCPOS_Y, ARCPOS_Z));

    private static List<ElectricMine> naiveTSP(List<ElectricMine> mines, RandomSource random) {
        ArrayList<ElectricMine> newMines = new ArrayList<>();
        while (mines.size() != 0) {
            newMines.add(mines.remove(random.nextInt(mines.size())));
        }
        return newMines;
    }

    public List<Pair<LerpVec3, LerpVec3>> bolts = List.of();
    public long seed = 0;
    private int ticksToNextState = IDLE_TICKS;

    public ShockTherapistBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(LocalBlockEntities.SHOCK_THERAPIST.get(), worldPosition, blockState);
    }

    private void arc(Level level, BlockPos pos, BlockState state) {
        double arcRadius = 8.0;
        AABB arcSpace = AABB.ofSize(pos.getCenter(), 2.0 * arcRadius, 2.0 * arcRadius, 2.0 * arcRadius);
        RandomSource random = RandomSource.createThreadLocalInstance(seed);

        List<ElectricMine> mines = level.getEntities(
            EntityTypeTest.forClass(ElectricMine.class),
            arcSpace,
            e -> true);
        bolts = new ArrayList<>();
        if (mines.size() > 1) {
            Vec3 leftPos = new Vec3(pos.getX(), pos.getY(), pos.getZ())
                .add(
                    LEFTPOS_MAP.get(state.getValue(ShockTherapistBlock.ATTACH_FACE))
                        .get(state.getValue(ShockTherapistBlock.HORIZONTAL_FACING)));
            Vec3 rightPos = new Vec3(pos.getX(), pos.getY(), pos.getZ())
                .add(
                    RIGHTPOS_MAP.get(state.getValue(ShockTherapistBlock.ATTACH_FACE))
                        .get(state.getValue(ShockTherapistBlock.HORIZONTAL_FACING)));
            LerpVec3 lastPos = new LerpVec3(leftPos);
            for (ElectricMine mine : naiveTSP(mines, random)) {
                LerpVec3 nextPos = new LerpVec3(mine, new Vec3(0.0, 1.0 / 16.0, 0.0));
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
    }

    private Direction getUp(BlockState state) {
        return switch (state.getValue(ShockTherapistBlock.ATTACH_FACE)) {
            case AttachFace.CEILING -> Direction.DOWN;
            case AttachFace.WALL -> state.getValue(ShockTherapistBlock.HORIZONTAL_FACING);
            case AttachFace.FLOOR -> Direction.UP;
        };
    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.seed = input.getLongOr("seed", 0);
        this.bolts = input.read("bolts", BOLTS_CODEC).orElseGet(List::of);
        this.ticksToNextState = input.getIntOr("clock", IDLE_TICKS);
    }

    @Override
    public void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putLong("seed", this.seed);
        output.store("bolts", BOLTS_CODEC, this.bolts);
        output.putInt("clock", this.ticksToNextState);
    }

    private void spawnMines(Level level, BlockPos pos, BlockState state) {
        RandomSource random = level.getRandom();
        // scale the number of mines by the number of nearby sources
        // reduces spamminess
        int nearby = Math.max(Utils.countBlocks(level, pos, 5, LocalBlocks.SHOCK_THERAPIST.get()), 1);
        int num = random.nextInt(Math.max(5 / nearby, 1), Math.max(10 / nearby, 2));
        for (int i = 0; i < num; ++i) {
            Entity electricMine = new ElectricMine(level, CHARGE_TICKS + DISCHARGE_TICKS);
            electricMine.setPos(pos.getCenter());

            Utils.VelocityHeading movement = Utils.randomVelInDirection(random, getUp(state), 0.2F, 0.4F);
            electricMine.setDeltaMovement(movement.velocity());
            level.addFreshEntity(electricMine);
        }
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        --this.ticksToNextState;
        if (this.ticksToNextState < 0) {
            Phase oldPhase = state.getValue(ShockTherapistBlock.PHASE);
            Phase newPhase = oldPhase.next();
            this.ticksToNextState = switch (newPhase) {
                case CHARGING -> CHARGE_TICKS;
                case DISCHARGING -> DISCHARGE_TICKS;
                case IDLE -> IDLE_TICKS;
            };
            state = state.setValue(ShockTherapistBlock.PHASE, newPhase);
            level.setBlock(pos, state, 0);

            if (newPhase == Phase.CHARGING) {
                spawnMines(level, pos, state);
            } else if (newPhase == Phase.IDLE) {
                this.bolts = List.of();
            }
        }

        if ((level.getGameTime() % 10) == 0) {
            seed = level.getRandom().nextLong();
        }

        if (state.getValue(ShockTherapistBlock.PHASE) == Phase.DISCHARGING) {
            arc(level, pos, state);
        }
    }
}
