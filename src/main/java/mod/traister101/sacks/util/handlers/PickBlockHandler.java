package mod.traister101.sacks.util.handlers;

import mod.traister101.sacks.common.items.SackItem;
import mod.traister101.sacks.config.SNSConfig;
import mod.traister101.sacks.network.SNSPacketHandler;
import mod.traister101.sacks.network.ServerBoundPickBlockPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.PacketDistributor;

import lombok.experimental.UtilityClass;
import java.util.Optional;

@UtilityClass
public final class PickBlockHandler {

	/**
	 * Handles the client pickblock and informs the server of the event
	 *
	 * @param player The player to handle
	 * @param target The target the player is aiming at
	 */
	public static boolean onPickBlock(final Player player, final HitResult target) {
		final Level world = player.level();

		// Only handle pick blocks
		if (target.getType() != Type.BLOCK) return false;

		final ItemStack stackToSelect;
		{
			final BlockPos blockPos = ((BlockHitResult) target).getBlockPos();
			final BlockState blockState = world.getBlockState(blockPos);
			if (blockState.isAir()) return false;

			stackToSelect = blockState.getBlock().getCloneItemStack(blockState, target, world, blockPos, player);
		}

		// Nothing to select
		if (stackToSelect.isEmpty()) return false;

		SNSPacketHandler.send(PacketDistributor.SERVER.noArg(), new ServerBoundPickBlockPacket(stackToSelect));
		return true;
	}

	/**
	 * Our handler for the server side of our pick block. Called from the packet sent from the client
	 *
	 * @param player The player we handle
	 * @param stackToSelect The stack to select
	 */
	public static void handlePickBlock(final ServerPlayer player, final ItemStack stackToSelect) {
		final Inventory inventoryPlayer = player.getInventory();

		// We only bother checking if there's an empty slot, so we don't have to do any annoying stack merging
		if (!hasSpace(inventoryPlayer)) return;

		final ItemStack foundStack = findStackInItemContainer(inventoryPlayer, stackToSelect);

		// Didn't find a matching ItemStack
		if (foundStack.isEmpty()) return;

		final int slotIndex = inventoryPlayer.getFreeSlot();
		inventoryPlayer.setItem(slotIndex, foundStack);

		if (Inventory.isHotbarSlot(slotIndex)) {
			inventoryPlayer.selected = slotIndex;
		} else {
			inventoryPlayer.pickSlot(slotIndex);
		}

		player.connection.send(new ClientboundContainerSetSlotPacket(ClientboundContainerSetSlotPacket.PLAYER_INVENTORY, 0, inventoryPlayer.selected,
				inventoryPlayer.getItem(inventoryPlayer.selected)));
		player.connection.send(new ClientboundContainerSetSlotPacket(ClientboundContainerSetSlotPacket.PLAYER_INVENTORY, 0, slotIndex,
				inventoryPlayer.getItem(slotIndex)));
		player.connection.send(new ClientboundSetCarriedItemPacket(inventoryPlayer.selected));
	}

	/**
	 * Finds and extracts a matching item stack from an Item Container inside the Player Inventory
	 *
	 * @param inventoryPlayer The player inventory to search
	 * @param stackToMatch The item we are looking for
	 *
	 * @return Extracted ItemStack or an empty ItemStack if one could not be found
	 */
	private static ItemStack findStackInItemContainer(final Inventory inventoryPlayer, final ItemStack stackToMatch) {
		for (final ItemStack itemContainer : inventoryPlayer.items) {

			final LazyOptional<IItemHandler> itemHandlerOpt = itemContainer.getCapability(ForgeCapabilities.ITEM_HANDLER);
			if (!itemHandlerOpt.isPresent()) continue;

			// Handle pick block for all items with containers
			if (!SNSConfig.SERVER.allPickBlock.get()) {
				// Not a sack
				if (!(itemContainer.getItem() instanceof SackItem)) continue;
			}

			final IItemHandler handler;
			{
				final Optional<IItemHandler> resolve = itemHandlerOpt.resolve();
				if (resolve.isEmpty()) continue;
				handler = resolve.get();
			}

			for (int slotIndex = 0; slotIndex < handler.getSlots(); slotIndex++) {
				final ItemStack slotStack = handler.getStackInSlot(slotIndex);
				if (!ItemStack.isSameItem(slotStack, stackToMatch)) continue;

				final int extractAmount = slotStack.getMaxStackSize();
				return handler.extractItem(slotIndex, extractAmount, false);
			}
		}

		return ItemStack.EMPTY;
	}

	/**
	 * Check if there's empty space in the player inventory
	 *
	 * @param inventoryPlayer The player inventory to search
	 *
	 * @return If there's an empty slot
	 */
	private static boolean hasSpace(final Inventory inventoryPlayer) {
		for (final ItemStack itemStack : inventoryPlayer.items) {
			if (itemStack.isEmpty()) return true;
		}

		return false;
	}
}