package com.outurnate.sargasso.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalItems;

public class ThingsCategory extends CategoryProvider {
    public ThingsCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(LocalItems.AA_BATTERY);
    }

    @Override
    public String categoryId() {
        return "things";
    }

    @Override
    protected String categoryName() {
        return "Curiosities";
    }

    @Override
    protected void generateEntries() {
        this.add(new BaseEntry(this, "batteries", "Batteries", LocalItems.AA_BATTERY) {
            @Override
            protected void generatePages() {
                this.spotlightPage(
                    "aa_battery",
                    "AA Battery",
                    LocalItems.AA_BATTERY,
                    "This item can supply power to items in the inventory, but, once it's out of charge, it's useless");
                this.spotlightPage(
                    "rechargable_aa_battery",
                    "Rechargable AA Battery",
                    LocalItems.RECHARGABLE_AA_BATTERY,
                    "There are rechargable variants. It can be recharged with $(l:things/potato_battery)Potato Batteries");
            }
        }.generate());
        this.add(new BaseEntry(this, "cream", "Bedrock Cream", LocalItems.BEDROCK_CREAM) {
            @Override
            protected void generatePages() {
                this.spotlightPage(
                    "bedrock_cream",
                    "Bedrock Cream",
                    LocalItems.BEDROCK_CREAM,
                    "This cream can be smeared on bedrock to make it softer. It's still hard to mine, but no longer impossible. The resulting slop can be made into a very hearty bread.");
                this.craftingRecipePage(
                    "slop",
                    "Breadrock",
                    SuperSargassoSea.ID("breadrock"),
                    "The slop can be eaten raw, but it's better baked into bread");
            }
        }.generate());
        this.add(new BaseEntry(this, "fox_ears", "Fox Ears", LocalItems.FOX_EARS) {
            @Override
            protected void generatePages() {
                this.spotlightPage(
                    "fox_ears",
                    "Fox Ears",
                    LocalItems.FOX_EARS,
                    "Wearing these seems to earn the trust of wild foxes. They can be dyed, and combined with armor.");
                this.craftingRecipePage(
                    "fox_ears_cosmetic",
                    SuperSargassoSea.ID("fox_ears_dyed"),
                    SuperSargassoSea.ID("apply_fox_ears"));
            }
        }.generate());
        this.add(new BaseEntry(this, "lightning", "Lightning Bottle", LocalItems.LIGHTNING_BOTTLE) {
            @Override
            protected void generatePages() {
                this.spotlightPage(
                    "lightning",
                    "Lightning Bottle",
                    LocalItems.LIGHTNING_BOTTLE,
                    "This impossible somehow contains a lightning bolt. Caution is advised when throwing it.");
            }
        }.generate());
        this.add(new BaseEntry(this, "potato_battery", "Potato Battery", LocalItems.POTATO_BATTERY) {
            @Override
            protected void generatePages() {
                this.spotlightPage(
                    "potato_battery",
                    "Potato Battery",
                    LocalItems.POTATO_BATTERY,
                    "Not particularly appetizing, and tastes strongly of copper. Eating it allows the body to produce a small amount of electrical energy. Items in the player's inventory will be recharged. The Personal Voltmeter can be used to check the current power level.");
                this.craftingRecipePage(
                    "potato_battery_crafting",
                    SuperSargassoSea.ID("potato_battery"),
                    SuperSargassoSea.ID("personal_voltmeter"));
            }
        }.generate());
        this.add(new BaseEntry(this, "pylon", "Pylon", LocalItems.PYLON) {
            @Override
            protected void generatePages() {
                this.spotlightPage(
                    "pylon",
                    "Pylon",
                    LocalItems.PYLON,
                    "This item makes pretty poor armour. Using it to obscure the eyes of a humanoid monster results in near total pacification.");
                this.entityPage(
                    "entity",
                    "Example",
                    "minecraft:zombie{equipment:{head:{count:1,id:\"sargasso:pylon\"}}}",
                    "You can slip it on a mob's head by using the item on them");
            }
        }.generate());
        this.add(new BaseEntry(this, "shock_therapist", "Shock Therapist", LocalItems.SHOCK_THERAPIST) {
            @Override
            protected void generatePages() {
                this.spotlightPage(
                    "shock_therapist",
                    "Shock Therapist",
                    LocalItems.SHOCK_THERAPIST,
                    "A device that periodically spews chunks of metal, and then creates arcs of electricity. Best not approached.");
            }
        }.generate());
        this.add(
            new BaseEntry(
                this,
                "studded_leather",
                "Studded Leather Armor",
                LocalItems.STUDDED_LEATHER_UPGRADE_SMITHING_TEMPLATE) {
                @Override
                protected void generatePages() {
                    this.spotlightPage(
                        "studded_leather",
                        "Studded Leather Armor",
                        LocalItems.STUDDED_LEATHER_UPGRADE_SMITHING_TEMPLATE,
                        "todo");
                    this.entityPage(
                        "entity",
                        "Example",
                        "minecraft:mannequin{profile:panicnot42,hidden_layers:[cape,left_sleeve,right_sleeve,hat],equipment:{head:{id:\"sargasso:studded_leather_helmet\"},chest:{id:\"sargasso:studded_leather_chestplate\"},legs:{id:\"sargasso:studded_leather_leggings\"},feet:{id:\"sargasso:studded_leather_boots\"}}}",
                        "todo");
                }
            }.generate());
        this.add(new BaseEntry(this, "toaster", "Chronometric Flux Toaster", LocalItems.TOASTER) {
            @Override
            protected void generatePages() {
                this.spotlightPage(
                    "toaster",
                    "C. Flux Toaster",
                    LocalItems.TOASTER,
                    "This device possesses a function that can't possibly be useful to anyone. Using the principals of chronometric science, it toasts bread after it's been eaten. It somehow does this despite having no discernable power source.");
            }
        }.generate());
    }
}
