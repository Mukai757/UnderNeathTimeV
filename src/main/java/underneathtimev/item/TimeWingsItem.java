package underneathtimev.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @author Mukai
 */

public class TimeWingsItem extends ElytraItem {
    // 速度加成常量 (50% = 1.5倍)
    private static final float SPEED_BOOST = 1.5f;

    public TimeWingsItem(Properties properties) {
        super(properties);
    }

    // 当玩家穿戴时之翼时调用

    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (player.isFallFlying()) {
            // 应用速度加成
            applySpeedBoost(player);
        }
    }

    // 应用速度加成的方法
    private void applySpeedBoost(LivingEntity entity) {
        // 获取当前速度向量
        double motionX = entity.getDeltaMovement().x;
        double motionY = entity.getDeltaMovement().y;
        double motionZ = entity.getDeltaMovement().z;

        // 计算速度提升 (仅水平方向)
        double horizontalSpeed = Math.sqrt(motionX * motionX + motionZ * motionZ);
        if (horizontalSpeed > 0) {
            double boostFactor = SPEED_BOOST / horizontalSpeed;
            entity.setDeltaMovement(
                    motionX * boostFactor,
                    motionY, // 保持垂直速度不变
                    motionZ * boostFactor
            );
        }
    }
}