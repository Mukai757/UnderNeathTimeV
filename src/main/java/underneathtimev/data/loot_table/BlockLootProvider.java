package underneathtimev.data.loot_table;

import java.util.Set;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import underneathtimev.UnderNeathTimeV;
import underneathtimev.block.UTVBlocks;

/**
 * @author AoXiang_Soar
 */

public class BlockLootProvider extends BlockLootSubProvider {
    public record Drops(String name, Builder lootTableBuilder) {}
    
	public BlockLootProvider(HolderLookup.Provider lookupProvider) {
		super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
	}

	@Override
	protected void generate() {
		// TODO createOreDrop
		dropSelf(UTVBlocks.CHRONOSTICE_CRYSTAL_ORE.get());
		dropSelf(UTVBlocks.SPACE_DUST_ORE.get());
		dropSelf(UTVBlocks.TIME_SAND_ORE.get());
		dropSelf(UTVBlocks.VOID_CRYSTAL_ORE.get());
	}
	
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return UnderNeathTimeV.BLOCKS.getEntries()
                .stream()
                .map(e -> (Block) e.value())
                .toList();
    }
}
