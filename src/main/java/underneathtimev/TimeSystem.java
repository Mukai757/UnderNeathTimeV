package underneathtimev;

import java.util.function.Supplier;

import com.mojang.serialization.Codec;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

/**
 * Also see <code>underneathtimev.bus.UpdatePlayerTimeEvents</code>
 * 
 * @author AoXiang_Soar
 */

public class TimeSystem {
	// Time constants
	public static final int SECOND = 20;
	public static final int MINUTE = 60 * SECOND;
	public static final int HOUR = 60 * MINUTE;
	public static final int DAY = 24 * HOUR;
	public static final int MONTH = 30 * DAY;
	public static final int YEAR = 12 * MONTH;

	private static final Supplier<AttachmentType<Long>> TIME = 
			UnderNeathTimeV.ATTACHMENT_TYPES.register("utime", () -> AttachmentType.builder(() -> 0L).serialize(Codec.LONG).build());

	public static void setPlayerTime(Player player, long time) {
		player.setData(TIME, time);
	}

	public static long getPlayerTime(Player player) {
		return player.getData(TIME);
	}

	public static void increacePlayerTime(Player player, long time) {
		long playerTime = player.getData(TIME);
		player.setData(TIME, time + playerTime);
	}

	public static void decreacePlayerTime(Player player, long time) {
		long playerTime = getPlayerTime(player);
		player.setData(TIME, Math.max(0, playerTime - time));
	}

	/**
	 * Determine whether the player has a time attachment
	 */
	public static boolean hasPlayerTimeAttachment(Player player) {
		return player.hasData(TIME);
	}

	public static void initPlayerTimeAttachment(Player player, long time) {
		if (!hasPlayerTimeAttachment(player)) {
			setPlayerTime(player, time);
		}
	}

	public static String format(long time) {
		// int tick = (int) (time % 20);
		int second = (int) (time / SECOND % 60);
		int minute = (int) (time / MINUTE % 60);
		int hour = (int) (time / HOUR % 24);
		int day = (int) (time / DAY % 30);
		int month = (int) (time / MONTH % 12);
		int year = (int) (time / YEAR);
		return year <= 9999 ? String.format("%04d:%02d:%02d:%02d:%02d:%02d", year, month, day, hour, minute, second)
				: "Αι:ɱο:ѕτ:Ιπ:φι:πι:τγ";
	}

	public static String getFormatPlayerTime(Player player) {
		return format(getPlayerTime(player));
	}
}
