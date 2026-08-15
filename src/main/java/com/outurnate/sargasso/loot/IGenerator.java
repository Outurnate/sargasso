/* (C)2026 */
package com.outurnate.sargasso.loot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.network.chat.contents.PlainTextContents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.util.RandomSource;

public interface IGenerator {
    public static IGenerator alt(IGenerator... alts) {
        return new IGenerator() {
            @Override
            public List<ComponentContents> generate(
                RandomSource random,
                Map<String, ComponentContents> params) {
                if (alts.length == 0) {
                    return List.of(PlainTextContents.EMPTY);
                }
                int choice = random.nextInt(alts.length);
                return alts[choice].generate(random, params);
            }
        };
    }

    public static IGenerator alt(LoreSet defs, IGenerator... appends) {
        return alt(
            Stream.concat(
                defs.keys().map(key -> new TranslatableContents(key, null, new Object[0]))
                    .map(IGenerator::terminal),
                Arrays.stream(appends)).toArray(IGenerator[]::new));
    }

    private static List<ComponentContents> flatten(List<ComponentContents> components) {
        List<ComponentContents> result = new ArrayList<>();
        boolean previousWasSpace = false;

        for (ComponentContents component : components) {
            boolean isSpace = component instanceof LiteralContents literal
                && " ".equals(literal.text());
            if (isSpace && previousWasSpace) {
                continue;
            }
            result.add(component);
            previousWasSpace = isSpace;
        }

        return result;
    }

    private static <T> List<T> intersperse(List<T> input, T separator) {
        if (input.isEmpty()) {
            return List.of();
        }

        List<T> result = new ArrayList<>(input.size() * 2 - 1);

        for (int i = 0; i < input.size(); i++) {
            if (i > 0) {
                result.add(separator);
            }
            result.add(input.get(i));
        }

        return result;
    }

    private static boolean isEmpty(List<ComponentContents> component) {
        return component.size() == 0 || component.stream().allMatch(PlainTextContents.EMPTY::equals);
    }

    public static IGenerator opt(float probability, IGenerator gen) {
        return opt(probability, gen, terminal(PlainTextContents.EMPTY));
    }

    public static IGenerator opt(float probability, IGenerator left, IGenerator right) {
        return new IGenerator() {
            @Override
            public List<ComponentContents> generate(
                RandomSource random,
                Map<String, ComponentContents> params) {
                return (random.nextFloat() < probability) ? left.generate(random, params)
                    : right.generate(random, params);
            }
        };
    }

    public static IGenerator range(int start, int end) {
        return new IGenerator() {
            private final int range = end - start;

            @Override
            public List<ComponentContents> generate(
                RandomSource random,
                Map<String, ComponentContents> params) {
                return List.of(PlainTextContents.create(Integer.toString(start + random.nextInt(range))));
            }
        };
    }

    public static IGenerator seq(IGenerator... parts) {
        return new IGenerator() {
            @Override
            public List<ComponentContents> generate(
                RandomSource random,
                Map<String, ComponentContents> params) {
                List<ComponentContents> results = new ArrayList<>();

                for (IGenerator part : parts) {
                    List<ComponentContents> result = part.generate(random, params);
                    if (!isEmpty(result))
                        results.addAll(result);
                }

                return flatten(intersperse(results, PlainTextContents.create(" ")));
            }
        };
    }

    public static IGenerator sub(String key) {
        return sub(key, (TranslatableContents) null);
    }

    public static IGenerator sub(String key, TranslatableContents defaultValue) {
        return new IGenerator() {
            @Override
            public List<ComponentContents> generate(
                RandomSource random,
                Map<String, ComponentContents> params) {
                if (params.get(key) != null)
                    return List.of(params.get(key));
                else if (defaultValue != null)
                    return List.of(defaultValue);
                return List.of(PlainTextContents.EMPTY);
            }
        };
    }

    public static IGenerator terminal(ComponentContents object) {
        return new IGenerator() {
            @Override
            public List<ComponentContents> generate(
                RandomSource random,
                Map<String, ComponentContents> params) {
                return List.of(object);
            }
        };
    }

    public static IGenerator terminal(String object) {
        return terminal(PlainTextContents.create(object));
    }

    public static IGenerator word(IGenerator... parts) {
        return new IGenerator() {
            @Override
            public List<ComponentContents> generate(
                RandomSource random,
                Map<String, ComponentContents> params) {
                List<ComponentContents> results = new ArrayList<>();

                for (IGenerator part : parts) {
                    List<ComponentContents> result = part.generate(random, params);
                    if (!isEmpty(result))
                        results.addAll(result);
                }

                return flatten(results);
            }
        };
    }

    public List<ComponentContents> generate(RandomSource random, Map<String, ComponentContents> params);
}
