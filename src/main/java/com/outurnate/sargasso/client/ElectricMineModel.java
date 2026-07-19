package com.outurnate.sargasso.client;

import com.outurnate.sargasso.SuperSargassoSea;
import com.outurnate.sargasso.entity.ElectricMineRenderState;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElectricMineModel extends EntityModel<ElectricMineRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
        SuperSargassoSea.ID("electric_mine"),
        "main");

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild(
            "main",
            CubeListBuilder.create().texOffs(0, 0)
                .addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    public ElectricMineModel(ModelPart root) {
        super(root);
    }
}