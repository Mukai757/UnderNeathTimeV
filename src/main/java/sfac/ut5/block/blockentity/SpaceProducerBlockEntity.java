package sfac.ut5.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import sfac.ut5.block.ILevelBlock;
import sfac.ut5.block.SpaceProducerBlock;
import sfac.ut5.fluid.UTVFluids;
import sfac.ut5.item.UTVItems;

public class SpaceProducerBlockEntity extends BlockEntity implements ILevelBlock {


    private final ItemStackHandler fuelHandler = new ItemStackHandler(1) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.is(UTVItems.CHRONOPLASM_BUCKET.get()); // 燃料物品
        }
    };
    private final FluidTank TimefluidTank = new FluidTank(10000) {
        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == UTVFluids.CHRONOPLASM_SOURCE.get();
        }
    };
    private final IItemHandler itemHandler = fuelHandler;
    private final IFluidHandler fluidHandler = TimefluidTank;
    private int energyStored;
    private int energyCapacity;
    private int productionRate;
    private final FluidTank fluidTank;

    private final SpaceProducerBlock.ProducerLevel producerLevel;
    Container Container;

    public SpaceProducerBlockEntity(BlockPos pos, BlockState state) {
        super(UTVBlockEntities.SPACE_PRODUCER.get(), pos, state);
        var block = (SpaceProducerBlock) state.getBlock();
        producerLevel = block.producerLevel;
        fluidTank = new FluidTank(producerLevel.getCapacity());
        updateLevel(state);
    }

    private void updateLevel(BlockState state) {
      //TOdo
    }

    public SpaceProducerBlock.ProducerLevel getProducerLevel() {
        return producerLevel;
    }
    public void tick() {
        if (level == null || level.isClientSide) return;

        // 燃料消耗逻辑
        ItemStack fuelStack = fuelHandler.getStackInSlot(0);
        if (!fuelStack.isEmpty()) {
            fuelStack.shrink(1);
            energyStored = Math.min(energyStored + productionRate, energyCapacity);
        }

        // 流体加速逻辑
        FluidStack fluidStack = TimefluidTank.getFluid();
        if (!fluidStack.isEmpty()) {
            TimefluidTank.drain(1, IFluidHandler.FluidAction.EXECUTE);
            energyStored = Math.min(energyStored + productionRate * 2, energyCapacity);
        }
    }

    public int getEnergyStored() {
        return energyStored;
    }

    public int getEnergyCapacity() {
        return energyCapacity;
    }

    public FluidStack getFluidStored() {
        return TimefluidTank.getFluid();
    }


    //临时代码Todo
    public Container getFuelHandler() {
        return Container;}

    public boolean stillValid(Player player) {
        boolean ture = false;
        return ture;
    }
}
