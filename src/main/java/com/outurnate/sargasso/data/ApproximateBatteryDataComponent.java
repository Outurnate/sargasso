package com.outurnate.sargasso.data;

import com.mojang.serialization.Codec;
import com.outurnate.sargasso.registry.LocalDataComponentTypes;
import io.netty.buffer.ByteBuf;
import java.util.function.Consumer;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

public record ApproximateBatteryDataComponent(int capacity) implements TooltipProvider {
    public static final Codec<ApproximateBatteryDataComponent> CODEC = Codec.INT
        .xmap(ApproximateBatteryDataComponent::new, ApproximateBatteryDataComponent::capacity);

    public static final StreamCodec<ByteBuf, ApproximateBatteryDataComponent> STREAM_CODEC = ByteBufCodecs.VAR_INT
        .map(ApproximateBatteryDataComponent::new, ApproximateBatteryDataComponent::capacity);

    @Override
    public void addToTooltip(
        TooltipContext context,
        Consumer<Component> consumer,
        TooltipFlag flag,
        DataComponentGetter components) {
        Integer energy = components.get(LocalDataComponentTypes.ENERGY);
        if (energy != null) {
            consumer.accept(Component.literal(energy + " FE"));
        }
    }

}
