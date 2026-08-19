package com.outurnate.sargasso.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.outurnate.sargasso.registry.LocalItems;

public class ThingsCategory extends CategoryProvider {
    public ThingsCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(LocalItems.AA_BATTERY);
    }

    @Override
    public String categoryId() {
        return "things";
    }

    @Override
    protected String categoryName() {
        return "Curiosities";
    }

    @Override
    protected void generateEntries() {
    }
}
