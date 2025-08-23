package sfac.ut5.block;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import sfac.ut5.TimeSystem;
import sfac.ut5.block.blockentity.SpaceProducerBlockEntity;
import sfac.ut5.block.blockentity.UTVBlockEntities;

/**
 * @author Mukai
 * @author AoXiang_Soar
 */
//Lazy to implement an abstract class :(
public class SpaceProducerBlock extends BaseEntityBlock implements ILevelBlock {
	public ProducerLevel producerLevel;
	
	public static final MapCodec<SpaceProducerBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> 
    instance.group(
            BlockBehaviour.Properties.CODEC.fieldOf("properties").forGetter(BlockBehaviour::properties),
            Codec.STRING.xmap(
                s -> ProducerLevel.valueOf(s),
                e -> e.name()
            ).fieldOf("producerLevel").forGetter(b -> b.producerLevel)
        ).apply(instance, SpaceProducerBlock::new)
    );

	public SpaceProducerBlock(Properties properties, ProducerLevel producerLevel) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(LEVEL, 0));
        this.producerLevel = producerLevel;
    }
	
    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        Containers.dropContentsOnDestroy(state, newState, level, pos);
        super.onRemove(state, level, pos, newState, isMoving);
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }
    
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
    	switch (this.producerLevel) {
		case LV1:
			return this.defaultBlockState();
		case LV2:
			return this.defaultBlockState().setValue(LEVEL, 1);
		case LV3:
			return this.defaultBlockState().setValue(LEVEL, 2);
		case LV4:
			return this.defaultBlockState().setValue(LEVEL, 3);
		case LV5:
			return this.defaultBlockState().setValue(LEVEL, 4);
		case LV6:
			return this.defaultBlockState().setValue(LEVEL, 5);
		default:
	    	return this.defaultBlockState();
		}
    }

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
			Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
			BlockEntity entity = level.getBlockEntity(pos);
			if (entity instanceof SpaceProducerBlockEntity blockEntity) {
				serverPlayer.openMenu(new SimpleMenuProvider((MenuConstructor) blockEntity,
						blockEntity.getName()), pos);
			}
		}
		return ItemInteractionResult.SUCCESS;
	}
	
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpaceProducerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == UTVBlockEntities.SPACE_PRODUCER.get() ? SpaceProducerBlockEntity::tick : null;
    }
    
	public enum ProducerLevel {
		// TODO 改数值
		LV1(0, 1000, TimeSystem.second()), 
		LV2(1, 10_000, TimeSystem.minute() / 10),
		LV3(2, 100_000, TimeSystem.hour() / 100), 
		LV4(3, 1_000_000, TimeSystem.day() / 1000),
		LV5(4, 10_000_000, TimeSystem.month() / 1000), 
		LV6(5, 100_000_000, TimeSystem.year() / 10000);

		private final int id;
		private final int capacity;
		private final int outputenergy;

		ProducerLevel(int id, int capacity, int outputRate) {
			this.id = id;
			this.capacity = capacity;
			this.outputenergy = outputRate;
		}

		public int getCapacity() {
			return capacity;
		}

		public int getOutputRate() {
			return outputenergy;
		}

		public int getId() {
			return id;
		}
	}
	
	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
