/* (C)2026 */
package com.outurnate.sargasso.datagen;

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import com.klikli_dev.modonomicon.api.datagen.NeoBookProvider;
import com.klikli_dev.modonomicon.api.datagen.research.ResearchCache;
import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.datagen.book.AtlasOfNowhere;
import com.outurnate.sargasso.datagen.worldgen.LocalBiomesProvider;
import com.outurnate.sargasso.datagen.worldgen.LocalConfiguredCarversProvider;
import com.outurnate.sargasso.datagen.worldgen.LocalConfiguredFeaturesProvider;
import com.outurnate.sargasso.datagen.worldgen.LocalDensityFunctionProvider;
import com.outurnate.sargasso.datagen.worldgen.LocalDimensionTypesProvider;
import com.outurnate.sargasso.datagen.worldgen.LocalDimensionsProvider;
import com.outurnate.sargasso.datagen.worldgen.LocalNoiseSettingsProvider;
import com.outurnate.sargasso.datagen.worldgen.LocalNoisesProvider;
import com.outurnate.sargasso.datagen.worldgen.LocalPlacedFeaturesProvider;
import com.outurnate.sargasso.datagen.worldgen.LocalStructureProcessorListProvider;
import com.outurnate.sargasso.datagen.worldgen.LocalStructureSetsProvider;
import com.outurnate.sargasso.datagen.worldgen.LocalStructureTemplatePoolsProvider;
import com.outurnate.sargasso.datagen.worldgen.LocalStructuresProvider;
import com.outurnate.sargasso.datagen.worldgen.LocalTimelinesProvider;
import com.outurnate.sargasso.datagen.worldgen.LocalWorldClocksProvider;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = SuperSargassoSea.MODID, value = Dist.CLIENT)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        LanguageProviderCache langCache = new LanguageProviderCache("en_us");
        event.getGenerator()
            .addProvider(
                true,
                NeoBookProvider.of(event, langCache, new ResearchCache(), new AtlasOfNowhere()));

        event.createProvider(LocalModelProvider::new);
        event.createProvider(LocalRecipeProvider.Runner::new);
        event.createProvider(LocalLootTableProvider::new);
        event.createProvider(LocalSoundDefinitionsProvider::new);
        event.createProvider(LocalAdvancementProvider::new);
        event.createProvider(LocalParticleDescriptionProvider::new);
        event.createProvider(LocalEquipmentInfoProvider::new);
        event.createProvider(LocalPoiTypeTagsProvider::new);
        event.createProvider(LocalTradesProvider::new);
        event.createProvider(
            (output, lookupProvider) -> new EnglishLanguageProvider(output, lookupProvider, langCache));
        event.createDatapackRegistryObjects(
            new RegistrySetBuilder()
                .add(Registries.BIOME, LocalBiomesProvider::provide)
                .add(Registries.CONFIGURED_FEATURE, LocalConfiguredFeaturesProvider::provide)
                .add(Registries.DAMAGE_TYPE, LocalDamageTypesProvider::provide)
                .add(Registries.DIMENSION_TYPE, LocalDimensionTypesProvider::provide)
                .add(Registries.LEVEL_STEM, LocalDimensionsProvider::provide)
                .add(Registries.NOISE_SETTINGS, LocalNoiseSettingsProvider::provide)
                .add(Registries.PLACED_FEATURE, LocalPlacedFeaturesProvider::provide)
                .add(Registries.STRUCTURE, LocalStructuresProvider::provide)
                .add(Registries.PROCESSOR_LIST, LocalStructureProcessorListProvider::provide)
                .add(Registries.STRUCTURE_SET, LocalStructureSetsProvider::provide)
                .add(
                    Registries.TEMPLATE_POOL,
                    bootstrap -> new LocalStructureTemplatePoolsProvider().provideTemplatePools(bootstrap))
                .add(Registries.TIMELINE, LocalTimelinesProvider::provide)
                .add(Registries.WORLD_CLOCK, LocalWorldClocksProvider::provide)
                .add(Registries.NOISE, LocalNoisesProvider::provide)
                .add(Registries.DENSITY_FUNCTION, LocalDensityFunctionProvider::provide)
                .add(Registries.JUKEBOX_SONG, LocalJukeboxSongProvider::provide)
                .add(Registries.TRIAL_SPAWNER_CONFIG, LocalTrialSpawnerProvider::provide)
                .add(Registries.CONFIGURED_CARVER, LocalConfiguredCarversProvider::provide)
                .add(Registries.PAINTING_VARIANT, LocalPaintingVariantsProvider::provide)
                .add(Registries.VILLAGER_TRADE, LocalTradesProvider::provideTrades)
                .add(Registries.TRADE_SET, LocalTradesProvider::provideTradeSets)
                .add(Registries.ENCHANTMENT, LocalEnchantmentProvider::provide));
        event.createProvider(LocalDamageTypesTagsProvider::new);
        event.createProvider(LocalTimelineTagsProvider::new);
        event.createProvider(LocalItemTagsProvider::new);
        event.createProvider(LocalBiomeTagsProvider::new);
        event.createProvider(LocalEntityTypeTagsProvider::new);
        event.createProvider(LocalBlockTagsProvider::new);
    }
}
