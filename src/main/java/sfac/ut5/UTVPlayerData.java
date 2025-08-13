package sfac.ut5;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

/**
 * @author zer0M1nd
 */
public class UTVPlayerData {

    private static final Codec<UTVPlayerData> CODEC = RecordCodecBuilder.create(instance -> // Given an instance
            instance.group(
                    Codec.BOOL.fieldOf("timeRunning").forGetter(UTVPlayerData::isTimeRunning),
                    Codec.LONG.fieldOf("time").forGetter(UTVPlayerData::getTime)
            ).apply(instance, UTVPlayerData::new) // Define how to create the object
    );

    public static final Supplier<AttachmentType<UTVPlayerData>> UTVDATA =
            UnderneathTimeV.ATTACHMENT_TYPES.register(UnderneathTimeV.MOD_ID,
                    () -> AttachmentType.builder(UTVPlayerData::new)
                            .serialize(CODEC)
                            .copyOnDeath()
                            .build());

    public static void init() {

    }


    private boolean timeRunning;
    private long time;

    public UTVPlayerData(boolean timeRunning, long time) {
        this.timeRunning = timeRunning;
        this.time = time;
    }

    public UTVPlayerData() {
        this.timeRunning = false;
        this.time = Config.INITIAL_TIME.get();
    }

    public boolean isTimeRunning() {
        return timeRunning;
    }

    public void setTimeRunning(boolean timeRunning) {
        this.timeRunning = timeRunning;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = Math.max(time, 0);
    }

    /**
     * @param time
     * @return time after modification
     */
    public long addTime(long time) {
        this.time = Math.max(time + this.time, 0);
        return time;
    }

    public boolean extractTimeIfEnough(long time) {
        if(this.time >= time){
            this.time -= time;
            return true;
        }
        return false;
    }
}
