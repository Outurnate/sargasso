package com.outurnate.sargasso.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

import net.minecraft.world.level.ItemLike;

public abstract class BaseEntry extends EntryProvider {
    private final String id;
    private final String name;
    private final ItemLike icon;

    public BaseEntry(CategoryProviderBase parent, String id, String name, ItemLike icon) {
        super(parent);
        this.id = id;
        this.icon = icon;
        this.name = name;
    }

    @Override
    protected final GuiSprite entryBackground() {
        return EntryBackground.CONDITION;
    }

    @Override
    protected final BookIconModel entryIcon() {
        return BookIconModel.create(icon);
    }

    @Override
    protected final String entryId() {
        return this.id;
    }

    @Override
    protected final String entryName() {
        return this.name;
    }

    protected final void spotlightPage(String id, String title, ItemLike item, String text) {
        this.page(
            id,
            () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .withItem(item));
        this.pageTitle(title);
        this.pageText(text);
    }

    protected final void textPage(String id, String title, String text) {
        this.page(
            id,
            () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle(title);
        this.pageText(text);
    }

    protected final void titlePage(String id, String text) {
        this.textPage(id, this.entryName(), text);
    }
}
