package underneathtimev.item;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import underneathtimev.component.UTVComponents;

/**
 * @author AoXiang_Soar
 */

public class BacktrackCompassItem extends Item {

	public record Position(ResourceKey<Level> dimension, double x, double y, double z,
			float xRot, float yRot) {
	}
	public record PlayerInfo(String name, float health) {
	}

	public static final Codec<Position> BACKTRACK_COMPASS_POSITION_CODEC = RecordCodecBuilder.create(instance -> instance
			.group(ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(Position::dimension),
					Codec.DOUBLE.fieldOf("x").forGetter(Position::x), Codec.DOUBLE.fieldOf("y").forGetter(Position::y),
					Codec.DOUBLE.fieldOf("z").forGetter(Position::z), Codec.FLOAT.fieldOf("xRot").forGetter(Position::xRot),
					Codec.FLOAT.fieldOf("yRot").forGetter(Position::yRot))
			.apply(instance, Position::new));
	public static final StreamCodec<ByteBuf, Position> BACKTRACK_COMPASS_POSITION_STREAM_CODEC = StreamCodec.composite(
			ResourceKey.streamCodec(Registries.DIMENSION), Position::dimension, ByteBufCodecs.DOUBLE, Position::x,
			ByteBufCodecs.DOUBLE, Position::y, ByteBufCodecs.DOUBLE, Position::z, ByteBufCodecs.FLOAT, Position::xRot,
			ByteBufCodecs.FLOAT, Position::yRot, Position::new);
	public static final Codec<PlayerInfo> BACKTRACK_COMPASS_PLAYER_INFO_CODEC = RecordCodecBuilder.create(instance -> instance
			.group(Codec.STRING.fieldOf("name").forGetter(PlayerInfo::name), Codec.FLOAT.fieldOf("health").forGetter(PlayerInfo::health))
			.apply(instance, PlayerInfo::new));
	public static final StreamCodec<ByteBuf, PlayerInfo> BACKTRACK_COMPASS_PLAYER_INFO_STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8, PlayerInfo::name, ByteBufCodecs.FLOAT, PlayerInfo::health, PlayerInfo::new);

	public BacktrackCompassItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.has(UTVComponents.BACKTRACK_COMPASS_POSITION_COMPONENT) && stack.has(UTVComponents.BACKTRACK_COMPASS_PLAYER_INFO_COMPONENT)
				&& !player.getName().getString().equals(stack.get(UTVComponents.BACKTRACK_COMPASS_PLAYER_INFO_COMPONENT).name())) 
			return InteractionResultHolder.fail(stack);
		if (player.isShiftKeyDown()) {
			if (!level.isClientSide) {
				recordState(player, stack);
				player.sendSystemMessage(Component.translatable("item.ut5.backtrace_compass.recorded"));
			}
			return InteractionResultHolder.success(stack);
		}
		return InteractionResultHolder.fail(stack);
	}

	private void recordState(Player player, ItemStack stack) {
		var pos = player.position();
		player.getXRot();
		stack.set(UTVComponents.BACKTRACK_COMPASS_POSITION_COMPONENT,
				new Position(player.level().dimension(), pos.x, pos.y, pos.z, player.getXRot(), player.getYRot()));
		stack.set(UTVComponents.BACKTRACK_COMPASS_PLAYER_INFO_COMPONENT,
				new PlayerInfo(player.getName().getString(), player.getHealth()));
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents,
			TooltipFlag tooltipFlag) {
		var player = Minecraft.getInstance().player;
		if (stack.has(UTVComponents.BACKTRACK_COMPASS_POSITION_COMPONENT) && stack.has(UTVComponents.BACKTRACK_COMPASS_PLAYER_INFO_COMPONENT)) {
			if (player.getName().getString().equals(stack.get(UTVComponents.BACKTRACK_COMPASS_PLAYER_INFO_COMPONENT).name()))
				tooltipComponents.add(Component.translatable("item.ut5.backtrace_compass.tooltip.recorded"));
			else
				tooltipComponents.add(Component.translatable("item.ut5.backtrace_compass.tooltip.wrong_owner"));
		} else {
			tooltipComponents.add(Component.translatable("item.ut5.backtrace_compass.tooltip.empty"));
		}
	}
}
