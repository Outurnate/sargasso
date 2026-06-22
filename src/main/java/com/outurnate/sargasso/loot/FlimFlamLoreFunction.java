/* (C)2026 */
package com.outurnate.sargasso.loot;

import static com.outurnate.sargasso.loot.IGenerator.alt;
import static com.outurnate.sargasso.loot.IGenerator.opt;
import static com.outurnate.sargasso.loot.IGenerator.range;
import static com.outurnate.sargasso.loot.IGenerator.seq;
import static com.outurnate.sargasso.loot.IGenerator.sub;
import static com.outurnate.sargasso.loot.IGenerator.word;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.slf4j.Logger;

public class FlimFlamLoreFunction extends LootItemConditionalFunction {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final MapCodec<FlimFlamLoreFunction> MAP_CODEC = RecordCodecBuilder.mapCodec(
        i -> commonFields(i)
            .and(LootContext.EntityTarget.CODEC.optionalFieldOf("entity").forGetter(f -> f.resolutionContext))
            .apply(i, FlimFlamLoreFunction::new));
    private static final IGenerator heroGenerator = createHeroGenerator();

    private static final IGenerator loreGenerator = createLoreGenerator();

    private static IGenerator createHeroGenerator() {
        IGenerator heroesPrefix = alt(
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
        IGenerator heroesPostfix = alt("ish", "ilde", "monkeybutt", "son", "shvili", "berg", "bert", "us");
        IGenerator heroName = word(heroesPrefix, opt(0.6f, heroesPostfix));
        IGenerator heroOptional = alt("slightly", "sometimes", "mistakenly", "somehow", "part-time");
        IGenerator heroAdj = alt(
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
        IGenerator heroClass = alt(
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
        IGenerator classicHeroes = seq(
            heroName,
            "the",
            seq(opt(0.2f, heroOptional), heroAdj, heroClass, opt(0.2f, word("(lvl. ", range(1, 11), ")"))));

        IGenerator firstName = alt(
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
        IGenerator lastNameComponent = alt(
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
        IGenerator lastName = alt(lastNameComponent, word(lastNameComponent, "-", lastNameComponent));
        IGenerator pseudonym = alt(
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
        IGenerator namePrefix = alt(
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
        IGenerator middleName = alt("W.", "T.", "F.");
        IGenerator nameSuffix = alt("M.Sc", "Ph.D", "OBE", "Jr.", "Sr.", "III", "II", "Esq.");

        final IGenerator middleStuff = seq(opt(0.1f, middleName), opt(0.6f, word("\"", pseudonym, "\"")));
        IGenerator modernHeroes = seq(
            opt(0.4f, namePrefix),
            firstName,
            middleStuff,
            opt(0.3f, alt("von", "de", "van", "van de", "de la")),
            lastName,
            opt(0.2f, word(" ", nameSuffix)));

        return alt(classicHeroes, modernHeroes);
    }

    private static IGenerator createLoreGenerator() {
        IGenerator adj1lc = alt(
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
        IGenerator adj1uc = alt(
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
        IGenerator adj2uc = alt(
            "Cursed",
            "Legendary",
            "Unique",
            "Penultimate",
            "Awesome",
            "Suboptimal",
            "Mighty",
            "Ridiculously",
            "Slightly");
        IGenerator adjs = opt(0.7f, seq(adj2uc, adj1lc), adj1uc);
        IGenerator parts = alt(
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

        IGenerator placeAdj = alt(
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
        IGenerator kingdomAdjective = alt(
            "loathing",
            "meat",
            "potatoes",
            "hydrocarbonates",
            "sweden",
            "slighlty unpleasant things",
            "herpaderp",
            "sobbing",
            "knitting");
        IGenerator kingdomish = seq(
            opt(0.4f, placeAdj),
            alt("kingdom", "cave", "gorge", "convention", "pit", "bazaar", "land"));
        IGenerator placeWithAdj = seq(
            kingdomish,
            "of",
            kingdomAdjective,
            opt(0.2f, seq("and", kingdomAdjective)));
        IGenerator mountainName = alt(
            "lard",
            "butter",
            "rotten eggs",
            "brimstone",
            "newts",
            "doom",
            "croc",
            "flipflop");
        IGenerator mountain = seq(opt(0.6f, placeAdj), "Mt.", mountainName);
        final IGenerator hardcodedPlaces = alt(
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
        IGenerator places = alt(placeWithAdj, mountain, hardcodedPlaces);

        IGenerator otherPeople = alt(
            "youtube personalities",
            "dwarves",
            "villagers",
            "elves",
            "tax collectors",
            "quality testers",
            "boring people");
        IGenerator actor = seq(alt(heroGenerator, seq(otherPeople, "of", places)));

        IGenerator story = alt("that nobody cares about", seq("that previously belonged to", actor));
        IGenerator epicLoot = seq(opt(0.5f, adjs), parts, story);

        IGenerator created = seq(
            alt("repurposed from", "originally bundled with ", "forged from", "not to be mistaken with"),
            epicLoot);
        IGenerator loaned = seq("loaned to", actor);
        IGenerator forgotten = seq("forgotten in", alt("post office", "loo", "deep hole", "hurry"));
        IGenerator origin = alt(
            created,
            seq(alt("stolen", loaned, "imagined", forgotten, "found behind couch"), "by", heroGenerator));

        IGenerator itemModifier = alt("replica of");
        IGenerator itemAction = alt(
            "beating",
            "bleeding",
            "winds",
            "things",
            word(sub("item", Component.literal("thing")), "ing"),
            "cooking",
            "looting",
            "scrubing",
            "backpain",
            "hernia");
        IGenerator itemType = alt(
            sub("item", Component.literal("gizmo")),
            alt("gizmo", "thingmajig", "doodad", "tat", "thingie"));
        IGenerator item = seq(opt(0.9f, adjs), itemType, opt(0.9f, seq("of", itemAction)));
        IGenerator fullItem = seq(opt(0.1f, itemModifier), item, opt(0.05f, seq("(TM)")));

        IGenerator taunt = alt("wimp", "noob", "git", "fool", "that scoundrel", "scumbag");
        IGenerator playerGet = seq(
            alt("stolen from", "found in", "bought in", "dug out in", "smuggled from"),
            places);
        IGenerator ownerInfo = seq(
            playerGet,
            "by",
            opt(0.3f, seq(taunt, "named")),
            sub("player", Component.literal("Frank")));

        IGenerator randomItems = alt(
            "bananas",
            "grapes",
            "hairpins",
            "corks",
            "shuffling",
            "squash",
            "penguins");
        IGenerator organizationSpeciality = alt(randomItems, seq(randomItems, "and", randomItems));

        IGenerator university = seq("university of", alt(hardcodedPlaces, organizationSpeciality));

        IGenerator institutish = alt("institute", "council", "committee");
        IGenerator institute = seq(institutish, "of", organizationSpeciality);

        IGenerator foundationFirst = alt(
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
        IGenerator foundationSecond = alt(
            "fish",
            "sauce",
            "leopard",
            "pick",
            "smell",
            "mayonaise",
            "steal",
            "grave",
            "derp");
        IGenerator foundation = seq(word(foundationFirst, "-a-", foundationSecond), "foundation");

        IGenerator organization = alt(university, foundation, institute);

        IGenerator restoredInfo = seq("restored by", organization);
        IGenerator recent = seq("Recently", alt(ownerInfo, restoredInfo));

        IGenerator extra = alt(
            "$1.99 each",
            "5 quids in plain wrapper",
            "Accept no substitues",
            "Made in China",
            "Batteries not included",
            "Patent pending");
        return word(
            fullItem,
            opt(0.5f, seq(",", origin)),
            opt(0.5f, seq(".", recent)),
            opt(0.2f, seq(".", extra)));
    }

    private static Component generate(
        RandomSource random,
        IGenerator generator,
        Map<String, Component> params) {
        return IGenerator.flatten(generator.generate(random, params));
    }

    private final Optional<LootContext.EntityTarget> resolutionContext;

    protected FlimFlamLoreFunction(
        List<LootItemCondition> predicates,
        Optional<LootContext.EntityTarget> resolutionContext) {
        super(predicates);
        this.resolutionContext = resolutionContext;
    }

    @Override
    public MapCodec<FlimFlamLoreFunction> codec() {
        return MAP_CODEC;
    }

    @Override
    public ItemStack run(ItemStack itemStack, LootContext context) {
        try {
            Map<String, Component> params = Maps.newHashMap();
            if (context.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof Player player) {
                params.put("player", player.getName());
            }
            params.put("item", itemStack.getItemName());
            itemStack.update(
                DataComponents.LORE,
                ItemLore.EMPTY,
                oldLore -> new ItemLore(
                    this.updateLore(
                        oldLore,
                        Arrays.asList(generate(context.getRandom(), loreGenerator, params)),
                        context)));
            return itemStack;
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw e;
        }
    }

    private List<Component> updateLore(
        @Nullable ItemLore itemLore,
        List<Component> lore,
        LootContext context) {
        if (itemLore == null && lore.isEmpty()) {
            return List.of();
        } else {
            UnaryOperator<Component> resolver = SetNameFunction
                .createResolver(context, this.resolutionContext.orElse(null));
            List<Component> resolvedLines = lore.stream().map(resolver).toList();
            return resolvedLines;
        }
    }
}
