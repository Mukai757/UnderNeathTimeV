package sfac.ut5.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sfac.ut5.gui.UTVGUITypes;

/**
 * @author Mukai
 */
public class TimeBookItem extends Item {

    public TimeBookItem(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand == InteractionHand.MAIN_HAND) {
            // 在服务端打开GUI
            player.openMenu((MenuProvider) UTVGUITypes.TIME_ANVIL_MENU.get());
            //记得改成书的Gui，TODO
            return InteractionResultHolder.success(player.getItemInHand(interactionHand));
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}

