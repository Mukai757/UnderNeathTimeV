package sfac.ut5.event;

/**
 * @author Mukai
 */

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import sfac.ut5.UTVPlayerData;
import sfac.ut5.network.NetworkManager;
import sfac.ut5.network.packets.PacketSyncPlayerData;

@OnlyIn(Dist.CLIENT)
public class NetworkEvents {

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(NetworkEvents::registerPayloadHandlers);
        NeoForge.EVENT_BUS.addListener(NetworkEvents::syncData);
    }

    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        NetworkManager.registerAll(registrar);
    }

    public static void syncData(ServerTickEvent.Pre event) {
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            var data = player.getData(UTVPlayerData.UTVDATA);
            if (data.isDirty()) {
                PacketDistributor.sendToPlayer(player, new PacketSyncPlayerData(data));
                data.markClean();
            }
        }
    }
}