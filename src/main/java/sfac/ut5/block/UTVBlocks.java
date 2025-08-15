package sfac.ut5.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.fluid.UTVFluids;

import java.util.function.Function;

/**
 * @author AoXiang_Soar
 */

public class UTVBlocks {

	/**
	 * UTV utilizes this method to automatically register blocks and add them to the
	 * Creative Mode inventory. See also {@link UnderneathTimeV#addItem2Tab}.
	 * 
	 * @param name  The register name of the block
	 * @param func  Initialization function called during registration; if no additional functionality is needed, 
	 * 				<code>Block::new</code> can be used
	 * @param props Properties of the block
	 * @param tab   Which creative tab the block to be added, use <code>null</code> if no one is suitable
	 */
	private static <B extends Block> DeferredBlock<B> register(String name,
			Function<BlockBehaviour.Properties, ? extends B> func, BlockBehaviour.Properties props,
			DeferredHolder<CreativeModeTab, CreativeModeTab> tab) {
		DeferredBlock<B> block = UnderneathTimeV.BLOCKS.registerBlock(name, func, props);
		UnderneathTimeV.ITEMS.registerSimpleBlockItem(block);
		UnderneathTimeV.addItem2Tab(tab, block);
		return block;
	}


	public static final DeferredBlock<LiquidBlock> CHRONOPLASM_BLOCK = register("chronoplasm",
			props -> new LiquidBlock(UTVFluids.CHRONOPLASM_SOURCE.get(), props),
			BlockBehaviour.Properties.ofFullCopy(Blocks.WATER), null);

	public static final DeferredBlock<Block> TIME_SAND_ORE = register("time_sand_ore", Block::new,
			BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().sound(SoundType.SAND),
			UnderneathTimeV.MAIN_TAB);

	public static final DeferredBlock<DropExperienceBlock> CHRONOSTICE_CRYSTAL_ORE = register("chronostice_crystal_ore",
			props -> new DropExperienceBlock(UniformInt.of(2, 4), props),
			BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST),
			UnderneathTimeV.MAIN_TAB);

	public static final DeferredBlock<DropExperienceBlock> SPACE_DUST_ORE = register("space_dust_ore",
			props -> new DropExperienceBlock(UniformInt.of(3, 6), props),
			BlockBehaviour.Properties.of().strength(2.5f).requiresCorrectToolForDrops().sound(SoundType.SOUL_SAND),
			UnderneathTimeV.MAIN_TAB);

	public static final DeferredBlock<DropExperienceBlock> VOID_CRYSTAL_ORE = register("void_crystal_ore",
			props -> new DropExperienceBlock(UniformInt.of(3, 6), props),
			BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE),
			UnderneathTimeV.MAIN_TAB);

	public static final DeferredBlock<TimeBinderBlock> TIME_BIND_ALTAR = register("time_bind_altar", TimeBinderBlock::new,
			BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().sound(SoundType.ANVIL),
			UnderneathTimeV.MAIN_TAB);

	public static final DeferredBlock<TimeBinderBlock> TIME_SPINDLE_COUPLER = register("time_spindle_coupler", TimeBinderBlock::new,
			BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().sound(SoundType.ANVIL),
			UnderneathTimeV.MAIN_TAB);

	private UTVBlocks() {} // No one initializes this!

	public static void init() {
	}
}