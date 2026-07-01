/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.loot.FlimFlamLore;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalItems;
import com.outurnate.sargasso.registry.LocalMobEffects;
import com.outurnate.sargasso.registry.LocalPotions;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EnglishLanguageProvider extends LanguageProvider {
    public EnglishLanguageProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, SuperSargassoSea.MODID, "en_us");
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

        this.add("item.minecraft.potion.effect.fizzy_lifting", "Fizzy Lifting Drink");
        this.add("sargasso.lore.fizzy_lifting", "Burp, Charlie! You've got to burp!");

        this.add("item.minecraft.potion.effect.bhj", "Bone Hurting Juice");
        this.add("sargasso.lore.bhj", "oof ouch my bones");

        this.add("sargasso.lore.george", "The helm of Sire George the shrunk, a legendary knight");
        this.add("sargasso.lore.george_name", "Sir George's Helm");

        this.add("sargasso.lore.liar_pants", "Liar's Pants");

        this.add("sargasso.lore.rocket_boots", "Dwarven Rocket Boots");

        this.addBlock(LocalBlocks.FLOTSAM, "Flotsam");
        this.addBlock(LocalBlocks.DEBRIS, "Debris");
        this.addBlock(LocalBlocks.CREAMY_BEDROCK, "Debris");
        this.addBlock(LocalBlocks.GLITCH, "Glitch in Reality");
        this.addItem(LocalItems.BEDROCK_CREAM, "Professor Murgatroyd's Miracle Bedrock Cream");
        this.addItem(LocalItems.BEDROCK_SLOP, "Bedrock Slop");
        this.addItem(LocalItems.BREADROCK, "Breadrock");
        this.addItem(LocalItems.LIGHTNING_BOTTLE, "Lightning in a Bottle");
        this.addItem(LocalItems.STUDDED_LEATHER_BOOTS, "Studded Leather Boots");
        this.addItem(LocalItems.STUDDED_LEATHER_CHESTPLATE, "Studded Leather Chestplate");
        this.addItem(LocalItems.STUDDED_LEATHER_HELMET, "Studded Leather Helmet");
        this.addItem(LocalItems.STUDDED_LEATHER_LEGGINGS, "Studded Leather Leggings");
        this.addEntityType(LocalEntities.LIGHTNING_BOTTLE, "Thrown Lightning in a Bottle");

        this.add(LocalMobEffects.HEAD_EXPLOSION.value(), "Impending Head Explosion");

        this.addPotion(
            "Juice that makes your head explode",
            List.of(
                LocalPotions.HEAD_EXPLOSION,
                LocalPotions.STRONG_HEAD_EXPLOSION,
                LocalPotions.EXTRA_STRONG_HEAD_EXPLOSION,
                LocalPotions.LONG_HEAD_EXPLOSION));

        this.add("death.attack.sargasso.head_explosion", "%s's head exploded");

        FlimFlamLore.dataGen(this);
    }
}
