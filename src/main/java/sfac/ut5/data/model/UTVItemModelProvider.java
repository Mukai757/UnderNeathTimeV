package sfac.ut5.data.model;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.block.UTVBlocks;

/**
 * @author AoXiang_Soar
 */

public class UTVItemModelProvider extends ItemModelProvider {

	public UTVItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, UnderneathTimeV.MOD_ID, existingFileHelper);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void registerModels() {
		for(DeferredHolder<Block, ? extends Block> block : UnderneathTimeV.getKnownBlocks()) {
			var path = block.getKey().location().getPath();
			if (block.get() instanceof LiquidBlock) {
				withExistingParent(path, mcLoc("item/generated")).texture("layer0", "block/"+path);
				continue;
			}
			if (block.is(UTVBlocks.REWINDING_CHEST)) {
				genChestModel(path);
				continue;
			}
			withExistingParent(block.getId().toString(), modLoc("block/"+path));
		}
		for(DeferredHolder<Item, ? extends Item> item : UnderneathTimeV.getKnownItems()) {
			if (item.get() instanceof BlockItem) continue;
			String name = item.getKey().location().getPath();
			withExistingParent(item.getId().toString(), mcLoc("item/generated")).texture("layer0", "item/"+name);
		}
	}

	private void genChestModel(String name) {
        getBuilder(name)
        .parent(new ModelFile.UncheckedModelFile(mcLoc("builtin/entity")))
        .texture("particle", mcLoc("block/oak_planks")) // TODO
        .transforms()
            .transform(ItemDisplayContext.GUI)
                .rotation(30, 45, 0)
                .translation(0, 0, 0)
                .scale(0.625f)
            .end()
            .transform(ItemDisplayContext.GROUND)
                .rotation(0, 0, 0)
                .translation(0, 3, 0)
                .scale(0.25f)
            .end()
            .transform(ItemDisplayContext.HEAD)
                .rotation(0, 180, 0)
                .translation(0, 0, 0)
                .scale(1)
            .end()
            .transform(ItemDisplayContext.FIXED)
                .rotation(0, 180, 0)
                .translation(0, 0, 0)
                .scale(0.5f)
            .end()
            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(75, 315, 0)
                .translation(0, 2.5f, 0)
                .scale(0.375f)
            .end()
            .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, 315, 0)
                .translation(0, 0, 0)
                .scale(0.4f)
            .end();
	}
}
