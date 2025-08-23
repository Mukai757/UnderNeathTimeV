package sfac.ut5.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import sfac.ut5.TimeSystem;
import sfac.ut5.block.blockentity.SpaceProducerBlockEntity;

public class SpaceProducerBlock extends Block {

    public ProducerLevel producerLevel;

    public SpaceProducerBlock(Properties properties) {
        super(properties);
    }


    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
    ) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof SpaceProducerBlockEntity blockEntity) {
                    //打开gui
                    serverPlayer.openMenu(new SimpleMenuProvider((MenuConstructor) blockEntity,
                            Component.translatable("block.ut5.space_producer_coupler")), pos);

            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    public enum ProducerLevel {
        SECOND_PRODUCER(20, 1000, TimeSystem.second()),
        MINUTE_PRODUCER(20, 10_000, TimeSystem.minute()/2),
        HOUR_PRODUCER(20, 100_000, TimeSystem.hour()/4),
        DAY_PRODUCER(20, 1_000_000, TimeSystem.day()/8),
        MONTH_PRODUCER(20, 10_000_000, TimeSystem.month()/16),
        YEAR_PRODUCER(20, 100_000_000, TimeSystem.year()/32);

        private final int ticksPerOperation;
        private final int energycapacity;
        private final int outputenergy;

        ProducerLevel(int ticksPerOperation, int Energycapacity, int outputEnergy) {
            this.ticksPerOperation = ticksPerOperation;
            this.energycapacity = Energycapacity;
            this.outputenergy = outputEnergy;
    }
        public int getTicksPerOperation() {
            return ticksPerOperation;
        }

        public int getCapacity() {
            return energycapacity;
        }

        public int getOutputPerSecond() {
            return outputenergy;
        }

}

}


