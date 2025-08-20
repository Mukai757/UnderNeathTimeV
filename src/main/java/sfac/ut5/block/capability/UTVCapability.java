package sfac.ut5.block.capability;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;
import sfac.ut5.UnderneathTimeV;

/**
 * 能力系统核心（使用标准资源路径格式）
 */
public final class UTVCapability {
    /**
     * 流体处理能力的资源路径
     */
    private static final String FLUID_HANDLER_PATH = "fluid_handler";
    
    /**
     * 流体处理能力实例
     */
    public static final BlockCapability<IFluidHandler, @Nullable Direction> FLUID_HANDLER =
        BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID,FLUID_HANDLER_PATH),
            IFluidHandler.class
        );

    private UTVCapability() {

    } // 防止实例化


}