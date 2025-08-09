package underneathtimev.event;

import java.util.HashSet;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import underneathtimev.TimeSystem;
import underneathtimev.component.UTVComponents;
import underneathtimev.item.UTVItems;

/**
 * @author AoXiang_Soar
 */

public class BacktrackCompassEvents {
	public static void register() {
		NeoForge.EVENT_BUS.addListener(BacktrackCompassEvents::onPlayerDeath);
	}

	public static void onPlayerDeath(LivingDeathEvent event) {
	    if (!(event.getEntity() instanceof ServerPlayer player)) {
	        return;
	    }

	    ItemStack matchingCompass = null;
	    
	    for (ItemStack stack : player.getInventory().items) {
	        if (stack.getItem() == UTVItems.BACKTRACK_COMPASS.asItem()
	            && stack.has(UTVComponents.BACKTRACK_COMPASS_POSITION_COMPONENT)
	            && stack.has(UTVComponents.BACKTRACK_COMPASS_PLAYER_INFO_COMPONENT)
	            && !player.getCooldowns().isOnCooldown(stack.getItem())
	            && player.getName().getString().equals(stack.get(UTVComponents.BACKTRACK_COMPASS_PLAYER_INFO_COMPONENT).name())) {
	            
	            matchingCompass = stack;
	            break;
	        }
	    }
	    
	    if (matchingCompass != null && TimeSystem.getPlayerTime(player) >= TimeSystem.HOUR) {
	        event.setCanceled(true);
	        var pos = matchingCompass.get(UTVComponents.BACKTRACK_COMPASS_POSITION_COMPONENT);
	        player.teleportTo(player.server.getLevel(pos.dimension()), pos.x(), pos.y(), pos.z(), new HashSet<>(), pos.yRot(), pos.xRot());
	        player.setHealth(matchingCompass.get(UTVComponents.BACKTRACK_COMPASS_PLAYER_INFO_COMPONENT).health());
	        player.getCooldowns().addCooldown(UTVItems.BACKTRACK_COMPASS.asItem(), TimeSystem.MINUTE);
	        TimeSystem.decreacePlayerTime(player, TimeSystem.HOUR);
	    }
	}
}
