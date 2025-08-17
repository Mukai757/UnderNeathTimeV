package sfac.ut5.blockstructure;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import sfac.ut5.block.UTVBlocks;

import java.util.Set;

public class TimeBindAltarStructure {
    private boolean isFast = false;

    private final static BlockPattern pattern0 = BlockPatternBuilder.start()
            .aisle( "AAA",
                    "AAA",
                    "AAA")
            .aisle( "AAA",
                    "ACA",
                    "ABA")
            .aisle( "AAA",
                    "AAA",
                    "AAA")
            .where('A', BlockInWorld.hasState(BlockStatePredicate.forBlock(UTVBlocks.TIME_BIND_ALTAR.get())))
            .where('B', BlockInWorld.hasState(BlockStatePredicate.ANY))
            .where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(UTVBlocks.TIME_BIND_ALTAR.get())))
            .build();
    private final static BlockPattern pattern90 = BlockPatternBuilder.start()
            .aisle( "AAA",
                    "AAA",
                    "AAA")
            .aisle( "AAA",
                    "ACB",
                    "AAA")
            .aisle( "AAA",
                    "AAA",
                    "AAA")
            .where('A', BlockInWorld.hasState(BlockStatePredicate.forBlock(UTVBlocks.TIME_BIND_ALTAR.get())))
            .where('B', BlockInWorld.hasState(BlockStatePredicate.ANY))
            .where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(UTVBlocks.TIME_BIND_ALTAR.get())))
            .build();
    private final static BlockPattern pattern180 = BlockPatternBuilder.start()
            .aisle( "AAA",
                    "AAA",
                    "AAA")
            .aisle( "ABA",
                    "ACA",
                    "AAA")
            .aisle( "AAA",
                    "AAA",
                    "AAA")
            .where('A', BlockInWorld.hasState(BlockStatePredicate.forBlock(UTVBlocks.TIME_BIND_ALTAR.get())))
            .where('B', BlockInWorld.hasState(BlockStatePredicate.ANY))
            .where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(UTVBlocks.TIME_BIND_ALTAR.get())))
            .build();
    private final static BlockPattern pattern270 = BlockPatternBuilder.start()
            .aisle( "AAA",
                    "AAA",
                    "AAA")
            .aisle( "AAA",
                    "BCA",
                    "AAA")
            .aisle( "AAA",
                    "AAA",
                    "AAA")
            .where('A', BlockInWorld.hasState(BlockStatePredicate.forBlock(UTVBlocks.TIME_BIND_ALTAR.get())))
            .where('B', BlockInWorld.hasState(BlockStatePredicate.ANY))
            .where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(UTVBlocks.TIME_BIND_ALTAR.get())))
            .build();

    private static Set<BlockPattern> patterns = Set.of(pattern0, pattern90, pattern180, pattern270);

    public static void onServerTick(Level level, BlockPos pos, BlockEntity timeBinderBlockEntity, Player player) {
        boolean find =patterns.stream().anyMatch(pattern -> pattern.find(level,pos)!= null);
        if (find) {
                player.sendSystemMessage(Component.translatable("ut5.welcome"));
        }




}


}
