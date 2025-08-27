package sfac.ut5.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import sfac.ut5.block.ILevelBlock;
import sfac.ut5.gui.TimeSpindleCouplerMenu;

/**
 * @author Mukai
 */
public class TimeBinderBlockEntity extends UTVBaseContainerBlockEntity {
	
    public TimeBinderBlockEntity(BlockPos pos, BlockState blockState) {
        super(UTVBlockEntities.TIME_BINDER.get(), pos, blockState, new int[]{0, 1}, new int[]{2});
    }

    /*
     * Link to {@link TimeBinderBlock#getTicker}
     */
    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
    	
    }

	@Override
	protected Component getDefaultName() {
		if (this.getBlockState().getValue(ILevelBlock.LEVEL) == 1)
			return Component.translatable("block.ut5.time_spindle_coupler");
		else
			return Component.translatable("block.ut5.time_bind_altar");
	}

	@Override
	protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
		if (this.getBlockState().getValue(ILevelBlock.LEVEL) == 0)
			return null;
		return new TimeSpindleCouplerMenu(containerId, inventory, this);
	}
}