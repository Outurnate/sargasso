package com.outurnate.sargasso.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.outurnate.sargasso.datagen.book.dimension.EnteringEntry;
import com.outurnate.sargasso.datagen.book.dimension.LeavingEntry;
import com.outurnate.sargasso.registry.LocalItems;

public class DimensionCategory extends CategoryProvider {
    public DimensionCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String categoryDescription() {
        return "How to enter, and, more importantly, how to leave";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(LocalItems.FLOTSAM);
    }

    @Override
    public String categoryId() {
        return "dimension";
    }

    @Override
    protected String categoryName() {
        return "Travelling";
    }

    @Override
    protected void generateEntries() {
        this.add(new EnteringEntry(this).generate());
        this.add(new LeavingEntry(this).generate());
    }
}
