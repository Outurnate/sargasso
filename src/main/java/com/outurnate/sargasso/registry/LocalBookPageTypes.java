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
package com.outurnate.sargasso.registry;

import com.klikli_dev.modonomicon.data.BookPageType;
import com.klikli_dev.modonomicon.registry.BookPageTypeRegistry;
import com.outurnate.sargasso.book.page.BookMultispotlightPage;
import net.neoforged.bus.api.IEventBus;

public class LocalBookPageTypes {
	public static final BookPageType<BookMultispotlightPage> MULTISPOTLIGHT = BookPageTypeRegistry.register(BookMultispotlightPage.ID, BookMultispotlightPage.CODEC, BookMultispotlightPage.STREAM_CODEC);;

	public static void register(IEventBus modEventBus) {}
}
