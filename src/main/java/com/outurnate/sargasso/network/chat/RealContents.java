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

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.jspecify.annotations.NonNull;

public record RealContents(float value, String formatTemplate) implements ComponentContents {
	@SuppressWarnings("null")
	public static final MapCodec<RealContents> MAP_CODEC = RecordCodecBuilder.mapCodec(
			i -> i
					.group(
							Codec.FLOAT.fieldOf("value").forGetter(RealContents::value),
							Codec.STRING.fieldOf("format").forGetter(RealContents::formatTemplate))
					.apply(i, RealContents::new));

	@Override
	public @NonNull MapCodec<? extends ComponentContents> codec() {
		return MAP_CODEC;
	}

	public static Component localizedReal(float value, String format) {
		return MutableComponent.create(new RealContents(value, format));
	}

	private String format() {
		Locale locale = Minecraft.getInstance().getLocale();
		DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		df.applyPattern(this.formatTemplate);
		return df.format(this.value);
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
