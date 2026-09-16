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
package com.outurnate.sargasso.loot;

import static com.outurnate.sargasso.loot.IGenerator.alt;
import static com.outurnate.sargasso.loot.IGenerator.opt;
import static com.outurnate.sargasso.loot.IGenerator.range;
import static com.outurnate.sargasso.loot.IGenerator.seq;
import static com.outurnate.sargasso.loot.IGenerator.sub;
import static com.outurnate.sargasso.loot.IGenerator.terminal;
import static com.outurnate.sargasso.loot.IGenerator.word;

import com.outurnate.sargasso.SuperSargassoSea;
import net.minecraft.network.chat.contents.TranslatableContents;

// concept originally from OpenBlocks
// https://github.com/OpenMods/OpenBlocks
// ported under MIT license
public class FlimFlamLore {
	public static final LoreSet heroesPrefixEntries = new LoreSet("heroesPrefix", 12);
	public static final LoreSet heroesPostfixEntries = new LoreSet("heroesPostfix", 8);
	public static final LoreSet heroOptionalEntries = new LoreSet("heroOptional", 5);
	public static final LoreSet heroAdjEntries = new LoreSet("heroAdj", 18);
	public static final LoreSet heroClassEntries = new LoreSet("heroClass", 13);
	public static final LoreSet firstNameEntries = new LoreSet("firstName", 18);
	public static final LoreSet lastNameEntries = new LoreSet("lastName", 19);
	public static final LoreSet pseudonymEntries = new LoreSet("pseudonym", 18);
	public static final LoreSet namePrefixEntries = new LoreSet("namePrefix", 14);
	public static final LoreSet middleNameEntries = new LoreSet("middleName", 3);
	public static final LoreSet nameSuffixEntries = new LoreSet("nameSuffix", 8);
	public static final LoreSet nameInfixEntries = new LoreSet("nameInfix", 5);
	public static final LoreSet adjective1LowercaseEntries = new LoreSet("adj1lc", 12);
	public static final LoreSet adjective1UppercaseEntries = new LoreSet("adj1uc", 12);
	public static final LoreSet adjective2UppercaseEntries = new LoreSet("adj2uc", 9);
	public static final LoreSet partsEntries = new LoreSet("parts", 11);
	public static final LoreSet placeAdjectiveEntries = new LoreSet("placeAdjective", 10);
	public static final LoreSet kingdomAdjectiveEntries = new LoreSet("kingdomAdjective", 9);
	public static final LoreSet kingdomishEntries = new LoreSet("kingdomish", 7);
	public static final LoreSet mountainNameEntries = new LoreSet("mountainName", 8);
	public static final LoreSet hardcodedPlacesEntries = new LoreSet("hardcodedPlaces", 15);
	public static final LoreSet otherPeopleEntries = new LoreSet("otherPeople", 7);
	public static final LoreSet createdEntries = new LoreSet("created", 4);
	public static final LoreSet forgottenEntries = new LoreSet("forgotten", 4);
	public static final LoreSet originEntries = new LoreSet("origin", 3);
	public static final LoreSet itemActionEntries = new LoreSet("itemAction", 9);
	public static final LoreSet itemTypeEntries = new LoreSet("itemType", 5);
	public static final LoreSet tauntEntries = new LoreSet("taunt", 6);
	public static final LoreSet playerGetEntries = new LoreSet("playerGet", 5);
	public static final LoreSet randomItemsEntries = new LoreSet("randomItems", 7);
	public static final LoreSet institutishEntries = new LoreSet("institutish", 3);
	public static final LoreSet foundationFirstEntries = new LoreSet("foundationFirst", 10);
	public static final LoreSet foundationSecondEntries = new LoreSet("foundationSecond", 9);
	public static final LoreSet extraEntries = new LoreSet("extraEntries", 6);
	public static final TranslatableContents classicHeroesThe = t("classicHeroesThe");
	public static final TranslatableContents levelPrefix = t("levelPrefix");
	public static final TranslatableContents levelSuffix = t("levelSuffix");
	public static final TranslatableContents noStory = t("noStory");
	public static final TranslatableContents kingdomAnd = t("kingdomAnd");
	public static final TranslatableContents kingdomOf = t("kingdomOf");
	public static final TranslatableContents mountainPrefix = t("mountainPrefix");
	public static final TranslatableContents actorOf = t("actorOf");
	public static final TranslatableContents storyIntro = t("storyIntro");
	public static final TranslatableContents loanedTo = t("loanedTo");
	public static final TranslatableContents forgottenIn = t("forgottenIn");
	public static final TranslatableContents originBy = t("originBy");
	public static final TranslatableContents thing = t("thing");
	public static final TranslatableContents infinitiveSuffix = t("infinitiveSuffix");
	public static final TranslatableContents gizmo = t("gizmo");
	public static final TranslatableContents itemOf = t("itemOf");
	public static final TranslatableContents defaultPlayer = t("defaultPlayer");
	public static final TranslatableContents ownerBy = t("ownerBy");
	public static final TranslatableContents named = t("named");
	public static final TranslatableContents organizationSpecialityAnd = t("organizationSpecialityAnd");
	public static final TranslatableContents universityOf = t("universityOf");
	public static final TranslatableContents instituteOf = t("instituteOf");
	public static final TranslatableContents foundationInfix = t("foundationInfix");
	public static final TranslatableContents foundationSuffix = t("foundationSuffix");
	public static final TranslatableContents restoredBy = t("restoredBy");
	public static final TranslatableContents recently = t("recently");
	private static final IGenerator heroGenerator = createHeroGenerator();
	public static final IGenerator INSTANCE = createLoreGenerator();

