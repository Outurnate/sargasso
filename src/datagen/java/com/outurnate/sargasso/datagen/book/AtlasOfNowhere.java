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
package com.outurnate.sargasso.datagen.book;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.book.model.Book;
import com.outurnate.sargasso.datagen.book.model.Category;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.registry.LocalPotions;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import org.apache.commons.io.FileUtils;

public class AtlasOfNowhere {
	public static Book x() {
		Book book = new Book(LocalItems.ATLAS, "The Atlas of Nowhere", "A travel companion",
				"Charles Fort called it the Super-Sargasso Sea, named after the mysterious Sargasso Sea; the dimension into which lost things go. Amelia Earhart's over at the bar. The Lost Colony of Roanoke is next door. USS Cyclops? In the harbor. Elvis Presley? Who do you think is on stage? The Dead Sea Scrolls? Have a pamphlet. And everybody has all the socks they could possibly need. Just not matched ones. Sometimes, even abstract concepts can wind up here — someone's temper or their wits, or even hope.\\\n\\\n-[TV Tropes](https://tvtropes.org/pmwiki/pmwiki.php/Main/SuperSargassoSea)");

		Category dimension = new Category("Introduction", "How to enter, and, more importantly, how to leave. Basic information about the terrain is also covered.", LocalItems.FLOTSAM);
		dimension.addEntry("Entering", LocalItems.FLOTSAM)
				.titlePage("More often than not, the path to this dimension is found accidentally. Falling out of the world, or perhaps, encountering a space where reality is a bit thinner can land you here.")
				.imagePage(SuperSargassoSea.ID("textures/gui/thinner.png"), "A photo of portal to the Super Sargasso Sea");
		dimension.addEntry("Flotsam", LocalItems.FLOTSAM)
				.titlePage("Every item that's ever been lost ends up here. Destroyed items, like those tossed in lava, not so much. Digging through the mountains of flotsam, one can find anything - eventually. Different biomes seem to host different kinds of items.")
				.imagePage(SuperSargassoSea.ID("textures/gui/flotsam.png"), "Heaps and heaps of junk, as far as the eye can see");
		dimension.addEntry("Leaving", Items.OBSIDIAN)
				.titlePage("Though there may be other ways out, this book only knows of one method. You must travel back to the Overworld via the Nether. Previous travellers have left behind ruined portals you may be able to repair.")
				.imagePage(SuperSargassoSea.ID("textures/gui/portal.png"), "A photo of a ruined nether portal");
		dimension.addEntry("Petrified Flotsam", LocalItems.PETRIFIED_FLOTSAM)
				.mainSpotlightPage("Just below the surface, flotsam begins to compact and solidify into a stone-like material. Attempting to scavenge this material is a futile effort.")
				.craftingRecipePage("Compacting", SuperSargassoSea.ID("petrified_flotsam"), "Flotsam can be petrified at a 9:1 ratio");
		dimension.addEntry("Rich Petrified Flotsam", LocalItems.RICH_PETRIFIED_FLOTSAM)
				.mainSpotlightPage("Sometimes, small metal objects may survive petrification. These can be mined out, and smelted down to recover the original metals")
				.multispotlightPage("Junk Items", List.of(LocalItems.LOOSE_WIRE, LocalItems.BROKEN_COG, LocalItems.CLOCKSPRING, LocalItems.CIRCUIT_BOARD, LocalItems.RUSTED_BOLT, LocalItems.LEAKY_BUCKET), "These items also function as a sort of currency amongst the traders who call this dimension home.")
				.blastingRecipePage("Recycling", SuperSargassoSea.ID("copper_nugget_from_blasting_copper_junk"), SuperSargassoSea.ID("gold_nugget_from_blasting_gold_junk"))
				.blastingRecipePage("Recycling (cont.)", SuperSargassoSea.ID("iron_nugget_from_blasting_iron_junk"), "Junk materials can be smelted down to recover raw metals.");
		dimension.addEntry("Reinforced Starmetal", LocalBlocks.REINFORCED_STARMETAL_BLOCK)
				.mainSpotlightPage("The bottom of this dimension is an endless expanse of impenetrable metal. Is there some kind of machine beneath? Is this place artificial?")
				.imagePage(SuperSargassoSea.ID("textures/gui/floor.png"), "The floor of this dimension, made of reinforced starmetal");
		book.addCategory(dimension);

		Category structures = new Category("Structures & Points of Interest", "Various structures have either been built here, or have been pulled here by whatever forces are at work.", LocalItems.KEY);
		structures.addEntry("Office Buildings", Items.ZOMBIE_HEAD)
				.titlePage("Packed to the brim with zombies. Strongly suggested that one brings weapons that can affect many enemies at once.")
				.imagePage(List.of(SuperSargassoSea.ID("textures/gui/office1.png"), SuperSargassoSea.ID("textures/gui/office2.png"), SuperSargassoSea.ID("textures/gui/office3.png")), "Interior and exterior photos of an office structure.");
		structures.addEntry("Bazaars", LocalItems.LOOSE_WIRE)
				.titlePage("Relatively safe havens where wandering traders gather. They seem unable to agree on a single currency - each trader favours a different kind of junk.")
				.imagePage(List.of(SuperSargassoSea.ID("textures/gui/bazaar1.png"), SuperSargassoSea.ID("textures/gui/bazaar2.png"), SuperSargassoSea.ID("textures/gui/bazaar3.png")), "Wandering traders in a bazaar");
		structures.addEntry("Portals", LocalItems.FLOTSAM)
				.titlePage("These portals bring new junk into the dimension. Standing beneath one is risky, but, if one wished to harvest flotsam on an industrial scale, setting up a factory beneath one would be wise.")
				.imagePage(List.of(SuperSargassoSea.ID("textures/gui/glitch1.png")), "A static-filled portal, emitting some items");
		structures.addEntry("Castles", Items.STONE_BRICKS)
				.titlePage("Pillagers have taken over and infested this structure. The lab on the second floor contains some interesting gadgets.")
				.imagePage(List.of(SuperSargassoSea.ID("textures/gui/castle1.png"), SuperSargassoSea.ID("textures/gui/castle2.png"), SuperSargassoSea.ID("textures/gui/castle3.png")), "Various monsters inside a castle");
		structures.addEntry("Shipwrecks", Items.SPYGLASS)
				.titlePage("These wrecks must have been here a long time - they are buried deep. The hold is on the very lowest deck.")
				.imagePage(List.of(SuperSargassoSea.ID("textures/gui/shipwreck1.png"), SuperSargassoSea.ID("textures/gui/shipwreck2.png"), SuperSargassoSea.ID("textures/gui/shipwreck3.png")), "A shipwreck, in various states of excavation.");
		structures.addEntry("Sky Dungeons", Items.QUARTZ_BLOCK)
				.titlePage("It's unclear what force holds these structures aloft, but, they are filled with trial spawners. The pathways are twisted in strange directions. Ender pearls and a ranged weapon are recommended.")
				.imagePage(List.of(SuperSargassoSea.ID("textures/gui/sky1.png"), SuperSargassoSea.ID("textures/gui/sky2.png"), SuperSargassoSea.ID("textures/gui/sky3.png")), "Various locations inside a sky dungeon.");
		structures.addEntry("Abandoned Structures", Items.MOSSY_COBBLESTONE)
				.titlePage("Various uninhabited structures and ruins dot the landscape. What could they be?")
				.imagePage(List.of(SuperSargassoSea.ID("textures/gui/abandoned1.png"), SuperSargassoSea.ID("textures/gui/abandoned2.png"), SuperSargassoSea.ID("textures/gui/abandoned3.png")), "Various abandoned structures");
		book.addCategory(structures);

		Category things = new Category("Curiosities", "Things you may find. Some are useful, some are not. Many of them are not craftable - exploration is the only method to obtain them.", LocalItems.AA_BATTERY);
		things.addEntry("Batteries", LocalItems.AA_BATTERY)
				.mainSpotlightPage("This item can supply power to items in the inventory, but, once it's out of charge, it's useless")
				.spotlightPage("Rechargeable AA Battery", LocalItems.RECHARGABLE_AA_BATTERY, "There are rechargeable variants. It can be recharged with [Potato Batteries](entry://curiosities/potato_battery)");
		things.addEntry("Bedrock Cream", LocalItems.BEDROCK_CREAM)
				.mainSpotlightPage("This cream can be smeared on bedrock to make it softer. It's still hard to mine, but no longer impossible. The resulting slop can be made into a very hearty bread.")
				.craftingRecipePage("Breadrock", SuperSargassoSea.ID("breadrock"), "The slop can be eaten raw, but it's better baked into bread");
		things.addEntry("Fox Ears", LocalItems.FOX_EARS)
				.mainSpotlightPage("Wearing these seems to earn the trust of wild foxes. They can be dyed, and combined with armor.")
				.craftingRecipePage(SuperSargassoSea.ID("fox_ears_dyed"), SuperSargassoSea.ID("apply_fox_ears"));
		things.addEntry("Lightning Bottle", LocalItems.LIGHTNING_BOTTLE)
				.mainSpotlightPage("This impossible somehow contains a lightning bolt. Caution is advised when throwing it.")
				.imagePage(SuperSargassoSea.ID("textures/gui/lightning.png"), "Lightning striking the ground, in broad daylight");
		things.addEntry("Bottle o' Spiders", LocalItems.SPIDER_BOTTLE)
				.mainSpotlightPage("A bottle of spiders. Throw them FAR away.")
				.imagePage(SuperSargassoSea.ID("textures/gui/spiders.png"), "A recently thrown bottle of spiders has spawned a number of tiny spiders");
		things.addEntry("Potato Battery", LocalItems.POTATO_BATTERY)
				.mainSpotlightPage("Not particularly appetizing, and tastes strongly of copper. Eating it allows the body to produce a small amount of electrical energy. Items in the player's inventory will be recharged. The Personal Voltmeter can be used to check the current power level.")
				.craftingRecipePage(SuperSargassoSea.ID("potato_battery"), SuperSargassoSea.ID("personal_voltmeter"));
		things.addEntry("Pylon", LocalItems.PYLON)
				.mainSpotlightPage("This item makes pretty poor armour. Using it to obscure the eyes of a humanoid monster results in near total pacification.")
				.entityPage("Example", "minecraft:zombie{equipment:{head:{count:1,id:\"sargasso:pylon\"}}}", -0.2F, "You can slip it on a mob's head by using the item on them");
		things.addEntry("Shock Therapist", LocalItems.SHOCK_THERAPIST)
				.mainSpotlightPage("A device that periodically spews chunks of metal, and then creates arcs of electricity. Best not approached. Requires Forge Energy to be supplied to the underside, and has an idle/active cycle.")
				.imagePage(SuperSargassoSea.ID("textures/gui/shock.png"), "A zombie, being shocked");
		things.addEntry("Studded Leather Armor", LocalItems.STUDDED_LEATHER_UPGRADE_SMITHING_TEMPLATE)
				.mainSpotlightPage("A minor upgrade to leather armor. It retains the thermal properties of leather, while being slightly more durable. Can be made by applying the proper upgrade template to leather armor in a smithing table.")
				.entityPage("Example", "minecraft:mannequin{profile:panicnot42,hidden_layers:[cape,left_sleeve,right_sleeve,hat],equipment:{head:{id:\"sargasso:studded_leather_helmet\"},chest:{id:\"sargasso:studded_leather_chestplate\"},legs:{id:\"sargasso:studded_leather_leggings\"},feet:{id:\"sargasso:studded_leather_boots\"}}}", -0.8F, "Studded leather, as it looks when worn.");
		things.addEntry("Chronometric Flux Toaster", LocalItems.TOASTER)
				.mainSpotlightPage("This device possesses a function that can't possibly be useful to anyone. Using the principals of chronometric science, it toasts bread after it's been eaten. It somehow does this despite having no discernible power source.")
				.textPage("Warning: do ++NOT++ take a bath with this item.");
		things.addEntry("Redstone Pulse Device", LocalItems.REDSTONE_EMP)
				.mainSpotlightPage("This item, when thrown, emits a pulse that temporarily disrupts the normal functioning of redstone apparatus. Caution should be used, as not all redstone contraptions respond well to such disruption.")
				.imagePage(SuperSargassoSea.ID("textures/gui/emp.png"), "A small redstone contraption being powered by a redstone pulse device");
		things.addEntry("Hammer", LocalItems.HAMMER)
				.mainSpotlightPage("A heavy hammer. Slow, but can deal heavy damage to a single foe. When thrown, it can strike many enemies in its path.")
				.craftingRecipePage("Crafting", SuperSargassoSea.ID("hammer"), "It would be easier to simply find one - but if it must be crafted, it can be.");
		things.addEntry("Starmetal", LocalItems.STARMETAL_INGOT)
				.mainSpotlightPage("A metal unlike any other. Impossibly strong, yet lightweight. The composition is unknown - thankfully, there's plenty around for scavenging.")
				.blastingRecipePage("Recycling", SuperSargassoSea.ID("starmetal_ingot_from_blasting_starmetal_scrap"), "Scraps can be reforged into new ingots in a blast furnace.");
		things.addEntry("Shrink Ray", LocalItems.SHRINK_RAY)
				.mainSpotlightPage("A gun that shrinks things. Doesn't seem to make creatures stronger or weaker - just, smaller. Batteries ++not++ included")
				.imagePage(SuperSargassoSea.ID("textures/gui/smol.png"), "A small creeper");
		things.addEntry("Growth Ray", LocalItems.GROW_RAY)
				.mainSpotlightPage("A gun that embiggens things. Doesn't seem to make creatures stronger or weaker - just, larger. Batteries ++not++ included")
				.imagePage(SuperSargassoSea.ID("textures/gui/big.png"), "A big creeper");
		things.addEntry("Everlasting Meats", LocalItems.INFINITE_BEEF)
				.mainMultispotlightPage(List.of(LocalItems.INFINITE_BEEF, LocalItems.INFINITE_CHICKEN, LocalItems.INFINITE_COD, LocalItems.INFINITE_MUTTON, LocalItems.INFINITE_PORK, LocalItems.INFINITE_RABBIT, LocalItems.INFINITE_SALMON), "No matter how long these meats are consumed, they never loses mass. However, they will never fully sate one's hunger, and one will find themselves hungry again shortly.")
				.textPage("A thoroughly cursed set of items. Consume at your own risk.");
		things.addEntry("Juice that makes your head explode", new ItemStackTemplate(Items.POTION, 1, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(LocalPotions.HEAD_EXPLOSION)).build()))
				.mainSpotlightPage("Brewed by adding TNT to a potion, this stuff will cause one's head to explode. Probably more useful as a splash potion, or tipped arrow.")
				.imagePage(SuperSargassoSea.ID("textures/gui/brewing.png"), "The process of brewing juice that makes your head explode");
		book.addCategory(things);

		File readme = Paths.get(System.getProperty("user.dir"), "README.md").toFile();
		try {
			FileUtils.writeStringToFile(readme, book.toMarkdown().toString(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return book;
	}
}
