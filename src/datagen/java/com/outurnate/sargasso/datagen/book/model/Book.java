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
package com.outurnate.sargasso.datagen.book.model;

import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.book.BookDisplayMode;
import com.klikli_dev.modonomicon.item.ModonomiconCustomItemBase;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.registries.DeferredItem;

public class Book extends SingleBookSubProvider implements IMarkdownProvider {
	private final Identifier id;
	private final String name;
	private final String tooltip;
	private final String description;
	private final List<Category> categories;

	public Book(DeferredItem<ModonomiconCustomItemBase> item, String name, String tooltip, String description) {
		super(item.getId().getPath(), item.getId().getNamespace());
		this.id = item.getId();
		this.name = name;
		this.tooltip = tooltip;
		this.description = description;
		this.categories = new ArrayList<>();
	}

	public void addCategory(Category category) {
		this.categories.add(category);
	}

	@Override
	protected final BookModel additionalSetup(BookModel book) {
		return book
				.withDisplayMode(BookDisplayMode.INDEX)
				.withCustomBookItem(id)
				.withGenerateBookItem(false);
	}

	@Override
	protected String bookDescription() {
		return description;
	}

	@Override
	protected final String bookName() {
		return name;
	}

	@Override
	protected final String bookTooltip() {
		return tooltip;
	}

	@Override
	protected final void generateCategories() {
		for (Category category : this.categories) {
			this.add(category.toModel(this));
		}
	}

	@Override
	protected final void registerDefaultMacros() {}

	@Override
	public final StringBuilder toMarkdown() {
		StringBuilder book = new StringBuilder();
		for (Category category : this.categories) {
			book.append(category.toMarkdown());
		}
		return book;
	}
}
