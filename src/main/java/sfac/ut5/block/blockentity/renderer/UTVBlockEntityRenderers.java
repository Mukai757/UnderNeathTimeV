package sfac.ut5.block.blockentity.renderer;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import sfac.ut5.block.blockentity.UTVBlockEntities;

/**
 * @author AoXiang_Soar
 */

public class UTVBlockEntityRenderers {
	
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
	    event.registerBlockEntityRenderer(
	            UTVBlockEntities.REWINDING_CHEST.get(),
	            RewindingChestBER::new);
	}
	
	private UTVBlockEntityRenderers() {}
}
