package com.outurnate.sargasso.datagen.book.dimension;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.outurnate.sargasso.datagen.book.BaseEntry;
import com.outurnate.sargasso.registry.LocalItems;

public class FlotsamEntry extends BaseEntry {
    public FlotsamEntry(CategoryProviderBase parent) {
        super(parent, "flotsam", "Flotsam", LocalItems.FLOTSAM);
    }

    @Override
    protected void generatePages() {
        this.titlePage(
            "flotsam",
            "Every item that's ever been lost ends up here. Destroyed items, like those tossed in lava, not so much. Digging through the mountains of flotsam, one can find anything - eventually. Different biomes seem to host different kinds of items.");
    }
}
