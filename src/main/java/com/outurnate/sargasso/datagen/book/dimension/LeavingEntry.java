package com.outurnate.sargasso.datagen.book.dimension;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.outurnate.sargasso.datagen.book.BaseEntry;

import net.minecraft.world.item.Items;

public class LeavingEntry extends BaseEntry {
    public LeavingEntry(CategoryProviderBase parent) {
        super(parent, "leaving", "Leaving", Items.OBSIDIAN);
    }

    @Override
    protected void generatePages() {
        this.titlePage(
            "info",
            "Though there may be other ways out, this book only knows of one method. You must travel back to the Overworld via the Nether. Previous travellers have left behind ruined portals you may be able to repair.");
        this.page(
            "portal",
            () -> BookMultiblockPageModel.create()
                .withMultiblockId(this.modLoc("nether_portal")));
    }
}
