package sfac.ut5.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.jetbrains.annotations.Nullable;

import sfac.ut5.block.ILevelBlock;
import sfac.ut5.block.TimeBinderBlock;
import sfac.ut5.gui.TimeSpindleCouplerMenu;

/**
 * @author Mukai
 */
public class TimeBinderBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
	private static final int[] INPUT = {0, 1};
	private static final int[] OUTPUT = {2};
	private static final int[] ALL = IntStream.concat(Arrays.stream(INPUT), Arrays.stream(OUTPUT)).toArray();
	public static final int SIZE = ALL.length;
	
    private NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
	
    public TimeBinderBlockEntity(BlockPos pos, BlockState blockState) {
        super(UTVBlockEntities.TIME_BINDER_BLOCK_ENTITY.get(), pos, blockState);
    }

    /**
     * Link to {@link TimeBinderBlock#getTicker}
     */
    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
    	
    }
    
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, items, registries);
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.items, registries);
    }
    
    /**
     * Called when chunk is loaded
     */
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    /**
     * Called when a block update occurs
     */
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

	@Override
	public int getContainerSize() {
		return SIZE;
	}

	@Override
	protected Component getDefaultName() {
		if (this.getBlockState().getValue(ILevelBlock.LEVEL) == 1)
			return Component.translatable("block.ut5.time_spindle_coupler");
		else
			return Component.translatable("block.ut5.time_bind_altar");
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> items) {
		this.items = items;
	}

	@Override
	protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
		if (this.getBlockState().getValue(ILevelBlock.LEVEL) == 0)
			return null;
		return new TimeSpindleCouplerMenu(containerId, inventory, this);
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return ALL;
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, Direction direction) {
		return canPlaceItem(index, itemStack);
	}

	/**
	 * Only determine whether a slot has the <b>CAPABILITY</b> to accept input, <b>without performing item comparison logic</b>
	 */
	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		for (int num : INPUT) 
            if (num == slot) return true;
		return false;
	}
	
	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		for (int num : OUTPUT) 
            if (num == index) return true;
		return false;
	}

}