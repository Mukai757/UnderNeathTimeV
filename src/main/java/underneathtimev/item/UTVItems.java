package underneathtimev.item;

/**
 * @author AoXiang_Soar
 */

import underneathtimev.UnderNeathTimeV;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public class UTVItems {
    public static final DeferredItem<TimeWingsItem> TIME_WINGS = // 时之翼
    		UnderNeathTimeV.ITEMS.registerItem("time_wings", TimeWingsItem::new, new Item.Properties().durability(432));
}
