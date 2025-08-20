package sfac.ut5.structure;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import sfac.ut5.block.UTVBlocks;

import java.util.Set;

/**
 * @author AoXiang_Soar
 */
public class TimeCoreAltarStructure implements IUTVStructure {
    public static final TimeCoreAltarStructure INSTANCE = new TimeCoreAltarStructure();
    
	// Adds a single aisle to this pattern, going in the z axis. (so multiple calls to this will increase the z-size by 1)
	// Note: You can rotate your head 90° to the right or turn your screen 90° to the left. XD
    private static final String[][] ORIGINAL_PATTERN = {
            {"   ", " A ", "   "},
            {"   ", "DCB", "   "},
            {"   ", " C ", "   "}
    };
    private static final Set<BlockPattern> PATTERNS = INSTANCE.generateRotatedPatterns();
    
	@Override
	public Set<BlockMapper> getMappings() {
		return Set.of(
				new BlockMapper('A', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.GOLD_BLOCK))),
				new BlockMapper('B', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.REDSTONE_BLOCK))),
				new BlockMapper('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(UTVBlocks.TIME_CORE_ALTAR.get()))),
				new BlockMapper('D', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.DIAMOND_BLOCK))),
				new BlockMapper(' ', BlockInWorld.hasState(BlockStatePredicate.ANY))
				);
	}

    private TimeCoreAltarStructure() {}
    
	@Override
	public String[][] getOriginalPatten() {
		return ORIGINAL_PATTERN;
	}

	@Override
	public Set<BlockPattern> getPatterns() {
		return PATTERNS;
	}
}
