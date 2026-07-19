/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalSoundEvents;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class LocalSoundDefinitionsProvider extends SoundDefinitionsProvider {
    protected LocalSoundDefinitionsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
        super(output, SuperSargassoSea.MODID);
    }

    @Override
    public void registerSounds() {
        add(
            LocalSoundEvents.CREAM_APPLY,
            SoundDefinition.definition()
                .with(
                    sound("minecraft:mob/slime/big1"),
                    sound("minecraft:mob/slime/big2"),
                    sound("minecraft:mob/slime/big3"),
                    sound("minecraft:mob/slime/big4"))
                .subtitle("sound.sargasso.cream_apply")
                .replace(true));
        add(
            LocalSoundEvents.GLITCH_TELEPORT,
            SoundDefinition.definition()
                .with(
                    sound("sargasso:glitch1"),
                    sound("sargasso:glitch2"),
                    sound("sargasso:glitch3"))
                .subtitle("sound.sargasso.glitch_teleport")
                .replace(true));
        add(
            LocalSoundEvents.TOASTER,
            SoundDefinition.definition()
                .with(
                    sound("sargasso:toaster"))
                .subtitle("sound.sargasso.toaster")
                .replace(true));
        add(
            LocalSoundEvents.PYLON,
            SoundDefinition.definition()
                .with(
                    sound("sargasso:pylon"))
                .subtitle("sound.sargasso.pylon")
                .replace(true));
    }
}
