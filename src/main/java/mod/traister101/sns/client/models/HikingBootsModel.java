package mod.traister101.sns.client.models;
// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import mod.traister101.sns.SacksNSuch;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class HikingBootsModel<T extends LivingEntity> extends HumanoidModel<T> {

	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(SacksNSuch.MODID, "hikingboots"), "main");

	public HikingBootsModel(final ModelPart root) {
		super(root);
	}

	@SuppressWarnings("unused")
	public static LayerDefinition createBodyLayer() {//@formatter:off
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(-3.0F, 12.0F, 1.0F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(1, 2).addBox(-2.0F, 5.0F, 3.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 26).addBox(-2.525F, 6.0F, 1.0F, 0.5F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(26, 19).addBox(-2.525F, 7.0F, -1.0F, 0.5F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 26).addBox(2.025F, 6.0F, 1.0F, 0.5F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(26, 19).addBox(2.025F, 7.0F, -1.0F, 0.5F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(17, 21).addBox(-2.0F, 7.0F, -1.525F, 4.0F, 1.0F, 0.5F, new CubeDeformation(0.0F))
				.texOffs(17, 11).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(17, 0).addBox(-2.0F, 6.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(17, 19).addBox(-2.0F, 6.0F, 3.025F, 4.0F, 1.0F, 0.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(3.0F, 12.0F, 1.0F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(17, 11).mirror().addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(17, 0).mirror().addBox(-2.0F, 6.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(17, 21).mirror().addBox(-2.0F, 7.0F, -1.525F, 4.0F, 1.0F, 0.5F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(1, 2).mirror().addBox(-2.0F, 5.0F, 3.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(26, 19).mirror().addBox(2.025F, 7.0F, -1.0F, 0.5F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 26).mirror().addBox(2.025F, 6.0F, 1.0F, 0.5F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(26, 19).mirror().addBox(-2.525F, 7.0F, -1.0F, 0.5F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 26).mirror().addBox(-2.525F, 6.0F, 1.0F, 0.5F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(17, 19).mirror().addBox(-2.0F, 6.0F, 3.025F, 4.0F, 1.0F, 0.5F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}//@formatter:on
}