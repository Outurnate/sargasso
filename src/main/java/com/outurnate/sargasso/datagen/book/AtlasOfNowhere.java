package com.outurnate.sargasso.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.book.BookDisplayMode;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalCreativeTabs;

public class AtlasOfNowhere extends SingleBookSubProvider {
    public AtlasOfNowhere() {
        super("atlas", SuperSargassoSea.MODID);
    }

    @Override
    protected BookModel additionalSetup(BookModel book) {
        return book
            .withDisplayMode(BookDisplayMode.INDEX)
            .withCreativeTab(LocalCreativeTabs.TAB.getId());
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
