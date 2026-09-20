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
package com.outurnate.sargasso.client.renderer.page;

import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.client.render.page.BookPageRenderer;
import com.klikli_dev.modonomicon.client.render.page.PageWithTextRenderer;
import com.outurnate.sargasso.book.page.BookMultispotlightPage;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class BookMultispotlightPageRenderer extends BookPageRenderer<BookMultispotlightPage> implements PageWithTextRenderer {
	public static final int ITEM_X = BookEntryScreen.PAGE_WIDTH / 2 - 8;
	public static final int ITEM_Y = 15;

	public BookMultispotlightPageRenderer(BookMultispotlightPage page) {
		super(page);
	}

	@Nullable @Override
	public Style getClickedComponentStyleAt(double pMouseX, double pMouseY) {
		if (pMouseX > 0 && pMouseY > 0) {
			if (this.page.hasTitle()) {
				Style titleStyle = this.getClickedComponentStyleAtForTitle(this.page.getTitle(), BookEntryScreen.PAGE_WIDTH / 2, 0, pMouseX, pMouseY);
				if (titleStyle != null) {
					return titleStyle;
				}
			}

			TextHolderBounds bounds = this.getBookTextHolderBounds(0, this.getTextY(), BookEntryScreen.PAGE_WIDTH, BookEntryScreen.PAGE_HEIGHT - this.getTextY());
			Style textStyle = this.getClickedComponentStyleAtForTextHolder(this.page.getText(), bounds.x, bounds.y, bounds.width, bounds.height, pMouseX, pMouseY);
			if (textStyle != null) {
				return textStyle;
			}
		}
		return super.getClickedComponentStyleAt(pMouseX, pMouseY);
	}

	@Override
	public int getTextY() {
		return 40;
	}

	@Override
	public void render(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float ticks) {
		if (this.page.hasTitle()) {
			this.renderTitle(guiGraphics, this.page.getTitle(), false, BookEntryScreen.PAGE_WIDTH / 2, 0);
		}

		int textY = this.getTextY();
		this.renderBookTextHolder(guiGraphics, this.getPage().getText(), 0, textY, BookEntryScreen.PAGE_WIDTH, BookEntryScreen.PAGE_HEIGHT - textY);

		int w = 66;

		List<Ingredient> items = this.page.getItems();
		int totalWidth = items.size() * 16;
		int offsetX = (BookEntryScreen.PAGE_WIDTH / 2) - (totalWidth / 2);
		for (int i = 0; i < items.size(); ++i) {
			this.parentScreen.renderIngredient(guiGraphics, offsetX + (i * 16), ITEM_Y, mouseX, mouseY, items.get(i));
		}

		Style style = this.getClickedComponentStyleAt(mouseX, mouseY);
		if (style != null) {
			this.parentScreen.renderComponentHoverEffect(guiGraphics, style, mouseX, mouseY);
		}
	}
}
