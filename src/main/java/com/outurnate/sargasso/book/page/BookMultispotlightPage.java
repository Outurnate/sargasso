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
package com.outurnate.sargasso.book.page;

import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.RenderedBookTextHolder;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.BookNoneCondition;
import com.klikli_dev.modonomicon.book.page.BookPage;
import com.klikli_dev.modonomicon.client.gui.book.markdown.BookTextRenderer;
import com.klikli_dev.modonomicon.data.BookPageType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalBookPageTypes;
import java.util.List;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.minecraft.world.level.Level;

public class BookMultispotlightPage extends BookPage {
	public static final Identifier ID = SuperSargassoSea.ID("multispotlight");
	public static final MapCodec<BookMultispotlightPage> CODEC = RecordCodecBuilder.mapCodec(
			i -> i.group(
					BookTextHolder.CODEC.fieldOf("title").forGetter(BookMultispotlightPage::getTitle),
					BookTextHolder.CODEC.fieldOf("text").forGetter(BookMultispotlightPage::getText),
					Ingredient.CODEC.listOf().fieldOf("items").forGetter(BookMultispotlightPage::getItems),
					Codec.STRING.fieldOf("id").forGetter(BookPage::getId),
					BookCondition.CODEC.optionalFieldOf("condition", new BookNoneCondition()).forGetter(BookPage::getCondition)
			).apply(i, BookMultispotlightPage::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, BookMultispotlightPage> STREAM_CODEC = StreamCodec.composite(
			BookTextHolder.STREAM_CODEC, BookMultispotlightPage::getTitle,
			BookTextHolder.STREAM_CODEC, BookMultispotlightPage::getText,
			ByteBufCodecs.fromCodecWithRegistries(Ingredient.CODEC.listOf()), BookMultispotlightPage::getItems,
			ByteBufCodecs.STRING_UTF8, BookPage::getId,
			BookCondition.STREAM_CODEC, BookPage::getCondition,
			BookMultispotlightPage::new
	);
	protected BookTextHolder title;
	protected BookTextHolder text;
	protected List<Ingredient> items;

	public BookMultispotlightPage(BookTextHolder title, BookTextHolder text, List<Ingredient> items, String id, BookCondition condition) {
		super(id, condition);
		this.title = title;
		this.text = text;
		this.items = items;
	}

	public List<Ingredient> getItems() {
		return this.items;
	}

	public BookTextHolder getText() {
		return this.text;
	}

	public BookTextHolder getTitle() {
		return this.title;
	}

	public boolean hasTitle() {
		return !this.title.isEmpty();
	}

	protected boolean ingredientMatchesQuery(String query, Level level) {
		return this.items.stream().anyMatch(item -> item.display().resolveForStacks(SlotDisplayContext.fromLevel(level)).stream().anyMatch((i) -> this.matchesQuery(i, query)));
	}

	protected boolean matchesQuery(ItemStack stack, String query) {
		return I18n.get(stack.getItem().getDescriptionId(), new Object[0]).toLowerCase().contains(query);
	}

	@Override
	public boolean matchesQuery(String query, Level level) {
		return this.title.getString().toLowerCase().contains(query) || this.ingredientMatchesQuery(query, level) || this.text.getString().toLowerCase().contains(query);
	}

	public void prerenderMarkdown(BookTextRenderer textRenderer) {
		super.prerenderMarkdown(textRenderer);
		if (!this.title.hasComponent()) {
			this.title = new BookTextHolder(Component.translatable(this.title.getKey()).withStyle(Style.EMPTY.withBold(true).withColor(this.getParentEntry().getBook().themeData().palette().defaultTitleColor())));
		}

		if (!this.text.hasComponent()) {
			this.text = new RenderedBookTextHolder(this.text, textRenderer.render(this.text.getString()));
		}

	}

	public BookPageType<?> type() {
		return LocalBookPageTypes.MULTISPOTLIGHT;
	}
}
