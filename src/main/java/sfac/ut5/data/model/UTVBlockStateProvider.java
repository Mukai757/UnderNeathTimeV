package sfac.ut5.data.model;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import sfac.ut5.UnderneathTimeV;

/**
 * @author AoXiang_Soar
 */

public class UTVBlockStateProvider extends BlockStateProvider {

	public UTVBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, UnderneathTimeV.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		for(DeferredHolder<Block, ? extends Block> block : UnderneathTimeV.getKnownBlocks()) {
			if (block.get() instanceof LiquidBlock) {
				
			}
			simpleBlock(block.get());
		}
		
		// TODO Change to a complex model
//		simpleBlock(UTVBlocks.TIME_BIND_ALTAR.get());
//		simpleBlock(UTVBlocks.TIME_SPINDLE_COUPLER.get());
	}
}
