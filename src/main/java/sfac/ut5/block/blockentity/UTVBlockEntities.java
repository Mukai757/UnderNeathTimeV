package sfac.ut5.block.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.block.UTVBlocks;

import java.util.function.Supplier;

/**
 * @author AoXiang_Soar
 */
public class UTVBlockEntities {
	public static final Supplier<BlockEntityType<TimeBinderBlockEntity>> TIME_BINDER_BLOCK_ENTITY = UnderneathTimeV.BLOCK_ENTITY_TYPES.register(
	       "time_binder",
	       () -> BlockEntityType.Builder.of(
	    		   TimeBinderBlockEntity::new,
	               UTVBlocks.TIME_BIND_ALTAR.get(), UTVBlocks.TIME_SPINDLE_COUPLER.get()
	       )
	       .build(null)
	  );

	private UTVBlockEntities() {
	} // No one initializes this!

	public static void init() {
	}
}
