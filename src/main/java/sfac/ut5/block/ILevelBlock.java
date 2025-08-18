package sfac.ut5.block;

import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * @author AoXiang_Soar
 */

public interface ILevelBlock {
	/** The level of block. Use for machines */
	public static final IntegerProperty LEVEL = IntegerProperty.create("ut5_block_level", 0, 63); // If the range is too wide, it will cause OOM
}
