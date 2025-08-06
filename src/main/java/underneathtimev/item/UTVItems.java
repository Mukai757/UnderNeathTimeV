package underneathtimev.item;

/**
 * @author AoXiang_Soar
 */

import underneathtimev.UnderNeathTimeV;

import java.util.function.Function;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

public class UTVItems {
	/**
	 * UTV utilizes this method to automatically register items and add them to the
	 * Creative Mode inventory. See also
	 * <code>underneathtimev.UnderNeathTimeV.addItem2Tab()</code>.
	 */
	private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> func,
			Item.Properties props, DeferredHolder<CreativeModeTab, CreativeModeTab> tab) {
		DeferredItem<I> item = UnderNeathTimeV.ITEMS.registerItem(name, func, props);
		UnderNeathTimeV.addItem2Tab(tab, item);
		return item;
	}

	public static final DeferredItem<TimeWingsItem> TIME_WINGS = // 时之翼
			register("time_wings", TimeWingsItem::new, new Item.Properties().durability(432), UnderNeathTimeV.MAIN_TAB);
	public static final DeferredItem<FatePocketWatchItem> FATE_POCKET_WATCH = // 宿命怀表
			register("fate_pocket_watch", FatePocketWatchItem::new, new Item.Properties(), UnderNeathTimeV.MAIN_TAB);
}
