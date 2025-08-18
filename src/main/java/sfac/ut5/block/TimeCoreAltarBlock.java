package sfac.ut5.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import sfac.ut5.TimeSystem;
import sfac.ut5.UTVPlayerData;
import sfac.ut5.structure.TimeCoreAltarStructure;

public class TimeCoreAltarBlock extends Block{

    public TimeCoreAltarBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
    ) {
		if (!level.isClientSide && player instanceof ServerPlayer) {
			if (TimeCoreAltarStructure.INSTANCE.isMatch(level, pos)) {
				int[] conditions = {0, TimeSystem.hour(), TimeSystem.day(), TimeSystem.month(), TimeSystem.year()};
				var data = player.getData(UTVPlayerData.UTVDATA);
				int playerLevel = data.getPlayerLevel();
				try {
					if (data.getTime() >= conditions[playerLevel]) {
						data.levelUp();
						data.setTimeRunning(true);
						TimeSystem.decreasePlayerTime(player, conditions[playerLevel]);
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					player.sendSystemMessage(Component.translatable("ut5.core_altar.invaild"));
				}
			}
		}
        return ItemInteractionResult.SUCCESS;
    }

}