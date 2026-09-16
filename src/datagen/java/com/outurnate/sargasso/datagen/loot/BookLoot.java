/*
 * This class is distributed as part of the Super Sargasso Sea mod.
 * Complete source on GitHub:
 * https://github.com/Outurnate/sargasso
 *
 * Super Sargasso Sea is free software and distributed
 * under the MIT License: https://opensource.org/license/mit
 *
 * © 2026 the authors of the Super Sargasso Sea mod
 */
package com.outurnate.sargasso.datagen.loot;

import com.mojang.logging.LogUtils;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.util.BookGenerator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.BiConsumer;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
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

public class BookLoot extends LootProvider {
	private record BookConfiguration(String url, String[] blacklist) {
	}

	public static final Logger LOGGER = LogUtils.getLogger();
	public static final ResourceKey<LootTable> BOOKS = ResourceKey.create(
			Registries.LOOT_TABLE,
			SuperSargassoSea.ID("books"));

	private final EpubReader reader = new EpubReader();

	public BookLoot(HolderLookup.Provider lookupProvider) {
		super(lookupProvider);
	}

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
		consumer.accept(
				BOOKS,
				LootTable.lootTable().withPool(generateLootPoolOfBooks()));
	}

	private LootPool.Builder generateLootPoolOfBooks() {
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
						"https://www.gutenberg.org/cache/epub/35/pg35-images-3.epub",
						new String[] {
								"The Time Machine",
								"CONTENTS",
								"THE FULL PROJECT GUTENBERG™ LICENSE" }),
				new BookConfiguration(
						"https://www.gutenberg.org/cache/epub/83/pg83-images-3.epub",
						new String[] {
								"From the Earth to the Moon",
								"Contents: From the Earth to the Moon",
								"Contents: Round the Moon",
								"FROM THE EARTH TO THE MOON",
								"ROUND THE MOON",
								"PRELIMINARY CHAPTER THE FIRST PART OF THIS WORK, AND SERVING AS A PREFACE TO THE SECOND",
								"CHAPTER I. TWENTY MINUTES PAST TEN TO FORTY-SEVEN MINUTES PAST TEN P. M.",
								"CHAPTER II. THE FIRST HALF-HOUR",
								"CHAPTER III. THEIR PLACE OF SHELTER",
								"CHAPTER IV. A LITTLE ALGEBRA",
								"CHAPTER V. THE COLD OF SPACE",
								"CHAPTER VI. QUESTION AND ANSWER",
								"CHAPTER VII. A MOMENT OF INTOXICATION",
								"CHAPTER VIII. AT SEVENTY-EIGHT THOUSAND FIVE HUNDRED AND FOURTEEN LEAGUES",
								"CHAPTER IX. THE CONSEQUENCES OF A DEVIATION",
								"CHAPTER X. THE OBSERVERS OF THE MOON",
								"CHAPTER XI. FANCY AND REALITY",
								"CHAPTER XII. OROGRAPHIC DETAILS",
								"CHAPTER XIII. LUNAR LANDSCAPES",
								"CHAPTER XIV. THE NIGHT OF THREE HUNDRED AND FIFTY-FOUR HOURS AND A HALF",
								"CHAPTER XV. HYPERBOLA OR PARABOLA",
								"CHAPTER XVI. THE SOUTHERN HEMISPHERE",
								"CHAPTER XVII. TYCHO",
								"CHAPTER XVIII. GRAVE QUESTIONS",
								"CHAPTER XIX. A STRUGGLE AGAINST THE IMPOSSIBLE",
								"CHAPTER XX. THE SOUNDINGS OF THE SUSQUEHANNA",
								"CHAPTER XXI. J. T. MASTON RECALLED",
								"CHAPTER XXII. RECOVERED FROM THE SEA",
								"CHAPTER XXIII. THE END",
								"THE FULL PROJECT GUTENBERG™ LICENSE" }),
				new BookConfiguration(
						"https://www.gutenberg.org/cache/epub/120/pg120-images-3.epub",
						new String[] {
								"TREASURE ISLAND",
								"Illustrated by Louis Rhead",
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
				LOGGER.debug("Parsing book \"" + title + "\" by \"" + authors + "\"");
				for (TOCReference reference : book.getTableOfContents().getTocReferences()) {
					String chapterTitle = reference.getTitle();
					LOGGER.debug("Parsing chapter \"" + chapterTitle + "\"");
					if (Arrays.stream(bookConfiguration.blacklist).noneMatch(chapterTitle::contains)) {
						BookGenerator generator = new BookGenerator(title, authors, chapterTitle);
						Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
								.parse(new ByteArrayInputStream(reference.getResource().getData()));
						NodeList pNodes = (NodeList) xpath.evaluate("//p", doc, XPathConstants.NODESET);
						for (int i = 0; i < pNodes.getLength(); i++) {
							generator.pushLines(
									pNodes.item(i).getTextContent().replace("\n", " ")
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
				throw new RuntimeException(e);
			}
		}
		return lootPool;
	}

	private Book retrieveBook(String urlText) throws IOException {
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