	private static IGenerator createHeroGenerator() {
		IGenerator heroesPrefix = alt(heroesPrefixEntries);
		IGenerator heroesPostfix = alt(heroesPostfixEntries);
		IGenerator heroName = word(heroesPrefix, opt(0.6f, heroesPostfix));
		IGenerator heroOptional = alt(heroOptionalEntries);
		IGenerator heroAdj = alt(heroAdjEntries);
		IGenerator heroClass = alt(heroClassEntries);
		IGenerator classicHeroes = seq(
				heroName,
				terminal(classicHeroesThe),
				seq(
						opt(0.2f, heroOptional),
						heroAdj,
						heroClass,
						opt(0.2f, word(terminal(levelPrefix), range(1, 11), terminal(levelSuffix)))));

		IGenerator firstName = alt(firstNameEntries);
		IGenerator lastNameComponent = alt(lastNameEntries);
		IGenerator lastName = alt(
				lastNameComponent,
				word(lastNameComponent, terminal("-"), lastNameComponent));
		IGenerator pseudonym = alt(pseudonymEntries);
		IGenerator namePrefix = alt(namePrefixEntries);
		IGenerator middleName = alt(middleNameEntries);
		IGenerator nameSuffix = alt(nameSuffixEntries);

		final IGenerator middleStuff = seq(
				opt(0.1f, middleName),
				opt(0.6f, word(terminal("\""), pseudonym, terminal("\""))));
		IGenerator modernHeroes = seq(
				opt(0.4f, namePrefix),
				firstName,
				middleStuff,
				opt(0.3f, alt(nameInfixEntries)),
				lastName,
				opt(0.2f, word(terminal(" "), nameSuffix)));

		return alt(classicHeroes, modernHeroes);
	}

	private static IGenerator createLoreGenerator() {
		IGenerator adj1lc = alt(adjective1LowercaseEntries);
		IGenerator adj1uc = alt(adjective1UppercaseEntries);
		IGenerator adj2uc = alt(adjective2UppercaseEntries);
		IGenerator adjs = opt(0.7f, seq(adj2uc, adj1lc), adj1uc);
		IGenerator parts = alt(partsEntries);

		IGenerator placeAdj = alt(placeAdjectiveEntries);
		IGenerator kingdomAdjective = alt(kingdomAdjectiveEntries);
		IGenerator kingdomish = seq(
				opt(0.4f, placeAdj),
				alt(kingdomishEntries));
		IGenerator placeWithAdj = seq(
				kingdomish,
				terminal(kingdomOf),
				kingdomAdjective,
				opt(0.2f, seq(terminal(kingdomAnd), kingdomAdjective)));
		IGenerator mountainName = alt(mountainNameEntries);
		IGenerator mountain = seq(opt(0.6f, placeAdj), terminal(mountainPrefix), mountainName);
		final IGenerator hardcodedPlaces = alt(hardcodedPlacesEntries);
		IGenerator places = alt(placeWithAdj, mountain, hardcodedPlaces);

		IGenerator otherPeople = alt(otherPeopleEntries);
		IGenerator actor = seq(alt(heroGenerator, seq(otherPeople, terminal(actorOf), places)));

		IGenerator story = alt(terminal(noStory), seq(terminal(storyIntro), actor));
		IGenerator epicLoot = seq(opt(0.5f, adjs), parts, story);

		IGenerator created = seq(
				alt(createdEntries),
				epicLoot);
		IGenerator loaned = seq(terminal(loanedTo), actor);
		IGenerator forgotten = seq(terminal(forgottenIn), alt(forgottenEntries));
		IGenerator origin = alt(
				created,
				seq(
						alt(originEntries, loaned, forgotten),
						terminal(originBy),
						heroGenerator));

		IGenerator itemAction = alt(itemActionEntries, word(sub("item", thing), terminal(infinitiveSuffix)));
		IGenerator itemType = alt(sub("item", gizmo), alt(itemTypeEntries));
		IGenerator item = seq(opt(0.9f, adjs), itemType, opt(0.9f, seq(terminal(itemOf), itemAction)));
		IGenerator fullItem = seq(item, opt(0.05f, seq(terminal("™"))));

		IGenerator taunt = alt(tauntEntries);
		IGenerator playerGet = seq(alt(playerGetEntries), places);
		IGenerator ownerInfo = seq(
				playerGet,
				terminal(ownerBy),
				opt(0.3f, seq(taunt, terminal(named))),
				sub("player", defaultPlayer));

		IGenerator randomItems = alt(randomItemsEntries);
		IGenerator organizationSpeciality = alt(
				randomItems,
				seq(randomItems, terminal(organizationSpecialityAnd), randomItems));

		IGenerator university = seq(terminal(universityOf), alt(hardcodedPlaces, organizationSpeciality));

		IGenerator institutish = alt(institutishEntries);
		IGenerator institute = seq(institutish, terminal(instituteOf), organizationSpeciality);

		IGenerator foundationFirst = alt(foundationFirstEntries);
		IGenerator foundationSecond = alt(foundationSecondEntries);
		IGenerator foundation = seq(
				word(foundationFirst, terminal(foundationInfix), foundationSecond),
				terminal(foundationSuffix));

		IGenerator organization = alt(university, foundation, institute);

		IGenerator restoredInfo = seq(terminal(restoredBy), organization);
		IGenerator recent = seq(terminal(recently), alt(ownerInfo, restoredInfo));

		IGenerator extra = alt(extraEntries);
		return word(
				fullItem,
				opt(0.5f, seq(terminal(","), origin)),
				opt(0.5f, seq(terminal("."), recent)),
				opt(0.2f, seq(terminal("."), extra)));
	}

	private static TranslatableContents t(String key) {
		return new TranslatableContents("lore." + SuperSargassoSea.MODID + "." + key, null, new Object[0]);
	}
}
