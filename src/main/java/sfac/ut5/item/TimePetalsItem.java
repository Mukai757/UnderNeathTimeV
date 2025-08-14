package sfac.ut5.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @author Mukai
 */
public class TimePetalsItem extends Item {
    public TimePetalsItem(Properties properties) {
        super(properties); // 设置物品分类，可根据实际情况调整
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand == InteractionHand.MAIN_HAND) { // 确保在服务端且是主手操作
            // 给玩家添加速度3效果，持续5秒（100tick）
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2)); // 2为速度等级，对应速度3
            // 设置冷却时间，例如10秒（200tick）
            player.getCooldowns().addCooldown(this, 200);
        }
        return InteractionResultHolder.success(player.getItemInHand(interactionHand));
    }
}