package sfac.ut5.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import sfac.ut5.block.blockentity.TimeBinderBlockEntity;
import sfac.ut5.block.blockentity.UTVBlockEntities;

import org.jetbrains.annotations.Nullable;

/**
 * @author Mukai
 * @author AoXiang_Soar
 */
public class TimeBinderBlock extends BaseEntityBlock {
//    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);
    public static final MapCodec<TimeBinderBlock> CODEC = simpleCodec(TimeBinderBlock::new);
    
    public static final BooleanProperty ADVANCED = BooleanProperty.create("advanced");

    public TimeBinderBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(ADVANCED, false));
    }

    /*@Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }*/

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ADVANCED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
    	if(pContext.getItemInHand().getItem() == UTVBlocks.TIME_SPINDLE_COUPLER.asItem())
    		return this.defaultBlockState().setValue(ADVANCED, true);
    	return this.defaultBlockState();
    }
    /* BLOCK ENTITY */

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == UTVBlockEntities.TIME_BINDER_BLOCK_ENTITY.get() ? (BlockEntityTicker<T>) TimeBinderBlockEntity::tick : null;
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TimeBinderBlockEntity(blockPos, blockState);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
    	if (state.getValue(ADVANCED) && !player.isShiftKeyDown())
    		return InteractionResult.SUCCESS;
        return InteractionResult.PASS;
    }

    @Override
    protected ItemInteractionResult useItemOn(
        ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
	) {
		if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
			BlockEntity entity = level.getBlockEntity(pos);
			if (entity instanceof TimeBinderBlockEntity blockEntity) {
				if (state.getValue(ADVANCED)) {
					serverPlayer.openMenu(new SimpleMenuProvider(blockEntity,
							Component.translatable("block.ut5.time_spindle_coupler")), pos);
				} else {
					return useItemOnAltar(blockEntity, serverPlayer, stack, hand);
				}
			}
		}
		return ItemInteractionResult.SUCCESS;
    }
    
	private ItemInteractionResult useItemOnAltar(TimeBinderBlockEntity entity, ServerPlayer player, ItemStack stack, InteractionHand hand) {
		int suitSlot = -1;
		for (int i = 0; i < entity.getContainerSize(); i++) {
			var containerItem = entity.getItem(i);
			if (player.isShiftKeyDown()) { // When shift key is down, get item from altar
				if (stack.isEmpty() && !containerItem.isEmpty()) {
					player.setItemInHand(hand, entity.removeItem(i, containerItem.getCount()));
					return ItemInteractionResult.SUCCESS;
				}
			} else if (!stack.isEmpty() && entity.canPlaceItem(i, stack)) { // Find a best slot
				if (containerItem.isEmpty())
					suitSlot = i;
				if (ItemStack.isSameItemSameComponents(stack, containerItem) && containerItem.getCount() < containerItem.getMaxStackSize()) {
					suitSlot = i;
					break;
				}
			}
		}
		if (suitSlot != -1) { // Put item into altar
			var containerItem = entity.getItem(suitSlot);
			var item = stack.consumeAndReturn(1, player);
			item.setCount(containerItem.getCount()+1);
			entity.setItem(suitSlot, item);
			return ItemInteractionResult.SUCCESS;
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}
    
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return (TimeBinderBlockEntity) newBlockEntity(pos, state);
    }

}