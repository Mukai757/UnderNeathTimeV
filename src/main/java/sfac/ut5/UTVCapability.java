package sfac.ut5;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import sfac.ut5.block.blockentity.UTVBlockEntities;

/**
 * @author Mukai
 */
public class UTVCapability {

    private UTVCapability() {}
    
    public static void init() {}

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(
			Capabilities.FluidHandler.BLOCK,
			UTVBlockEntities.TIME_PRODUCER.get(),
			(be, side) -> be instanceof IFluidHandler handler ? handler : null
		);
	}
}