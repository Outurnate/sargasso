/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalSoundEvents;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class LocalSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public static final TranslatableContents creamApply = t("cream_apply");

    public static final TranslatableContents glitchTeleport = t("glitch_teleport");
    public static final TranslatableContents toaster = t("toaster");
    public static final TranslatableContents pylon = t("pylon");
    public static final TranslatableContents zap = t("zap");

    private static TranslatableContents t(String key) {
        return new TranslatableContents("sound." + SuperSargassoSea.MODID + "." + key, null, new Object[0]);
    }

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
                .subtitle(creamApply.getKey())
                .replace(true));
        add(
            LocalSoundEvents.GLITCH_TELEPORT,
            SoundDefinition.definition()
                .with(
                    sound("sargasso:glitch1"),
                    sound("sargasso:glitch2"),
                    sound("sargasso:glitch3"))
                .subtitle(glitchTeleport.getKey())
                .replace(true));
        add(
            LocalSoundEvents.TOASTER,
            SoundDefinition.definition()
                .with(
                    sound("sargasso:toaster"))
                .subtitle(toaster.getKey())
                .replace(true));
        add(
            LocalSoundEvents.PYLON,
            SoundDefinition.definition()
                .with(
                    sound("sargasso:pylon"))
                .subtitle(pylon.getKey())
                .replace(true));
        add(
            LocalSoundEvents.RECORD_UNCHECKED,
            SoundDefinition.definition()
                .with(
                    sound("sargasso:unchecked_tapeless_mix"))
                .replace(true));
        add(
            LocalSoundEvents.ZAP,
            SoundDefinition.definition()
                .with(
                    sound("sargasso:zap1"),
                    sound("sargasso:zap2"),
                    sound("sargasso:zap3"),
                    sound("sargasso:zap4"))
                .subtitle(zap.getKey())
                .replace(true));
    }
}
