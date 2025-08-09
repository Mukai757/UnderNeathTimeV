package underneathtimev.item;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

/**
 * @author AoXiang_Soar
 */

public class DebugRodItem extends Item {

	public DebugRodItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		return InteractionResultHolder.success(stack);
	}
	

	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents,
			TooltipFlag tooltipFlag) {
		tooltipComponents.add(Component.translatable("item.ut5.debug_rod.tooltip.1").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC, ChatFormatting.STRIKETHROUGH));
		tooltipComponents.add(Component.translatable("item.ut5.debug_rod.tooltip.2").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
	}
}
