package sfac.ut5.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockEvent;
import sfac.ut5.block.UTVBlocks;

/**
 * @author Mukai
 */
public class TimeFluidEvents {
    public static void register() {
        NeoForge.EVENT_BUS.addListener(TimeFluidEvents::onFluidPlaceBlock);
    }

    // 监听流体放置方块事件
    public static void onFluidPlaceBlock(BlockEvent.FluidPlaceBlockEvent event) {
        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState placedBlockState = event.getNewState();
        if (!placedBlockState.is(UTVBlocks.CHRONOPLASM_BLOCK.get()))
        	return;
        // 假设你的流体有对应的方块状态，这里需要根据实际情况获取
        // 这里简单假设通过某种方式可以判断放置的是否为你的流体
            //     检查周围是否有钻石块
            BlockPos[] adjacentPositions = {
                    pos.above(), pos.below(),
                    pos.north(), pos.south(),
                    pos.east(), pos.west()
            };
            for (BlockPos adjacentPos : adjacentPositions) {
                BlockState adjacentBlockState = level.getBlockState(adjacentPos);
                if (adjacentBlockState.is(Blocks.DIAMOND_BLOCK)) {
                    // 将当前位置设置为钻石矿石
                    event.setNewState(Blocks.DIAMOND_ORE.defaultBlockState());
                    break;
                }
            }
        }
}
