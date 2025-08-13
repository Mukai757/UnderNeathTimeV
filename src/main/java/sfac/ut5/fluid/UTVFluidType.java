package sfac.ut5.fluid;

import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.fluids.FluidType;
import sfac.ut5.UnderneathTimeV;

import java.util.function.Supplier;

/**
 * @author Mukai
 */
public class UTVFluidType {
	public static final Supplier<FluidType> CHRONOPLASM = UnderneathTimeV.FLUID_TYPES.register("chronoplasm",
			() -> new Chronoplasm(FluidType.Properties.create().lightLevel(2).density(15).viscosity(5)
					.sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)));
	
	private UTVFluidType() {}
	
	public static void init() {}
}