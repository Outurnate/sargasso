/* (C)2026 */
package com.outurnate.sargasso.loot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.PlainTextContents.LiteralContents;
import net.minecraft.util.RandomSource;

public interface IGenerator {
    public static IGenerator alt(Object... obj) {
        return new IGenerator() {
            private final List<IGenerator> alts = ImmutableList.copyOf(convert(obj));

            @Override
            public Component generate(RandomSource random, Map<String, Component> params) {
                if (alts.isEmpty())
                    return Component.empty();
                int choice = random.nextInt(alts.size());
                return alts.get(choice).generate(random, params);
            }
        };
    }

    private static IGenerator[] convert(Object... args) {
        IGenerator[] result = new IGenerator[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof IGenerator)
                result[i] = (IGenerator) arg;
            else if (arg instanceof Component)
                result[i] = terminal((Component) arg);
            else
                result[i] = terminal(Component.literal(arg.toString()));
        }
        return result;
    }

    public static Component flatten(Component component) {
        return flatten(component.toFlatList());
    }

    private static Component flatten(List<Component> components) {
        MutableComponent result = Component.empty();
        boolean previousWasSpace = false;

        for (Component component : components) {
            boolean isSpace = component.getContents() instanceof LiteralContents literal
                && " ".equals(literal.text());
            if (isSpace && previousWasSpace) {
                continue;
            }
            result.append(component);
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

    private static boolean isEmpty(Component component) {
        return component.equals(Component.empty());
    }

    public static IGenerator opt(float probability, IGenerator gen) {
        return opt(probability, gen, terminal(Component.empty()));
    }

    public static IGenerator opt(float probability, IGenerator left, IGenerator right) {
        return new IGenerator() {
            @Override
            public Component generate(RandomSource random, Map<String, Component> params) {
                return (random.nextFloat() < probability) ? left.generate(random, params)
                    : right.generate(random, params);
            }
        };
    }

    public static IGenerator range(int start, int end) {
        return new IGenerator() {
            private final int range = end - start;

            @Override
            public Component generate(RandomSource random, Map<String, Component> params) {
                return Component.literal(Integer.toString(start + random.nextInt(range)));
            }
        };
    }

    public static IGenerator seq(Object... obj) {
        return new IGenerator() {
            private final List<IGenerator> parts = ImmutableList.copyOf(convert(obj));

            @Override
            public Component generate(RandomSource random, Map<String, Component> params) {
                List<Component> results = new ArrayList<Component>();

                for (IGenerator part : parts) {
                    Component result = part.generate(random, params);
                    if (!isEmpty(result))
                        results.add(result);
                }

                return flatten(intersperse(results, Component.literal(" ")));
            }
        };
    }

    public static IGenerator sub(String key) {
        return sub(key, null);
    }

    public static IGenerator sub(String key, Component defaultValue) {
        return new IGenerator() {
            @Override
            public Component generate(RandomSource random, Map<String, Component> params) {
                if (params.get(key) != null)
                    return params.get(key);
                else if (defaultValue != null)
                    return defaultValue;
                return Component.empty();
            }
        };
    }

    public static IGenerator terminal(Component object) {
        return new IGenerator() {
            @Override
            public Component generate(RandomSource random, Map<String, Component> params) {
                return object;
            }
        };
    }

    public static IGenerator word(Object... obj) {
        return new IGenerator() {
            private final List<IGenerator> parts = ImmutableList.copyOf(convert(obj));

            @Override
            public Component generate(RandomSource random, Map<String, Component> params) {
                List<Component> results = Lists.newArrayList();

                for (IGenerator part : parts) {
                    Component result = part.generate(random, params);
                    if (!isEmpty(result))
                        results.add(result);
                }

                return flatten(results);
            }
        };
    }

    public Component generate(RandomSource random, Map<String, Component> params);
}
