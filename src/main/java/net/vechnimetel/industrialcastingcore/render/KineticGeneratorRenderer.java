package net.vechnimetel.industrialcastingcore.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.vechnimetel.industrialcastingcore.block.entity.KineticGeneratorBlockEntity;

public class KineticGeneratorRenderer extends KineticBlockEntityRenderer<KineticGeneratorBlockEntity> {

    public KineticGeneratorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(KineticGeneratorBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        KineticBlockEntityRenderer.renderRotatingBuffer(be, getRotatedModel(be,getRenderedBlockState(be)), ms, buffer.getBuffer(RenderType.cutout()), light);
    }

    @Override
    public boolean shouldRenderOffScreen(KineticGeneratorBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    protected BlockState getRenderedBlockState(KineticGeneratorBlockEntity be) {
        return shaft(getRotationAxisOf(be));
    }

    @Override
    protected SuperByteBuffer getRotatedModel(KineticGeneratorBlockEntity be, BlockState state) {
        return CachedBuffers.block(KineticBlockEntityRenderer.KINETIC_BLOCK, state);
    }
}
