/* (C)2026 */
package com.outurnate.sargasso.loot;

import static com.outurnate.sargasso.loot.IGenerator.alt;
import static com.outurnate.sargasso.loot.IGenerator.opt;
import static com.outurnate.sargasso.loot.IGenerator.range;
import static com.outurnate.sargasso.loot.IGenerator.seq;
import static com.outurnate.sargasso.loot.IGenerator.sub;
import static com.outurnate.sargasso.loot.IGenerator.terminal;
import static com.outurnate.sargasso.loot.IGenerator.word;

import com.google.common.collect.Streams;
import com.mojang.logging.LogUtils;
import com.outurnate.sargasso.SuperSargassoSea;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.slf4j.Logger;

public class FlimFlamLore {
    public static final Logger LOGGER = LogUtils.getLogger();

    static {
        heroesPrefixEntries = map(
            "heroesPrefix",
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
        heroesPostfixEntries = map(
            "heroesPostfix",
            "ish",
            "ilde",
            "monkeybutt",
            "son",
            "shvili",
            "berg",
            "bert",
            "us");
        heroOptionalEntries = map(
            "heroOptional",
            "slightly",
            "sometimes",
            "mistakenly",
            "somehow",
            "part-time");
        heroAdjEntries = map(
            "heroAdj",
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
        heroClassEntries = map(
            "heroClass",
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
        firstNameEntries = map(
            "firstName",
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
        lastNameEntries = map(
            "lastName",
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
        pseudonymEntries = map(
            "pseudonym",
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
        namePrefixEntries = map(
            "namePrefix",
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
        middleNameEntries = map("middleName", "W.", "T.", "F.");
        nameSuffixEntries = map(
            "nameSuffix",
            "M.Sc",
            "Ph.D",
            "OBE",
            "Jr.",
            "Sr.",
            "III",
            "II",
            "Esq.");
        nameInfixEntries = map(
            "nameInfix",
            "von",
            "de",
            "van",
            "van de",
            "de la");
        classicHeroesThe = map("classicHeroesThe", "the");
        levelPrefix = map("levelPrefix", "(lvl. ");
        levelSuffix = map("levelSuffix", ")");
        adjective1LowercaseEntries = map(
            "adj1lc",
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
        adjective1UppercaseEntries = map(
            "adj1uc",
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
        adjective2UppercaseEntries = map(
            "adj2uc",
            "Cursed",
            "Legendary",
            "Unique",
            "Penultimate",
            "Awesome",
            "Suboptimal",
            "Mighty",
            "Ridiculously",
            "Slightly");
        partsEntries = map(
            "parts",
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
        placeAdjectiveEntries = map(
            "placeAdjective",
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
        kingdomAdjectiveEntries = map(
            "kingdomAdjective",
            "loathing",
            "meat",
            "potatoes",
            "hydrocarbonates",
            "sweden",
            "slighlty unpleasant things",
            "herpaderp",
            "sobbing",
            "knitting");
        kingdomishEntries = map(
            "kingdomish",
            "kingdom",
            "cave",
            "gorge",
            "convention",
            "pit",
            "bazaar",
            "land");
        mountainNameEntries = map(
            "mountainName",
            "lard",
            "butter",
            "rotten eggs",
            "brimstone",
            "newts",
            "doom",
            "croc",
            "flipflop");
        hardcodedPlacesEntries = map(
            "hardcodedPlaces",
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
        otherPeopleEntries = map(
            "otherPeople",
            "youtube personalities",
            "dwarves",
            "villagers",
            "elves",
            "tax collectors",
            "quality testers",
            "boring people");
        noStory = map("noStory", "that nobody cares about");
        kingdomAnd = map("kingdomAnd", "and");
        kingdomOf = map("kingdomOf", "of");
        mountainPrefix = map("mountainPrefix", "Mt.");
        actorOf = map("actorOf", "of");
        storyIntro = map("storyIntro", "that previously belonged to");
        createdEntries = map(
            "created",
            "repurposed from",
            "originally bundled with ",
            "forged from",
            "not to be mistaken with");
        forgottenEntries = map(
            "forgotten",
            "post office",
            "loo",
            "deep hole",
            "hurry");
        loanedTo = map("loanedTo", "loaned to");
        forgottenIn = map("forgottenIn", "forgotten in");
        originEntries = map(
            "origin",
            "stolen",
            "imagined",
            "found behind couch");
        originBy = map("originBy", "by");
        itemActionEntries = map(
            "itemAction",
            "beating",
            "bleeding",
            "winds",
            "things",
            "cooking",
            "looting",
            "scrubing",
            "backpain",
            "hernia");
        thing = map("thing", "thing");
        infinitiveSuffix = map("infinitiveSuffix", "ing");
        gizmo = map("gizmo", "gizmo");
        itemTypeEntries = map(
            "itemType",
            "gizmo",
            "thingmajig",
            "doodad",
            "tat",
            "thingie");
        itemOf = map("itemOf", "of");
        tauntEntries = map(
            "taunt",
            "wimp",
            "noob",
            "git",
            "fool",
            "that scoundrel",
            "scumbag");
        playerGetEntries = map(
            "playerGet",
            "stolen from",
            "found in",
            "bought in",
            "dug out in",
            "smuggled from");
        defaultPlayer = map("defaultPlayer", "Frank");
        randomItemsEntries = map(
            "randomItems",
            "bananas",
            "grapes",
            "hairpins",
            "corks",
            "shuffling",
            "squash",
            "penguins");
        ownerBy = map("ownerBy", "by");
        named = map("named", "named");
        organizationSpecialityAnd = map(
            "organizationSpecialityAnd",
            "and");
        universityOf = map("universityOf", "university of");
        institutishEntries = map(
            "institutishEntries",
            "institute",
            "council",
            "committee");
        foundationFirstEntries = map(
            "foundationFirst",
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
        foundationSecondEntries = map(
            "foundationSecond",
            "fish",
            "sauce",
            "leopard",
            "pick",
            "smell",
            "mayonaise",
            "steal",
            "grave",
            "derp");
        instituteOf = map("instituteOf", "of");
        foundationInfix = map("foundationInfix", "-a-");
        foundationSuffix = map("foundationSuffix", "foundation");
        restoredBy = map("restoredBy", "restored by");
        recently = map("recently", "Recently");
        extraEntries = map(
            "extraEntries",
            "$1.99 each",
            "5 quids in plain wrapper",
            "Accept no substitues",
            "Made in China",
            "Batteries not included",
            "Patent pending");

        heroGenerator = createHeroGenerator();
        INSTANCE = createLoreGenerator();
    }
    private static final List<TranslatableContents> heroesPrefixEntries;
    private static final List<TranslatableContents> heroesPostfixEntries;
    private static final List<TranslatableContents> heroOptionalEntries;
    private static final List<TranslatableContents> heroAdjEntries;
    private static final List<TranslatableContents> heroClassEntries;
    private static final List<TranslatableContents> firstNameEntries;
    private static final List<TranslatableContents> lastNameEntries;
    private static final List<TranslatableContents> pseudonymEntries;
    private static final List<TranslatableContents> namePrefixEntries;
    private static final List<TranslatableContents> middleNameEntries;
    private static final List<TranslatableContents> nameSuffixEntries;
    private static final List<TranslatableContents> nameInfixEntries;
    private static final List<TranslatableContents> adjective1LowercaseEntries;
    private static final List<TranslatableContents> adjective1UppercaseEntries;
    private static final List<TranslatableContents> adjective2UppercaseEntries;
    private static final List<TranslatableContents> partsEntries;
    private static final List<TranslatableContents> placeAdjectiveEntries;
    private static final List<TranslatableContents> kingdomAdjectiveEntries;
    private static final List<TranslatableContents> kingdomishEntries;
    private static final List<TranslatableContents> mountainNameEntries;
    private static final List<TranslatableContents> hardcodedPlacesEntries;
    private static final List<TranslatableContents> otherPeopleEntries;
    private static final List<TranslatableContents> createdEntries;
    private static final List<TranslatableContents> forgottenEntries;
    private static final List<TranslatableContents> originEntries;
    private static final List<TranslatableContents> itemActionEntries;
    private static final List<TranslatableContents> itemTypeEntries;
    private static final List<TranslatableContents> tauntEntries;
    private static final List<TranslatableContents> playerGetEntries;
    private static final List<TranslatableContents> randomItemsEntries;
    private static final List<TranslatableContents> institutishEntries;
    private static final List<TranslatableContents> foundationFirstEntries;
    private static final List<TranslatableContents> foundationSecondEntries;
    private static final List<TranslatableContents> extraEntries;
    private static final TranslatableContents classicHeroesThe;
    private static final TranslatableContents levelPrefix;
    private static final TranslatableContents levelSuffix;
    private static final TranslatableContents noStory;
    private static final TranslatableContents kingdomAnd;
    private static final TranslatableContents kingdomOf;
    private static final TranslatableContents mountainPrefix;
    private static final TranslatableContents actorOf;
    private static final TranslatableContents storyIntro;
    private static final TranslatableContents loanedTo;
    private static final TranslatableContents forgottenIn;
    private static final TranslatableContents originBy;
    private static final TranslatableContents thing;
    private static final TranslatableContents infinitiveSuffix;
    private static final TranslatableContents gizmo;
    private static final TranslatableContents itemOf;
    private static final TranslatableContents defaultPlayer;
    private static final TranslatableContents ownerBy;
    private static final TranslatableContents named;
    private static final TranslatableContents organizationSpecialityAnd;
    private static final TranslatableContents universityOf;
    private static final TranslatableContents instituteOf;
    private static final TranslatableContents foundationInfix;
    private static final TranslatableContents foundationSuffix;
    private static final TranslatableContents restoredBy;
    private static final TranslatableContents recently;
    private static final IGenerator heroGenerator;
    public static final IGenerator INSTANCE;

    private static IGenerator createHeroGenerator() {
        IGenerator heroesPrefix = alt(terminal(heroesPrefixEntries));
        IGenerator heroesPostfix = alt(terminal(heroesPostfixEntries));
        IGenerator heroName = word(heroesPrefix, opt(0.6f, heroesPostfix));
        IGenerator heroOptional = alt(terminal(heroOptionalEntries));
        IGenerator heroAdj = alt(terminal(heroAdjEntries));
        IGenerator heroClass = alt(terminal(heroClassEntries));
        IGenerator classicHeroes = seq(
            heroName,
            terminal(classicHeroesThe),
            seq(
                opt(0.2f, heroOptional),
                heroAdj,
                heroClass,
                opt(0.2f, word(terminal(levelPrefix), range(1, 11), terminal(levelSuffix)))));

        IGenerator firstName = alt(terminal(firstNameEntries));
        IGenerator lastNameComponent = alt(terminal(lastNameEntries));
        IGenerator lastName = alt(
            lastNameComponent,
            word(lastNameComponent, terminal("-"), lastNameComponent));
        IGenerator pseudonym = alt(terminal(pseudonymEntries));
        IGenerator namePrefix = alt(terminal(namePrefixEntries));
        IGenerator middleName = alt(terminal(middleNameEntries));
        IGenerator nameSuffix = alt(terminal(nameSuffixEntries));

        final IGenerator middleStuff = seq(
            opt(0.1f, middleName),
            opt(0.6f, word(terminal("\""), pseudonym, terminal("\""))));
        IGenerator modernHeroes = seq(
            opt(0.4f, namePrefix),
            firstName,
            middleStuff,
            opt(0.3f, alt(terminal(nameInfixEntries))),
            lastName,
            opt(0.2f, word(terminal(" "), nameSuffix)));

        return alt(classicHeroes, modernHeroes);
    }

    private static IGenerator createLoreGenerator() {
        IGenerator adj1lc = alt(terminal(adjective1LowercaseEntries));
        IGenerator adj1uc = alt(terminal(adjective1UppercaseEntries));
        IGenerator adj2uc = alt(terminal(adjective2UppercaseEntries));
        IGenerator adjs = opt(0.7f, seq(adj2uc, adj1lc), adj1uc);
        IGenerator parts = alt(terminal(partsEntries));

        IGenerator placeAdj = alt(terminal(placeAdjectiveEntries));
        IGenerator kingdomAdjective = alt(terminal(kingdomAdjectiveEntries));
        IGenerator kingdomish = seq(
            opt(0.4f, placeAdj),
            alt(terminal(kingdomishEntries)));
        IGenerator placeWithAdj = seq(
            kingdomish,
            terminal(kingdomOf),
            kingdomAdjective,
            opt(0.2f, seq(terminal(kingdomAnd), kingdomAdjective)));
        IGenerator mountainName = alt(terminal(mountainNameEntries));
        IGenerator mountain = seq(opt(0.6f, placeAdj), terminal(mountainPrefix), mountainName);
        final IGenerator hardcodedPlaces = alt(terminal(hardcodedPlacesEntries));
        IGenerator places = alt(placeWithAdj, mountain, hardcodedPlaces);

        IGenerator otherPeople = alt(terminal(otherPeopleEntries));
        IGenerator actor = seq(alt(heroGenerator, seq(otherPeople, terminal(actorOf), places)));

        IGenerator story = alt(terminal(noStory), seq(terminal(storyIntro), actor));
        IGenerator epicLoot = seq(opt(0.5f, adjs), parts, story);

        IGenerator created = seq(
            alt(terminal(createdEntries)),
            epicLoot);
        IGenerator loaned = seq(terminal(loanedTo), actor);
        IGenerator forgotten = seq(terminal(forgottenIn), alt(terminal(forgottenEntries)));
        IGenerator origin = alt(
            created,
            seq(
                alt(
                    Stream.concat(
                        Arrays.stream(terminal(originEntries)),
                        Stream.of(loaned, forgotten))
                        .toArray(IGenerator[]::new)),
                terminal(originBy),
                heroGenerator));

        IGenerator itemAction = alt(
            Stream.concat(
                Arrays.stream(terminal(itemActionEntries)),
                Stream.of(word(sub("item", thing), terminal(infinitiveSuffix)))).toArray(IGenerator[]::new));
        IGenerator itemType = alt(
            sub("item", gizmo),
            alt(terminal(itemTypeEntries)));
        IGenerator item = seq(opt(0.9f, adjs), itemType, opt(0.9f, seq(terminal(itemOf), itemAction)));
        IGenerator fullItem = seq(item, opt(0.05f, seq(terminal("™️"))));

        IGenerator taunt = alt(terminal(tauntEntries));
        IGenerator playerGet = seq(
            alt(terminal(playerGetEntries)),
            places);
        IGenerator ownerInfo = seq(
            playerGet,
            terminal(ownerBy),
            opt(0.3f, seq(taunt, terminal(named))),
            sub("player", defaultPlayer));

        IGenerator randomItems = alt(
            terminal(randomItemsEntries));
        IGenerator organizationSpeciality = alt(
            randomItems,
            seq(randomItems, terminal(organizationSpecialityAnd), randomItems));

        IGenerator university = seq(terminal(universityOf), alt(hardcodedPlaces, organizationSpeciality));

        IGenerator institutish = alt(terminal(institutishEntries));
        IGenerator institute = seq(institutish, terminal(instituteOf), organizationSpeciality);

        IGenerator foundationFirst = alt(terminal(foundationFirstEntries));
        IGenerator foundationSecond = alt(terminal(foundationSecondEntries));
        IGenerator foundation = seq(
            word(foundationFirst, terminal(foundationInfix), foundationSecond),
            terminal(foundationSuffix));

        IGenerator organization = alt(university, foundation, institute);

        IGenerator restoredInfo = seq(terminal(restoredBy), organization);
        IGenerator recent = seq(terminal(recently), alt(ownerInfo, restoredInfo));

        IGenerator extra = alt(terminal(extraEntries));
        return word(
            fullItem,
            opt(0.5f, seq(terminal(","), origin)),
            opt(0.5f, seq(terminal("."), recent)),
            opt(0.2f, seq(terminal("."), extra)));
    }

    public static void dataGen(LanguageProvider prov) {
        Stream.of(
            heroesPrefixEntries.stream(),
            heroesPostfixEntries.stream(),
            heroOptionalEntries.stream(),
            heroAdjEntries.stream(),
            heroClassEntries.stream(),
            firstNameEntries.stream(),
            lastNameEntries.stream(),
            pseudonymEntries.stream(),
            namePrefixEntries.stream(),
            middleNameEntries.stream(),
            nameSuffixEntries.stream(),
            nameInfixEntries.stream(),
            adjective1LowercaseEntries.stream(),
            adjective1UppercaseEntries.stream(),
            adjective2UppercaseEntries.stream(),
            partsEntries.stream(),
            placeAdjectiveEntries.stream(),
            kingdomAdjectiveEntries.stream(),
            kingdomishEntries.stream(),
            mountainNameEntries.stream(),
            hardcodedPlacesEntries.stream(),
            otherPeopleEntries.stream(),
            createdEntries.stream(),
            forgottenEntries.stream(),
            originEntries.stream(),
            itemActionEntries.stream(),
            itemTypeEntries.stream(),
            tauntEntries.stream(),
            playerGetEntries.stream(),
            randomItemsEntries.stream(),
            institutishEntries.stream(),
            foundationFirstEntries.stream(),
            foundationSecondEntries.stream(),
            extraEntries.stream(),
            Stream.of(
                classicHeroesThe,
                levelPrefix,
                levelSuffix,
                noStory,
                kingdomAnd,
                kingdomOf,
                mountainPrefix,
                actorOf,
                storyIntro,
                loanedTo,
                forgottenIn,
                originBy,
                thing,
                infinitiveSuffix,
                gizmo,
                itemOf,
                defaultPlayer,
                ownerBy,
                named,
                organizationSpecialityAnd,
                universityOf,
                instituteOf,
                foundationInfix,
                foundationSuffix,
                restoredBy,
                recently))
            .flatMap(Function.identity()).forEach(entry -> {
                prov.add(entry.getKey(), entry.getFallback());
            });
    }

    private static List<TranslatableContents> map(String setName, String... values) {
        return Streams.mapWithIndex(
            Arrays.stream(values),
            (value, i) -> new TranslatableContents(
                "lore." + SuperSargassoSea.MODID + "." + setName + "." + String.valueOf(i),
                value,
                TranslatableContents.NO_ARGS))
            .toList();
    }

    private static TranslatableContents map(String setName, String value) {
        return new TranslatableContents(
            "lore." + SuperSargassoSea.MODID + "." + setName,
            value,
            TranslatableContents.NO_ARGS);
    }
}
