package com.outurnate.sargasso.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;

import net.minecraft.resources.Identifier;
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

    protected final void craftingRecipePage(String id, Identifier recipeId1, Identifier recipeId2) {
        this.page(
            id,
            () -> BookCraftingRecipePageModel.create()
                .withRecipeId1(recipeId1)
                .withRecipeId2(recipeId2));
    }

    protected final void craftingRecipePage(String id, String title, Identifier recipeId, String text) {
        this.page(
            id,
            () -> BookCraftingRecipePageModel.create()
                .withTitle1(this.context().pageTitle())
                .withRecipeId1(recipeId)
                .withText(this.context().pageText()));
        this.pageTitle(title);
        this.pageText(text);
    }

    protected final void entityPage(String id, String title, String entity, String text) {
        this.page(
            id,
            () -> BookEntityPageModel.create()
                .withEntityName(this.context().pageTitle())
                .withEntityId(entity)
                .withText(this.context().pageText()));
        this.pageTitle(title);
        this.pageText(text);
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

    protected final void spotlightPage(String id, String title, ItemLike item, String text) {
        this.page(
            id,
            () -> BookSpotlightPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
                .withItem(item));
        this.pageTitle(title);
        this.pageText(text);
    }

    protected final void textPage(String id, String title, String text) {
        this.page(
            id,
            () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle(title);
        this.pageText(text);
    }

    protected final void titlePage(String id, String text) {
        this.textPage(id, this.entryName(), text);
    }
}
