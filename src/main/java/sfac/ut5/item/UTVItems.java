package sfac.ut5.item;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import sfac.ut5.TimeSystem;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.fluid.UTVFluids;

import java.util.function.Function;

/**
 * @author AoXiang_Soar
 */

public class UTVItems {
    /**
     * UTV utilizes this method to automatically register items and add them to the
     * Creative Mode inventory. See also
     * <code>underneathtimev.UnderNeathTimeV.addItem2Tab()</code>.
     *
     * @param name  The register name of the item
     * @param func  Initialization function called during registration; if no
     *              additional functionality is needed, <code>Item::new</code> can be used
     * @param props Properties of the item
     * @param tab   Which creative tab the item to be added, use <code>null</code> if no one is suitable
     */
    private static <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, ? extends I> func,
                                                             Item.Properties props, DeferredHolder<CreativeModeTab, CreativeModeTab> tab) {
        DeferredItem<I> item = UnderneathTimeV.ITEMS.registerItem(name, func, props);
        UnderneathTimeV.addItem2Tab(tab, item);
        return item;
    }
    
    /**
     * UTV utilizes this method to automatically register items and add them to the
     * Creative Mode inventory. See also
     * <code>underneathtimev.UnderNeathTimeV.addItem2Tab()</code>.
     *
     * @param name  The register name of the item
     * @param tab   Which creative tab the item to be added, use <code>null</code> if no one is suitable
     */
    private static DeferredItem<Item> registerSimpleItem(String name, DeferredHolder<CreativeModeTab, CreativeModeTab> tab) {
        DeferredItem<Item> item = UnderneathTimeV.ITEMS.registerSimpleItem(name);
        UnderneathTimeV.addItem2Tab(tab, item);
        return item;
    }

	public static final DeferredItem<BucketItem> CHRONOPLASM_BUCKET = register("chronoplasm_bucket",
			prop -> new BucketItem(UTVFluids.CHRONOPLASM_SOURCE.get(), prop),
			new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1), UnderneathTimeV.MAIN_TAB);
    
    public static final DeferredItem<ElytraItem> TIME_WINGS =
            register("time_wings", ElytraItem::new, new Item.Properties().durability(432), UnderneathTimeV.MAIN_TAB);

    public static final DeferredItem<FatePocketWatchItem> FATE_POCKET_WATCH =
            register("fate_pocket_watch", FatePocketWatchItem::new, new Item.Properties().stacksTo(1), UnderneathTimeV.MAIN_TAB);
    public static final DeferredItem<BacktrackCompassItem> BACKTRACK_COMPASS =
            register("backtrack_compass", BacktrackCompassItem::new, new Item.Properties().stacksTo(1), UnderneathTimeV.MAIN_TAB);
    public static final DeferredItem<DebugRodItem> DEBUG_ROD =
            register("debug_rod", DebugRodItem::new, new Item.Properties().stacksTo(1), UnderneathTimeV.MAIN_TAB);

    public static final DeferredItem<SimpleTimeStorageItem> HOURGLASS_1 =
            register("hourglass_1", x -> new SimpleTimeStorageItem(x, TimeSystem.MINUTE),
                    new Item.Properties().stacksTo(1), UnderneathTimeV.MAIN_TAB);
    public static final DeferredItem<SimpleTimeStorageItem> HOURGLASS_2 =
            register("hourglass_2", x -> new SimpleTimeStorageItem(x, TimeSystem.HOUR),
                    new Item.Properties().stacksTo(1), UnderneathTimeV.MAIN_TAB);
    public static final DeferredItem<SimpleTimeStorageItem> HOURGLASS_3 =
            register("hourglass_3", x -> new SimpleTimeStorageItem(x, TimeSystem.HOUR * 6),
                    new Item.Properties().stacksTo(1), UnderneathTimeV.MAIN_TAB);

    public static final DeferredItem<TimePetalsItem> TimePetalsItem =
            register("backtrack_compass", TimePetalsItem::new, new Item.Properties().stacksTo(1), UnderneathTimeV.MAIN_TAB);
    public static void init() {
    }

    private UTVItems() {
    } // No one initializes this!
}
