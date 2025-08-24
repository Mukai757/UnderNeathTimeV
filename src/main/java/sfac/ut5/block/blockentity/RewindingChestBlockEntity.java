package sfac.ut5.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.state.BlockState;
import sfac.ut5.block.RewindingChestBlock;

/**
 * @author zer0M1nd
 * @author AoXiang_Soar
 */
public class RewindingChestBlockEntity extends ChestBlockEntity {

    public RewindingChestBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    /** 
     * Client only. Link to {@link RewindingChestBlock#getTicker}
     */
    public static <T extends BlockEntity> void lidAnimateTick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        ((RewindingChestBlockEntity)blockEntity).chestLidController.tickLid();
    }
    
    /** 
     * Server only. Link to {@link RewindingChestBlock#getTicker}
     */
    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        
    }
    
    protected Component getDefaultName() {
        return Component.translatable("block.ut5.rewinding_chest");
    }
    
    @Override
    public BlockEntityType<?> getType() {
        return UTVBlockEntities.REWINDING_CHEST.get();
    }
    
    private final ChestLidController chestLidController = new ChestLidController();
    
    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.chestLidController.shouldBeOpen(type > 0);
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }
    
    @Override
    public float getOpenNess(float partialTicks) {
        return this.chestLidController.getOpenness(partialTicks);
    }
}