package sfac.ut5.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import sfac.ut5.TimeSystem;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.block.ILevelBlock;
import sfac.ut5.block.SpaceProducerBlock;
import sfac.ut5.fluid.UTVFluidTypes;
import sfac.ut5.gui.SpaceProducerMenu;

/**
 * @author Mukai
 * @author AoXiang_Soar
 */
public class SpaceProducerBlockEntity extends UTVBaseContainerBlockEntity implements ILevelBlock, IFluidHandler {
    private int space = 0;
    private int timeLeft = 0;
    
	private final FluidTank fluidTank;
	private final SpaceProducerBlock.ProducerLevel producerLevel;

	private final static String SPACE_TAG_KEY = UnderneathTimeV.MOD_ID + ":space";
	private final static String TIME_LEFT_KEY = UnderneathTimeV.MOD_ID + ":time_left";

	public SpaceProducerBlockEntity(BlockPos pos, BlockState state) {
		super(UTVBlockEntities.SPACE_PRODUCER.get(), pos, state, new int[] {0}, null);
		var block = (SpaceProducerBlock) state.getBlock();
		this.producerLevel = block.producerLevel;
		this.fluidTank = new FluidTank(producerLevel.getCapacity());
	}

	public SpaceProducerBlock.ProducerLevel getProducerLevel() {
		return producerLevel;
	}

	public int getSpace() {
		return space;
	}
	
	public int getTimeLeft() {
		return timeLeft;
	}
	
	/**
     * Link to {@link SpaceProducerBlock#getTicker}
     */
    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
		if (blockEntity instanceof SpaceProducerBlockEntity entity) {
			produceSpace(level, pos, state, entity);
			if (entity.timeLeft > 0) entity.timeLeft--;
		}
	}
    
    private static void produceSpace(Level level, BlockPos pos, BlockState state, SpaceProducerBlockEntity entity) {
		ItemStack fuelStack = entity.getItem(0);
		FluidStack fluidStack = entity.getFluidInTank(0);

    	if (fuelStack.isEmpty() && entity.timeLeft <= 0) return;
		int rate = 1;

		if (entity.timeLeft == 0) {
			fuelStack.shrink(1);
			entity.timeLeft = TimeSystem.minute();
		}

		if (!fluidStack.isEmpty()) {
			entity.drain(1, IFluidHandler.FluidAction.EXECUTE);
			rate = 2;
		}
		entity.increaseSpace(rate);
		entity.setChanged();
	}
    
    private void increaseSpace(int rate) {
    	space = Math.min(space + rate * producerLevel.getOutputRate(), producerLevel.getCapacity());
    }

	public FluidStack getFluidStored() {
		return fluidTank.getFluid();
	}

	public boolean stillValid(Player player) {
		boolean ture = false;
		return ture;
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
        this.space = tag.getInt(SPACE_TAG_KEY);
        this.timeLeft = tag.getInt(TIME_LEFT_KEY);
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        fluidTank.writeToNBT(registries, tag);
        tag.putInt(SPACE_TAG_KEY, this.space);
        tag.putInt(TIME_LEFT_KEY, this.timeLeft);
    }

	@Override
	protected Component getDefaultName() {
		String s = "block.ut5.space_producer_block_" + producerLevel.getId();
		return Component.translatable(s);
	}

	@Override
	protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
		return new SpaceProducerMenu(containerId, inventory, this);
	}
}
