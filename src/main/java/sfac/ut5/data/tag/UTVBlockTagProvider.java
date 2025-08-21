package sfac.ut5.data.tag;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.block.UTVBlocks;

/**
 * @author AoXiang_Soar
 */

public class UTVBlockTagProvider extends BlockTagsProvider {

	public UTVBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, UnderneathTimeV.MOD_ID, null);
    }

	@Override
	protected void addTags(Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .add(UTVBlocks.CHRONOSTICE_CRYSTAL_ORE.get())
        .add(UTVBlocks.VOID_CRYSTAL_ORE.get())
        .add(UTVBlocks.TIME_BIND_ALTAR.get())
        .add(UTVBlocks.TIME_SPINDLE_COUPLER.get())
        .add(UTVBlocks.TIME_CORE_ALTAR.get())
        .add(UTVBlocks.SECOND_PRODUCER.get())
        .add(UTVBlocks.MINUTE_PRODUCER.get())
        .add(UTVBlocks.HOUR_PRODUCER.get())
        .add(UTVBlocks.DAY_PRODUCER.get())
        .add(UTVBlocks.MONTH_PRODUCER.get())
        .add(UTVBlocks.YEAR_PRODUCER.get());
		
        tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .add(UTVBlocks.SPACE_DUST_ORE.get())
        .add(UTVBlocks.TIME_SAND_ORE.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
        .add(UTVBlocks.MINUTE_PRODUCER.get());
        
        tag(BlockTags.NEEDS_IRON_TOOL)
        .add(UTVBlocks.VOID_CRYSTAL_ORE.get())
        .add(UTVBlocks.SPACE_DUST_ORE.get())
        .add(UTVBlocks.TIME_SAND_ORE.get())
        .add(UTVBlocks.TIME_BIND_ALTAR.get())
        .add(UTVBlocks.TIME_SPINDLE_COUPLER.get())
        .add(UTVBlocks.TIME_CORE_ALTAR.get())
        .add(UTVBlocks.HOUR_PRODUCER.get())
        .add(UTVBlocks.DAY_PRODUCER.get());
        
        tag(BlockTags.NEEDS_DIAMOND_TOOL)
        .add(UTVBlocks.CHRONOSTICE_CRYSTAL_ORE.get())
        .add(UTVBlocks.MONTH_PRODUCER.get())
        .add(UTVBlocks.YEAR_PRODUCER.get());
	}

}
