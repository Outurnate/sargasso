/* (C)2026 */
package com.outurnate.sargasso.item;

import com.outurnate.sargasso.registry.LocalBlocks;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;

public class BedrockCreamItem extends Item {
    public BedrockCreamItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() == Blocks.BEDROCK) {
            if (context.getPlayer().gameMode() != GameType.CREATIVE) {
                context.getItemInHand().setCount(context.getItemInHand().getCount() - 1);
            }
            context.getLevel()
                .setBlock(context.getClickedPos(), LocalBlocks.CREAMY_BEDROCK.get().defaultBlockState(), 0);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }
}
