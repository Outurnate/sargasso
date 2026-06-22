/* (C)2026 */
package com.outurnate.sargasso;

import net.minecraft.util.RandomSource;

public class Utils {
    public static long nextLong(RandomSource randomSource, long max) {
        long r;
        long limit = Long.MAX_VALUE - (Long.MAX_VALUE % max);
        do {
            r = randomSource.nextLong() & Long.MAX_VALUE;
        } while (r >= limit);
        return r % max;
    }
}
