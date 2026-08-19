package com.outurnate.sargasso.datagen.book.dimension;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.outurnate.sargasso.registry.LocalItems;

import net.minecraft.resources.Identifier;

public class EnteringEntry extends BaseEntry {
    public EnteringEntry(CategoryProviderBase parent) {
        super(parent, "entering", "Entering", LocalItems.FLOTSAM);
    }

    @Override
    protected void generatePages() {
        this.page(
            "info",
            () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle(this.entryName());
        this.pageText(
            "More often than not, the path to this dimension is found accidentally. Falling out of the world, or perhaps, encountering a space where reality is a bit thinner can land you here.");
        this.page(
            "image",
            () -> BookImagePageModel.create()
                .withImages(Identifier.parse("sargasso:textures/gui/thinner.png")));
    }
}
