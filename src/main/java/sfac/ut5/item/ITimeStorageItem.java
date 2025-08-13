package sfac.ut5.item;

import net.minecraft.world.item.ItemStack;
import sfac.ut5.TimeSystem;
import sfac.ut5.component.UTVComponents;

import javax.annotation.Nonnegative;

public interface ITimeStorageItem {

    default long getTime(ItemStack is) {
        return is.getOrDefault(UTVComponents.TIME_STORAGE_ITEM_COMPONENT, 0L);
    }

    default void setTime(ItemStack is, long time) {
        is.set(UTVComponents.TIME_STORAGE_ITEM_COMPONENT, Math.max(Math.min(time, getCapacity(is)), 0L));
    }

    /**
     * Check if the item accepts the amount of modification
     * (has enough time to extract or has enough space to add time).
     * Modification never really happens.
     *
     * @param is   The itemstack
     * @param time The amount of time in ticks to add, may be negative
     * @return true if the item accepts the modification, false otherwise.
     */
    default boolean checkAddTime(ItemStack is, long time) {
        long time0 = getTime(is);
        return time0 + time < getCapacity(is) && time0 + time >= 0;
    }

    /**
     * Check if the item accepts the amount of modification
     * (has enough time to extract or has enough space to add time).
     * If true, the modification is done.
     *
     * @param is   The itemstack
     * @param time The amount of time in ticks to add, may be negative
     * @return true if the item accepts the modification (and it's done), false otherwise.
     */
    default boolean addTimeChecked(ItemStack is, long time) {
        long time0 = getTime(is);
        if (time0 + time < getCapacity(is) && time0 + time >= 0) {
            setTime(is, time0 + time);
            return true;
        }
        return false;
    }


    /**
     * @return The maximum amount of time for this storage item.
     */
    @Nonnegative
    long getCapacity(ItemStack is);

    default boolean showTooltip(ItemStack is) {
        return true;
    }

    /**
     * Use this in the tooltip. It can be overridden if necessary.
     */
    default String getFormattedTime(ItemStack is) {
        long time0 = getTime(is);
        return TimeSystem.format(time0) + " / " + TimeSystem.format(getCapacity(is));
    }
}
