package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfig;

public class LocalTrialSpawnerProvider {
    public static final ResourceKey<TrialSpawnerConfig> ESCHER = ResourceKey
        .create(Registries.TRIAL_SPAWNER_CONFIG, SuperSargassoSea.ID("escher"));
    public static final ResourceKey<TrialSpawnerConfig> ESCHER_OMINOUS = ResourceKey
        .create(Registries.TRIAL_SPAWNER_CONFIG, SuperSargassoSea.ID("escher_ominous"));

    private static <T extends Entity> CompoundTag entity(EntityType<T> type) {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(type).toString());
        return tag;
    }

    public static void provide(BootstrapContext<TrialSpawnerConfig> bootstrap) {
        bootstrap.register(
            ESCHER,
            TrialSpawnerConfig.builder()
                .spawnPotentialsDefinition(
                    WeightedList.<SpawnData>builder()
                        .add(new SpawnData(entity(EntityType.SHULKER), Optional.empty(), Optional.empty()), 1)
                        .build())
                .build());
        bootstrap.register(
            ESCHER_OMINOUS,
            TrialSpawnerConfig.builder()
                .spawnPotentialsDefinition(
                    WeightedList.<SpawnData>builder()
                        .add(new SpawnData(entity(EntityType.SHULKER), Optional.empty(), Optional.empty()), 1)
                        .build())
                .build());
    }
}
