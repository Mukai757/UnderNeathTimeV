package sfac.ut5.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import sfac.ut5.TimeSystem;
import sfac.ut5.block.blockentity.TimeProducerBlockEntity;
import sfac.ut5.block.blockentity.UTVBlockEntities;
import sfac.ut5.item.UTVItems;

/**
 * @author Mukai
 */
public class TimeProducerBlock extends BaseEntityBlock implements ILevelBlock {
    public ProducerLevel producerLevel;
    
	public static final MapCodec<TimeProducerBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> 
    instance.group(
            BlockBehaviour.Properties.CODEC.fieldOf("properties").forGetter(BlockBehaviour::properties),
            Codec.STRING.xmap(
                s -> ProducerLevel.valueOf(s),
                e -> e.name()
            ).fieldOf("producerLevel").forGetter(b -> b.producerLevel)
        ).apply(instance, TimeProducerBlock::new)
    );
    
    public TimeProducerBlock(Properties properties, ProducerLevel producerLevel) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(LEVEL, 0));
        this.producerLevel = producerLevel;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }
    
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
    	switch (this.producerLevel) {
		case SECOND_PRODUCER:
			return this.defaultBlockState();
		case MINUTE_PRODUCER:
			return this.defaultBlockState().setValue(LEVEL, 1);
		case HOUR_PRODUCER:
			return this.defaultBlockState().setValue(LEVEL, 2);
		case DAY_PRODUCER:
			return this.defaultBlockState().setValue(LEVEL, 3);
		case MONTH_PRODUCER:
			return this.defaultBlockState().setValue(LEVEL, 4);
		case YEAR_PRODUCER:
			return this.defaultBlockState().setValue(LEVEL, 5);
		default:
	    	return this.defaultBlockState();
		}
    }
    
    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack, BlockState blockstate, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
    ) {
        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof TimeProducerBlockEntity producer) {
                FluidStack fluid = producer.getFluidInTank(0);
//                player.sendSystemMessage(Component.literal("当前流体量: " + fluid.getAmount() + "mB"));

                ItemStack heldItem = player.getItemInHand(hand);
                if (heldItem.is(Items.BUCKET) && fluid.getAmount() >= 1000) {
                    producer.drain(1000, IFluidHandler.FluidAction.EXECUTE);
                    heldItem.shrink(1);
                    be.setChanged();
                    if (heldItem.isEmpty()) {
                        player.setItemInHand(hand, new ItemStack(UTVItems.CHRONOPLASM_BUCKET.get()));
                    } else {
                        player.getInventory().add(new ItemStack(UTVItems.CHRONOPLASM_BUCKET.get()));
                    }
                    return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
                }
            }
        }
        if (player.getItemInHand(hand).is(Items.BUCKET))
            return ItemInteractionResult.SUCCESS;
        return super.useItemOn(stack,blockstate, level, pos, player, hand, hitResult);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TimeProducerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == UTVBlockEntities.TIME_PRODUCER.get() ? TimeProducerBlockEntity::tick : null;
    }
    
    public enum ProducerLevel {
    	// TODO 改数值，用脚填的
        SECOND_PRODUCER(1000, TimeSystem.second()),
        MINUTE_PRODUCER(10_000, TimeSystem.minute()/2),
        HOUR_PRODUCER(100_000, TimeSystem.hour()/4),
        DAY_PRODUCER(1_000_000, TimeSystem.day()/8),
        MONTH_PRODUCER(10_000_000, TimeSystem.month()/16),
        YEAR_PRODUCER(100_000_000, TimeSystem.year()/32);

        private final int capacity;
        private final int outputPerSecond;

        ProducerLevel(int capacity, int outputPerSecond) {
            this.capacity = capacity;
            this.outputPerSecond = outputPerSecond;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getOutputPerSecond() {
            return outputPerSecond;
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