package com.outurnate.sargasso.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.outurnate.sargasso.registry.LocalItems;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;

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
        this.add(new BaseEntry(this, "entering", "Entering", LocalItems.FLOTSAM) {
            @Override
            protected void generatePages() {
                this.titlePage(
                    "info",
                    "More often than not, the path to this dimension is found accidentally. Falling out of the world, or perhaps, encountering a space where reality is a bit thinner can land you here.");
                this.page(
                    "image",
                    () -> BookImagePageModel.create()
                        .withImages(Identifier.parse("sargasso:textures/gui/thinner.png")));
            }
        }.generate());
        this.add(new BaseEntry(this, "flotsam", "Flotsam", LocalItems.FLOTSAM) {
            @Override
            protected void generatePages() {
                this.titlePage(
                    "flotsam",
                    "Every item that's ever been lost ends up here. Destroyed items, like those tossed in lava, not so much. Digging through the mountains of flotsam, one can find anything - eventually. Different biomes seem to host different kinds of items.");
            }
        }.generate());
        this.add(new BaseEntry(this, "leaving", "Leaving", Items.OBSIDIAN) {
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
        }.generate());
    }
}
