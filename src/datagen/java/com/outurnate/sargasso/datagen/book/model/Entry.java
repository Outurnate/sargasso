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

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.*;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class Entry implements IMarkdownProvider {
	private class InnerProvider extends EntryProvider implements IEntryProvider {
		public InnerProvider(CategoryProviderBase parent) {
			super(parent);
		}

		@Override
		public BookContextHelper context() {
			return super.context();
		}

		@Override
		protected GuiSprite entryBackground() {
			return EntryBackground.CONDITION;
		}

		@Override
		protected BookIconModel entryIcon() {
			return BookIconModel.create(icon);
		}

		@Override
		protected String entryId() {
			return identify(name);
		}

		@Override
		protected String entryName() {
			return name;
		}

		@Override
		protected void generatePages() {
			for (IPage page : pages) {
				page.toPage(this);
			}
		}

		@Override
		public <T extends BookPageModel<?>> T page(String page, Supplier<T> modelSupplier) {
			return super.page(page, modelSupplier);
		}

		@Override
		public void pageText(String text) {
			super.pageText(text);
		}

		@Override
		public void pageTitle(String title) {
			super.pageTitle(title);
		}
	}

	private static void appendImage(StringBuilder page, Identifier path, String altText) {
		page.append("![");
		page.append(altText);
		page.append("](");
		page.append(path.getPath());
		page.append(".png");
		page.append(")");
	}

	private final String name;

	private final ItemStackTemplate icon;

	private final List<IPage> pages;

	public Entry(String name, ItemLike icon) {
		this(name, new ItemStackTemplate(icon.asItem()));
	}

	public Entry(String name, ItemStackTemplate icon) {
		this.name = name;
		this.icon = icon;
		this.pages = new ArrayList<>();
	}

	public Entry blastingRecipePage(String title, Identifier recipeId1, Identifier recipeId2) {
		this.pages.add(new IPage() {
			@Override
			public StringBuilder toMarkdown() {
				StringBuilder page = new StringBuilder();
				appendImage(page, recipeId1, "Crafting recipe");
				page.append("\n");
				appendImage(page, recipeId2, "Crafting recipe");
				return page;
			}

			@Override
			public void toPage(IEntryProvider provider) {
				provider.page(
						generateId(),
						() -> BookBlastingRecipePageModel.create()
								.withTitle1(title)
								.withRecipeId1(recipeId1)
								.withTitle2(" ")
								.withRecipeId2(recipeId2));
				provider.pageTitle(title);
			}
		});
		return this;
	}

	public Entry blastingRecipePage(String title, Identifier recipeId, String text) {
		this.pages.add(new IPage() {
			@Override
			public StringBuilder toMarkdown() {
				StringBuilder page = new StringBuilder();
				appendImage(page, recipeId, "Crafting recipe");
				page.append("\n");
				page.append(text);
				return page;
			}

			@Override
			public void toPage(IEntryProvider provider) {
				provider.page(
						identify(title),
						() -> BookBlastingRecipePageModel.create()
								.withTitle1(provider.context().pageTitle())
								.withRecipeId1(recipeId)
								.withText(provider.context().pageText()));
				provider.pageTitle(title);
				provider.pageText(text);
			}
		});
		return this;
	}

	public Entry craftingRecipePage(Identifier recipeId1, Identifier recipeId2) {
		this.pages.add(new IPage() {
			@Override
			public StringBuilder toMarkdown() {
				StringBuilder page = new StringBuilder();
				appendImage(page, recipeId1, "Crafting recipe");
				page.append("\n");
				appendImage(page, recipeId2, "Crafting recipe");
				return page;
			}

			@Override
			public void toPage(IEntryProvider provider) {
				provider.page(
						generateId(),
						() -> BookCraftingRecipePageModel.create()
								.withRecipeId1(recipeId1)
								.withRecipeId2(recipeId2));
			}
		});
		return this;
	}

	public Entry craftingRecipePage(String title, Identifier recipeId, String text) {
		this.pages.add(new IPage() {
			@Override
			public StringBuilder toMarkdown() {
				StringBuilder page = new StringBuilder();
				appendImage(page, recipeId, "Crafting recipe");
				page.append("\n");
				page.append(text);
				return page;
			}

			@Override
			public void toPage(IEntryProvider provider) {
				provider.page(
						identify(title),
						() -> BookCraftingRecipePageModel.create()
								.withTitle1(provider.context().pageTitle())
								.withRecipeId1(recipeId)
								.withText(provider.context().pageText()));
				provider.pageTitle(title);
				provider.pageText(text);
			}
		});
		return this;
	}

	public Entry entityPage(String title, String entity, float offset, String text) {
		this.pages.add(new IPage() {
			@Override
			public StringBuilder toMarkdown() {
				StringBuilder page = new StringBuilder();
				appendImage(page, Identifier.fromNamespaceAndPath("x", identify(title)), "Entity");
				page.append("\n");
				page.append(text);
				return page;
			}

			@Override
			public void toPage(IEntryProvider provider) {
				provider.page(
						identify(title),
						() -> BookEntityPageModel.create()
								.withEntityName(provider.context().pageTitle())
								.withEntityId(entity)
								.withOffset(offset)
								.withText(provider.context().pageText()));
				provider.pageTitle(title);
				provider.pageText(text);
			}
		});
		return this;
	}

	private String generateId() {
		return "page_" + pages.size();
	}

	public Entry imagePage(Identifier path, String altText) {
		return imagePage(List.of(path), altText);
	}

	public Entry imagePage(List<Identifier> paths, String altText) {
		this.pages.add(new IPage() {
			@Override
			public StringBuilder toMarkdown() {
				StringBuilder page = new StringBuilder();
				for (Identifier path : paths) {
					appendImage(page, path, altText);
				}
				return page;
			}

			@Override
			public void toPage(IEntryProvider provider) {
				provider.page(generateId(), () -> BookImagePageModel.create().withImages(paths.toArray(Identifier[]::new)));
			}
		});
		return this;
	}

	public Entry mainMultispotlightPage(List<ItemLike> items, String text) {
		return multispotlightPage(this.name, items, text);
	}

	public Entry mainSpotlightPage(String text) {
		return spotlightPage(this.name, this.icon, text);
	}

	public Entry multispotlightPage(String title, List<ItemLike> items, String text) {
		this.pages.add(new IPage() {
			@Override
			public StringBuilder toMarkdown() {
				StringBuilder page = new StringBuilder();
				page.append("### ");
				//appendImage(page, Identifier.parse(item.asItem().toString()), title);
				page.append(" ");
				page.append(title);
				page.append("\n");
				page.append(text);
				return page;
			}

			@Override
			public void toPage(IEntryProvider provider) {
				provider.page(
						identify(title),
						() -> BookMultispotlightPageModel.create()
								.withTitle(provider.context().pageTitle())
								.withText(provider.context().pageText())
								.withItems(items.stream().map(Ingredient::of).toList()));
				provider.pageTitle(title);
				provider.pageText(text);
			}
		});
		return this;
	}

	public Entry spotlightPage(String title, ItemLike item, String text) {
		return spotlightPage(title, new ItemStackTemplate(item.asItem()), text);
	}

	public Entry spotlightPage(String title, ItemStackTemplate item, String text) {
		this.pages.add(new IPage() {
			@Override
			public StringBuilder toMarkdown() {
				StringBuilder page = new StringBuilder();
				page.append("### ");
				appendImage(page, Objects.requireNonNull(item.item().getKey()).identifier(), title);
				page.append(" ");
				page.append(title);
				page.append("\n");
				page.append(text);
				return page;
			}

			@Override
			public void toPage(IEntryProvider provider) {
				provider.page(
						identify(title),
						() -> BookSpotlightPageModel.create()
								.withTitle(provider.context().pageTitle())
								.withText(provider.context().pageText())
								.withItem(item));
				provider.pageTitle(title);
				provider.pageText(text);
			}
		});
		return this;
	}

	public Entry textPage(String text) {
		this.pages.add(new IPage() {
			@Override
			public StringBuilder toMarkdown() {
				StringBuilder page = new StringBuilder();
				page.append(text);
				return page;
			}

			@Override
			public void toPage(IEntryProvider provider) {
				provider.page(
						generateId(),
						() -> BookTextPageModel.create()
								.withText(provider.context().pageText()));
				provider.pageText(text);
			}
		});
		return this;
	}

	public Entry textPage(String title, String text) {
		this.pages.add(new IPage() {
			@Override
			public StringBuilder toMarkdown() {
				StringBuilder page = new StringBuilder();
				page.append("### ");
				page.append(title);
				page.append("\n");
				page.append(text);
				return page;
			}

			@Override
			public void toPage(IEntryProvider provider) {
				provider.page(
						identify(title),
						() -> BookTextPageModel.create()
								.withTitle(provider.context().pageTitle())
								.withText(provider.context().pageText()));
				provider.pageTitle(title);
				provider.pageText(text);
			}
		});
		return this;
	}

	public Entry titlePage(String text) {
		return this.textPage(name, text);
	}

	@Override
	public StringBuilder toMarkdown() {
		StringBuilder entry = new StringBuilder();
		for (IMarkdownProvider page : this.pages) {
			entry.append(page.toMarkdown());
			entry.append("\n");
		}
		return entry;
	}

	public BookEntryModel toModel(CategoryProviderBase parent) {
		return new InnerProvider(parent).generate();
	}
}
