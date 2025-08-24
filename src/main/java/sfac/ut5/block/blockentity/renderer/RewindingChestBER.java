package sfac.ut5.block.blockentity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import sfac.ut5.UnderneathTimeV;
import sfac.ut5.block.RewindingChestBlock;
import sfac.ut5.block.blockentity.RewindingChestBlockEntity;

/**
 * @author AoXiang_Soar
 */

public class RewindingChestBER implements BlockEntityRenderer<RewindingChestBlockEntity> {
    private final ModelPart lid;
    private final ModelPart bottom;

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
        UnderneathTimeV.MOD_ID, 
        "textures/entity/chest/rewinding_chest.png"
    );
    
    public RewindingChestBER(BlockEntityRendererProvider.Context context) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        
        root.addOrReplaceChild("bottom", 
            CubeListBuilder.create()
                .texOffs(0, 19)
                .addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F),
            PartPose.offset(0.0F, 0.0F, 0.0F));
        
        PartDefinition lid = root.addOrReplaceChild("lid", 
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(1.0F, 0.0F, 1.0F, 14.0F, 5.0F, 14.0F),
            PartPose.offset(0.0F, 9.0F, 0.0F));
        
        lid.addOrReplaceChild("lock", 
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(7.0F, -1.0F, -1.0F, 2.0F, 4.0F, 1.0F),
            PartPose.offset(0.0F, 0.0F, 16.0F));
        
        ModelPart model = LayerDefinition.create(mesh, 64, 64).bakeRoot();
        this.lid = model.getChild("lid");
        this.bottom = model.getChild("bottom");
    }
    
    @Override
    public void render(RewindingChestBlockEntity blockEntity, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        
        float openness = blockEntity.getOpenNess(partialTick);
        float easedOpenness = 1.0F - (1.0F - openness) * (1.0F - openness);
        this.lid.xRot = -easedOpenness * ((float)Math.PI / 2f);
        
        var facing = blockEntity.getBlockState().getValue(RewindingChestBlock.FACING);
        float yRot = facing.toYRot();
		
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
        poseStack.translate(-0.5F, -0.5F, -0.5F);
        
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
        this.lid.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        this.bottom.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        
        poseStack.popPose();
    }
    
    public static class RewindingChestItemExtensions implements IClientItemExtensions {
        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return UTVBlockEntityWithoutLevelRenderer.INSTANCE;
        }
    }
}
