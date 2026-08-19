package com.outurnate.sargasso.datagen.book.dimension;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
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
}
