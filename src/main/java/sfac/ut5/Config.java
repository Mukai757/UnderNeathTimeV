package sfac.ut5;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * @author AoXiang_Soar
 */

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_RITUAL = BUILDER
            .comment("If set to true, then players need to perform a ritual to start the contents of this mod.",
                    "Otherwise, all contents, including time count-down, are started by default.")
            .define("enableRitual", true);
    public static final ModConfigSpec.LongValue INITIAL_RITUAL_TIME = BUILDER
            .comment("The initial time(ticks) when player first enters the game. Takes effect only when enableRitual is true")
            .defineInRange("initialRitualTime", 30 * TimeSystem.MINUTE, 0L, Long.MAX_VALUE);
    public static final ModConfigSpec.LongValue INITIAL_TIME = BUILDER
            .comment("The initial time(ticks) when the player first completes the ritual through Time Core Altar. Takes effect only when enableRitual is false")
            .defineInRange("initialTime", 3 * TimeSystem.HOUR, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue TIME_DISPLAY_X = BUILDER
            .comment("The amount of x-offset of time displaying. If it's in [0, 1], then it's a percentage value. Otherwise, it's measured in pixels.")
            .defineInRange("timeDisplayX", 0.5, 0.0, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue TIME_DISPLAY_Y = BUILDER
            .comment("The amount of y-offset of time displaying. If it's in [0, 1], then it's a percentage value. Otherwise, it's measured in pixels.")
            .defineInRange("timeDisplayY", 0.5, 0.0, Double.MAX_VALUE);
    public static final ModConfigSpec.DoubleValue TIME_DISPLAY_SCALE = BUILDER
            .comment("The amount of scaling of time displaying.")
            .defineInRange("timeDisplayScale", 1.0, 0.0, Double.MAX_VALUE);

    public static final ModConfigSpec.ConfigValue<String> TIME_DISPLAY_COLOR = BUILDER
            .comment("The color of time displaying.").define(
                    "timeDisplayColor", "00FF00"
            );

    // Modified by zer0M1nd: ensure all fields initialized before build
    static ModConfigSpec build() {
        return BUILDER.build();
    }
}
