package com.outurnate.sargasso.client;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import org.jspecify.annotations.Nullable;

public record FromCosmeticItemTintSource(int defaultColor) implements ItemTintSource {
    @SuppressWarnings("null")
    public static final MapCodec<FromCosmeticItemTintSource> MAP_CODEC = ExtraCodecs.RGB_COLOR_CODEC
        .fieldOf("default")
        .xmap(FromCosmeticItemTintSource::new, FromCosmeticItemTintSource::defaultColor);

    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        return DyedItemColor.getOrDefault(itemStack, this.defaultColor);
    }

    @Override
    public MapCodec<FromCosmeticItemTintSource> type() {
        return MAP_CODEC;
    }
}
