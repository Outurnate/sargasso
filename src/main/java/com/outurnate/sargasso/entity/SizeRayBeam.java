package com.outurnate.sargasso.entity;

import com.outurnate.sargasso.registry.LocalEntities;

import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class SizeRayBeam extends Projectile {
    public SizeRayBeam(EntityType<SizeRayBeam> type, Level level) {
        super(type, level);
    }

    public SizeRayBeam(Level level) {
        super(LocalEntities.SIZE_RAY_BEAM.get(), level);
    }

    @Override
    protected void defineSynchedData(Builder entityData) {
    }
}
