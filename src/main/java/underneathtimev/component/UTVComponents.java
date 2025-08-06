package underneathtimev.component;

import java.util.function.Supplier;

import net.minecraft.core.component.DataComponentType;
import underneathtimev.UnderNeathTimeV;
import underneathtimev.item.FatePocketWatchItem;

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
}
