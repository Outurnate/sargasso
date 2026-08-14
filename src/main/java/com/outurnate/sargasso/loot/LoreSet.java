package com.outurnate.sargasso.loot;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record LoreSet(String name, int size) {
    public Stream<String> keys() {
        return IntStream.range(0, size).boxed()
            .map(i -> "lore." + SuperSargassoSea.MODID + "." + name + "." + String.valueOf(i));
    }
}
