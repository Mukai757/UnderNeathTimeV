package sfac.ut5.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import sfac.ut5.block.UTVBlocks;

import java.util.Set;

/**
 * @author Mukai
 */
public class TimeCoreAltarStructure {
	// Adds a single aisle to this pattern, going in the z axis. (so multiple calls to this will increase the z-size by 1)
	private final static BlockPattern pattern0 = BlockPatternBuilder.start()
			.aisle("AAA", "AAA", "AAA").aisle("AAA", "ACA", "ABA").aisle("AAA", "AAA", "AAA")
			.where('A', BlockInWorld.hasState(BlockStatePredicate.ANY))
			.where('B', BlockInWorld.hasState(BlockStatePredicate.ANY))
			.where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(UTVBlocks.TIME_CORE_ALTAR.get()))).build();
	private final static BlockPattern pattern90 = BlockPatternBuilder.start()
			.aisle("AAA", "AAA", "AAA").aisle("AAA", "ACB", "AAA").aisle("AAA", "AAA", "AAA")
			.where('A', BlockInWorld.hasState(BlockStatePredicate.ANY))
			.where('B', BlockInWorld.hasState(BlockStatePredicate.ANY))
			.where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(UTVBlocks.TIME_CORE_ALTAR.get()))).build();
	private final static BlockPattern pattern180 = BlockPatternBuilder.start()
			.aisle("AAA", "AAA", "AAA").aisle("ABA", "ACA", "AAA").aisle("AAA", "AAA", "AAA")
			.where('A', BlockInWorld.hasState(BlockStatePredicate.ANY))
			.where('B', BlockInWorld.hasState(BlockStatePredicate.ANY))
			.where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(UTVBlocks.TIME_CORE_ALTAR.get()))).build();
	private final static BlockPattern pattern270 = BlockPatternBuilder.start()
			.aisle("AAA", "AAA", "AAA").aisle("AAA", "BCA", "AAA").aisle("AAA", "AAA", "AAA")
			.where('A', BlockInWorld.hasState(BlockStatePredicate.ANY))
			.where('B', BlockInWorld.hasState(BlockStatePredicate.ANY))
			.where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(UTVBlocks.TIME_CORE_ALTAR.get()))).build();

	private static Set<BlockPattern> patterns = Set.of(pattern0, pattern90, pattern180, pattern270);
	
	public static boolean isMatch(Level level, BlockPos pos) {
		return patterns.stream().anyMatch(pattern -> pattern.find(level, pos) != null);
	}

}
