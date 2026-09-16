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
package com.outurnate.sargasso.repository;

import com.outurnate.sargasso.SuperSargassoSea;
import java.util.Objects;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public class LocalAdvancements {
	public static final Identifier ENTER = SuperSargassoSea.ID("enter");
	public static final Identifier LEAVE = SuperSargassoSea.ID("leave");
	public static final Identifier LEAVE_OTHER = SuperSargassoSea.ID("leave_other");
	public static final Identifier TOAST = SuperSargassoSea.ID("toast");
	public static final Identifier PYLON = SuperSargassoSea.ID("pylon");
	public static final Identifier STRIKE = SuperSargassoSea.ID("strike");

	public static void Award(ServerPlayer serverPlayer, Identifier advancement, String criteria) {
		serverPlayer.getAdvancements().award(
				Objects.requireNonNull(serverPlayer.level().getServer().getAdvancements()
						.get(advancement)),
				criteria);
	}
}
