package com.outurnate.sargasso.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.outurnate.sargasso.SuperSargassoSea;

import java.util.function.Consumer;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

public record Cosmetic(ItemStackTemplate cosmetic) implements TooltipProvider {
    @SuppressWarnings("null")
    public static final Codec<Cosmetic> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(ItemStackTemplate.CODEC.fieldOf("cosmetic").forGetter(Cosmetic::cosmetic))
            .apply(instance, Cosmetic::new));

    @SuppressWarnings("null")
    public static final StreamCodec<RegistryFriendlyByteBuf, Cosmetic> STREAM_CODEC = StreamCodec
        .composite(ItemStackTemplate.STREAM_CODEC, Cosmetic::cosmetic, Cosmetic::new);

    @Override
    public void addToTooltip(
        TooltipContext context,
        Consumer<Component> consumer,
        TooltipFlag flag,
        DataComponentGetter components) {
        SuperSargassoSea.LOGGER.error("ass");
        consumer.accept(cosmetic.item().value().getDefaultInstance().getDisplayName());
        consumer.accept(Component.literal("ass"));
    }
}
