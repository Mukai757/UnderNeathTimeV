package sfac.ut5.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author Mukai
 * @author AoXiang_Soar
 */
public class RewindingChestBlockEntity extends BarrelBlockEntity {

    public RewindingChestBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    protected Component getDefaultName() {
        return Component.translatable("container.ut5.rewindingchest.title");
    }
}