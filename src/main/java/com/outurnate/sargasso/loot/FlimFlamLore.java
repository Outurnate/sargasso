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
import com.outurnate.sargasso.SuperSargassoSea;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;

public class FlimFlamLore {
    public static final List<TranslatableContents> heroesPrefixEntries = map(
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
    public static final List<TranslatableContents> heroesPostfixEntries = map(
        "heroesPostfix",
        "ish",
        "ilde",
        "monkeybutt",
        "son",
        "shvili",
        "berg",
        "bert",
        "us");
    public static final List<TranslatableContents> heroOptionalEntries = map(
        "heroOptional",
        "slightly",
        "sometimes",
        "mistakenly",
        "somehow",
        "part-time");
    public static final List<TranslatableContents> heroAdjEntries = map(
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
    public static final List<TranslatableContents> heroClassEntries = map(
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
    public static final List<TranslatableContents> firstNameEntries = map(
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
    public static final List<TranslatableContents> lastNameEntries = map(
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
    public static final List<TranslatableContents> pseudonymEntries = map(
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
    public static final List<TranslatableContents> namePrefixEntries = map(
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
    public static final List<TranslatableContents> middleNameEntries = map("middleName", "W.", "T.", "F.");
    public static final List<TranslatableContents> nameSuffixEntries = map(
        "nameSuffix",
        "M.Sc",
        "Ph.D",
        "OBE",
        "Jr.",
        "Sr.",
        "III",
        "II",
        "Esq.");
    public static final List<TranslatableContents> nameInfixEntries = map(
        "nameInfix",
        "von",
        "de",
        "van",
        "van de",
        "de la");

    private static final IGenerator heroGenerator = createHeroGenerator();

    public static final IGenerator INSTANCE = createLoreGenerator();

    public static final TranslatableContents classicHeroesThe = map("classicHeroesThe", "the");

    public static final TranslatableContents levelPrefix = map("levelPrefix", "(lvl. ");

    public static final TranslatableContents levelSuffix = map("levelSuffix", ")");

    public static final List<TranslatableContents> adjective1LowercaseEntries = map(
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

    public static final List<TranslatableContents> adjective1UppercaseEntries = map(
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
    public static final List<TranslatableContents> adjective2UppercaseEntries = map(
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
    public static final List<TranslatableContents> partsEntries = map(
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

    public static final List<TranslatableContents> placeAdjectiveEntries = map(
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

    public static final List<TranslatableContents> kingdomAdjectiveEntries = map(
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
    public static final List<TranslatableContents> kingdomishEntries = map(
        "kingdomish",
        "kingdom",
        "cave",
        "gorge",
        "convention",
        "pit",
        "bazaar",
        "land");
    public static final List<TranslatableContents> mountainNameEntries = map(
        "mountainName",
        "lard",
        "butter",
        "rotten eggs",
        "brimstone",
        "newts",
        "doom",
        "croc",
        "flipflop");
    public static final List<TranslatableContents> hardcodedPlacesEntries = map(
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
    public static final List<TranslatableContents> otherPeopleEntries = map(
        "otherPeople",
        "youtube personalities",
        "dwarves",
        "villagers",
        "elves",
        "tax collectors",
        "quality testers",
        "boring people");
    public static final TranslatableContents noStory = map("noStory", "that nobody cares about");
    public static final TranslatableContents kingdomAnd = map("kingdomAnd", "and");
    public static final TranslatableContents kingdomOf = map("kingdomOf", "of");
    public static final TranslatableContents mountainPrefix = map("mountainPrefix", "Mt.");
    public static final TranslatableContents actorOf = map("kingdomOf", "of");
    public static final TranslatableContents storyIntro = map("storyIntro", "that previously belonged to");
    public static final List<TranslatableContents> createdEntries = map(
        "created",
        "repurposed from",
        "originally bundled with ",
        "forged from",
        "not to be mistaken with");
    public static final List<TranslatableContents> forgottenEntries = map(
        "forgotten",
        "post office",
        "loo",
        "deep hole",
        "hurry");
    public static final TranslatableContents loanedTo = map("loanedTo", "loaned to");
    public static final TranslatableContents forgottenIn = map("forgottenIn", "forgotten in");
    public static final List<TranslatableContents> originEntries = map(
        "origin",
        "stolen",
        "imagined",
        "found behind couch");
    public static final TranslatableContents originBy = map("originBy", "by");
    public static final List<TranslatableContents> itemActionEntries = map(
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
    public static final TranslatableContents thing = map("thing", "thing");
    public static final TranslatableContents infinitiveSuffix = map("infinitiveSuffix", "ing");
    public static final TranslatableContents gizmo = map("gizmo", "gizmo");
    public static final List<TranslatableContents> itemTypeEntries = map(
        "itemType",
        "gizmo",
        "thingmajig",
        "doodad",
        "tat",
        "thingie");
    public static final TranslatableContents itemOf = map("itemOf", "of");
    public static final List<TranslatableContents> tauntEntries = map(
        "taunt",
        "wimp",
        "noob",
        "git",
        "fool",
        "that scoundrel",
        "scumbag");
    public static final List<TranslatableContents> playerGetEntries = map(
        "playerGet",
        "stolen from",
        "found in",
        "bought in",
        "dug out in",
        "smuggled from");
    public static final TranslatableContents defaultPlayer = map("defaultPlayer", "Frank");
    public static final List<TranslatableContents> randomItemsEntries = map(
        "randomItems",
        "bananas",
        "grapes",
        "hairpins",
        "corks",
        "shuffling",
        "squash",
        "penguins");
    public static final TranslatableContents ownerBy = map("ownerBy", "by");
    public static final TranslatableContents named = map("named", "named");
    public static final TranslatableContents organizationSpecialityAnd = map(
        "organizationSpecialityAnd",
        "and");
    public static final TranslatableContents universityOf = map("universityOf", "university of");
    public static final List<TranslatableContents> institutishEntries = map(
        "institutishEntries",
        "institute",
        "council",
        "committee");
    public static final List<TranslatableContents> foundationFirstEntries = map(
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
    public static final List<TranslatableContents> foundationSecondEntries = map(
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
    public static final TranslatableContents instituteOf = map("instituteOf", "of");
    public static final TranslatableContents foundationInfix = map("foundationInfix", "-a-");
    public static final TranslatableContents foundationSuffix = map("foundationSuffix", "foundation");
    public static final TranslatableContents restoredBy = map("restoredBy", "restored by");
    public static final TranslatableContents recently = map("recently", "Recently");
    public static final List<TranslatableContents> extraEntries = map(
        "extraEntries",
        "$1.99 each",
        "5 quids in plain wrapper",
        "Accept no substitues",
        "Made in China",
        "Batteries not included",
        "Patent pending");

    private static IGenerator createHeroGenerator() {
        IGenerator heroesPrefix = alt(gen(heroesPrefixEntries));
        IGenerator heroesPostfix = alt(gen(heroesPostfixEntries));
        IGenerator heroName = word(heroesPrefix, opt(0.6f, heroesPostfix));
        IGenerator heroOptional = alt(gen(heroOptionalEntries));
        IGenerator heroAdj = alt(gen(heroAdjEntries));
        IGenerator heroClass = alt(gen(heroClassEntries));
        IGenerator classicHeroes = seq(
            heroName,
            gen(classicHeroesThe),
            seq(
                opt(0.2f, heroOptional),
                heroAdj,
                heroClass,
                opt(0.2f, word(gen(levelPrefix), range(1, 11), gen(levelSuffix)))));

        IGenerator firstName = alt(gen(firstNameEntries));
        IGenerator lastNameComponent = alt(gen(lastNameEntries));
        IGenerator lastName = alt(
            lastNameComponent,
            word(lastNameComponent, terminal("-"), lastNameComponent));
        IGenerator pseudonym = alt(gen(pseudonymEntries));
        IGenerator namePrefix = alt(gen(namePrefixEntries));
        IGenerator middleName = alt(gen(middleNameEntries));
        IGenerator nameSuffix = alt(gen(nameSuffixEntries));

        final IGenerator middleStuff = seq(
            opt(0.1f, middleName),
            opt(0.6f, word(terminal("\""), pseudonym, terminal("\""))));
        IGenerator modernHeroes = seq(
            opt(0.4f, namePrefix),
            firstName,
            middleStuff,
            opt(0.3f, alt(gen(nameInfixEntries))),
            lastName,
            opt(0.2f, word(terminal(" "), nameSuffix)));

        return alt(classicHeroes, modernHeroes);
    }

    private static IGenerator createLoreGenerator() {
        IGenerator adj1lc = alt(gen(adjective1LowercaseEntries));
        IGenerator adj1uc = alt(gen(adjective1UppercaseEntries));
        IGenerator adj2uc = alt(gen(adjective2UppercaseEntries));
        IGenerator adjs = opt(0.7f, seq(adj2uc, adj1lc), adj1uc);
        IGenerator parts = alt(gen(partsEntries));

        IGenerator placeAdj = alt(gen(placeAdjectiveEntries));
        IGenerator kingdomAdjective = alt(gen(kingdomAdjectiveEntries));
        IGenerator kingdomish = seq(
            opt(0.4f, placeAdj),
            alt(gen(kingdomishEntries)));
        IGenerator placeWithAdj = seq(
            kingdomish,
            gen(kingdomOf),
            kingdomAdjective,
            opt(0.2f, seq(gen(kingdomAnd), kingdomAdjective)));
        IGenerator mountainName = alt(gen(mountainNameEntries));
        IGenerator mountain = seq(opt(0.6f, placeAdj), gen(mountainPrefix), mountainName);
        final IGenerator hardcodedPlaces = alt(gen(hardcodedPlacesEntries));
        IGenerator places = alt(placeWithAdj, mountain, hardcodedPlaces);

        IGenerator otherPeople = alt(gen(otherPeopleEntries));
        IGenerator actor = seq(alt(heroGenerator, seq(otherPeople, gen(actorOf), places)));

        IGenerator story = alt(gen(noStory), seq(gen(storyIntro), actor));
        IGenerator epicLoot = seq(opt(0.5f, adjs), parts, story);

        IGenerator created = seq(
            alt(gen(createdEntries)),
            epicLoot);
        IGenerator loaned = seq(gen(loanedTo), actor);
        IGenerator forgotten = seq(gen(forgottenIn), alt(gen(forgottenEntries)));
        IGenerator origin = alt(
            created,
            seq(
                alt(
                    Stream.concat(
                        Arrays.stream(gen(originEntries)),
                        Stream.of(loaned, forgotten))
                        .toArray(IGenerator[]::new)),
                gen(originBy),
                heroGenerator));

        IGenerator itemAction = alt(
            Stream.concat(
                Arrays.stream(gen(itemActionEntries)),
                Stream.of(word(sub("item", thing), gen(infinitiveSuffix)))).toArray(IGenerator[]::new));
        IGenerator itemType = alt(
            sub("item", gizmo),
            alt(gen(itemTypeEntries)));
        IGenerator item = seq(opt(0.9f, adjs), itemType, opt(0.9f, seq(gen(itemOf), itemAction)));
        IGenerator fullItem = seq(item, opt(0.05f, seq(terminal("™️"))));

        IGenerator taunt = alt(gen(tauntEntries));
        IGenerator playerGet = seq(
            alt(gen(playerGetEntries)),
            places);
        IGenerator ownerInfo = seq(
            playerGet,
            gen(ownerBy),
            opt(0.3f, seq(taunt, gen(named))),
            sub("player", defaultPlayer));

        IGenerator randomItems = alt(
            gen(randomItemsEntries));
        IGenerator organizationSpeciality = alt(
            randomItems,
            seq(randomItems, gen(organizationSpecialityAnd), randomItems));

        IGenerator university = seq(gen(universityOf), alt(hardcodedPlaces, organizationSpeciality));

        IGenerator institutish = alt(gen(institutishEntries));
        IGenerator institute = seq(institutish, gen(instituteOf), organizationSpeciality);

        IGenerator foundationFirst = alt(gen(foundationFirstEntries));
        IGenerator foundationSecond = alt(gen(foundationSecondEntries));
        IGenerator foundation = seq(
            word(foundationFirst, gen(foundationInfix), foundationSecond),
            gen(foundationSuffix));

        IGenerator organization = alt(university, foundation, institute);

        IGenerator restoredInfo = seq(gen(restoredBy), organization);
        IGenerator recent = seq(gen(recently), alt(ownerInfo, restoredInfo));

        IGenerator extra = alt(gen(extraEntries));
        return word(
            fullItem,
            opt(0.5f, seq(terminal(","), origin)),
            opt(0.5f, seq(terminal("."), recent)),
            opt(0.2f, seq(terminal("."), extra)));
    }

    private static IGenerator[] gen(List<TranslatableContents> def) {
        return def.stream().map(FlimFlamLore::gen).toArray(IGenerator[]::new);
    }

    private static IGenerator gen(TranslatableContents def) {
        return IGenerator.terminal(MutableComponent.create(def));
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
