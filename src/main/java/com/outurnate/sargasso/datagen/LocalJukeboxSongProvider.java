package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalJukeboxSongs;
import com.outurnate.sargasso.registry.LocalSoundEvents;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.JukeboxSong;

public class LocalJukeboxSongProvider {
    public static void provide(BootstrapContext<JukeboxSong> bootstrap) {
        bootstrap.register(
            LocalJukeboxSongs.UNCHECKED,
            new JukeboxSong(
                LocalSoundEvents.RECORD_UNCHECKED,
                Component.translatable("jukebox_song." + SuperSargassoSea.MODID + ".unchecked"),
                184.0F,
                12));
    }
}
