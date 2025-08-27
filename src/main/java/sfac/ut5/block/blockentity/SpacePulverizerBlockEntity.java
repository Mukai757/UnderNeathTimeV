package sfac.ut5.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import sfac.ut5.gui.SpacePulverizerMenu;

/**
 * @author Mukai
 */
public class SpacePulverizerBlockEntity extends UTVBaseContainerBlockEntity {

    public SpacePulverizerBlockEntity(BlockPos pos, BlockState blockState) {
        super(UTVBlockEntities.SPACE_PULVERIZER.get(), pos, blockState, new int[]{0}, new int[]{1});
    }
    
    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity){

    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.ut5.space_pulverizer");
    }
    
    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new SpacePulverizerMenu(containerId, inventory, this);
    }
}
