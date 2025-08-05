package underneathtimev.bus;

import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
@Mod("xt5")
public class helloworld {
    public void playerloggedin() {}

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
    public static class PlayerLoggedInHandler {
        @SubscribeEvent
        public static void onLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
            var player = event.getEntity();
            player.sendSystemMessage(Component.literal("Welcome to Under Neath Time V!"));
        }
    }
}

