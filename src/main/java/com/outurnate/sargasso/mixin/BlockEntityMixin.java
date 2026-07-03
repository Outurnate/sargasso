/* (C)2026 */
package com.outurnate.sargasso.mixin;

import com.mojang.logging.LogUtils;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SeededContainerLoot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin {
    private static final Logger LOGGER = LogUtils.getLogger();

    // The injection site for this is strange for a reason
    // ChiseledBookShelfBlockEntity's loadAdditional is called before
    // level is set, so rolling the loot table is impossible there
    // ChiseledBookShelfBlockEntity doesn't override setLevel, so we
    // must inject to the parent class
    @Inject(method = "setLevel", at = @At("TAIL"))
    private void sargasso$setLevel(Level level, CallbackInfo callbackInfo) {
        if ((Object) this instanceof ChiseledBookShelfBlockEntity self
            && level != null && level instanceof ServerLevel server) {
            try {
                if (!self.components().has(DataComponents.CONTAINER_LOOT)) {
                    return;
                }

                SeededContainerLoot containerLoot = self.components().get(DataComponents.CONTAINER_LOOT);

                LootTable lootTable = server.getServer().reloadableRegistries()
                    .getLootTable(containerLoot.lootTable());
                LootParams params = new LootParams.Builder(server)
                    .withParameter(LootContextParams.ORIGIN, self.getBlockPos().getCenter())
                    .create(LootContextParamSets.CHEST);

                self.clearContent();

                int slot = 0;
                List<Integer> slotMixer = IntStream.range(0, 6).boxed().collect(Collectors.toList());
                Collections.shuffle(slotMixer);
                for (ItemStack stack : lootTable.getRandomItems(params, containerLoot.seed())) {
                    self.setItem(slotMixer.get(slot++), stack);
                    LOGGER.error("SET ITEM");
                }

                self.applyComponents(
                    self.collectComponents(),
                    DataComponentPatch.builder().remove(DataComponents.CONTAINER_LOOT).build());
                LOGGER.error("REMOVED COMPONENT");
                // self.setChanged();
                level.blockEntityChanged(self.getBlockPos());
                LOGGER.error("MARKED DIRTY");
            } catch (Exception e) {
                LOGGER.error(e.toString());
                throw e;
            }
        }
    }
}
