package sfac.ut5.event;

/**
 * @author Mukai
 */

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import sfac.ut5.item.ITimeStorageItem;

public class DisplayEvents {
    public static void register() {
        NeoForge.EVENT_BUS.addListener(DisplayEvents::onLoggedIn);
    }

    public static void onLoggedIn(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof ITimeStorageItem ti) {
            if (ti.showTooltip(stack)) {
                event.getToolTip().add(1, Component.literal(ti.getFormattedTime(stack)));
            }
        }
    }
}
