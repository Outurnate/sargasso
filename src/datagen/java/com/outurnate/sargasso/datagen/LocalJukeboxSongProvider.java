package com.outurnate.sargasso.datagen;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.registry.LocalSoundEvents;
import com.outurnate.sargasso.repository.LocalJukeboxSongs;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.JukeboxSong;

public class LocalJukeboxSongProvider {
    public static final TranslatableContents unchecked = new TranslatableContents(
        "jukebox_song." + SuperSargassoSea.MODID + ".unchecked",
        null,
        new Object[0]);

    public static void provide(BootstrapContext<JukeboxSong> bootstrap) {
        bootstrap.register(
            LocalJukeboxSongs.UNCHECKED,
            new JukeboxSong(
                LocalSoundEvents.RECORD_UNCHECKED,
                MutableComponent.create(unchecked),
                184.0F,
                12));
    }
}
