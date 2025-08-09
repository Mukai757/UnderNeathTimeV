package underneathtimev.event;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import underneathtimev.*;

/**
 * @author AoXiang_Soar
 */

public class UpdatePlayerTimeEvents {
	public static void register() {
		NeoForge.EVENT_BUS.addListener(UpdatePlayerTimeEvents::onPlayerTick);
		NeoForge.EVENT_BUS.addListener(UpdatePlayerTimeEvents::onLoggedIn);
		NeoForge.EVENT_BUS.addListener(UpdatePlayerTimeEvents::onPlayerClone);
		NeoForge.EVENT_BUS.addListener(UpdatePlayerTimeEvents::onCommandRegister);
	}

	public static void onPlayerTick(PlayerTickEvent.Pre event) {
		var player = event.getEntity();
		TimeSystem.decreacePlayerTime(player, 1);
	}

	public static void onLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		var player = event.getEntity();
		TimeSystem.initPlayerTimeAttachment(player, Config.INITIAL_TIME.get());
	}

	public static void onPlayerClone(Clone event) {
		var player = event.getEntity();
		var oldPlayer = event.getOriginal();
		if (event.isWasDeath())
			TimeSystem.setPlayerTime(player, TimeSystem.getPlayerTime(oldPlayer) / 2);
	}

	public static void onCommandRegister(RegisterCommandsEvent event) {
		CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
		BiFunction<CommandContext<CommandSourceStack>, BiConsumer<Player, Long>, Integer> timeHandler = 
				(command, operation) -> {
			var player = command.getSource().getPlayer();
			long time = LongArgumentType.getLong(command, "utime");
			operation.accept(player, time);
			var msg = Component.translatable("command.ut5.time", player.getName(), TimeSystem.getFormatPlayerTime(player));
			player.sendSystemMessage(msg);
			return 1;
		};

		dispatcher.register(Commands.literal(UnderNeathTimeV.MOD_ID)
				.then(Commands.literal("time")
						.then(Commands.literal("set").requires(player -> player.hasPermission(2))
								.then(Commands.argument("utime", LongArgumentType.longArg(0))
										.executes(command -> timeHandler.apply(command, TimeSystem::setPlayerTime))))
						.then(Commands.literal("increase").requires(player -> player.hasPermission(2))
								.then(Commands.argument("utime", LongArgumentType.longArg(0)).executes(
										command -> timeHandler.apply(command, TimeSystem::increacePlayerTime))))
						.then(Commands.literal("decrease").requires(player -> player.hasPermission(2))
								.then(Commands.argument("utime", LongArgumentType.longArg(0)).executes(
										command -> timeHandler.apply(command, TimeSystem::decreacePlayerTime))))
						.then(Commands.literal("query").executes(command -> {
							var player = command.getSource().getPlayer();
							player.sendSystemMessage(Component.translatable("command.ut5.time", player.getName(), TimeSystem.getFormatPlayerTime(player)));
							return 1;
						}))));
	}
}
