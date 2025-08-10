package underneathtimev.block.blockentity;

import java.util.function.Supplier;

import net.minecraft.world.level.block.entity.BlockEntityType;
import underneathtimev.UnderNeathTimeV;
import underneathtimev.block.UTVBlocks;

/**
 * @author AoXiang_Soar
 */

public class UTVBlockEntities {
	/*
	 * 由于某人不懂而写的贴心示例
	 * 
	 * 把所有的MyBlockEntity改为你的BlockEntity，即MyBlockEntity extends BlockEntity
	 * public static final Supplier<BlockEntityType<MyBlockEntity>> MY_BLOCK_ENTITY = UnderNeathTimeV.BLOCK_ENTITY_TYPES.register(
	 *      "my_block_entity", // 这个是方块实体的注册名
	 *      () -> BlockEntityType.Builder.of(
	 *              MyBlockEntity::new,
	 *              UTVBlocks.MY_BLOCK_1.get(), UTVBlocks.MY_BLOCK_2.get() // 这里填任意多个方块，所填的方块均会应用这个方块实体
	 *      )
	 *      .build(null)
	 * );
	 *
	 * 不要乱加乱改其它东西，注册已经在主类完成
	 * 加完一个之后记得把这条注释删了
	 */
}
