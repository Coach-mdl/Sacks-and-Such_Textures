package mod.traister101.sacks.common.menu;

import mod.traister101.sacks.network.ClientBoundExtendedSlotInitialDataPacket;
import mod.traister101.sacks.network.ClientBoundExtendedSlotSyncPacket;
import mod.traister101.sacks.network.SNSPacketHandler;

import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerSynchronizer;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.network.PacketDistributor;

/**
 * A {@link ContainerSynchronizer} for containers with extended slot capacity
 */
public class ExtendedSlotCapacitySynchronizer implements ContainerSynchronizer {

	private final ServerPlayer player;

	public ExtendedSlotCapacitySynchronizer(final ServerPlayer player) {
		this.player = player;
	}

	@Override
	public void sendInitialData(final AbstractContainerMenu containerMenu, final NonNullList<ItemStack> itemStacks, final ItemStack carriedStack,
			final int[] dataSlotsValues) {
		SNSPacketHandler.send(PacketDistributor.PLAYER.with(() -> player),
				new ClientBoundExtendedSlotInitialDataPacket(containerMenu.incrementStateId(), containerMenu.containerId, itemStacks, carriedStack));

		for (int index = 0; index < dataSlotsValues.length; ++index) {
			broadcastDataValue(containerMenu, index, dataSlotsValues[index]);
		}
	}

	@Override
	public void sendSlotChange(final AbstractContainerMenu containerMenu, final int slotIndex, final ItemStack itemStack) {
		SNSPacketHandler.send(PacketDistributor.PLAYER.with(() -> player),
				new ClientBoundExtendedSlotSyncPacket(containerMenu.containerId, slotIndex, itemStack));
	}

	@Override
	public void sendCarriedChange(final AbstractContainerMenu containerMenu, final ItemStack itemStack) {
		player.connection.send(new ClientboundContainerSetSlotPacket(-1, containerMenu.incrementStateId(), -1, itemStack));
	}

	@Override
	public void sendDataChange(final AbstractContainerMenu containerMenu, final int index, final int value) {
		broadcastDataValue(containerMenu, index, value);
	}

	private void broadcastDataValue(final AbstractContainerMenu containerMenu, final int index, final int value) {
		player.connection.send(new ClientboundContainerSetDataPacket(containerMenu.containerId, index, value));
	}
}