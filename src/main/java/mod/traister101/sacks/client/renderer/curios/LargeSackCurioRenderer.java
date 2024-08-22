package mod.traister101.sacks.client.renderer.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import mod.traister101.sacks.SacksNSuch;
import mod.traister101.sacks.client.models.LargeSackModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class LargeSackCurioRenderer implements ICurioRenderer {

	public static final ResourceLocation ORE_SACK_TEXTURE = new ResourceLocation(SacksNSuch.MODID, "textures/curios/ore_sack.png");
	private final ResourceLocation texture;
	private final LargeSackModel model;

	public LargeSackCurioRenderer(final ResourceLocation texture) {
		this.texture = texture;
		final EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
		this.model = new LargeSackModel(entityModels.bakeLayer(LargeSackModel.LAYER_LOCATION));
	}

	public static Supplier<ICurioRenderer> create(final ResourceLocation texture) {
		return () -> new SmallSackCurioRenderer(texture);
	}

	@Override
	public <T extends LivingEntity, M extends EntityModel<T>> void render(final ItemStack itemStack, final SlotContext slotContext,
			final PoseStack poseStack, final RenderLayerParent<T, M> renderLayerParent, final MultiBufferSource bufferSource, final int packedLight,
			final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw,
			final float headPitch) {

		if (!itemStack.isEmpty()) {
			final LivingEntity entity = slotContext.entity();
			poseStack.pushPose();

			poseStack.mulPose(Axis.YP.rotationDegrees(90));

			poseStack.scale(0.75F, 0.75F, 0.75F);
			poseStack.translate(0, -0.15, 0.80);
			if (entity.isCrouching()) {
				poseStack.translate(-0.35, 0.1875F, 0);
			}

			model.setupAnim(limbSwing, limbSwingAmount);
			model.renderToBuffer(poseStack, bufferSource.getBuffer(model.renderType(texture)), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

			poseStack.popPose();
		}
	}
}