package mod.traister101.sns.common;

import mod.traister101.sns.SacksNSuch;
import mod.traister101.sns.common.items.SNSItems;
import net.dries007.tfc.common.TFCCreativeTabs.CreativeTabHolder;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import net.minecraftforge.registries.*;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class SNSCreativeTab {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SacksNSuch.MODID);

	public static final CreativeTabHolder SACKS = register("sacks",
			() -> new ItemStack(SNSItems.LEATHER_SACK.get()),
			(displayParameters, output) -> SNSItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(output::accept));

	@SuppressWarnings("SameParameterValue")
	private static CreativeTabHolder register(final String name, final Supplier<ItemStack> icon,
			final CreativeModeTab.DisplayItemsGenerator displayItems) {
		final RegistryObject<CreativeModeTab> reg = CREATIVE_TABS.register(name, () -> CreativeModeTab.builder()
				.icon(icon)
				.title(Component.translatable(SacksNSuch.MODID + ".creative_tab." + name))
				.displayItems(displayItems)
				.build());
		return new CreativeTabHolder(reg, displayItems);
	}
}