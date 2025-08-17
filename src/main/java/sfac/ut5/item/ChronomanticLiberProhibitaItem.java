package sfac.ut5.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sfac.ut5.gui.ChronomanticLiberProhibitaMenu;

/**
 * @author Mukai
 */
public class ChronomanticLiberProhibitaItem extends Item {
    public ChronomanticLiberProhibitaItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand == InteractionHand.MAIN_HAND) {
        	player.openMenu(new SimpleMenuProvider((c, i, p) -> new ChronomanticLiberProhibitaMenu(c, i), Component.empty()));
            return InteractionResultHolder.success(player.getItemInHand(interactionHand));
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}

