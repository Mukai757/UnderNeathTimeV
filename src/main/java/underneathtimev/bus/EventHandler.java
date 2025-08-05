package underneathtimev.bus;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import underneathtimev.item.TimeWingsItem;

@Mod("ut5")
public class EventHandler {

    // 注册所有事件监听器
    public static void register() {
        // 自动注册，无需额外代码
    }

    // 监听玩家跳跃事件（用于测试）
    @SubscribeEvent
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
    @SubscribeEvent
    public static void onPlayerFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
            if (chestItem.getItem() instanceof TimeWingsItem && player.isFallFlying()) {
                event.setCanceled(true);
            }
        }
    }
}
