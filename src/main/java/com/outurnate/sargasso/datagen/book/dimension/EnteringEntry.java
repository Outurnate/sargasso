package com.outurnate.sargasso.datagen.book.dimension;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import com.outurnate.sargasso.registry.LocalItems;

import net.minecraft.resources.Identifier;

public class EnteringEntry extends EntryProvider {
    public EnteringEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.CONDITION;
    }

    @Override
    protected String entryDescription() {
        return "More often than not, the path to this dimension is found accidentally. Falling out of the world, or perhaps, encountering a space where reality is a bit thinner, can land you here.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(LocalItems.FLOTSAM);
    }

    @Override
    protected String entryId() {
        return "entering";
    }

    @Override
    protected String entryName() {
        return "Entering";
    }

    @Override
    protected void generatePages() {
        this.page(
            "info",
            () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.page(
            "image",
            () -> BookImagePageModel.create()
                .withText(this.context().pageText())
                .withTitle(this.context().pageTitle())
                .withImages(Identifier.parse("sargasso:textures/gui/thinner.png")));
    }
}
