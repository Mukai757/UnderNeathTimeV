package underneathtimev.event;

import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import underneathtimev.item.TimeWingsItem;

/**
 * @author Mukai
 * @author AoXiang_Soar
 */
public class TimeWingsEvents {
	public static void register() {
		NeoForge.EVENT_BUS.addListener(TimeWingsEvents::onPlayerJump);
		NeoForge.EVENT_BUS.addListener(TimeWingsEvents::onPlayerHurt);
	}
    // 监听玩家跳跃事件（用于测试）
    //@SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
            if (chestItem.getItem() instanceof TimeWingsItem) {
                // 穿戴时之翼时给予额外跳跃高度
                player.setDeltaMovement(player.getDeltaMovement().add(0, 0.2, 0));
            }
        }
    }

    // 防止穿戴时之翼时受到摔落伤害
    //@SubscribeEvent
    public static void onPlayerHurt(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
            if (chestItem.getItem() instanceof TimeWingsItem && event.getSource().is(DamageTypes.FALL)) {
                event.setNewDamage(0);
            }
        }
    }
}
