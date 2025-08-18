package sfac.ut5.block.blockentity;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.neoforged.neoforge.registries.DeferredBlock;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.block.UTVBlocks;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * @author AoXiang_Soar
 */
public class UTVBlockEntities {
	public static final Supplier<BlockEntityType<TimeBinderBlockEntity>> TIME_BINDER_BLOCK_ENTITY = 
			register("time_binder", TimeBinderBlockEntity::new, UTVBlocks.TIME_BIND_ALTAR, UTVBlocks.TIME_SPINDLE_COUPLER);

	@SafeVarargs
	private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String name,
			BlockEntitySupplier<T> entityBlock, DeferredBlock<? extends Block>... blocks) {
		return UnderneathTimeV.BLOCK_ENTITY_TYPES.register(name, () -> BlockEntityType.Builder
				.of(entityBlock, Arrays.stream(blocks).map(DeferredBlock::get).toArray(Block[]::new)).build(null));
	}

	private UTVBlockEntities() {
	} // No one initializes this!

	public static void init() {
	}
}
