package com.outurnate.sargasso.effects;

import com.mojang.serialization.MapCodec;
import com.outurnate.sargasso.Rational;
import com.outurnate.sargasso.registry.LocalAttachmentTypes;
import com.outurnate.sargasso.registry.LocalConsumeEffects;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

public record AddGeneratorConsumeEffect() implements ConsumeEffect {
    private static final AddGeneratorConsumeEffect INSTANCE = new AddGeneratorConsumeEffect();

    public static final MapCodec<AddGeneratorConsumeEffect> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, AddGeneratorConsumeEffect> STREAM_CODEC = StreamCodec
        .unit(INSTANCE);

    @Override
    public ConsumeEffect.Type<AddGeneratorConsumeEffect> getType() {
        return LocalConsumeEffects.ADD_GENERATOR.get();
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity user) {
        Rational newGenerator = user
            .getData(LocalAttachmentTypes.GENERATOR_COUNT)
            .add(new Rational(1, 5));
        user.setData(LocalAttachmentTypes.GENERATOR_COUNT, newGenerator);
        return true;
    }
}
