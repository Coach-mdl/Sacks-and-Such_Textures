package mod.traister101.sns.config;

import mod.traister101.sns.common.items.HikingBootsItem.BootModelType;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

public final class ClientConfig {

	public final BooleanValue voidGlint;
	public final BooleanValue displayItemContentsAsImages;
	public final EnumValue<BootModelType> bootModelType;

	ClientConfig(final ForgeConfigSpec.Builder builder) {
		voidGlint = builder.comment("Swaps the enchant glint from when auto pickup is enabled to when it's dissabled").define("voidGlint", true);
		displayItemContentsAsImages = builder.comment("When enabled sacks will display their contents like how TFC vessels do")
				.define("displayItemContentsAsImages", true);
		bootModelType = builder.comment(
						"Config for which hiking boots model is used. FANCY for the full 3D model, NO_FLOOF for only toes and VANILLA for the vanilla style model")
				.defineEnum("bootModelType", BootModelType.FANCY);
	}
}