package underneathtimev.block.blockentity;

import java.util.function.Supplier;

import net.minecraft.world.level.block.entity.BlockEntityType;
import underneathtimev.UnderneathTimeV;
import underneathtimev.block.UTVBlocks;

/**
 * @author AoXiang_Soar
 */
public class UTVBlockEntities {
	public static final Supplier<BlockEntityType<TimeAnvilBlockEntity>> TIME_ANVIL_BLOCK_ENTITY = UnderneathTimeV.BLOCK_ENTITY_TYPES.register(
	       "time_anvil",
	       () -> BlockEntityType.Builder.of(
	    		   TimeAnvilBlockEntity::new,
	               UTVBlocks.CHRONOSTICE_CRYSTAL_ORE.get() // 这里填任意多个方块，所填的方块均会应用这个方块实体
	       )
	       .build(null)
	  );
}
