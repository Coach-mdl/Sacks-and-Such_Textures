package mod.traister101.sacks.common.capability;

import mod.trasiter101.esc.common.capability.ExtendedSlotCapacityHandler;
import net.dries007.tfc.common.capabilities.size.*;

import mod.traister101.sacks.common.SNSTags;
import mod.traister101.sacks.common.SNSTags.Items;
import mod.traister101.sacks.common.items.DefaultContainers;
import mod.traister101.sacks.config.SNSConfig;
import mod.traister101.sacks.util.ContainerType;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.Nullable;

public class ContainerItemHandler extends ExtendedSlotCapacityHandler {

	private final ContainerType type;
	@Nullable
	private Weight cachedWeight;

	public ContainerItemHandler(final ContainerType type) {
		super(type.getSlotCount(), type.getSlotCapacity());
		this.type = type;
	}

	@Override
	public int getStackLimit(final int slotIndex, final ItemStack itemStack) {
		if (type == DefaultContainers.FRAME_PACK) return itemStack.getMaxStackSize();
		return super.getStackLimit(slotIndex, itemStack);
	}

	@Override
	public CompoundTag serializeNBT() {
		final CompoundTag compoundTag = super.serializeNBT();
		compoundTag.putByte("weight", (byte) (cachedWeight != null ? cachedWeight.ordinal() : -1));
		return compoundTag;
	}

	@Override
	public void deserializeNBT(final CompoundTag compoundTag) {
		super.deserializeNBT(compoundTag);
		final byte weight = compoundTag.getByte("weight");
		if (weight != -1) cachedWeight = Weight.valueOf(weight);
	}

	@Override
	public boolean isItemValid(final int slotIndex, final ItemStack itemStack) {
		if (itemStack.is(SNSTags.Items.PREVENTED_IN_ITEM_CONTAINERS)) return false;

		if (type == DefaultContainers.SEED_POUCH && !itemStack.is(Items.ALLOWED_IN_SEED_POUCH)) return false;

		if (type == DefaultContainers.LUNCHBOX && !itemStack.is(SNSTags.Items.LUNCHBOX_FOOD)) return false;

		if (!SNSConfig.SERVER.allAllowFood.get() && type != DefaultContainers.LUNCHBOX && itemStack.is(Items.TFC_FOODS)) return false;

		if (type == DefaultContainers.ORE_SACK && !itemStack.is(Items.TFC_ORE)) return false;

		if (!SNSConfig.SERVER.allAllowOre.get() && type != DefaultContainers.ORE_SACK && itemStack.is(Items.TFC_ORE)) return false;

		final IItemSize stackSize = ItemSizeManager.get(itemStack);
		final Size size = stackSize.getSize(itemStack);
		// Larger than the sacks slot size
		return size.isEqualOrSmallerThan(type.getAllowedSize());
	}

	@Override
	protected void onContentsChanged(final int slotIndex) {
		// Invalidate our cached weight when any contents change
		this.cachedWeight = null;
		super.onContentsChanged(slotIndex);
	}

	/**
	 * @return The weight of the sack
	 */
	public Weight getWeight() {
		if (cachedWeight != null) return cachedWeight;

		int totalItems = 0, maxCapacity = 0;

		for (int slotIndex = 0; slotIndex < getSlots(); slotIndex++) {
			final ItemStack itemStack = stacks.get(slotIndex);
			totalItems += itemStack.getCount();
			maxCapacity += getStackLimit(slotIndex, itemStack);
		}

		final float amountFilled = (float) totalItems / (float) maxCapacity;

		// TODO Simple percentage based approuch, maybe not the best?
		if (0.80 <= amountFilled) {
			return cachedWeight = Weight.VERY_HEAVY;
		}

		if (0.60 <= amountFilled) {
			return cachedWeight = Weight.HEAVY;
		}

		if (0.40 <= amountFilled) {
			return cachedWeight = Weight.MEDIUM;
		}

		if (0.20 <= amountFilled) {
			return cachedWeight = Weight.LIGHT;
		}

		return cachedWeight = Weight.VERY_LIGHT;
	}
}