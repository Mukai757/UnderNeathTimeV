package sfac.ut5.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * @author AoXiang_Soar
 */

public class SimpleTimeStorageItem extends Item implements ITimeStorageItem {

    private final long capacity;

    public SimpleTimeStorageItem(Properties properties,
                                 long capacity) {
        super(properties);
        this.capacity = capacity;
    }


    @Override
    public long getCapacity(ItemStack is) {
        return capacity;
    }
}
