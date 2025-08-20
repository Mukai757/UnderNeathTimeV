package sfac.ut5.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import sfac.ut5.block.TimeProducerBlock;
import sfac.ut5.block.capability.UTVCapability;
import sfac.ut5.fluid.UTVFluids;

public class TimeProducerBlockEntity extends BlockEntity implements IFluidHandler {

    private int tickCounter = 0;
    private final TimeProducerBlock.ProducerLevel level = TimeProducerBlock.ProducerLevel.SECOND_PRODUCER;
    private final FluidTank fluidTank = new FluidTank(1000); // 1000mB 容量

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
        return fluidTank.isFluidValid(tank, stack);
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

    public TimeProducerBlockEntity(BlockPos pos, BlockState state) {
        super(UTVBlockEntities.SECOND_PRODUCER.get(), pos, state);
    }

    public void tick() {
        if (level == null) return;

        tickCounter++;
        if (tickCounter >= level.getTicksPerOperation()) {
            tickCounter = 0;
            produceFluid();
        }
    }

    private void produceFluid() {
        if (getLevel() != null) {
            FluidStack fluidStack = new FluidStack(UTVFluids.CHRONOPLASM_SOURCE.get(), level.getFluidAmount());
            BlockPos targetPos = getBlockPos().below();
            
            // NeoForge 1.21.1能力API调用
            var handler = getLevel().getCapability(
                UTVCapability.FLUID_HANDLER,
                targetPos,
                getLevel().getBlockState(targetPos),
                getLevel().getBlockEntity(targetPos),
                Direction.UP
            );
            
            if (handler != null) {
                handler.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
            }
        }
    }
}