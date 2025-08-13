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

    // Modified by zer0M1nd: ensure all fields initialized before build
    static ModConfigSpec build(){
        return BUILDER.build();
    }
}
