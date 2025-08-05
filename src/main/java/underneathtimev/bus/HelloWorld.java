package underneathtimev.bus;

/**
 * @author Mukai
 */

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class HelloWorld {
	public static void register() {
		NeoForge.EVENT_BUS.addListener(HelloWorld::onLoggedIn);
	}

	public static void onLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		var player = event.getEntity();
		player.sendSystemMessage(Component.translatable("ut5.welcome"));
	}
}
