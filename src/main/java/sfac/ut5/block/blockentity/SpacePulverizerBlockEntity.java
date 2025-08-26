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
import org.jetbrains.annotations.Nullable;
import sfac.ut5.gui.SpacePulverizerMenu;
import java.util.Arrays;
import java.util.stream.IntStream;

public class SpacePulverizerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    private static final int[] INPUT = {0};
    private static final int[] OUTPUT = {1};
    private static final int[] ALL = IntStream.concat(Arrays.stream(INPUT), Arrays.stream(OUTPUT)).toArray();
    public static final int SIZE = ALL.length;

    private NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);

    //主类，抄
    public SpacePulverizerBlockEntity(BlockPos pos, BlockState blockState) {
        super(UTVBlockEntities.Space_Pulverizer.get(), pos, blockState);
    }
    //tick更新，看不懂，得抄
    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity){

    }

    //加载存储nbt，选抄
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
    //区块加载时更新nbt数据，选抄
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }
    //同步数据，选抄
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    //以下是物品栏，需要才抄
    @Override
    public int getContainerSize() {
        return SIZE;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }
    //获取方块名字，父类需要，一定抄
    @Override
    protected Component getDefaultName() {
        // if (this.getBlockState().getValue(ILevelBlock.LEVEL) == 1)
        return Component.translatable("block.ut5.space_pulverizer");
    }
    //以下是容器，需要才抄
    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new SpacePulverizerMenu(containerId, inventory, this);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return ALL;
    }
    //物品栏限制放入取出物品，需要才抄
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
