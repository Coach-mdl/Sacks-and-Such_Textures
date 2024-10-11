package mod.traister101.sns.datagen.providers;

import mod.traister101.sns.SacksNSuch;
import mod.traister101.sns.common.capability.LunchboxFoodTrait;
import mod.traister101.sns.common.items.*;
import mod.traister101.sns.datagen.SmartLanguageProvider;
import mod.traister101.sns.util.SNSUtils;
import mod.traister101.sns.util.SNSUtils.ToggleType;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.stream.Collectors;

public class BuiltIntLanguage extends SmartLanguageProvider {

	private static final Set<RegistryObject<? extends Item>> MANUAL_ITEM_LANG = Set.of(SNSItems.STEEL_HORSESHOE, SNSItems.BLACK_STEEL_HORSESHOE,
			SNSItems.BLUE_STEEL_HORSESHOE, SNSItems.RED_STEEL_HORSESHOE, SNSItems.STEEL_HORSESHOES, SNSItems.BLACK_STEEL_HORSESHOES,
			SNSItems.BLUE_STEEL_HORSESHOES, SNSItems.RED_STEEL_HORSESHOES);

	public BuiltIntLanguage(final PackOutput output) {
		super(output, SacksNSuch.MODID, "en_us");
	}

	/**
	 * Takes a string like dark_oak and converts it to Dark Oak.
	 */
	public static String langify(final String serializedName) {
		return Arrays.stream(serializedName.split("_"))
				.map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
				.collect(Collectors.joining(" "));
	}

	@Override
	protected void addTranslations() {
		addItemTranslations();

		// Keybinds
		add("sns.key.pickup", "Toggle Container Item Pickup");
		add("sns.key.void", "Toggle Container Item Voiding");
		// Creative Tab
		add("sns.creative_tab.sacks", "Sacks 'N Such");
		// Tooltips
		add(ContainerItem.TYPE_NO_VOID, "%s has item voiding disabled");
		add(ContainerItem.TYPE_NO_PICKUP, "%s has item pickup disabled");
		add(ContainerItem.HOLD_SHIFT_TOOLTIP, "Hold (Shift) for container info");
		add(ContainerItem.PICKUP_TOOLTIP, "Item Pickup %s");
		add(ContainerItem.VOID_TOOLTIP, "Item Voiding %s");
		add(ContainerItem.SLOT_COUNT_TOOLTIP, "Slot Count: %s");
		add(ContainerItem.SLOT_CAPACITY_TOOLTIP, "Slot Capacity: %s");
		add(ContainerItem.INVENTORY_INTERACTION_TOOLTIP, "Inventory Interaction: %s");
		add(ContainerItem.ALLOWED_SIZE_TOOLTIP, "Fits at most size: %s");
		add(LunchBoxItem.SELECTED_SLOT_TOOLTIP, "Selected Slot: %s");
		add(MobNetItem.CANNOT_CAPTURE_PLAYERS, "You cannot capture players");
		add(MobNetItem.CANNOT_CAPTURE_GENERIC, "You cannot capture %ss");
		add(MobNetItem.CANNOT_CAPTURE_SIZE, "The %s is too large to capture");
		add(MobNetItem.CANNOT_PLACE, "There's not enough space to release captured %s");
		add(MobNetItem.STACK_NAME, "%s (%s)");
		add(Horseshoes.HORSESHOE_MODIFIER_TOOLTIP, "When on Horse:");
		add(ToggleType.PICKUP.langKey, "Item Pickup %s");
		add(ToggleType.VOID.langKey, "Item Voiding %s");
		add(SNSUtils.ENABLED, "Enabled");
		add(SNSUtils.DISABLED, "Disabled");

		add(LunchboxFoodTrait.LUNCHBOX_LANG, "Lunchbox Preserved");
	}

	@Override
	protected Iterable<Item> getKnownItems() {
		return SNSItems.ITEMS.getEntries().stream().map(RegistryObject::get)::iterator;
	}

	private void addItemTranslations() {
		SNSItems.ITEMS.getEntries().stream().filter(o -> !MANUAL_ITEM_LANG.contains(o)).forEach(this::addSimpleItem);
		addItem(SNSItems.STEEL_HORSESHOE, "Steel Horseshoe");
		addItem(SNSItems.BLACK_STEEL_HORSESHOE, "Black Steel Horseshoe");
		addItem(SNSItems.BLUE_STEEL_HORSESHOE, "Blue Steel Horseshoe");
		addItem(SNSItems.RED_STEEL_HORSESHOE, "Red Steel Horseshoe");
		addItem(SNSItems.STEEL_HORSESHOES, "Steel Horseshoes");
		addItem(SNSItems.BLACK_STEEL_HORSESHOES, "Black Steel Horseshoes");
		addItem(SNSItems.BLUE_STEEL_HORSESHOES, "Blue Steel Horseshoes");
		addItem(SNSItems.RED_STEEL_HORSESHOES, "Red Steel Horseshoes");
	}

	private void addSimpleItem(final RegistryObject<Item> item) {
		add(item.get(), langify(item.getId().getPath()));
	}
}