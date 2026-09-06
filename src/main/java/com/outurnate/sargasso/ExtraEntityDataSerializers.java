package com.outurnate.sargasso;

import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.effect.MobEffect;

public class ExtraEntityDataSerializers {
    public static final EntityDataSerializer<Holder<MobEffect>> MOB_EFFECT = EntityDataSerializer
        .forValueType(MobEffect.STREAM_CODEC);
}
