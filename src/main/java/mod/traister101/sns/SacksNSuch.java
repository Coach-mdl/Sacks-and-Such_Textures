package mod.traister101.sns;

import com.mojang.logging.LogUtils;
import mod.traister101.sns.client.*;
import mod.traister101.sns.common.SNSCreativeTab;
import mod.traister101.sns.common.capability.LunchboxFoodTrait;
import mod.traister101.sns.common.items.SNSItems;
import mod.traister101.sns.common.menu.SNSMenus;
import mod.traister101.sns.config.SNSConfig;
import mod.traister101.sns.network.SNSPacketHandler;
import org.slf4j.Logger;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@SuppressWarnings("FieldMayBeFinal")
@Mod(SacksNSuch.MODID)
public final class SacksNSuch {

	public static final String MODID = "sns";
	public static final String NAME = "Sacks 'N Such";
	public static final Logger LOGGER = LogUtils.getLogger();

	public SacksNSuch() {
		final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		eventBus.addListener(SacksNSuch::commonSetup);

		SNSItems.ITEMS.register(eventBus);
		SNSMenus.MENUS.register(eventBus);
		SNSCreativeTab.CREATIVE_TABS.register(eventBus);

		SNSConfig.init();
		SNSPacketHandler.init();
		ForgeEventHandler.init();

		if (FMLEnvironment.dist == Dist.CLIENT) {
			ClientEventHandler.init();
			ClientForgeEventHandler.init();
		}
	}

	private static void commonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(LunchboxFoodTrait::init);
	}
}