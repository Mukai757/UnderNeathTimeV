package sfac.ut5.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import sfac.ut5.block.blockentity.SpacePulverizerBlockEntity;
import sfac.ut5.block.blockentity.UTVBlockEntities;

/**
 * @author Mukai
 */
public class SpacePulverizerBlock extends BaseEntityBlock{
    public static final MapCodec<SpacePulverizerBlock> CODEC = simpleCodec(SpacePulverizerBlock::new);
    
    public SpacePulverizerBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SpacePulverizerBlockEntity(blockPos, blockState);
    }
    
    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
    
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == UTVBlockEntities.SPACE_PULVERIZER.get() ? SpacePulverizerBlockEntity::tick : null;
    }
    
    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
    ) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof SpacePulverizerBlockEntity blockEntity) {
                serverPlayer.openMenu(new SimpleMenuProvider((MenuConstructor) blockEntity,
                        blockEntity.getName()), pos);
                }
            }
        return ItemInteractionResult.SUCCESS;
    }
}
