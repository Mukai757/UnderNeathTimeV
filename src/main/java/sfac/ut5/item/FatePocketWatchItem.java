package sfac.ut5.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import sfac.ut5.TimeSystem;
import sfac.ut5.component.UTVComponents;

import java.util.List;

/**
 * @author Mukai
 * @author AoXiang_Soar
 */

public class FatePocketWatchItem extends Item {
	private static final long COOLDOWN_TICKS = 5 * TimeSystem.MINUTE;
	private static final String TAG_HEALTH = "storedHealth";
	private static final String TAG_FOOD_LEVEL = "storedFood";
	private static final String TAG_NAME = "storedName";

	public record Record(float health, int food, String name) {
	}

	public static final Codec<Record> FATE_POCKET_WATCH_CODEC = RecordCodecBuilder
			.create(instance -> instance.group(Codec.FLOAT.fieldOf(TAG_HEALTH).forGetter(Record::health),
					Codec.INT.fieldOf(TAG_FOOD_LEVEL).forGetter(Record::food),
					Codec.STRING.fieldOf(TAG_NAME).forGetter(Record::name)).apply(instance, Record::new));
	public static final StreamCodec<ByteBuf, Record> FATE_POCKET_WATCH_STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.FLOAT, Record::health, ByteBufCodecs.INT, Record::food, ByteBufCodecs.STRING_UTF8,
			Record::name, Record::new);

	public FatePocketWatchItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (player.isShiftKeyDown()) {
			if (!level.isClientSide) {
				recordState(player, stack);
				player.sendSystemMessage(Component.translatable("item.ut5.fate_pocket_watch.recorded"));
			}
			return InteractionResultHolder.success(stack);
		} else {
			if (stack.has(UTVComponents.FATE_POCKET_WATCH_COMPONENT) &&
					stack.get(UTVComponents.FATE_POCKET_WATCH_COMPONENT).name.equals(player.getName().getString())) {
				if (!level.isClientSide) {
					restoreState(player, stack);
					player.getCooldowns().addCooldown(this, (int) COOLDOWN_TICKS);
				}
				return InteractionResultHolder.success(stack);
			} else {
				if (!level.isClientSide)
					player.sendSystemMessage(Component.translatable("item.ut5.fate_pocket_watch.empty"));
				return InteractionResultHolder.fail(stack);
			}
		}
	}

	private void recordState(Player player, ItemStack stack) {
		stack.set(UTVComponents.FATE_POCKET_WATCH_COMPONENT,
				new Record(player.getHealth(), player.getFoodData().getFoodLevel(), player.getName().getString()));
		// TODO Add more effects
	}

	private void restoreState(Player player, ItemStack stack) {
		var component = stack.get(UTVComponents.FATE_POCKET_WATCH_COMPONENT);

		player.setHealth(component.health);
		player.getFoodData().setFoodLevel(component.food);

		stack.remove(UTVComponents.FATE_POCKET_WATCH_COMPONENT);
	}

	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents,
			TooltipFlag tooltipFlag) {
		var player = Minecraft.getInstance().player;
		if (stack.has(UTVComponents.FATE_POCKET_WATCH_COMPONENT) &&
				stack.get(UTVComponents.FATE_POCKET_WATCH_COMPONENT).name.equals(player.getName().getString())) {
			tooltipComponents.add(Component
					.translatable("item.ut5.fate_pocket_watch.tooltip.stored")
					.withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		} else {
			tooltipComponents.add(Component.translatable("item.ut5.fate_pocket_watch.tooltip.empty")
					.withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
		}
	}

}