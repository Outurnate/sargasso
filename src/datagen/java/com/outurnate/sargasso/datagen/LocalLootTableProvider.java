/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.mojang.logging.LogUtils;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.util.BookGenerator;
import com.outurnate.sargasso.loot.FlimFlamLoreFunction;
import com.outurnate.sargasso.loot.LostItemFunction;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.registry.LocalPotions;
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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.EntryGroup;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction;
import net.minecraft.world.level.storage.loot.functions.SetAttributesFunction.ModifierBuilder;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SetLoreFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction.Target;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
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

        private LootTable.Builder createFlotsamOreDrops(Block block) {
            return LootTable.lootTable()
                .withPool(
                    LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(
                            LootItem.lootTableItem(block)
                                .when(this.hasSilkTouch())
                                .otherwise(
                                    EntryGroup.list(
                                        createOreEntry(block, LocalItems.LEAKY_BUCKET, 2.0F),
                                        createOreEntry(block, LocalItems.RUSTED_BOLT, 4.0F),
                                        createOreEntry(block, LocalItems.BROKEN_COG, 3.0F),
                                        createOreEntry(block, LocalItems.LOOSE_WIRE, 5.0F),
                                        createOreEntry(block, LocalItems.CIRCUIT_BOARD, 1.0F),
                                        createOreEntry(block, LocalItems.CLOCKSPRING, 1.0F)))));
        }

        private LootPoolEntryContainer.Builder<?> createOreEntry(Block block, ItemLike ore, float max) {
            HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries
                .lookupOrThrow(Registries.ENCHANTMENT);
            return (LootPoolEntryContainer.Builder<?>) this.applyExplosionDecay(
                block,
                LootItem.lootTableItem(ore)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, max)))
                    .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))));
        }

        @Override
        protected void generate() {
            this.add(LocalBlocks.DEBRIS.get(), this.createSingleItemTable(LocalItems.DEBRIS.get()));
            this.add(LocalBlocks.TOASTER.get(), this.createSingleItemTable(LocalItems.TOASTER.get()));
            this.add(LocalBlocks.SORTING_BIN.get(), this.createSingleItemTable(LocalItems.SORTING_BIN.get()));
            this.add(LocalBlocks.BETA_CHEST.get(), this.createSingleItemTable(Items.CHEST));
            this.add(
                LocalBlocks.ALPHA_GRASS.get(),
                this.createSilkTouchDispatchTable(
                    Blocks.GRASS_BLOCK,
                    LootItem.lootTableItem(Blocks.DIRT)));
            this.add(
                LocalBlocks.STARMETAL_BLOCK.get(),
                this.createSilkTouchDispatchTable(
                    LocalBlocks.STARMETAL_BLOCK.get(),
                    LootItem.lootTableItem(LocalItems.STARMETAL_SCRAP.get())));
            this.add(LocalBlocks.PYLON.get(), this.createSingleItemTable(LocalItems.PYLON.get()));
            this.add(
                LocalBlocks.SHOCK_THERAPIST.get(),
                this.createSingleItemTable(LocalItems.SHOCK_THERAPIST.get()));
            this.add(
                LocalBlocks.CREAMY_BEDROCK.get(),
                this.createSingleItemTable(
                    LocalItems.BEDROCK_SLOP.get(),
                    UniformGenerator.between(1.0F, 3.0F)));
            this.add(
                LocalBlocks.FLOTSAM.get(),
                this.createSilkTouchDispatchTable(
                    LocalBlocks.FLOTSAM.get(),
                    LootItem.lootTableItem(LocalItems.DEBRIS.get())
                        .apply(LostItemFunction.createBuilder())));
            this.add(
                LocalBlocks.PETRIFIED_FLOTSAM.get(),
                this.createSingleItemTable(LocalItems.PETRIFIED_FLOTSAM.get()));
            this.add(
                LocalBlocks.RICH_PETRIFIED_FLOTSAM.get(),
                this.createFlotsamOreDrops(LocalBlocks.RICH_PETRIFIED_FLOTSAM.get()));
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
        public static final TranslatableContents LORE_FIZZY = l("fizzy_lifting");
        public static final TranslatableContents LORE_BHJ = l("bhj");
        public static final String POTION_FIZZY = "fizzy_lifting";
        public static final String POTION_BHJ = "bhj";

        public static final TranslatableContents NAME_LIAR_PANTS = n("liar_pants");
        public static final TranslatableContents NAME_HAMMER_0 = n("hammer.0");
        public static final TranslatableContents NAME_HAMMER_1 = n("hammer.1");
        public static final TranslatableContents NAME_HAMMER_2 = n("hammer.2");
        public static final TranslatableContents NAME_ROCKET_BOOTS = n("rocket_boots");
        public static final TranslatableContents LORE_SHRINK_HELM = l("george");
        public static final TranslatableContents NAME_SHRINK_HELM = n("george");

        private static TranslatableContents l(String name) {
            return new TranslatableContents(
                "lore." + SuperSargassoSea.MODID + "." + name,
                null,
                new Object[0]);
        }

        private static TranslatableContents n(String name) {
            return new TranslatableContents(
                "item." + SuperSargassoSea.MODID + ".custom." + name,
                null,
                new Object[0]);
        }

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
                BOOKS,
                LootTable.lootTable().withPool(generateLootPoolOfBooks()));

            consumer.accept(
                ESCHER,
                LootTable.lootTable()
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.DIRT))));

            consumer.accept(
                ESCHER_OMINOUS,
                LootTable.lootTable()
                    .withPool(generateHammers()));

            consumer.accept(
                OFFICE,
                LootTable.lootTable()
                    // crap items pool
                    .withPool(
                        LootPool.lootPool()
                            .setRolls(UniformGenerator.between(10, 14))
                            .add(LootItem.lootTableItem(Items.BOWL).setWeight(2))
                            .add(
                                LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(1)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                            .add(
                                LootItem.lootTableItem(Items.COPPER_NUGGET).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 20))))
                            .add(
                                LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 7))))
                            .add(
                                LootItem.lootTableItem(Items.PAPER).setWeight(15)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 20))))
                            .add(
                                LootItem.lootTableItem(LocalItems.QUARTER.get()).setWeight(7)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4))))
                            .add(LootItem.lootTableItem(LocalItems.DEBRIS.get()).setWeight(7))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))
                    // good items pool
                    .withPool(
                        LootPool.lootPool()
                            .setRolls(UniformGenerator.between(1, 2))
                            .add(LootItem.lootTableItem(LocalItems.AA_BATTERY.get()).setWeight(16))
                            .add(LootItem.lootTableItem(LocalItems.RECHARGABLE_AA_BATTERY.get()).setWeight(2))
                            .add(LootItem.lootTableItem(LocalItems.RECORD_UNCHECKED.get()).setWeight(2))));

            consumer.accept(
                CURIOS,
                LootTable.lootTable()
                    // crap items pool
                    .withPool(
                        LootPool.lootPool()
                            .setRolls(UniformGenerator.between(3, 7))
                            .add(LootItem.lootTableItem(Items.BONE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.BOWL).setWeight(2))
                            .add(LootItem.lootTableItem(Items.LEATHER).setWeight(5))
                            .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(1))
                            .add(LootItem.lootTableItem(Items.STICK).setWeight(15))
                            .add(LootItem.lootTableItem(Items.DEAD_BUSH).setWeight(1))
                            .add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(7))
                            .add(LootItem.lootTableItem(Items.COPPER_NUGGET).setWeight(5))
                            .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(5))
                            .add(LootItem.lootTableItem(Items.COBBLESTONE).setWeight(15))
                            .add(generateTerribleTool(Items.WOODEN_AXE).setWeight(1))
                            .add(generateTerribleTool(Items.WOODEN_HOE).setWeight(1))
                            .add(generateTerribleTool(Items.WOODEN_PICKAXE).setWeight(1))
                            .add(generateTerribleTool(Items.WOODEN_SHOVEL).setWeight(1))
                            .add(generateTerribleTool(Items.WOODEN_SPEAR).setWeight(1))
                            .add(generateTerribleTool(Items.WOODEN_SWORD).setWeight(1))
                            .add(generateLiarsPants().setWeight(1)))
                    // good items pool
                    .withPool(
                        LootPool.lootPool()
                            .setRolls(UniformGenerator.between(1, 2))
                            .add(
                                LootItem.lootTableItem(Items.POTION)
                                    .apply(SetPotionFunction.setPotion(LocalPotions.HEAD_EXPLOSION))
                                    .setWeight(1))
                            .add(LootItem.lootTableItem(LocalItems.BEDROCK_CREAM).setWeight(10))
                            .add(LootItem.lootTableItem(LocalItems.LIGHTNING_BOTTLE).setWeight(1))
                            .add(
                                LootItem.lootTableItem(LocalItems.STUDDED_LEATHER_UPGRADE_SMITHING_TEMPLATE)
                                    .setWeight(5))
                            .add(
                                generateLootItemCustomPotion(
                                    POTION_BHJ,
                                    255,
                                    60,
                                    0,
                                    LORE_BHJ,
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
                                        1)).setWeight(1)))
                    // rare items pool
                    .withPool(
                        LootPool.lootPool()
                            .setRolls(UniformGenerator.between(0, 1))
                            .add(
                                generateLootItemCustomPotion(
                                    POTION_FIZZY,
                                    0,
                                    0,
                                    255,
                                    LORE_FIZZY,
                                    new MobEffectInstance(
                                        MobEffects.LEVITATION,
                                        4000,
                                        4)))
                            .add(generateShrinkingHelm())
                            .add(generateRocketBoots())));

            consumer.accept(
                ATLAS,
                LootTable.lootTable()
                    .withPool(
                        LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(
                                LootItem.lootTableItem(LocalItems.ATLAS))));

            LootPool.Builder baseCastlePool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(1, 6))
                .add(
                    LootItem.lootTableItem(Items.WHEAT).setWeight(7)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                .add(
                    LootItem.lootTableItem(Items.CARROT).setWeight(5)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                .add(
                    LootItem.lootTableItem(Items.POTATO).setWeight(5)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                .add(
                    LootItem.lootTableItem(Items.ARROW).setWeight(2)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 7))))
                .add(
                    LootItem.lootTableItem(Items.STRING).setWeight(2)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 7))));
            consumer.accept(CASTLE, LootTable.lootTable().withPool(baseCastlePool));
            consumer.accept(
                CASTLE_BARREL,
                LootTable.lootTable()
                    .withPool(baseCastlePool)
                    .withPool(
                        LootPool.lootPool()
                            .setRolls(UniformGenerator.between(1, 2))
                            .add(LootItem.lootTableItem(Items.CROSSBOW).setWeight(2))
                            .add(LootItem.lootTableItem(Items.TRIPWIRE_HOOK).setWeight(2))
                            .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2))));
            consumer.accept(
                BONE,
                LootTable.lootTable().withPool(
                    LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.BONE))));
        }

        private LootPool.Builder generateHammers() {
            TranslatableContents[] names = new TranslatableContents[] {
                NAME_HAMMER_0,
                NAME_HAMMER_1,
                NAME_HAMMER_2 };
            LootPool.Builder pool = LootPool.lootPool();
            pool.setRolls(UniformGenerator.between(0.0F, 1.0F));
            for (TranslatableContents name : names) {
                pool.add(
                    LootItem.lootTableItem(LocalItems.HAMMER)
                        .apply(SetNameFunction.setName(MutableComponent.create(name), Target.ITEM_NAME)));
                pool.add(
                    LootItem.lootTableItem(LocalItems.HAMMER)
                        .apply(SetNameFunction.setName(MutableComponent.create(name), Target.ITEM_NAME))
                        .apply(
                            EnchantWithLevelsFunction
                                .enchantWithLevels(lookupProvider, UniformGenerator.between(15.0F, 30.0F))));
                pool.add(
                    LootItem.lootTableItem(LocalItems.HAMMER)
                        .apply(SetNameFunction.setName(MutableComponent.create(name), Target.ITEM_NAME))
                        .apply(FlimFlamLoreFunction.setFlimFlam())
                        .apply(
                            EnchantWithLevelsFunction
                                .enchantWithLevels(lookupProvider, UniformGenerator.between(15.0F, 30.0F))));
                pool.add(
                    LootItem.lootTableItem(LocalItems.HAMMER)
                        .apply(SetNameFunction.setName(MutableComponent.create(name), Target.ITEM_NAME))
                        .apply(FlimFlamLoreFunction.setFlimFlam()));
            }
            return pool;
        }

        private LootItem.Builder<?> generateLiarsPants() {
            Identifier modifierIdentifier = Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "liar");
            return LootItem.lootTableItem(Items.LEATHER_LEGGINGS)
                .apply(
                    SetNameFunction
                        .setName(MutableComponent.create(NAME_LIAR_PANTS), Target.ITEM_NAME))
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
            TranslatableContents lore,
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
                        .addLine(MutableComponent.create(lore)))
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
                    throw new RuntimeException(e);
                }
            }
            return lootPool;
        }

        private LootItem.Builder<?> generateRocketBoots() {
            Identifier modifierIdentifier = Identifier.fromNamespaceAndPath(SuperSargassoSea.MODID, "rocket");
            return LootItem.lootTableItem(Items.GOLDEN_BOOTS)
                .apply(
                    SetNameFunction
                        .setName(MutableComponent.create(NAME_ROCKET_BOOTS), Target.ITEM_NAME))
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

            Identifier modifierIdentifier = SuperSargassoSea.ID("george");
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
                .apply(SetLoreFunction.setLore().addLine(MutableComponent.create(LORE_SHRINK_HELM)))
                .apply(
                    SetNameFunction
                        .setName(MutableComponent.create(NAME_SHRINK_HELM), Target.ITEM_NAME))
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
                .apply(FlimFlamLoreFunction.setFlimFlam())
                .apply(
                    EnchantWithLevelsFunction
                        .enchantWithLevels(this.lookupProvider, ConstantValue.exactly(1.0F)))
                .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1F, 0.2F)));
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

    public static final ResourceKey<LootTable> BOOKS = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("books"));

    public static final ResourceKey<LootTable> CURIOS = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("chests/curios"));

    public static final ResourceKey<LootTable> OFFICE = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("chests/office"));

    public static final ResourceKey<LootTable> ESCHER = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("chests/escher"));

    public static final ResourceKey<LootTable> ESCHER_OMINOUS = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("chests/escher_ominous"));

    public static final ResourceKey<LootTable> ATLAS = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("atlas"));

    public static final ResourceKey<LootTable> CASTLE = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("chests/castle"));

    public static final ResourceKey<LootTable> CASTLE_BARREL = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("chests/castle_barrel"));

    public static final ResourceKey<LootTable> BONE = ResourceKey.create(
        Registries.LOOT_TABLE,
        SuperSargassoSea.ID("village/bone"));

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
