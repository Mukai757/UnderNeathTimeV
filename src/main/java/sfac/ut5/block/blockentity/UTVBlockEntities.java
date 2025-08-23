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
	public static final Supplier<BlockEntityType<TimeBinderBlockEntity>> TIME_BINDER = 
			register("time_binder", TimeBinderBlockEntity::new, UTVBlocks.TIME_BIND_ALTAR, UTVBlocks.TIME_SPINDLE_COUPLER);
			
	public static final Supplier<BlockEntityType<TimeProducerBlockEntity>> TIME_PRODUCER = register("time_producer",
			TimeProducerBlockEntity::new, UTVBlocks.SECOND_PRODUCER, UTVBlocks.MINUTE_PRODUCER,
			UTVBlocks.HOUR_PRODUCER, UTVBlocks.DAY_PRODUCER, UTVBlocks.MONTH_PRODUCER, UTVBlocks.YEAR_PRODUCER);

	public static final Supplier<BlockEntityType<SpaceProducerBlockEntity>> SPACE_PRODUCER = register("space_producer",
			SpaceProducerBlockEntity::new, UTVBlocks.SPACE_PRODUCER_0, UTVBlocks.SPACE_PRODUCER_1
			, UTVBlocks.SPACE_PRODUCER_2, UTVBlocks.SPACE_PRODUCER_3, UTVBlocks.SPACE_PRODUCER_4, UTVBlocks.SPACE_PRODUCER_5);

	public static final Supplier<BlockEntityType<RewindingChestBlockEntity>> REWINDING_CHEST = register("rewinding_chest",
			RewindingChestBlockEntity::new); // TODO add block

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
