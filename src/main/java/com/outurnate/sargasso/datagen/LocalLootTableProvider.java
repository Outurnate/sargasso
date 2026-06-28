/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.mojang.logging.LogUtils;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.util.BookGenerator;
import com.outurnate.sargasso.loot.LostItemFunction;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalItems;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ARGB;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LocalLootTableProvider extends LootTableProvider {
    public static class LocalBlockLootSubProvider extends BlockLootSubProvider {
        protected LocalBlockLootSubProvider(HolderLookup.Provider lookupProvider) {
            super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
        }

        @Override
        protected void generate() {
            this.add(LocalBlocks.DEBRIS.get(), this.createSingleItemTable(LocalItems.JUNK.get()));
            this.add(
                LocalBlocks.CREAMY_BEDROCK.get(),
                this.createSingleItemTable(
                    LocalItems.BEDROCK_SLOP.get(),
                    UniformGenerator.between(1.0F, 3.0F)));
            this.add(
                LocalBlocks.FLOTSAM.get(),
                this.createSilkTouchDispatchTable(
                    LocalBlocks.FLOTSAM.get(),
                    LootItem.lootTableItem(LocalItems.JUNK.get())
                        .apply(LostItemFunction.createBuilder())));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return LocalBlocks.REGISTRY.getEntries().stream().map(e -> (Block) e.value()).toList();
        }
    }

    public static class LocalLootTableSubProvider implements LootTableSubProvider {
        private record BookConfiguration(String url, String[] blacklist) {
        }

        public static final Logger LOGGER = LogUtils.getLogger();
        private final EpubReader reader = new EpubReader();

        protected LocalLootTableSubProvider(HolderLookup.Provider lookupProvider) {
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
            LootPool.Builder lootPool = LootPool.lootPool();
            lootPool.setRolls(new UniformGenerator(new ConstantValue(1), new ConstantValue(6)));
            BookConfiguration[] books = new BookConfiguration[] {
                new BookConfiguration(
                    "https://www.gutenberg.org/cache/epub/11/pg11-images-3.epub",
                    new String[] {
                        "Alice’s Adventures in Wonderland",
                        "THE MILLENNIUM FULCRUM EDITION 3.0",
                        "Contents",
                        "THE FULL PROJECT GUTENBERG™ LICENSE" }),
                new BookConfiguration(
                    "https://www.gutenberg.org/cache/epub/84/pg84-images-3.epub",
                    new String[] {
                        "Frankenstein;",
                        "or, the Modern Prometheus",
                        "CONTENTS",
                        "THE FULL PROJECT GUTENBERG™ LICENSE" }),
                new BookConfiguration(
                    "https://www.gutenberg.org/cache/epub/68283/pg68283-images-3.epub",
                    new String[] { "THE FULL PROJECT GUTENBERG™ LICENSE" }),
                new BookConfiguration(
                    "https://www.gutenberg.org/cache/epub/345/pg345-images-3.epub",
                    new String[] {
                        "D R A C U L A",
                        "Contents",
                        "NOTE",
                        "THE FULL PROJECT GUTENBERG™ LICENSE" }),
                new BookConfiguration(
                    "https://www.gutenberg.org/cache/epub/35/pg35-images-3.epub",
                    new String[] {
                        "The Time Machine",
                        "CONTENTS",
                        "THE FULL PROJECT GUTENBERG™ LICENSE" }), };
            for (BookConfiguration bookConfiguration : books) {
                try {
                    Book book = retrieveBook(bookConfiguration.url);
                    XPath xpath = XPathFactory.newInstance().newXPath();
                    String title = book.getTitle();
                    String authors = String.join(
                        ", ",
                        book.getMetadata().getAuthors().stream()
                            .map(author -> author.getFirstname() + " " + author.getLastname()).toList());
                    LOGGER.debug("Parsing book \n" + title + "\" by \"" + authors + "\"");
                    for (TOCReference reference : book.getTableOfContents().getTocReferences()) {
                        String chapterTitle = reference.getTitle();
                        LOGGER.debug("Parsing chapters \"" + chapterTitle + "\"");
                        if (!Arrays.stream(bookConfiguration.blacklist).anyMatch(chapterTitle::contains)) {
                            BookGenerator generator = new BookGenerator(title, authors, chapterTitle);
                            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                                .parse(new ByteArrayInputStream(reference.getResource().getData()));
                            NodeList pNodes = (NodeList) xpath.evaluate("//p", doc, XPathConstants.NODESET);
                            for (int i = 0; i < pNodes.getLength(); i++) {
                                generator.pushLines(
                                    pNodes.item(i).getTextContent().replaceAll("\n", " ")
                                        .replaceAll("\\s+", " "));
                            }
                            for (LootPoolSingletonContainer.Builder<?> bookLoot : generator.finish()) {
                                lootPool.add(bookLoot);
                            }
                        }
                    }
                } catch (IOException | SAXException | ParserConfigurationException
                    | XPathExpressionException e) {
                    LOGGER.error(e.toString());
                }
            }
            consumer.accept(
                ResourceKey.create(
                    Registries.LOOT_TABLE,
                    Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "books")),
                LootTable.lootTable().withPool(lootPool));
            consumer.accept(
                ResourceKey.create(
                    Registries.LOOT_TABLE,
                    Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "chests/curios")),
                LootTable.lootTable()
                    .withPool(
                        LootPool.lootPool()
                            .setRolls(UniformGenerator.between(3, 7))
                            .setBonusRolls(ConstantValue.exactly(1))
                            .add(
                                LootItem.lootTableItem(Items.POTION)
                                    .apply(
                                        SetComponentsFunction.setComponent(
                                            DataComponents.POTION_CONTENTS,
                                            new PotionContents(
                                                Optional.empty(),
                                                Optional.of(ARGB.color(0, 0, 255)),
                                                List.of(
                                                    new MobEffectInstance(
                                                        MobEffects.LEVITATION,
                                                        4000,
                                                        4,
                                                        false,
                                                        false)),
                                                Optional.of("fizzy_lifting")))))));
        }

        private Book retrieveBook(String urlText) throws MalformedURLException, IOException {
            URL url = URI.create(urlText).toURL();
            File cachedCopy = Paths.get(System.getProperty("user.dir"), FilenameUtils.getName(url.getPath()))
                .toFile();
            LOGGER.debug("Checking cache for " + cachedCopy.getAbsolutePath());
            if (!cachedCopy.exists()) {
                try (InputStream in = url.openStream()) {
                    Files.copy(in, cachedCopy.toPath());
                }
            }
            try (InputStream in = new FileInputStream(cachedCopy)) {
                return reader.readEpub(in);
            }
        }
    }

    public LocalLootTableProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(
            output,
            Set.of(),
            List.of(
                new SubProviderEntry(LocalBlockLootSubProvider::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(LocalLootTableSubProvider::new, LootContextParamSets.CHEST)),
            lookupProvider);
    }
}
