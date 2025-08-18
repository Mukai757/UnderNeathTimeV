package sfac.ut5.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import sfac.ut5.TimeSystem;

/**
 * @author Mukai
 */
public class ChronosticePetalsItem extends Item {
    public ChronosticePetalsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand == InteractionHand.MAIN_HAND) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5 * TimeSystem.second(), 2));
            player.getCooldowns().addCooldown(this, 10 * TimeSystem.second());
            player.getItemInHand(interactionHand).consume(1, player);
        }
        return InteractionResultHolder.success(player.getItemInHand(interactionHand));
    }
}