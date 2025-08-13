package sfac.ut5.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import sfac.ut5.UTVPlayerData;
import sfac.ut5.UnderneathTimeV;

public record PacketSyncPlayerData(UTVPlayerData data) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PacketSyncPlayerData> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID, "syncplayer_data"));

    public static final StreamCodec<ByteBuf, PacketSyncPlayerData> STREAM_CODEC = StreamCodec.composite(
            UTVPlayerData.STREAM_CODEC,
            PacketSyncPlayerData::data,
            PacketSyncPlayerData::new
    );


    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
