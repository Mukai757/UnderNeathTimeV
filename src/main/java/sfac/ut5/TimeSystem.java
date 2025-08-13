package sfac.ut5;

import com.mojang.serialization.Codec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Also see <code>underneathtimev.bus.UpdatePlayerTimeEvents</code>
 * <p>
 * All time values (type long), if not specified, are measured in **TICKS**, not seconds.
 *
 * @author AoXiang_Soar
 */
public class TimeSystem {
    // Time constants
    public static final long TICK = 1;
    public static final long SECOND = 20;
    public static final long MINUTE = 60 * SECOND;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    public static final long MONTH = 30 * DAY;
    public static final long YEAR = 12 * MONTH;

    public static final String ALMOST_INFINITY = "Αιɱο:ѕτ:Ιπ:φι:πι:τγ";

    private static final Supplier<AttachmentType<Long>> TIME =
            UnderneathTimeV.ATTACHMENT_TYPES.register("utime", () -> AttachmentType.builder(() -> Config.INITIAL_TIME.get()).serialize(Codec.LONG).build());

    public static void setPlayerTime(Player player, long time) {
        player.setData(TIME, Math.max(0, time));
    }

    public static long getPlayerTime(Player player) {
        return player.getData(TIME);
    }

    public static void increasePlayerTime(Player player, long time) {
        long playerTime = player.getData(TIME);
        player.setData(TIME, time + playerTime);
    }

    public static void decreasePlayerTime(Player player, long time) {
        long playerTime = getPlayerTime(player);
        player.setData(TIME, playerTime - time);
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

    /**
     * Format the ticks into YYYY:MM:DD:hh:mm:ss
     *
     * @param time Ticks
     * @return Formated string, return "Αιɱο:ѕτ:Ιπ:φι:πι:τγ" if out of range(more than 9999 years)
     */
    public static String format(long time) {
        // long tick = (long) (time % 20);
        long second = time / SECOND % 60;
        long minute = time / MINUTE % 60;
        long hour = time / HOUR % 24;
        long day = time / DAY % 30;
        long month = time / MONTH % 12;
        long year = time / YEAR;
        return year <= 9999 ? String.format("%04d:%02d:%02d:%02d:%02d:%02d", year, month, day, hour, minute, second)
                : ALMOST_INFINITY;
    }

    public static String getFormatPlayerTime(Player player) {
        return format(getPlayerTime(player));
    }

    private static final Map<String, Long> TIME_UNITS = Map.of(
            "Y", YEAR,
            "M", MONTH,
            "D", DAY,
            "h", HOUR,
            "m", MINUTE,
            "s", SECOND,
            "t", TICK
    );

    public static long parseTime(String timeString) {
        if (timeString.chars().allMatch(Character::isDigit)) {
            return Long.parseLong(timeString) * SECOND;
        }
        if (timeString.contains(":") || timeString.contains(".")) {
            String[] s0 = timeString.split("\\.");
            long ticks = s0.length >= 2 ? Long.parseLong(s0[1]) : 0;
            String s1 = s0[0];
            String[] s = s1.split(":");
            long[] t = new long[6];
            for (int i = 0; i < s.length; i++) {
                t[6 - i - 1] = Long.parseLong(s[s.length - i - 1]);
            }
            return t[0] * YEAR + t[1] * MONTH + t[2] * DAY + t[3] * HOUR + t[4] * MINUTE + t[5] * SECOND + ticks;
        }
        long t = 0;
        int begin = 0;
        for (int i = 0; i < timeString.length(); i++) {
            char c = timeString.charAt(i);
            if (!Character.isDigit(c)) {
                try {
                    t += Long.parseLong(timeString.substring(begin, i)) * TIME_UNITS.get(timeString.substring(i, i + 1));
                } catch (NullPointerException | NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid time string: " + timeString);
                }
                begin = i + 1;
            }
        }
        return t;
    }

    private TimeSystem() {
    } // No one initializes this!

    public static void init() {
    }
}
