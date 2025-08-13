package sfac.ut5.event;

import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import sfac.ut5.item.UTVItems;

/**
 * @author Mukai
 * @author AoXiang_Soar
 */
public class TimeWingsEvents {
	private static final float SPEED_BOOST = 1.5f;
	private static final float MAX_SPEED = 3.0f;

	public static void register() {
		NeoForge.EVENT_BUS.addListener(TimeWingsEvents::onPlayerJump);
		NeoForge.EVENT_BUS.addListener(TimeWingsEvents::onPlayerHurt);
		NeoForge.EVENT_BUS.addListener(TimeWingsEvents::onPlayerTick);
	}

	// 监听玩家跳跃事件（用于测试）
	// @SubscribeEvent
	public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
		if (event.getEntity() instanceof Player player) {
			ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
			if (chestItem.getItem() == UTVItems.TIME_WINGS.asItem()) {
				// 穿戴时之翼时给予额外跳跃高度
				player.setDeltaMovement(player.getDeltaMovement().add(0, 0.2, 0));
			}
		}
	}

	// 防止穿戴时之翼时受到摔落伤害
	// @SubscribeEvent
	public static void onPlayerHurt(LivingDamageEvent.Pre event) {
		if (event.getEntity() instanceof Player player) {
			ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
			if (chestItem.getItem() == UTVItems.TIME_WINGS.asItem() && event.getSource().is(DamageTypes.FALL)) {
				event.setNewDamage(0);
			}
		}
	}

	public static void onPlayerTick(PlayerTickEvent.Pre event) {
		var player = event.getEntity();
		var chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
		if (player.isFallFlying() && chestItem.getItem() == UTVItems.TIME_WINGS.asItem()) {
			applySpeedBoost(player, 0.03);
		}
	}

	private static void applySpeedBoost(Player player, double factor) {
		Vec3 motion = player.getDeltaMovement();
		double horizontalSpeed = Math.sqrt(motion.x * motion.x + motion.z * motion.z);

		double targetSpeed = Math.min(horizontalSpeed * SPEED_BOOST, MAX_SPEED);

		if (horizontalSpeed < targetSpeed && motion.y < 0) {
			double scale = targetSpeed / horizontalSpeed * factor;
			Vec3 add = new Vec3(motion.x * scale, 0, motion.z * scale);
			player.addDeltaMovement(add);
		}
	}
}
