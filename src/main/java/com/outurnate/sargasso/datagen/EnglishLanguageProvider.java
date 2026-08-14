/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.loot.FlimFlamLore;
import com.outurnate.sargasso.loot.LoreSet;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalDamageTypes;
import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.registry.LocalMobEffects;
import com.outurnate.sargasso.registry.LocalPotions;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EnglishLanguageProvider extends LanguageProvider {
    public EnglishLanguageProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, SuperSargassoSea.MODID, "en_us");
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

    private void addLore(TranslatableContents lore, String value) {
        this.add(lore.getKey(), value);
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

        this.add("sound.sargasso.cream_apply", "Bedrock cream applied");
        this.add("sound.sargasso.glitch_teleport", "Glitched");
        this.add("sound.sargasso.toaster", "Time travel happened");
        this.add("sound.sargasso.pylon", "Pylon equipped");
        this.add("sound.sargasso.zap", "Electric arc");
        this.add("jukebox_song.sargasso.unchecked", "Tarn Kazsuhara - Unchecked (Tapeless Mix)");
        this.addItem(LocalItems.RECORD_UNCHECKED, "Music Disc");

        this.add("item.minecraft.potion.effect.fizzy_lifting", "Fizzy Lifting Drink");
        this.add("sargasso.lore.fizzy_lifting", "Burp, Charlie! You've got to burp!");

        this.add("item.minecraft.potion.effect.bhj", "Bone Hurting Juice");
        this.add("sargasso.lore.bhj", "oof ouch my bones");

        this.add("sargasso.lore.george", "The helm of Sire George the shrunk, a legendary knight");
        this.add("sargasso.lore.george_name", "Sir George's Helm");

        this.add("sargasso.lore.liar_pants", "Liar's Pants");

        this.add("sargasso.lore.rocket_boots", "Dwarven Rocket Boots");

        this.add("sargasso.lore.no_toast", "Nothing happens.");
        this.add(
            "sargasso.lore.toast",
            "You have a strange feeling that the bread you ate %s was (and always has been) toast.");

        this.addBlock(LocalBlocks.FLOTSAM, "Flotsam");
        this.addBlock(LocalBlocks.DEBRIS, "Debris");
        this.addBlock(LocalBlocks.CREAMY_BEDROCK, "Debris");
        this.addBlock(LocalBlocks.GLITCH, "Glitch in Reality");
        this.addBlock(LocalBlocks.PORTAL, "Portal");
        this.addBlock(LocalBlocks.TOASTER, "Chronometric Flux Toaster");
        this.addBlock(LocalBlocks.PYLON, "Pylon");
        this.addBlock(LocalBlocks.SHOCK_THERAPIST, "Shock Therapist");
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

        this.add(LocalMobEffects.HEAD_EXPLOSION.value(), "Impending Head Explosion");
        this.add(
            "chat." + SuperSargassoSea.MODID + ".voltmeter",
            "The potatoes in your body current produce %s FE/t");

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

        for (Entry<String, String> entry : LocalAdvancementProvider.getEnglishTranslations().entrySet()) {
            this.add(entry.getKey(), entry.getValue());
        }

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
        this.addLore(FlimFlamLore.classicHeroesThe, "the");
        this.addLore(FlimFlamLore.levelPrefix, "(lvl. ");
        this.addLore(FlimFlamLore.levelSuffix, ")");
        this.addLore(FlimFlamLore.noStory, "that nobody cares about");
        this.addLore(FlimFlamLore.kingdomAnd, "and");
        this.addLore(FlimFlamLore.kingdomOf, "of");
        this.addLore(FlimFlamLore.mountainPrefix, "Mt.");
        this.addLore(FlimFlamLore.actorOf, "of");
        this.addLore(FlimFlamLore.storyIntro, "that previously belonged to");
        this.addLore(FlimFlamLore.loanedTo, "loaned to");
        this.addLore(FlimFlamLore.forgottenIn, "forgotten in");
        this.addLore(FlimFlamLore.originBy, "by");
        this.addLore(FlimFlamLore.thing, "thing");
        this.addLore(FlimFlamLore.infinitiveSuffix, "ing");
        this.addLore(FlimFlamLore.gizmo, "gizmo");
        this.addLore(FlimFlamLore.itemOf, "of");
        this.addLore(FlimFlamLore.defaultPlayer, "Frank");
        this.addLore(FlimFlamLore.ownerBy, "by");
        this.addLore(FlimFlamLore.named, "named");
        this.addLore(FlimFlamLore.organizationSpecialityAnd, "and");
        this.addLore(FlimFlamLore.universityOf, "university of");
        this.addLore(FlimFlamLore.instituteOf, "of");
        this.addLore(FlimFlamLore.foundationInfix, "-a-");
        this.addLore(FlimFlamLore.foundationSuffix, "foundation");
        this.addLore(FlimFlamLore.restoredBy, "restored by");
        this.addLore(FlimFlamLore.recently, "Recently");
    }
}
