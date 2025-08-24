package sfac.ut5.data.model;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.block.UTVBlocks;

/**
 * @author AoXiang_Soar
 */

public class UTVBlockStateProvider extends BlockStateProvider {

	public UTVBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, UnderneathTimeV.MOD_ID, exFileHelper);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void registerStatesAndModels() {
		for(DeferredHolder<Block, ? extends Block> block : UnderneathTimeV.getKnownBlocks()) {
			if (block.is(UTVBlocks.REWINDING_CHEST)) {
				ModelFile model = models().getBuilder(block.getKey().location().getPath()).texture("particle", mcLoc("block/oak_planks"));
				simpleBlock(block.get(), model);
				continue;
			}
			simpleBlock(block.get());
		}
		
		// TODO Change to a complex model
//		simpleBlock(UTVBlocks.TIME_BIND_ALTAR.get());
//		simpleBlock(UTVBlocks.TIME_SPINDLE_COUPLER.get());
	}
}
