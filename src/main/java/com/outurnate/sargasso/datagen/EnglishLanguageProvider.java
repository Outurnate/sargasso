/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.klikli_dev.modonomicon.api.datagen.AbstractModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.loot.FlimFlamLore;
import com.outurnate.sargasso.loot.LoreSet;
import com.outurnate.sargasso.registry.LocalAdvancements;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalDamageTypes;
import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.registry.LocalMobEffects;
import com.outurnate.sargasso.registry.LocalPotions;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;
import net.minecraft.world.item.alchemy.Potion;

public class EnglishLanguageProvider extends AbstractModonomiconLanguageProvider {
    public EnglishLanguageProvider(
        PackOutput output,
        CompletableFuture<Provider> lookupProvider,
        ModonomiconLanguageProvider cachedProvider) {
        super(output, SuperSargassoSea.MODID, "en_us", cachedProvider);
    }

    private void addAdvancement(Identifier name, String title, String description) {
        this.add(name.toLanguageKey("advancements", "title"), title);
        this.add(name.toLanguageKey("advancements", "description"), description);
    }

    private void addCustomPotion(String key, String name) {
        this.add("item.minecraft.potion.effect." + key, name);
    }

    private void addDamageType(ResourceKey<DamageType> damageType, String translation) {
        this.add(damageType.identifier().toLanguageKey("death.attack"), translation);
    }

    private void addLore(LoreSet lore, String... values) {
        if (lore.size() != values.length) {
            throw new RuntimeException(
                "Lore set " + lore.name() + " definition size mismatch (" + lore.size() + "/" + values.length
                    + "}");
        }
        String[] loreKeys = lore.keys().toArray(String[]::new);
        for (int i = 0; i < loreKeys.length; ++i) {
            this.add(loreKeys[i], values[i]);
        }
    }

    private void addMisc(TranslatableContents contents, String value) {
        this.add(contents.getKey(), value);
    }

    private void addPainting(ResourceKey<PaintingVariant> variant, String title) {
        this.add(variant.identifier().toLanguageKey("painting", "title"), title);
    }

