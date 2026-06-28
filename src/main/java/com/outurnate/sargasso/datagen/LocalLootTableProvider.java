/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.mojang.logging.LogUtils;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.util.BookGenerator;
import com.outurnate.sargasso.loot.FlimFlamLoreFunction;
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
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ARGB;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.item.equipment.trim.TrimPatterns;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction.ModifierBuilder;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetLoreFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction.Target;
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
        private final HolderLookup.Provider lookupProvider;
        private final HolderGetter<TrimMaterial> trimMaterialProvider;
        private final HolderGetter<TrimPattern> trimPatternProvider;

        protected LocalLootTableSubProvider(HolderLookup.Provider lookupProvider) {
            this.lookupProvider = lookupProvider;
            this.trimMaterialProvider = this.lookupProvider.lookup(Registries.TRIM_MATERIAL).get();
            this.trimPatternProvider = this.lookupProvider.lookup(Registries.TRIM_PATTERN).get();
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
            consumer.accept(
                ResourceKey.create(
                    Registries.LOOT_TABLE,
                    Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "books")),
                LootTable.lootTable().withPool(generateLootPoolOfBooks()));
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
                                generateLootItemCustomPotion(
                                    "fizzy_lifting",
                                    0,
                                    0,
                                    255,
                                    new MobEffectInstance(
                                        MobEffects.LEVITATION,
                                        4000,
                                        4)))
                            .add(
                                generateLootItemCustomPotion(
                                    "bhj",
                                    255,
                                    60,
                                    0,
                                    new MobEffectInstance(
                                        MobEffects.SPEED,
                                        36000,
                                        2),
                                    new MobEffectInstance(
                                        MobEffects.STRENGTH,
                                        36000,
                                        3),
                                    new MobEffectInstance(
                                        MobEffects.REGENERATION,
                                        36000,
                                        2),
                                    new MobEffectInstance(
                                        MobEffects.SLOWNESS,
                                        36000,
                                        3),
                                    new MobEffectInstance(
                                        MobEffects.WEAKNESS,
                                        36000,
                                        2),
                                    new MobEffectInstance(
                                        MobEffects.POISON,
                                        36000,
                                        1)))
                            .add(generateShrinkingHelm())
                            .add(LootItem.lootTableItem(LocalItems.BEDROCK_CREAM))
                            .add(LootItem.lootTableItem(LocalItems.LIGHTNING_BOTTLE))
                            .add(generateLiarsPants())
                            .add(generateRocketBoots())
                            .add(generateTerribleTool(Items.WOODEN_AXE))
                            .add(generateTerribleTool(Items.WOODEN_HOE))
                            .add(generateTerribleTool(Items.WOODEN_PICKAXE))
                            .add(generateTerribleTool(Items.WOODEN_SHOVEL))
                            .add(generateTerribleTool(Items.WOODEN_SPEAR))
                            .add(generateTerribleTool(Items.WOODEN_SWORD))));
        }

        private LootItem.Builder<?> generateLiarsPants() {
            Identifier modifierIdentifier = Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "liar");
            return LootItem.lootTableItem(Items.LEATHER_LEGGINGS)
                .apply(
                    SetNameFunction
                        .setName(Component.translatable("sargasso.lore.liar_pants"), Target.ITEM_NAME))
                .apply(
                    SetAttributesFunction.setAttributes()
                        .withModifier(
                            new ModifierBuilder(
                                modifierIdentifier,
                                Attributes.BURNING_TIME,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                                ConstantValue.exactly(1.0F))
                                    .forSlot(EquipmentSlotGroup.ARMOR)));
        }

        private LootItem.Builder<?> generateLootItemCustomPotion(
            String name,
            int red,
            int green,
            int blue,
            MobEffectInstance... effects) {
            return LootItem.lootTableItem(Items.POTION)
                .apply(
                    SetComponentsFunction.setComponent(
                        DataComponents.POTION_CONTENTS,
                        new PotionContents(
                            Optional.empty(),
                            Optional.of(ARGB.color(red, green, blue)),
                            Arrays.asList(effects),
                            Optional.of(name))))
                .apply(
                    SetLoreFunction.setLore()
                        .addLine(Component.translatable("sargasso.lore." + name)))
                .apply(
                    SetComponentsFunction.setComponent(
                        DataComponents.TOOLTIP_DISPLAY,
                        TooltipDisplay.DEFAULT
                            .withHidden(DataComponents.POTION_CONTENTS, true)));
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
            return lootPool;
        }

        private LootItem.Builder<?> generateRocketBoots() {
            Identifier modifierIdentifier = Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "liar");
            return LootItem.lootTableItem(Items.GOLDEN_BOOTS)
                .apply(
                    SetNameFunction
                        .setName(Component.translatable("sargasso.lore.rocket_boots"), Target.ITEM_NAME))
                .apply(
                    SetComponentsFunction.setComponent(
                        DataComponents.TRIM,
                        new ArmorTrim(
                            trimMaterialProvider.getOrThrow(TrimMaterials.REDSTONE),
                            trimPatternProvider.getOrThrow(TrimPatterns.SNOUT))))
                .apply(
                    SetAttributesFunction.setAttributes()
                        .withModifier(
                            new ModifierBuilder(
                                modifierIdentifier,
                                Attributes.JUMP_STRENGTH,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                                ConstantValue.exactly(10.0F))
                                    .forSlot(EquipmentSlotGroup.ARMOR))
                        .withModifier(
                            new ModifierBuilder(
                                modifierIdentifier,
                                Attributes.FALL_DAMAGE_MULTIPLIER,
                                AttributeModifier.Operation.ADD_MULTIPLIED_BASE,
                                ConstantValue.exactly(.2F))
                                    .forSlot(EquipmentSlotGroup.ARMOR)));
        }

        private LootItem.Builder<?> generateShrinkingHelm() {
            HolderGetter<Enchantment> enchantmentProvider = this.lookupProvider.lookup(Registries.ENCHANTMENT)
                .get();

            ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
            enchantments.set(enchantmentProvider.getOrThrow(Enchantments.PROTECTION), 4);
            enchantments.set(enchantmentProvider.getOrThrow(Enchantments.RESPIRATION), 3);
            enchantments.set(enchantmentProvider.getOrThrow(Enchantments.AQUA_AFFINITY), 1);
            enchantments.set(enchantmentProvider.getOrThrow(Enchantments.THORNS), 3);
            enchantments.set(enchantmentProvider.getOrThrow(Enchantments.BINDING_CURSE), 1);
            enchantments.set(enchantmentProvider.getOrThrow(Enchantments.UNBREAKING), 3);
            enchantments.set(enchantmentProvider.getOrThrow(Enchantments.MENDING), 1);

            Identifier modifierIdentifier = Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "george");
            return LootItem.lootTableItem(Items.DIAMOND_HELMET)
                .apply(
                    SetComponentsFunction
                        .setComponent(DataComponents.ENCHANTMENTS, enchantments.toImmutable()))
                .apply(
                    SetComponentsFunction.setComponent(
                        DataComponents.TOOLTIP_DISPLAY,
                        TooltipDisplay.DEFAULT.withHidden(DataComponents.ATTRIBUTE_MODIFIERS, true)))
                .apply(
                    SetComponentsFunction.setComponent(
                        DataComponents.TRIM,
                        new ArmorTrim(
                            trimMaterialProvider.getOrThrow(TrimMaterials.NETHERITE),
                            trimPatternProvider.getOrThrow(TrimPatterns.SILENCE))))
                .apply(SetLoreFunction.setLore().addLine(Component.translatable("sargasso.lore.george")))
                .apply(
                    SetNameFunction
                        .setName(Component.translatable("sargasso.lore.george_name"), Target.ITEM_NAME))
                .apply(
                    SetAttributesFunction.setAttributes()
                        .withModifier(
                            new ModifierBuilder(
                                modifierIdentifier,
                                Attributes.MAX_HEALTH,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                                ConstantValue.exactly(-0.95F)).forSlot(EquipmentSlotGroup.ARMOR))
                        .withModifier(
                            new ModifierBuilder(
                                modifierIdentifier,
                                Attributes.SCALE,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                                ConstantValue.exactly(-0.9F)).forSlot(EquipmentSlotGroup.ARMOR))
                        .withModifier(
                            new ModifierBuilder(
                                modifierIdentifier,
                                Attributes.MOVEMENT_SPEED,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                                ConstantValue.exactly(-0.9F)).forSlot(EquipmentSlotGroup.ARMOR))
                        .withModifier(
                            new ModifierBuilder(
                                modifierIdentifier,
                                Attributes.JUMP_STRENGTH,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                                ConstantValue.exactly(-0.5F)).forSlot(EquipmentSlotGroup.ARMOR)));
        }

        private LootItem.Builder<?> generateTerribleTool(ItemLike item) {
            return LootItem.lootTableItem(item)
                .apply(FlimFlamLoreFunction.setFlimFlam());
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
