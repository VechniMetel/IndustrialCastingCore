package net.vechnimetel.industrialcastingcore.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.vechnimetel.industrialcastingcore.block.entity.ElectricMotorBlockEntity;

public class ElectricMotorRenderer extends KineticBlockEntityRenderer<ElectricMotorBlockEntity> {

    public ElectricMotorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(ElectricMotorBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        KineticBlockEntityRenderer.renderRotatingBuffer(be, getRotatedModel(be,getRenderedBlockState(be)), ms, buffer.getBuffer(RenderType.cutout()), light);
    }

    @Override
    public boolean shouldRenderOffScreen(ElectricMotorBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    protected BlockState getRenderedBlockState(ElectricMotorBlockEntity be) {
        return shaft(getRotationAxisOf(be));
    }

    @Override
    protected SuperByteBuffer getRotatedModel(ElectricMotorBlockEntity be, BlockState state) {
        return CachedBufferer.block(KineticBlockEntityRenderer.KINETIC_BLOCK, state);
    }
}
