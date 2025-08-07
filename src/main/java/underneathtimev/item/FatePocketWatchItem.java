package underneathtimev.item;

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
import underneathtimev.TimeSystem;
import underneathtimev.component.UTVComponents;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;

/**
 * @author Mukai
 * @author AoXiang_Soar
 */

public class FatePocketWatchItem extends Item {
	private static final int COOLDOWN_TICKS = 5 * TimeSystem.MINUTE;
	private static final String TAG_HEALTH = "storedHealth";
	private static final String TAG_FOOD_LEVEL = "storedFood";

	public record Record(float health, int food) {
	}

	public static final Codec<Record> FATE_POCKET_WATCH_CODEC = RecordCodecBuilder
			.create(instance -> instance.group(Codec.FLOAT.fieldOf(TAG_HEALTH).forGetter(Record::health),
					Codec.INT.fieldOf(TAG_FOOD_LEVEL).forGetter(Record::food)).apply(instance, Record::new));
	public static final StreamCodec<ByteBuf, Record> FATE_POCKET_WATCH_STREAM_CODEC = StreamCodec
			.composite(ByteBufCodecs.FLOAT, Record::health, ByteBufCodecs.INT, Record::food, Record::new);

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
			if (stack.has(UTVComponents.FATE_POCKET_WATCH_COMPONENT)) {
				if (!level.isClientSide) {
					restoreState(player, stack);
					player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
				}
				return InteractionResultHolder.success(stack);
			} else {
				if (!level.isClientSide) player.sendSystemMessage(Component.translatable("item.ut5.fate_pocket_watch.empty"));
				return InteractionResultHolder.fail(stack);
			}
		}
	}

	private void recordState(Player player, ItemStack stack) {
		stack.set(UTVComponents.FATE_POCKET_WATCH_COMPONENT,
				new Record(player.getHealth(), player.getFoodData().getFoodLevel()));
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
		if (stack.has(UTVComponents.FATE_POCKET_WATCH_COMPONENT)) {
			var component = stack.get(UTVComponents.FATE_POCKET_WATCH_COMPONENT);
			tooltipComponents.add(Component.translatable("item.ut5.fate_pocket_watch.tooltip.stored",
					component.health, component.food));
		} else {
			tooltipComponents.add(Component.translatable("item.ut5.fate_pocket_watch.tooltip.empty"));
		}

	}

}