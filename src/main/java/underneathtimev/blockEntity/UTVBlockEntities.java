package underneathtimev.blockEntity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import underneathtimev.UnderNeathTimeV;
public class UTVBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, UnderNeathTimeV.MOD_ID);
    /*不会注册喵
    public static final Supplier<BlockEntityType<TimeAnvilBlockEntity>> PEDESTAL_BE =
        BLOCK_ENTITIES.register("pedestal_be", () -> new BlockEntityType<>(TimeAnvilBlockEntity::new, UTVBlocks.PEDESTAL, UnderNeathTimeV.MAIN_TAB));
    */

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
