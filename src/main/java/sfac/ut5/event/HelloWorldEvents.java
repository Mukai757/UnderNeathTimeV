package sfac.ut5.event;

/**
 * @author Mukai
 */

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class HelloWorldEvents {
	public static void register() {
		NeoForge.EVENT_BUS.addListener(HelloWorldEvents::onLoggedIn);
	}

	public static void onLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		var player = event.getEntity();
		player.sendSystemMessage(Component.translatable("ut5.welcome"));
	}
}
