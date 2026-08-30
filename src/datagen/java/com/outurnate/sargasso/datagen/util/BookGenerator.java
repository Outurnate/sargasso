/* (C)2026 */
package com.outurnate.sargasso.datagen.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.client.StringSplitter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ListOperation;
import net.minecraft.world.level.storage.loot.functions.SetBookCoverFunction;
import net.minecraft.world.level.storage.loot.functions.SetLoreFunction;
import net.minecraft.world.level.storage.loot.functions.SetWrittenBookPagesFunction;

public class BookGenerator {
    private static final StringSplitter splitter = new StringSplitter(new StringSplitter.WidthProvider() {
        @Override
        public float getWidth(int codepoint, Style style) {
            return codepoint < FontWidths.WIDTHS.length ? FontWidths.WIDTHS[codepoint] : 6;
        }
    });

    private final List<ArrayList<String>> contents = new ArrayList<>(List.of(new ArrayList<>()));
    private final String title;
    private final String author;
    private final String chapter;

    public BookGenerator(String title, String author, String chapter) {
        if (title.length() > 32) {
            if (title.contains(";")) {
                this.title = title.split(";")[0];
            } else if (title.contains(":")) {
                this.title = title.split(":")[0];
            } else {
                this.title = title.substring(0, 32);
            }
        } else {
            this.title = title;
        }
        this.author = author;
        this.chapter = chapter;
    }

    public List<LootPoolSingletonContainer.Builder<?>> finish() {
        List<Filterable<Component>> pages = contents.stream().map(lines -> String.join("\n", lines))
            .map(page -> Filterable.passThrough((Component) Component.literal(page))).toList();
        int volumes = pages.size() / 100;
        List<LootPoolSingletonContainer.Builder<?>> books = new ArrayList<>();
        for (int start = 0; start < pages.size(); start += 100) {
            List<Filterable<Component>> batch = pages.subList(start, Math.min(start + 100, pages.size()));
            String volumeIndicator = volumes == 0 ? ""
                : " (" + String.valueOf((start / 100) + 1) + "/" + String.valueOf(volumes + 1) + ")";
            books.add(
                LootItem.lootTableItem(Items.WRITTEN_BOOK)
                    .apply(
                        SetWrittenBookPagesFunction.simpleBuilder(
                            conditions -> new SetWrittenBookPagesFunction(
                                conditions,
                                batch,
                                ListOperation.ReplaceAll.INSTANCE)))
                    .apply(
                        SetBookCoverFunction.simpleBuilder(
                            conditions -> new SetBookCoverFunction(
                                conditions,
                                Optional.of(Filterable.passThrough(this.title)),
                                Optional.of(this.author),
                                Optional.of(3))))
                    .apply(
                        SetLoreFunction.simpleBuilder(
                            conditions -> new SetLoreFunction(
                                conditions,
                                List.of(Component.literal(this.chapter + volumeIndicator)),
                                ListOperation.ReplaceAll.INSTANCE,
                                Optional.empty()))));
        }
        return books;
    }

    public void pushLines(String text) {
        ArrayList<String> lastPage = contents.getLast();
        for (String line : splitter.splitLines(text, 114, Style.EMPTY).stream().map(x -> x.getString())
            .toList()) {
            if (lastPage.size() >= 14) {
                lastPage = new ArrayList<>();
                contents.add(lastPage);
            }
            lastPage.add(line.trim());
        }
    }
}
