/* (C)2026 */
package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class LocalDimensions {
    public static final ResourceKey<Level> SEA = ResourceKey
        .create(Registries.DIMENSION, SuperSargassoSea.ID("sea"));
}
