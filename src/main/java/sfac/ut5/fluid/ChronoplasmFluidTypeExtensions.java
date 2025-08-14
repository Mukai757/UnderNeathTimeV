package sfac.ut5.fluid;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import sfac.ut5.UnderneathTimeV;
/**
 * @author AoXiang_Soar
 */
public class ChronoplasmFluidTypeExtensions implements IClientFluidTypeExtensions {
	@Override
	public ResourceLocation getStillTexture() {
        return ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID, "block/chronoplasm_still");
    }
	
	@Override
	public ResourceLocation getFlowingTexture() {
        return ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID, "block/chronoplasm_flow");
    }
	
	@Override
	public ResourceLocation getOverlayTexture() {
        return ResourceLocation.fromNamespaceAndPath(UnderneathTimeV.MOD_ID, "block/chronoplasm");
    }

}