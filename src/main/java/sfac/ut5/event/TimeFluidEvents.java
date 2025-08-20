package sfac.ut5.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockEvent;
import sfac.ut5.fluid.UTVFluids;

/**
 * @author Mukai
 */
public class TimeFluidEvents {
    public static void register() {
        NeoForge.EVENT_BUS.addListener(TimeFluidEvents::onFluidNotify);
    }

    public static void onFluidNotify(BlockEvent.NeighborNotifyEvent event) {
        Level level = (Level) event.getLevel();
        BlockPos sourcePos = event.getPos();
        BlockState sourceState = level.getBlockState(sourcePos);
// 仅处理流体更新
        if (!sourceState.getFluidState().is(UTVFluids.CHRONOPLASM_FLOWING.get())) {
            return;
        }

        // 遍历流体更新的方向（即可能流向的相邻位置）
        for (Direction direction : event.getNotifiedSides()) {
            BlockPos neighborPos = sourcePos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);

            // 检测相邻位置是否为钻石块
            if (neighborState.is(Blocks.DIAMOND_BLOCK)) {
                BlockPos targetPos = sourcePos; // 流体试图占据的位置
                // 在流体放置前，将该位置替换为钻石矿石
                level.setBlock(targetPos, Blocks.DIAMOND_ORE.defaultBlockState(), 3); // Flag 3：触发更新但不引发连锁事件
                event.setCanceled(true); // 阻止原流体放置
                break;
            }
        }
    }

}
