package sfac.ut5.network;

import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import sfac.ut5.network.packets.PacketSyncPlayerData;

public class NetworkManager {

    public static void registerAll(PayloadRegistrar reg){
        reg.playToClient(
                PacketSyncPlayerData.TYPE,
                PacketSyncPlayerData.STREAM_CODEC,
                ClientPayloadHandler::handleDataOnMain
                );
    }
}
