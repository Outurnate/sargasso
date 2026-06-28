/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalBlocks;
import com.outurnate.sargasso.registry.LocalEntities;
import com.outurnate.sargasso.registry.LocalItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EnglishLanguageProvider extends LanguageProvider {
    public EnglishLanguageProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, SuperSargassoSea.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup.sargasso", "Super Sargasso Sea");
        this.add("sound.sargasso.cream_apply", "Bedrock cream applied");
        this.add("item.minecraft.potion.effect.fizzy_lifting", "Fizzy Lifting Drink");
        this.add("sargasso.lore.fizzy_lifting", "Burp, Charlie! You've got to burp!");
        this.addBlock(LocalBlocks.FLOTSAM, "Flotsam");
        this.addBlock(LocalBlocks.DEBRIS, "Debris");
        this.addBlock(LocalBlocks.CREAMY_BEDROCK, "Debris");
        this.addBlock(LocalBlocks.GLITCH, "Glitch in Reality");
        this.addItem(LocalItems.JUNK, "Unidentifiable Junk");
        this.addItem(LocalItems.BEDROCK_CREAM, "Professor Murgatroyd's Miracle Bedrock Cream");
        this.addItem(LocalItems.BEDROCK_SLOP, "Bedrock Slop");
        this.addItem(LocalItems.BREADROCK, "Breadrock");
        this.addItem(LocalItems.LIGHTNING_BOTTLE, "Lightning in a Bottle");
        this.addEntityType(LocalEntities.LIGHTNING_BOTTLE, "Thrown Lightning in a Bottle");
    }
}
