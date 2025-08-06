package underneathtimev.item;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class FateHourglassItem extends Item {
    private static final int COOLDOWN_TICKS = 5 * 60 * 20; // 5分钟（游戏刻）
    private static final String TAG_HEALTH = "StoredHealth";
    private static final String TAG_FOOD = "StoredFood";
    public FateHourglassItem(Properties properties) {
        super(properties);
    }
    

    // 核心功能
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            // Shift+右键：记录状态
            recordState(player, stack);
            player.displayClientMessage(Component.translatable("item.ut5.fate_hourglass.recorded"), true);
            return InteractionResultHolder.success(stack);
        } else {
            // 普通右键：恢复状态
            if (player.getCooldowns().isOnCooldown(this)) {
                player.displayClientMessage(Component.translatable("item.ut5.fate_hourglass.cooldown"), true);
                return InteractionResultHolder.fail(stack);
            }

            if (restoreState(player, stack)) {
                player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
                return InteractionResultHolder.success(stack);
            } else {
                player.displayClientMessage(Component.translatable("item.ut5.fate_hourglass.empty"), true);
                return InteractionResultHolder.fail(stack);
            }
        }
    }

    // 记录玩家当前状态到NBT（todo包含药水效果）
    private void recordState(Player player, ItemStack stack) {
        CompoundTag tag = (CompoundTag) stack.getTags();
        tag.putFloat(TAG_HEALTH, player.getHealth());
        tag.putInt(TAG_FOOD, player.getFoodData().getFoodLevel());
    }

    // 从NBT恢复玩家状态
    private boolean restoreState(Player player, ItemStack stack) {
        CompoundTag tag = (CompoundTag) stack.getTags();
        if (tag == null || !tag.contains(TAG_HEALTH)) return false;

        float health = tag.getFloat(TAG_HEALTH);
        int food = tag.getInt(TAG_FOOD);

        // 应用记录
        player.setHealth(health);
        if (player instanceof ServerPlayer) {
            ((ServerPlayer)player).getFoodData().setFoodLevel(food);
        }

        // 清空记录
        tag.remove(TAG_HEALTH);
        tag.remove(TAG_FOOD);
        return true;
    }


    //客户端
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag tag = (CompoundTag) stack.getTags();
        if (tag != null && tag.contains(TAG_HEALTH)) {
            tooltip.add(Component.translatable("item.ut5.fate_hourglass.tooltip.stored",
                    String.format("%.1f", tag.getFloat(TAG_HEALTH)),
                    tag.getInt(TAG_FOOD)
            ));
        } else {
            tooltip.add(Component.translatable("item.ut5.fate_hourglass.tooltip.empty"));
        }
    }

}