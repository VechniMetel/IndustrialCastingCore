package net.vechnimetel.industrialcastingcore.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.vechnimetel.industrialcastingcore.block.entity.ElectrolysisBlockEntity;
import net.vechnimetel.industrialcastingcore.block.state.properties.ElectrolysisLightLevel;

public class ElectrolysisRenderer implements BlockEntityRenderer<ElectrolysisBlockEntity> {
    private static final int TICKS_PER_ROTATION = 20;

    public ElectrolysisRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(ElectrolysisBlockEntity blockEntity, float partialTick,
                       PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {

        ItemStack lensItem = blockEntity.getLensItemCopy();
        if(!lensItem.isEmpty()) {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            poseStack.pushPose();
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.scale(0.4f,0.4f,0.4f);

            float angle;
            if(blockEntity.getLightLevel() == ElectrolysisLightLevel.BRIGHT) {
                angle = (Minecraft.getInstance().level.getGameTime() % TICKS_PER_ROTATION) *
                        ((float) 360 / TICKS_PER_ROTATION);
            } else angle = 0;

            poseStack.mulPose(Axis.YP.rotationDegrees(angle));

            itemRenderer.renderStatic(lensItem,ItemDisplayContext.FIXED,
                    this.getLightLevel(blockEntity.getLevel(),blockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY,poseStack,multiBufferSource,blockEntity.getLevel(),1);

            poseStack.popPose();
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        return LightTexture.pack(level.getBrightness(LightLayer.BLOCK,pos),level.getBrightness(LightLayer.SKY,pos));
    }
}
