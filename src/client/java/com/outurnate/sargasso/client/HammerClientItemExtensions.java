/*
 * This class is distributed as part of the Super Sargasso Sea mod.
 * Complete source on GitHub:
 * https://github.com/Outurnate/sargasso
 *
 * Super Sargasso Sea is free software and distributed
 * under the MIT License: https://opensource.org/license/mit
 *
 * © 2026 the authors of the Super Sargasso Sea mod
 */
package com.outurnate.sargasso.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class HammerClientItemExtensions implements IClientItemExtensions {
	public HumanoidModel.@Nullable ArmPose getArmPose(
			@NonNull LivingEntity entityLiving,
			@NonNull InteractionHand hand,
			@NonNull ItemStack itemStack) {
		return HammerArmPoseTransformer.PROXY.getValue();
	}
}
