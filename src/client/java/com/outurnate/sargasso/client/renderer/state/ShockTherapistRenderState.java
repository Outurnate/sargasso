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
package com.outurnate.sargasso.client.renderer.state;

import com.outurnate.sargasso.client.model.ElectricArc;
import java.util.List;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

public class ShockTherapistRenderState extends BlockEntityRenderState {
	public List<ElectricArc> bolts;
}
