/* (C)2026 */
package com.outurnate.sargasso.network.chat;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.time.Instant;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

public record LocalizedDurationContents(Instant since) implements ComponentContents {
    public static final MapCodec<LocalizedDurationContents> CODEC = RecordCodecBuilder.mapCodec(
        i -> i.group(ExtraCodecs.INSTANT_ISO8601.fieldOf("since").forGetter(LocalizedDurationContents::since))
            .apply(i, LocalizedDurationContents::new));
    public static final StreamCodec<ByteBuf, LocalizedDurationContents> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_LONG,
        c -> c.since.toEpochMilli(),
        millis -> new LocalizedDurationContents(Instant.ofEpochMilli(millis)));

    @Override
    public MapCodec<? extends ComponentContents> codec() {
        return CODEC;
    }

    public static Component localizedDate(Instant since) {
        return MutableComponent.create(new LocalizedDurationContents(since));
    }

    private String format() {
        return "yabbadabbado";
    }

    @Override
    public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> output, Style currentStyle) {
        return output.accept(currentStyle, format());
    }

    @Override
    public <T> Optional<T> visit(FormattedText.ContentConsumer<T> output) {
        return output.accept(format());
    }
}
