package sfac.ut5.data.model;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import sfac.ut5.UnderneathTimeV;

/**
 * @author AoXiang_Soar
 */

public class UTVItemModelProvider extends ItemModelProvider {

	public UTVItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, UnderneathTimeV.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		for(DeferredHolder<Block, ? extends Block> block : UnderneathTimeV.getKnownBlocks()) {
			withExistingParent(block.getId().toString(), modLoc("block/"+block.getKey().location().getPath()));
		}
		for(DeferredHolder<Item, ? extends Item> item : UnderneathTimeV.getKnownItems()) {
			if (item.get() instanceof BlockItem) continue;
			String name = item.getKey().location().getPath();
			withExistingParent(item.getId().toString(), mcLoc("item/generated")).texture("layer0", "item/"+name);
		}
	}

}
