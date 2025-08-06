package underneathtimev;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.LongValue INITIAL_TIME = BUILDER
            .comment("The initial time when player first enter the game")
            .defineInRange("initialTime", 20 * 60 * 60 * 3L, 0L, Long.MAX_VALUE);
    
    static final ModConfigSpec SPEC = BUILDER.build();
}
