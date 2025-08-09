package underneathtimev.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import underneathtimev.UnderNeathTimeV;

import java.util.function.Function;

/**
 * @author AoXiang_Soar
 */

public class UTVBlocks {
	/**
	 * UTV utilizes this method to automatically register blocks and add them to the
	 * Creative Mode inventory. See also
	 * <code>underneathtimev.UnderNeathTimeV.addItem2Tab()</code>.
	 */
	private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, ? extends B> func,
			BlockBehaviour.Properties props, DeferredHolder<CreativeModeTab, CreativeModeTab> tab) {
		DeferredBlock<B> block = UnderNeathTimeV.BLOCKS.registerBlock(name, func, props);
		UnderNeathTimeV.ITEMS.registerSimpleBlockItem(block);
		UnderNeathTimeV.addItem2Tab(tab, block);
		return block;
	}

    public static final DeferredBlock<Block> TIME_SAND_ORE = register("time_sand_ore", Block::new, 
            BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST), UnderNeathTimeV.MAIN_TAB);
    
    public static final DeferredBlock<DropExperienceBlock> CHRONOSTICE_CRYSTAL_ORE = register("chronostice_crystal_ore", 
    		props -> new DropExperienceBlock(UniformInt.of(2, 4), props),
            BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.STONE), 
            UnderNeathTimeV.MAIN_TAB);

    public static final DeferredBlock<DropExperienceBlock> SPACE_DUST_ORE = register("space_dust_ore", 
    		props -> new DropExperienceBlock(UniformInt.of(3, 6), props),
            BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE), 
            UnderNeathTimeV.MAIN_TAB);

    public static final DeferredBlock<DropExperienceBlock> VOID_CRYSTAL_ORE = register("void_crystal_ore", 
    		props -> new DropExperienceBlock(UniformInt.of(3, 6), props),
            BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE), 
            UnderNeathTimeV.MAIN_TAB);

}