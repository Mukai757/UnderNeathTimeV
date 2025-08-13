package sfac.ut5.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.item.BacktrackCompassItem;
import sfac.ut5.item.FatePocketWatchItem;

import java.util.function.Supplier;

/**
 * @author AoXiang_Soar
 */

public class UTVComponents {
	public static final Supplier<DataComponentType<FatePocketWatchItem.Record>> FATE_POCKET_WATCH_COMPONENT = UnderneathTimeV.DATA_COMPONENTS.registerComponentType(
		    "fate_pocket_watch_component",
		    builder -> builder
		        .persistent(FatePocketWatchItem.FATE_POCKET_WATCH_CODEC)
		        .networkSynchronized(FatePocketWatchItem.FATE_POCKET_WATCH_STREAM_CODEC)
		);
	public static final Supplier<DataComponentType<BacktrackCompassItem.Position>> BACKTRACK_COMPASS_POSITION_COMPONENT = UnderneathTimeV.DATA_COMPONENTS.registerComponentType(
		    "backtrack_compass_position_component",
		    builder -> builder
		        .persistent(BacktrackCompassItem.BACKTRACK_COMPASS_POSITION_CODEC)
		        .networkSynchronized(BacktrackCompassItem.BACKTRACK_COMPASS_POSITION_STREAM_CODEC)
		);
	public static final Supplier<DataComponentType<BacktrackCompassItem.PlayerInfo>> BACKTRACK_COMPASS_PLAYER_INFO_COMPONENT = UnderneathTimeV.DATA_COMPONENTS.registerComponentType(
			"backtrack_compass_health_component",
			builder -> builder
					.persistent(BacktrackCompassItem.BACKTRACK_COMPASS_PLAYER_INFO_CODEC)
					.networkSynchronized(BacktrackCompassItem.BACKTRACK_COMPASS_PLAYER_INFO_STREAM_CODEC)
	);
	public static final Supplier<DataComponentType<Long>> TIME_STORAGE_ITEM_COMPONENT = UnderneathTimeV.DATA_COMPONENTS.registerComponentType(
			"time_storage_item",
			builder -> builder
					.persistent(Codec.LONG)
					.networkSynchronized(ByteBufCodecs.VAR_LONG)
	);

	private UTVComponents() {}

	public static void init() {
	}
}
