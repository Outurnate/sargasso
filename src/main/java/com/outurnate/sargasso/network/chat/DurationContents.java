/*
 * This class is distributed as part of the Super Sargasso Sea mod.
 * Complete source on GitHub:
 * https://github.com/Outurnate/sargasso
 *
 * Super Sargasso Sea is free software and distributed
 * under the MIT License: https://opensource.org/license/mit
 *
 * © 2026 the authors of the Super Sargasso Sea mod
 */
package com.outurnate.sargasso.network.chat;

import com.ibm.icu.text.RelativeDateTimeFormatter;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.ExtraCodecs;
import org.jspecify.annotations.NonNull;

public record DurationContents(Instant since) implements ComponentContents {
	@SuppressWarnings("null")
	public static final MapCodec<DurationContents> MAP_CODEC = RecordCodecBuilder.mapCodec(
			i -> i.group(ExtraCodecs.INSTANT_ISO8601.fieldOf("since").forGetter(DurationContents::since))
					.apply(i, DurationContents::new));

	@Override
	public @NonNull MapCodec<? extends ComponentContents> codec() {
		return MAP_CODEC;
	}

	public static Component localizedDate(Instant since) {
		return MutableComponent.create(new DurationContents(since));
	}

	private String format() {
		RelativeDateTimeFormatter fmt = RelativeDateTimeFormatter
				.getInstance(Minecraft.getInstance().getLocale());

		long seconds = Duration.between(since, Instant.now()).getSeconds();

		if (Math.abs(seconds) < 60) {
			return fmt.format(
					Math.round(seconds),
					RelativeDateTimeFormatter.Direction.LAST,
					RelativeDateTimeFormatter.RelativeUnit.SECONDS);
		}

		long minutes = seconds / 60;
		if (Math.abs(minutes) < 60) {
			return fmt.format(
					minutes,
					RelativeDateTimeFormatter.Direction.LAST,
					RelativeDateTimeFormatter.RelativeUnit.MINUTES);
		}

		long hours = minutes / 60;
		if (Math.abs(hours) < 24) {
			return fmt.format(
					hours,
					RelativeDateTimeFormatter.Direction.LAST,
					RelativeDateTimeFormatter.RelativeUnit.HOURS);
		}

		long days = hours / 24;
		return fmt.format(
				days,
				RelativeDateTimeFormatter.Direction.LAST,
				RelativeDateTimeFormatter.RelativeUnit.DAYS);
	}

	@Override
	public <T> @NonNull Optional<T> visit(FormattedText.StyledContentConsumer<T> output, @NonNull Style currentStyle) {
		return output.accept(currentStyle, format());
	}

	@Override
	public <T> @NonNull Optional<T> visit(FormattedText.ContentConsumer<T> output) {
		return output.accept(format());
	}
}
