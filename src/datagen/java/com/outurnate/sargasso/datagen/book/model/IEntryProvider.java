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

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;

import java.util.function.Supplier;

public interface IEntryProvider {
	BookContextHelper context();
	<T extends BookPageModel<?>> T page(String page, Supplier<T> modelSupplier);
	void pageText(String text);
	void pageTitle(String title);
}