    private void addPotion(String name, List<Holder<Potion>> potions) {
        for (Holder<Potion> potion : potions) {
            String key = potion.value().name();
            this.add("item.minecraft.potion.effect." + key, name);
            this.add("item.minecraft.splash_potion.effect." + key, "Splash Potion of " + name.toLowerCase());
            this.add(
                "item.minecraft.lingering_potion.effect." + key,
                "Lingering Potion of " + name.toLowerCase());
            this.add("item.minecraft.tipped_arrow.effect." + key, "Tipped Arrow of " + name.toLowerCase());
        }
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup.sargasso", "Super Sargasso Sea");

        this.addPainting(LocalPaintingVariantsProvider.GENE, "Gene");

        this.addMisc(LocalSoundDefinitionsProvider.creamApply, "Bedrock cream applied");
        this.addMisc(LocalSoundDefinitionsProvider.glitchTeleport, "Glitched");
        this.addMisc(LocalSoundDefinitionsProvider.toaster, "Time travel happened");
        this.addMisc(LocalSoundDefinitionsProvider.pylon, "Pylon equipped");
        this.addMisc(LocalSoundDefinitionsProvider.zap, "Electric arc");
        this.addMisc(LocalSoundDefinitionsProvider.hammer_hit, "Hammer slams");
        this.addMisc(LocalSoundDefinitionsProvider.hammer_hit_ground, "Hammer thuds");
        this.addMisc(LocalSoundDefinitionsProvider.hammer_return, "Hammer returns");
        this.addMisc(LocalSoundDefinitionsProvider.hammer_throw, "Hammer clangs");
        this.addMisc(LocalJukeboxSongProvider.unchecked, "Tarn Kazsuhara - Unchecked (Tapeless Mix)");
        this.addCustomPotion(
            LocalLootTableProvider.LocalLootTableSubProvider.POTION_BHJ,
            "Bone Hurting Juice");
        this.addCustomPotion(
            LocalLootTableProvider.LocalLootTableSubProvider.POTION_FIZZY,
            "Fizzy Lifting Drink");
        this.addMisc(
            LocalLootTableProvider.LocalLootTableSubProvider.LORE_FIZZY,
            "Burp, Charlie! You've got to burp!");
        this.addMisc(LocalLootTableProvider.LocalLootTableSubProvider.LORE_BHJ, "oof ouch my bones");
        this.addMisc(LocalLootTableProvider.LocalLootTableSubProvider.NAME_LIAR_PANTS, "Liar's Pants");
        this.addMisc(
            LocalLootTableProvider.LocalLootTableSubProvider.NAME_ROCKET_BOOTS,
            "Dwarven Rocket Boots");
        this.addMisc(LocalLootTableProvider.LocalLootTableSubProvider.NAME_SHRINK_HELM, "Sir George's Helm");
        this.addMisc(
            LocalLootTableProvider.LocalLootTableSubProvider.LORE_SHRINK_HELM,
            "The helm of Sire George the shrunk, a legendary knight");

        this.add(
            "chat." + SuperSargassoSea.MODID + ".no_toast",
            "Nothing happens.");
        this.add(
            "chat." + SuperSargassoSea.MODID + ".toast",
            "You have a strange feeling that the bread you ate %s was (and always has been) toast.");
        this.add(
            "chat." + SuperSargassoSea.MODID + ".voltmeter",
            "The potatoes in your body current produce %s FE/t");

        this.addBlock(LocalBlocks.FLOTSAM, "Flotsam");
        this.addBlock(LocalBlocks.DEBRIS, "Debris");
        this.addBlock(LocalBlocks.CREAMY_BEDROCK, "Creamy Bedrock");
        this.addBlock(LocalBlocks.GLITCH, "Glitch in Reality");
        this.addBlock(LocalBlocks.PORTAL, "Portal");
        this.addBlock(LocalBlocks.TOASTER, "Chronometric Flux Toaster");
        this.addBlock(LocalBlocks.PYLON, "Pylon");
        this.addBlock(LocalBlocks.SHOCK_THERAPIST, "Shock Therapist");
        this.addBlock(LocalBlocks.PETRIFIED_FLOTSAM, "Petrified Flotsam");
        this.addBlock(LocalBlocks.RICH_PETRIFIED_FLOTSAM, "Rich Petrified Flotsam");
        this.addBlock(LocalBlocks.STARMETAL_BLOCK, "Starmetal Block");
        this.addBlock(LocalBlocks.REINFORCED_STARMETAL_BLOCK, "Reinforced Starmetal Block");

        this.addItem(LocalItems.RECORD_UNCHECKED, "Music Disc");
        this.addItem(LocalItems.BEDROCK_CREAM, "Professor Murgatroyd's Miracle Bedrock Cream");
        this.addItem(LocalItems.BEDROCK_SLOP, "Bedrock Slop");
        this.addItem(LocalItems.BREADROCK, "Breadrock");
        this.addItem(LocalItems.POTATO_BATTERY, "Potato Battery");
        this.addItem(LocalItems.PERSONAL_VOLTMETER, "Personal Voltmeter");
        this.addItem(LocalItems.LIGHTNING_BOTTLE, "Lightning in a Bottle");
        this.addItem(LocalItems.STUDDED_LEATHER_BOOTS, "Studded Leather Boots");
        this.addItem(LocalItems.STUDDED_LEATHER_CHESTPLATE, "Studded Leather Chestplate");
        this.addItem(LocalItems.STUDDED_LEATHER_HELMET, "Studded Leather Helmet");
        this.addItem(LocalItems.STUDDED_LEATHER_LEGGINGS, "Studded Leather Leggings");
        this.addItem(
            LocalItems.STUDDED_LEATHER_UPGRADE_SMITHING_TEMPLATE,
            "Studded Leather Upgrade Smithing Template");
        this.addItem(LocalItems.AA_BATTERY, "Durable AA Battery Cell");
        this.addItem(LocalItems.RECHARGABLE_AA_BATTERY, "Rechargable Durable AA Battery Cell");
        this.addItem(LocalItems.REDSTONE_EMP, "Redstone Pulse Device");
        this.addItem(LocalItems.FOX_EARS, "Fox Ears");
        this.addItem(LocalItems.COMICALLY_TALL_FOX_EARS, "Comically Tall Fox Ears");
        this.addItem(LocalItems.QUARTER, "25¢ Coin");
        this.addItem(LocalItems.KEY, "Sky Key");
        this.addItem(LocalItems.KEY_OMINOUS, "Ominous Sky Key");
        this.addItem(LocalItems.LOOSE_WIRE, "Loose Wire");
        this.addItem(LocalItems.CLOCKSPRING, "Clock Spring");
        this.addItem(LocalItems.CIRCUIT_BOARD, "Circuit Board");
        this.addItem(LocalItems.BROKEN_COG, "Broken Cog");
        this.addItem(LocalItems.RUSTED_BOLT, "Rusted Bolt");
        this.addItem(LocalItems.LEAKY_BUCKET, "Leaky Bucket");

        this.add(LocalMobEffects.HEAD_EXPLOSION.value(), "Impending Head Explosion");

        this.addPotion(
            "Juice that makes your head explode",
            List.of(
                LocalPotions.HEAD_EXPLOSION,
                LocalPotions.STRONG_HEAD_EXPLOSION,
                LocalPotions.EXTRA_STRONG_HEAD_EXPLOSION,
                LocalPotions.LONG_HEAD_EXPLOSION));

        this.addDamageType(LocalDamageTypes.ELECTRIC_SHOCK, "%s was electrocuted");
        this.addDamageType(LocalDamageTypes.HEAD_EXPLOSION, "%s's head exploded");

        this.addEntityType(LocalEntities.LIGHTNING_BOTTLE, "Thrown Lightning in a Bottle");
        this.addEntityType(LocalEntities.ELECTRIC_MINE, "Electric Mine");
        this.addEntityType(LocalEntities.REDSTONE_EMP, "Redstone Pulse Device");
        this.addEntityType(LocalEntities.REDSTONE_BUG, "Redstone Bug");

        this.addAdvancement(
            LocalAdvancements.ENTER,
            "The Super Sargasso Sea",
            "Not all those who wander are lost...but you sure are");
        this.addAdvancement(LocalAdvancements.LEAVE, "Through the Nether", "Twisting, turning...");
        this.addAdvancement(LocalAdvancements.TOAST, "Time Travel!", "Experience a temporal anomaly");
        this.addAdvancement(LocalAdvancements.PYLON, "Groove Crusader", "Acquire a cool hat");

        this.addLore(
            FlimFlamLore.heroesPrefixEntries,
            "Grunnar",
            "Hermann",
            "Sven",
            "Grarg",
            "Blarf",
            "Hans",
            "Nathan",
            "Oglaf",
            "Eric",
            "Bob",
            "Banan",
            "Alaric");
        this.addLore(
            FlimFlamLore.heroesPostfixEntries,
            "ish",
            "ilde",
            "monkeybutt",
            "son",
            "shvili",
            "berg",
            "bert",
            "us");
        this.addLore(
            FlimFlamLore.heroOptionalEntries,
            "slightly",
            "sometimes",
            "mistakenly",
            "somehow",
            "part-time");
        this.addLore(
            FlimFlamLore.heroAdjEntries,
            "insane",
            "brave",
            "smelly",
            "philosophical",
            "jumping",
            "toothless",
            "burning",
            "heroic",
            "shy",
            "narcoleptic",
            "manly",
            "girly",
            "non-euclidian",
            "euphoric",
            "misanthropic",
            "ambivalent",
            "fictional",
            "fetishist");
        this.addLore(
            FlimFlamLore.heroClassEntries,
            "babycrusher",
            "wrestler",
            "nitpicker",
            "barber",
            "anesthesiologist",
            "sharpshooter",
            "plumber",
            "insurance salesman",
            "clown",
            "empiricist",
            "defenestrator",
            "visigoth",
            "nipple twister");
        this.addLore(
            FlimFlamLore.firstNameEntries,
            "Bill",
            "Juliet",
            "Nigel",
            "Steve",
            "Parsnip",
            "Cucumber",
            "Ludwig",
            "Markus",
            "Sven",
            "Clark",
            "Carl",
            "Throatwobbler",
            "Raymond",
            "Nancy",
            "Brian",
            "Brunhilda",
            "Richard",
            "Rupert");
        this.addLore(
            FlimFlamLore.lastNameEntries,
            "Smith",
            "Weston",
            "Banana",
            "Drum",
            "Forklift",
            "Ampersand",
            "Fruitbat",
            "Fhtagn",
            "Svenson",
            "Stein",
            "Gutenabend",
            "Mangrove",
            "Bigglesworth",
            "Larch",
            "Semicolon",
            "Wurst",
            "Nixon",
            "Baden",
            "Priapus");
        this.addLore(
            FlimFlamLore.pseudonymEntries,
            "Duckie",
            "Nosepicker",
            "Snort",
            "Bomber",
            "Ouch",
            "Anvil",
            "Halfslab",
            "Radiator",
            "Barbie",
            "Biggles",
            "Income Tax",
            "Not In Face",
            "Tea Time",
            "Twerk",
            "Mutalisk",
            "Bueno",
            "Sixpack",
            "Yellow Snow");
        this.addLore(
            FlimFlamLore.namePrefixEntries,
            "Dr.",
            "Rev.",
            "Ms",
            "Mr",
            "Prof.",
            "Hon.",
            "Sgt.",
            "Cmdr.",
            "Sir",
            "Lady",
            "Comrade",
            "His Magnificence",
            "Her Holiness",
            "The Right Honourable");
        this.addLore(FlimFlamLore.middleNameEntries, "W.", "T.", "F.");
        this.addLore(
            FlimFlamLore.nameSuffixEntries,
            "M.Sc",
            "Ph.D",
            "OBE",
            "Jr.",
            "Sr.",
            "III",
            "II",
            "Esq.");
        this.addLore(
            FlimFlamLore.nameInfixEntries,
            "von",
            "de",
            "van",
            "van de",
            "de la");
        this.addLore(
            FlimFlamLore.adjective1LowercaseEntries,
            "overpowered",
            "misspelled",
            "store-brand",
            "unsettling",
            "unremarkable",
            "sleazy",
            "boring",
            "golden",
            "junky",
            "ergonomic",
            "low voltage",
            "many-angled");
        this.addLore(
            FlimFlamLore.adjective1UppercaseEntries,
            "Overpowered",
            "Misspelled",
            "Store-brand",
            "Unsettling",
            "Unremarkable",
            "Sleazy",
            "Boring",
            "Golden",
            "Junky",
            "Ergonomic",
            "Low voltage",
            "Many-angled");
        this.addLore(
            FlimFlamLore.adjective2UppercaseEntries,
            "Cursed",
            "Legendary",
            "Unique",
            "Penultimate",
            "Awesome",
            "Suboptimal",
            "Mighty",
            "Ridiculously",
            "Slightly");
        this.addLore(
            FlimFlamLore.partsEntries,
            "codpiece",
            "loincloth",
            "toothbrush",
            "dental floss",
            "eggbeater",
            "rubber chicken with a pulley in the middle",
            "shovel",
            "hammoc",
            "panties",
            "spatula",
            "fedora");
        this.addLore(
            FlimFlamLore.placeAdjectiveEntries,
            "deadly",
            "dreadful",
            "boring",
            "cheap",
            "backwater",
            "tax-free",
            "gluten-free",
            "dark",
            "evil",
            "misunderstood");
        this.addLore(
            FlimFlamLore.kingdomAdjectiveEntries,
            "loathing",
            "meat",
            "potatoes",
            "hydrocarbonates",
            "sweden",
            "slighlty unpleasant things",
            "herpaderp",
            "sobbing",
            "knitting");
        this.addLore(
            FlimFlamLore.kingdomishEntries,
            "kingdom",
            "cave",
            "gorge",
            "convention",
            "pit",
            "bazaar",
            "land");
        this.addLore(
            FlimFlamLore.mountainNameEntries,
            "lard",
            "butter",
            "rotten eggs",
            "brimstone",
            "newts",
            "doom",
            "croc",
            "flipflop");
        this.addLore(
            FlimFlamLore.hardcodedPlacesEntries,
            "dalania",
            "prussia",
            "foobaria",
            "hot dog stand",
            "abyssinia",
            "zanzibar",
            "eastasia",
            "freedonia",
            "latveria",
            "woolloomooloo",
            "breslau",
            "uzbekistan",
            "north korea",
            "lower intestine",
            "hyperborea");
        this.addLore(
            FlimFlamLore.otherPeopleEntries,
            "youtube personalities",
            "dwarves",
            "villagers",
            "elves",
            "tax collectors",
            "quality testers",
            "boring people");
        this.addLore(
            FlimFlamLore.createdEntries,
            "repurposed from",
            "originally bundled with ",
            "forged from",
            "not to be mistaken with");
        this.addLore(
            FlimFlamLore.forgottenEntries,
            "post office",
            "loo",
            "deep hole",
            "hurry");
        this.addLore(
            FlimFlamLore.originEntries,
            "stolen",
            "imagined",
            "found behind couch");
        this.addLore(
            FlimFlamLore.itemActionEntries,
            "beating",
            "bleeding",
            "winds",
            "things",
            "cooking",
            "looting",
            "scrubing",
            "backpain",
            "hernia");
        this.addLore(
            FlimFlamLore.itemTypeEntries,
            "gizmo",
            "thingmajig",
            "doodad",
            "tat",
            "thingie");
        this.addLore(
            FlimFlamLore.tauntEntries,
            "wimp",
            "noob",
            "git",
            "fool",
            "that scoundrel",
            "scumbag");
        this.addLore(
            FlimFlamLore.playerGetEntries,
            "stolen from",
            "found in",
            "bought in",
            "dug out in",
            "smuggled from");
        this.addLore(
            FlimFlamLore.randomItemsEntries,
            "bananas",
            "grapes",
            "hairpins",
            "corks",
            "shuffling",
            "squash",
            "penguins");
        this.addLore(
            FlimFlamLore.institutishEntries,
            "institute",
            "council",
            "committee");
        this.addLore(
            FlimFlamLore.foundationFirstEntries,
            "lick",
            "pick",
            "poke",
            "prod",
            "smell",
            "ring",
            "steal",
            "hug",
            "kick",
            "fwap");
        this.addLore(
            FlimFlamLore.foundationSecondEntries,
            "fish",
            "sauce",
            "leopard",
            "pick",
            "smell",
            "mayonaise",
            "steal",
            "grave",
            "derp");
        this.addLore(
            FlimFlamLore.extraEntries,
            "$1.99 each",
            "5 quids in plain wrapper",
            "Accept no substitues",
            "Made in China",
            "Batteries not included",
            "Patent pending");
        this.addMisc(FlimFlamLore.classicHeroesThe, "the");
        this.addMisc(FlimFlamLore.levelPrefix, "(lvl. ");
        this.addMisc(FlimFlamLore.levelSuffix, ")");
        this.addMisc(FlimFlamLore.noStory, "that nobody cares about");
        this.addMisc(FlimFlamLore.kingdomAnd, "and");
        this.addMisc(FlimFlamLore.kingdomOf, "of");
        this.addMisc(FlimFlamLore.mountainPrefix, "Mt.");
        this.addMisc(FlimFlamLore.actorOf, "of");
        this.addMisc(FlimFlamLore.storyIntro, "that previously belonged to");
        this.addMisc(FlimFlamLore.loanedTo, "loaned to");
        this.addMisc(FlimFlamLore.forgottenIn, "forgotten in");
        this.addMisc(FlimFlamLore.originBy, "by");
        this.addMisc(FlimFlamLore.thing, "thing");
        this.addMisc(FlimFlamLore.infinitiveSuffix, "ing");
        this.addMisc(FlimFlamLore.gizmo, "gizmo");
        this.addMisc(FlimFlamLore.itemOf, "of");
        this.addMisc(FlimFlamLore.defaultPlayer, "Frank");
        this.addMisc(FlimFlamLore.ownerBy, "by");
        this.addMisc(FlimFlamLore.named, "named");
        this.addMisc(FlimFlamLore.organizationSpecialityAnd, "and");
        this.addMisc(FlimFlamLore.universityOf, "university of");
        this.addMisc(FlimFlamLore.instituteOf, "of");
        this.addMisc(FlimFlamLore.foundationInfix, "-a-");
        this.addMisc(FlimFlamLore.foundationSuffix, "foundation");
        this.addMisc(FlimFlamLore.restoredBy, "restored by");
        this.addMisc(FlimFlamLore.recently, "Recently");
    }
}
