package com.outurnate.sargasso;

import com.outurnate.sargasso.registry.LocalAttachmentTypes;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.item.consume_effects.TeleportRandomlyConsumeEffect;
import net.minecraft.world.level.Level;

public record AddGeneratorConsumeEffect() implements ConsumeEffect {
    @Override
    public ConsumeEffect.Type<TeleportRandomlyConsumeEffect> getType() {
        return ConsumeEffect.Type.TELEPORT_RANDOMLY;
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
