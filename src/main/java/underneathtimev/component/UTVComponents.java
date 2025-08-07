package underneathtimev.component;

import java.util.function.Supplier;

import net.minecraft.core.component.DataComponentType;
import underneathtimev.UnderNeathTimeV;
import underneathtimev.item.*;

/**
 * @author AoXiang_Soar
 */

public class UTVComponents {
	public static final Supplier<DataComponentType<FatePocketWatchItem.Record>> FATE_POCKET_WATCH_COMPONENT = UnderNeathTimeV.DATA_COMPONENTS.registerComponentType(
		    "fate_pocket_watch_component",
		    builder -> builder
		        .persistent(FatePocketWatchItem.FATE_POCKET_WATCH_CODEC)
		        .networkSynchronized(FatePocketWatchItem.FATE_POCKET_WATCH_STREAM_CODEC)
		);
	public static final Supplier<DataComponentType<BacktrackCompassItem.Position>> BACKTRACK_COMPASS_POSITION_COMPONENT = UnderNeathTimeV.DATA_COMPONENTS.registerComponentType(
		    "backtrack_compass_position_component",
		    builder -> builder
		        .persistent(BacktrackCompassItem.BACKTRACK_COMPASS_POSITION_CODEC)
		        .networkSynchronized(BacktrackCompassItem.BACKTRACK_COMPASS_POSITION_STREAM_CODEC)
		);
	public static final Supplier<DataComponentType<BacktrackCompassItem.PlayerInfo>> BACKTRACK_COMPASS_PLAYER_INFO_COMPONENT = UnderNeathTimeV.DATA_COMPONENTS.registerComponentType(
		    "backtrack_compass_health_component",
		    builder -> builder
		        .persistent(BacktrackCompassItem.BACKTRACK_COMPASS_PLAYER_INFO_CODEC)
		        .networkSynchronized(BacktrackCompassItem.BACKTRACK_COMPASS_PLAYER_INFO_STREAM_CODEC)
		);
}
