package com.outurnate.sargasso.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.book.BookDisplayMode;
import com.outurnate.sargasso.registry.LocalItems;

import net.minecraft.resources.Identifier;

public class AtlasOfNowhere extends SingleBookSubProvider {
    private static final Identifier ID = LocalItems.ATLAS.getId();

    public AtlasOfNowhere() {
        super(ID.getPath(), ID.getNamespace());
    }

    @Override
    protected BookModel additionalSetup(BookModel book) {
        return book
            .withDisplayMode(BookDisplayMode.INDEX)
            .withCustomBookItem(ID);
    }

    @Override
    protected String bookName() {
        return "The Atlas of Nowhere";
    }

    @Override
    protected String bookTooltip() {
        return "A travel companion";
    }

    @Override
    protected void generateCategories() {
        this.add(new DimensionCategory(this).generate());
        this.add(new ThingsCategory(this).generate());
    }

    @Override
    protected void registerDefaultMacros() {
    }
}
