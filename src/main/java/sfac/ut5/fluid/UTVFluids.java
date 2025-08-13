package sfac.ut5.fluid;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.block.UTVBlocks;
import sfac.ut5.item.UTVItems;

import java.util.function.Supplier;

/**
 * @author Mukai
 */
public class UTVFluids {

	private static final BaseFlowingFluid.Properties FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
			UTVFluidTypes.CHRONOPLASM, DeferredHolder.create(Registries.FLUID, getKey("chronoplasm")),
			DeferredHolder.create(Registries.FLUID, getKey("flowing_chronoplasm"))).bucket(UTVItems.CHRONOPLASM_BUCKET)
			.slopeFindDistance(2).levelDecreasePerBlock(2).block(UTVBlocks.CHRONOPLASM_BLOCK);

	public static final Supplier<FlowingFluid> CHRONOPLASM_SOURCE = UnderneathTimeV.FLUIDS.register("chronoplasm",
			() -> new BaseFlowingFluid.Source(UTVFluids.FLUID_PROPERTIES));
	public static final Supplier<FlowingFluid> CHRONOPLASM_FLOWING = UnderneathTimeV.FLUIDS.register("flowing_chronoplasm",
			() -> new BaseFlowingFluid.Flowing(UTVFluids.FLUID_PROPERTIES));

	private UTVFluids() {
	}

	public static void init() {
	}

	private static ResourceLocation getKey(String name) {
		return ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID, name);
	}

	public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
	    event.registerFluidType(new ChronoplasmFluidTypeExtensions(), UTVFluidTypes.CHRONOPLASM.get());
	}
}