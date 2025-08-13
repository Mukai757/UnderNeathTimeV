package sfac.ut5;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * @author AoXiang_Soar
 */

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.LongValue INITIAL_TIME = BUILDER
            .comment("The initial time(ticks) when player first enters the game")
            .defineInRange("initialTime", 3 * TimeSystem.HOUR, 0L, Long.MAX_VALUE);

    public static final ModConfigSpec.BooleanValue ENABLE_RITUAL = BUILDER
            .comment("If set to true, then players need to perform a ritual to start the contents of this mod.",
                    "Otherwise, all contents, including time count-down, are started by default.")
            .define("enableRitual", false);

    // Modified by zer0M1nd: ensure all fields initialized before build
    static ModConfigSpec build(){
        return BUILDER.build();
    }
}
