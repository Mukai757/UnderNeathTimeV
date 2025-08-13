package sfac.ut5.fluid;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.block.UTVBlocks;
import sfac.ut5.item.UTVItems;

import java.util.function.Supplier;

/**
 * @author Mukai
 */
public class UTVFluid {
	public static final Supplier<Fluid> CHRONOPLASM_SOURCE = UnderneathTimeV.FLUIDS.register("chronoplasm_still",
			() -> new BaseFlowingFluid.Source(UTVFluid.FLUID_PROPERTIES));
	public static final Supplier<FlowingFluid> CHRONOPLASM_FLOW = UnderneathTimeV.FLUIDS.register("chronoplasm_flow",
			() -> new BaseFlowingFluid.Flowing(UTVFluid.FLUID_PROPERTIES));

	private static final BaseFlowingFluid.Properties FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
			UTVFluidType.CHRONOPLASM, UTVFluid.CHRONOPLASM_SOURCE, UTVFluid.CHRONOPLASM_FLOW)
			.bucket(UTVItems.CHRONOPLASM_BUCKET).slopeFindDistance(2).levelDecreasePerBlock(2)
			.block(()->UTVBlocks.CHRONOPLASM_BLOCK.get());
	

	private UTVFluid() {}
	
	public static void init() {}
}