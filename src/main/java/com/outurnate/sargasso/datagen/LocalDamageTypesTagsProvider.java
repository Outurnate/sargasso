/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalDamageTypes;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;

public class LocalDamageTypesTagsProvider extends DamageTypeTagsProvider {
    public LocalDamageTypesTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, lookupProvider, SuperSargassoSea.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(LocalDamageTypes.HEAD_EXPLOSION);
        this.tag(DamageTypeTags.BYPASSES_COOLDOWN).add(LocalDamageTypes.HEAD_EXPLOSION);
        this.tag(DamageTypeTags.BYPASSES_INVULNERABILITY).add(LocalDamageTypes.HEAD_EXPLOSION);
        this.tag(DamageTypeTags.BYPASSES_SHIELD).add(LocalDamageTypes.HEAD_EXPLOSION);
        this.tag(DamageTypeTags.IS_EXPLOSION).add(LocalDamageTypes.HEAD_EXPLOSION);
    }
}
