package sfac.ut5.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;
import sfac.ut5.block.blockentity.TimeProducerBlockEntity;
import sfac.ut5.item.UTVItems;


public class TimeProducerBlock extends Block implements EntityBlock {

    public TimeProducerBlock(Properties properties) {
        super(properties);
    }


    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack, BlockState blockstate, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
    ) {
        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof TimeProducerBlockEntity producer) {
                // 输出当前流体量
                FluidStack fluid = producer.getFluidInTank(0);
                player.sendSystemMessage(Component.literal("当前流体量: " + fluid.getAmount() + "mB"));

                // 检查空桶和流体量
                ItemStack heldItem = player.getItemInHand(hand);
                if (heldItem.is(Items.BUCKET) && fluid.getAmount() >= 1000) {
                    // 消耗流体
                    producer.drain(1000, IFluidHandler.FluidAction.EXECUTE);
                    // 替换为流体桶
                    heldItem.shrink(1);
                    if (heldItem.isEmpty()) {
                        player.setItemInHand(hand, new ItemStack(UTVItems.CHRONOPLASM_BUCKET.get()));
                    } else {
                        player.getInventory().add(new ItemStack(UTVItems.CHRONOPLASM_BUCKET.get()));
                    }
                    return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
                }
            }
        }
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
        return (lvl, pos, st, be) -> {
            if (be instanceof TimeProducerBlockEntity producer) {
                producer.tick();
            }
        };
    }

    public enum ProducerLevel {
        SECOND_PRODUCER(20, 1000, 1),
        MINUTE_PRODUCER(20 * 60, 10_000, 60),
        HOUR_PRODUCER(20 * 60 * 60, 100_000, 60 * 60),
        DAY_PRODUCER(20 * 60 * 60 * 24, 1_000_000, 60 * 60 * 24),
        MONTH_PRODUCER(20 * 60 * 60 * 24 * 30, 10_000_000, 60 * 60 * 24 * 30),
        YEAR_PRODUCER(20 * 60 * 60 * 24 * 365, 100_000_000, 60 * 60 * 24 * 365);

        private final int ticksPerOperation;
        private final int capacity;
        private final int outputPerSecond;

        ProducerLevel(int ticksPerOperation, int capacity, int outputPerSecond) {
            this.ticksPerOperation = ticksPerOperation;
            this.capacity = capacity;
            this.outputPerSecond = outputPerSecond;
        }

        public int getTicksPerOperation() {
            return ticksPerOperation;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getOutputPerSecond() {
            return outputPerSecond;
        }

        public int getFluidAmount() {
            return 1;
        }
    }
}