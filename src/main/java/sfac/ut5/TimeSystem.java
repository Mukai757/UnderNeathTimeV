package sfac.ut5;

import net.minecraft.world.entity.player.Player;

import java.util.Map;

/**
 * Also see <code>underneathtimev.bus.UpdatePlayerTimeEvents</code>
 * <p>
 * All time values (type long), if not specified, are measured in <b>TICKS</b>, not seconds.
 * Under all circumstances, only consider <i>1 second = 20 tick</i>
 *
 * @author AoXiang_Soar
 * @author zer0M1nd
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

    private static final Map<String, Long> TIME_UNITS = Map.of(
            "Y", YEAR,
            "M", MONTH,
            "D", DAY,
            "h", HOUR,
            "m", MINUTE,
            "s", SECOND,
            "t", TICK
    );

    public static final String ALMOST_INFINITY = "Αιɱο:ѕτ:Ιπ:φι:πι:τγ";

    public static void setPlayerTime(Player player, long time) {
        player.getData(UTVPlayerData.UTVDATA).setTime(Math.max(time, 0));
    }

    public static long getPlayerTime(Player player) {
        return player.getData(UTVPlayerData.UTVDATA).getTime();
    }

    public static void increasePlayerTime(Player player, long time) {
        player.getData(UTVPlayerData.UTVDATA).addTime(time);
    }

    public static void decreasePlayerTime(Player player, long time) {
        player.getData(UTVPlayerData.UTVDATA).addTime(-time);
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

    /**
     * Parse a given String to the amount of time (in ticks) it represents.
     * The String may be in these formats:</br>
     * - A pure number: considered as the number of seconds</br>
     * - 1~6 numbers splitted by ":", for example, "1:23:4:56.7". Only last number can be float number.
     * 		The decimal part will be rounded down to ticks using the conversion rate of 1s = 20t</br>
     * - Numbers in the form "1Y2M3D4h5m6s7t"</br>
     * - No part can be negative, but the numbers in each part can be large values, such as ​​1Y99M​​ or ​​114514:1919810​​.
     * 		This method will convert these numbers according to their units, but ​​will throw an exception if the conversion result overflows
     *
     * @param timeString the String to parse
     * @return the time in ticks
     * @throws IllegalArgumentException if the timeString is not well-formatted
     */
    public static long parseTime(String timeString) throws IllegalArgumentException {
    	if (timeString.toLowerCase().equals("max"))
    		return Long.MAX_VALUE;
    	else if (timeString.toLowerCase().equals("min"))
    		return 0;
        if (timeString.chars().allMatch(Character::isDigit)) {
        	try {
        		long ticks = Math.multiplyExact(Long.parseLong(timeString), SECOND);
        		if (ticks < 0)
        			throw new IllegalArgumentException("Invalid time string: " + timeString + " . Time cannot be minus.");
        		return ticks;
        	} catch (NumberFormatException e) {
        		throw new IllegalArgumentException("Invalid time string: " + timeString + " . Not contain a parsable long.");
			} catch (ArithmeticException e) {
            	throw new IllegalArgumentException("Invalid time string: " + timeString + " . Calculation result overflow.");
			}
        }
        if (timeString.contains(":") || timeString.contains(".")) {
            String[] s0 = timeString.split("\\.");

            long ticks;
            try {
            	ticks = (long) (s0.length >= 2 ? Double.parseDouble("0."+s0[1]) * SECOND : 0);
            } catch (NumberFormatException e) {
        		throw new IllegalArgumentException("Invalid time string: " + timeString + " in part: " + s0[1] + " .");
			}

            String s1 = s0[0];
            String[] s = s1.split(":");
            if(s.length >= 7){
                throw new IllegalArgumentException("Invalid time string: " + timeString + " . Contain more than six colon(:).");
            }
            long[] t = new long[6];
            for (int i = 0; i < s.length; i++) {
            	String part = s[s.length - i - 1];
                try {
                	long pl = Long.parseLong(part);
            		if (pl < 0)
            			throw new IllegalArgumentException("Invalid time string: " + timeString + " in part: " + part + " . Time cannot be minus.");
                    t[6 - i - 1] = pl;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid time string: " + timeString + " in part: " + part + " . Not contain a parsable long.");
                }
            }

            long[] units = {YEAR, MONTH, DAY, HOUR, MINUTE, SECOND};
            long result = 0;
            try {
	            for (int i = 0; i < t.length; i++) {
	            	long temp = Math.multiplyExact(t[i], units[i]);
	            	result = Math.addExact(result, temp);
	            }
	            result = Math.addExact(result, ticks);
            } catch (ArithmeticException e) {
            	throw new IllegalArgumentException("Invalid time string: " + timeString + " . Calculation result overflow.");
			}
            return result;
        }

        long t = 0;
        int begin = 0;
        for (int i = 0; i < timeString.length(); i++) {
            char c = timeString.charAt(i);
            if (!Character.isDigit(c)) {
                try {
                	long part = Long.parseLong(timeString.substring(begin, i));
                	long unit = TIME_UNITS.get(timeString.substring(i, i + 1));
                	part = Math.multiplyExact(part, unit);
                	t = Math.addExact(t, part);
                } catch (NullPointerException | NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid time string: " + timeString);
                } catch (ArithmeticException e) {
                    throw new IllegalArgumentException("Invalid time string: " + timeString + " . Calculation result overflow.");
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
