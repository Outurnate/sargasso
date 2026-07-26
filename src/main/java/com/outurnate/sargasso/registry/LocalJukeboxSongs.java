package com.outurnate.sargasso.registry;

import com.outurnate.sargasso.SuperSargassoSea;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

public class LocalJukeboxSongs {
    public static final ResourceKey<JukeboxSong> UNCHECKED = ResourceKey
        .create(Registries.JUKEBOX_SONG, SuperSargassoSea.ID("unchecked"));
}
