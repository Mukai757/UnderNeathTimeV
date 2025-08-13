package sfac.ut5.data.loot_table;

import java.util.Set;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import sfac.ut5.UnderneathTimeV;

/**
 * @author AoXiang_Soar
 */

public class BlockLootProvider extends BlockLootSubProvider {
    
	public BlockLootProvider(HolderLookup.Provider lookupProvider) {
		super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
	}

	@Override
	protected void generate() {
		for(Block block : getKnownBlocks()) {
			// TODO createOreDrop
			if(block instanceof LiquidBlock)
				continue;
			dropSelf(block);
		}
	}
	
    @Override
    protected Iterable<Block> getKnownBlocks() {
    	return UnderneathTimeV.BLOCKS.getEntries()
                .stream()
                .map(e -> (Block) e.value())
                .toList();
    }
}
