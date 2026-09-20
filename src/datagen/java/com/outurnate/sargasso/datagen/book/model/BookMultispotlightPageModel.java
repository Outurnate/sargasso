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

import com.klikli_dev.modonomicon.api.datagen.book.BookTextHolderModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.book.page.BookPage;
import com.outurnate.sargasso.book.page.BookMultispotlightPage;
import java.util.List;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;

public class BookMultispotlightPageModel extends BookPageModel<BookMultispotlightPageModel> {
	public static BookMultispotlightPageModel create() {
		return new BookMultispotlightPageModel();
	}

	protected List<Ingredient> items;
	protected BookTextHolderModel title = new BookTextHolderModel("");

	protected BookTextHolderModel text = new BookTextHolderModel("");

	protected BookMultispotlightPageModel() {
		super(BookMultispotlightPage.ID);
	}

	public List<Ingredient> getItem() {
		return this.items;
	}

	public BookTextHolderModel getText() {
		return this.text;
	}

	public BookTextHolderModel getTitle() {
		return this.title;
	}

	public BookPage toBookPage(HolderLookup.Provider provider) {
		return new BookMultispotlightPage(this.title.toBookTextHolder(), this.text.toBookTextHolder(), this.items, this.id, this.condition(provider));
	}

	public BookMultispotlightPageModel withItems(List<Ingredient> items) {
		this.items = items;
		return this;
	}

	public BookMultispotlightPageModel withText(Component text) {
		this.text = new BookTextHolderModel(text);
		return this;
	}

	public BookMultispotlightPageModel withText(String text) {
		this.text = new BookTextHolderModel(text);
		return this;
	}

	public BookMultispotlightPageModel withTitle(Component title) {
		this.title = new BookTextHolderModel(title);
		return this;
	}

	public BookMultispotlightPageModel withTitle(String title) {
		this.title = new BookTextHolderModel(title);
		return this;
	}
}
