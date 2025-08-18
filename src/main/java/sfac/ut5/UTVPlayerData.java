package sfac.ut5;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

/**
 * @author zer0M1nd
 */
public class UTVPlayerData {

    public static final Codec<UTVPlayerData> CODEC = RecordCodecBuilder.create(instance -> // Given an instance
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

    public static final StreamCodec<ByteBuf, UTVPlayerData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            UTVPlayerData::isTimeRunning,
            ByteBufCodecs.VAR_LONG,
            UTVPlayerData::getTime,
            UTVPlayerData::new
    );

    public static void init() {}


    private boolean timeRunning;
    private long time;
    private long TimePlayerLevel = 0;
    /**
     * Mark if the data needs to be synced.
     * This field should not be saved.
     */
    private boolean dirty = true;

    public UTVPlayerData(boolean timeRunning, long time) {
        this.timeRunning = timeRunning;
        this.time = time;
    }

    public UTVPlayerData() {
        this.timeRunning = !Config.ENABLE_RITUAL.get();
        this.time = Config.INITIAL_TIME.get();
    }

    public boolean isTimeRunning() {
        return timeRunning;
    }

    public void setTimeRunning(boolean timeRunning) {
        this.timeRunning = timeRunning;
        markDirty();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = Math.max(time, 0);
        markDirty();
    }


    public long getTimePlayerLevel() {
        return TimePlayerLevel;
    }

    public long setTimePlayerLevel(long timeplayerlevel) {
        this.TimePlayerLevel = timeplayerlevel;
        return TimePlayerLevel;
    }
    /**
     * Does not markDirty()
     */
	public void tick() {
        if(TimePlayerLevel!=0){
		if (this.timeRunning) {
			this.time--;
		}
        }
    }

    /**
     * @param time
     * @return time after modification
     */
    public long addTime(long time) {
        this.time = Math.max(time + this.time, 0);
        markDirty();
        return time;
    }

    public boolean extractTimeIfEnough(long time) {
        if(this.time >= time){
            this.time -= time;
            markDirty();
            return true;
        }
        return false;
    }

    public void markDirty(){
        this.dirty = true;
    }

    public void markClean(){
        this.dirty = false;
    }

    public boolean isDirty() {
        return dirty;
    }
}
