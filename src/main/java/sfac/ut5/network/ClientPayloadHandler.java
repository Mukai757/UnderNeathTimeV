package sfac.ut5.network;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import sfac.ut5.UTVPlayerData;
import sfac.ut5.network.packets.PacketSyncPlayerData;

public class ClientPayloadHandler {

    public static void handleDataOnMain(final PacketSyncPlayerData data, final IPayloadContext context) {
        if (Minecraft.getInstance().player != null) {
            context.enqueueWork(() -> {
                Minecraft.getInstance().player.setData(
                        UTVPlayerData.UTVDATA, data.data()
                );
            });
        }
    }
}
