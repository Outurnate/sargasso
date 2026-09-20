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

import static com.outurnate.sargasso.datagen.book.model.ModelUtils.identify;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.ItemLike;

public class Category implements IMarkdownProvider {
	private final String name;
	private final String description;
	private final ItemLike icon;
	private final List<Entry> entries;

	public Category(String name, String description, ItemLike icon) {
		this.name = name;
		this.description = description;
		this.icon = icon;
		this.entries = new ArrayList<>();
	}

	public Entry addEntry(String name, ItemLike icon) {
		Entry entry = new Entry(name, icon);
		this.entries.add(entry);
		return entry;
	}

	public Entry addEntry(String name, ItemStackTemplate icon) {
		Entry entry = new Entry(name, icon);
		this.entries.add(entry);
		return entry;
	}

	@Override
	public StringBuilder toMarkdown() {
		StringBuilder category = new StringBuilder();
		category.append("## ");
		category.append(this.name);
		category.append("\n");
		category.append(this.description);
		category.append("\n");
		for (Entry entry : this.entries) {
			category.append(entry.toMarkdown());
		}
		return category;
	}

	public BookCategoryModel toModel(ModonomiconProviderBase parent) {
		return new CategoryProvider(parent) {
			@Override
			protected String categoryDescription() {
				return description;
			}

			@Override
			protected BookIconModel categoryIcon() {
				return BookIconModel.create(icon);
			}

			@Override
			public String categoryId() {
				return identify(name);
			}

			@Override
			protected String categoryName() {
				return name;
			}

			@Override
			protected void generateEntries() {
				for (Entry entry : entries) {
					this.add(entry.toModel(this));
				}
			}
		}.generate();
	}
}
