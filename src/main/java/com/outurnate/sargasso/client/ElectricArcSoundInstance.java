package com.outurnate.sargasso.client;

import com.outurnate.sargasso.block.ShockTherapistBlock;
import com.outurnate.sargasso.block.ShockTherapistBlock.Phase;
import com.outurnate.sargasso.registry.LocalSoundEvents;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElectricArcSoundInstance extends AbstractTickableSoundInstance {
    private final BlockPos pos;
    private final Level level;

    public ElectricArcSoundInstance(Level level, BlockPos pos) {
        super(LocalSoundEvents.ZAP.value(), SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
        this.level = level;
        this.pos = pos;
        this.looping = true;
        this.delay = 0;
        this.volume = 1.0F;
        this.pitch = 1.0F;
        this.x = pos.getX() + 0.5;
        this.y = pos.getY() + 0.5;
        this.z = pos.getZ() + 0.5;
    }

    @Override
    public void tick() {
        if (!level.isLoaded(pos)
            || level.getBlockState(pos).getValue(ShockTherapistBlock.PHASE) != Phase.DISCHARGING) {
            this.stop();
            return;
        }
    }
}
