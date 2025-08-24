package sfac.ut5.block.blockentity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import sfac.ut5.block.UTVBlocks;
import sfac.ut5.block.blockentity.RewindingChestBlockEntity;

/**
 * @author AoXiang_Soar
 */

public class UTVBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
	public static final UTVBlockEntityWithoutLevelRenderer INSTANCE = new UTVBlockEntityWithoutLevelRenderer();
    
    private UTVBlockEntityWithoutLevelRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }
    
    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transform, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
    	poseStack.pushPose();
        
        Minecraft.getInstance().getBlockEntityRenderDispatcher()
                .renderItem(new RewindingChestBlockEntity(BlockPos.ZERO, UTVBlocks.REWINDING_CHEST.get().defaultBlockState()),
                		poseStack, bufferSource, packedLight, packedOverlay);
        
        poseStack.popPose();
    }
    
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(
                new RewindingChestBER.RewindingChestItemExtensions(),
                UTVBlocks.REWINDING_CHEST.asItem()
        );
    }
}