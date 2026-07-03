/* (C)2026 */
package com.outurnate.sargasso.mixin;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SeededContainerLoot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.common.extensions.IBlockEntityExtension;

import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChiseledBookShelfBlockEntity.class)
public abstract class ChiseledBookShelfBlockEntityMixin implements IBlockEntityExtension {
    private static final Logger LOGGER = LogUtils.getLogger();

    private void addRandomItem(LootPool self, Consumer<ItemStack> result, LootContext context)
        throws NoSuchFieldException, IllegalAccessException {
        Field entriesField = self.getClass().getDeclaredField("entries");
        entriesField.setAccessible(true);
        List<LootPoolEntryContainer> entries = (List<LootPoolEntryContainer>) entriesField.get(self);

        RandomSource random = context.getRandom();
        List<LootPoolEntry> validEntries = Lists.newArrayList();
        MutableInt totalWeight = new MutableInt();

        for (LootPoolEntryContainer entry : entries) {
            entry.expand(context, e -> {
                int weight = e.getWeight(context.getLuck());
                if (weight > 0) {
                    validEntries.add(e);
                    totalWeight.add(weight);
                }
            });
        }

        int entryCount = validEntries.size();
        if (totalWeight.intValue() != 0 && entryCount != 0) {
            if (entryCount == 1) {
                validEntries.get(0).createItemStack(result, context);
            } else {
                int index = random.nextInt(totalWeight.intValue());

                for (LootPoolEntry entry : validEntries) {
                    index -= entry.getWeight(context.getLuck());
                    if (index < 0) {
                        entry.createItemStack(result, context);
                        return;
                    }
                }
            }
        }
    }

    private void addRandomItems(LootPool self, Consumer<ItemStack> result, LootContext context)
        throws NoSuchFieldException, IllegalAccessException {
        Field compositeFunctionField = self.getClass().getDeclaredField("compositeFunction");
        compositeFunctionField.setAccessible(true);
        BiFunction<ItemStack, LootContext, ItemStack> compositeFunction = (BiFunction<ItemStack, LootContext, ItemStack>) compositeFunctionField
            .get(self);

        Field compositeConditionField = self.getClass().getDeclaredField("compositeCondition");
        compositeConditionField.setAccessible(true);
        Predicate<LootContext> compositeCondition = (Predicate<LootContext>) compositeFunctionField.get(self);

        if (compositeCondition.test(context)) {
            Consumer<ItemStack> decoratedConsumer = LootItemFunction
                .decorate(compositeFunction, result, context);
            int count = self.getRolls().getInt(context)
                + Mth.floor(self.getBonusRolls().getFloat(context) * context.getLuck());

            for (int i = 0; i < count; i++) {
                addRandomItem(self, decoratedConsumer, context);
            }
        } else {
            LOGGER.error("composite condition failed");
        }
    }

    private ObjectArrayList<ItemStack> getRandomItems(LootTable self, LootContext context)
        throws NoSuchFieldException, IllegalAccessException {
        ObjectArrayList<ItemStack> result = new ObjectArrayList<>();
        getRandomItemsRaw(self, context, LootTable.createStackSplitter(context.getLevel(), result::add));
        LOGGER.error("BEFORE" + result.size());
        result = net.neoforged.neoforge.common.CommonHooks.modifyLoot(self.getLootTableId(), result, context);
        LOGGER.error("AFTER" + result.size());
        return result;
    }

    public ObjectArrayList<ItemStack> getRandomItems(
        LootTable self,
        LootParams params,
        long optionalLootTableSeed) throws NoSuchFieldException, IllegalAccessException {
        Field randomSequenceField = self.getClass().getDeclaredField("randomSequence");
        randomSequenceField.setAccessible(true);
        Optional<Identifier> randomSequence = (Optional<Identifier>) randomSequenceField.get(self);
        return getRandomItems(
            self,
            new LootContext.Builder(params).withOptionalRandomSeed(optionalLootTableSeed)
                .create(randomSequence));
    }

    private void getRandomItemsRaw(LootTable self, LootContext context, Consumer<ItemStack> output)
        throws NoSuchFieldException, IllegalAccessException {
        Field poolsField = self.getClass().getDeclaredField("pools");
        poolsField.setAccessible(true);
        List<LootPool> pools = (List<LootPool>) poolsField.get(self);
        LOGGER.error("POOLS" + pools.size());

        Field compositeFunctionField = self.getClass().getDeclaredField("compositeFunction");
        compositeFunctionField.setAccessible(true);
        BiFunction<ItemStack, LootContext, ItemStack> compositeFunction = (BiFunction<ItemStack, LootContext, ItemStack>) compositeFunctionField
            .get(self);

        LootContext.VisitedEntry<?> breadcrumb = LootContext.createVisitedEntry(self);
        if (context.pushVisitedElement(breadcrumb)) {
            Consumer<ItemStack> decoratedOutput = LootItemFunction
                .decorate(compositeFunction, output, context);

            for (LootPool pool : pools) {
                LOGGER.error("rolling pool");
                addRandomItems(pool, decoratedOutput, context);
            }

            context.popVisitedElement(breadcrumb);
        } else {
            LOGGER.error("Detected infinite loop in loot tables");
        }
    }

    @Override
    // @Overwrite
    public void onLoad() {
        requestModelDataUpdate();
        if ((Object) this instanceof ChiseledBookShelfBlockEntity self) {
            Level level = self.getLevel();
            if (level != null && level instanceof ServerLevel server) {
                sargasso$populateLoot(self, server);
            }
        }
    }

    private void sargasso$populateLoot(ChiseledBookShelfBlockEntity self, ServerLevel server) {
        try {
            if (!self.components().has(DataComponents.CONTAINER_LOOT)) {
                return;
            }

            SeededContainerLoot containerLoot = self.components().get(DataComponents.CONTAINER_LOOT);

            LootTable lootTable = server.getServer().reloadableRegistries()
                .getLootTable(containerLoot.lootTable());
            LOGGER.error(containerLoot.lootTable().toString());
            LootParams params = new LootParams.Builder(server)
                .create(LootContextParamSets.EMPTY);
            LOGGER.error(String.valueOf(containerLoot.seed()));
            List<ItemStack> loot = lootTable.getRandomItems(
                params,
                containerLoot.seed() == 0 ? server.getRandom().nextLong() : containerLoot.seed());
            LOGGER.error(String.valueOf(loot.size()));

            self.clearContent();

            int slot = 0;
            List<Integer> slotMixer = IntStream.range(0, 6).boxed().collect(Collectors.toList());
            Collections.shuffle(slotMixer);
            for (ItemStack stack : loot) {
                self.setItem(slotMixer.get(slot++), stack);
                LOGGER.error("SET ITEM");
            }

            self.applyComponents(
                self.collectComponents(),
                DataComponentPatch.builder().remove(DataComponents.CONTAINER_LOOT).build());
            LOGGER.error("REMOVED COMPONENT");
            self.setChanged();
            LOGGER.error("MARKED DIRTY");
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }
}
