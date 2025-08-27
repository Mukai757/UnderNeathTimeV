package sfac.ut5.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author AoXiang_Soar
 */
public abstract class UTVBaseContainerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    private int[] input;
    private int[] output;
    private int[] all;
    private int size;

    private NonNullList<ItemStack> items;

    /**
     * @param input An array that determine which slots are used for input
     * @param output An array that determine which slots are used for output, input slots can be same as output slots
     */
    public UTVBaseContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, @Nullable int[] input, @Nullable int[] output) {
        super(type, pos, blockState);
        input = input == null ? new int[] {} : input;
        output = output == null ? new int[] {} : output;
        this.input = input;
        this.output = output;
        this.all = IntStream.concat(Arrays.stream(input), Arrays.stream(output)).toArray();
        this.size = all.length;
        this.items = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public int getSize() {
    	return this.size;
    }
    
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (this.size > 0)
        	ContainerHelper.loadAllItems(tag, items, registries);
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.size > 0)
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
        return size;
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
    public int[] getSlotsForFace(Direction side) {
        return all;
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
        for (int num : input)
            if (num == slot) return true;
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        for (int num : output)
            if (num == index) return true;
        return false;
    }

}
