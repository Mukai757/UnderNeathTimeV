package sfac.ut5.block.blockentity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import sfac.ut5.block.TimeProducerBlock;
import sfac.ut5.fluid.UTVFluidTypes;
import sfac.ut5.fluid.UTVFluids;

public class TimeProducerBlockEntity extends BlockEntity implements IFluidHandler {

    private int tickCounter = 0; // Don't need to save... need it?
    private final TimeProducerBlock.ProducerLevel producerLevel;
    private final FluidTank fluidTank;
    
    public TimeProducerBlockEntity(BlockPos pos, BlockState state) {
        super(UTVBlockEntities.TIME_PRODUCER.get(), pos, state);
        var block = (TimeProducerBlock) state.getBlock();
        producerLevel = block.producerLevel;
        fluidTank = new FluidTank(producerLevel.getCapacity());
    }

    @Override
    public int getTanks() {
        return fluidTank.getTanks();
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        return fluidTank.getFluidInTank(tank);
    }

    @Override
    public int getTankCapacity(int tank) {
        return fluidTank.getTankCapacity(tank);
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        return fluidTank.isFluidValid(tank, stack) && stack.is(UTVFluidTypes.CHRONOPLASM.get());
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return fluidTank.fill(resource, action);
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return fluidTank.drain(resource, action);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return fluidTank.drain(maxDrain, action);
    }
    
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        fluidTank.readFromNBT(registries, tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        fluidTank.writeToNBT(registries, tag);
    }
    
    /**
     * Called when chunk is loaded
     */
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    /**
     * Called when a block update occurs
     */
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    
    public int getTickCounter() {
    	return tickCounter;
    }
    
    public void increaseTickCounter() {
    	tickCounter++;
    }
    
    public void resetTickCounter() {
    	tickCounter = 0;
    }
    
    public TimeProducerBlock.ProducerLevel getProducerLevel() {
		return producerLevel;
    }
    /**
     * Link to {@link TimeProducerBlock#getTicker}
     */
    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
		if (level == null || level.isClientSide) return;
		if (blockEntity instanceof TimeProducerBlockEntity be) {
			be.increaseTickCounter();
			if (be.getTickCounter() >= be.getProducerLevel().getTicksPerOperation()) {
				be.resetTickCounter();
				produceFluid(level, pos, state, be);
			}
		}
    }

    private static void produceFluid(Level level, BlockPos pos, BlockState state, TimeProducerBlockEntity blockEntity) {
        if (level != null) {
			FluidStack fluidStack = new FluidStack(UTVFluids.CHRONOPLASM_SOURCE.get(), blockEntity.producerLevel.getOutputPerSecond());
			var handler = level.getCapability(Capabilities.FluidHandler.BLOCK, pos.below(), state, blockEntity, Direction.UP);
            
            if (handler != null) {
                handler.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
                blockEntity.setChanged();
            }
        }
    }
}