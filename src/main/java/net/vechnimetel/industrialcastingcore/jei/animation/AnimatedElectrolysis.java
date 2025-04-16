package net.vechnimetel.industrialcastingcore.jei.animation;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import net.minecraft.client.gui.GuiGraphics;
import net.vechnimetel.industrialcastingcore.block.ModBlocks;

public class AnimatedElectrolysis extends AnimatedKinetics {

    @Override
    public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
        PoseStack matrixStack = graphics.pose();
        matrixStack.pushPose();
        matrixStack.translate((float)xOffset, (float)yOffset, 100.0F);
        matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5F));
        matrixStack.mulPose(Axis.YP.rotationDegrees(22.5F));
        int scale = 20;
        this.blockElement(ModBlocks.ELECTROLYSIS.get().defaultBlockState()).scale(scale).render(graphics);
        this.blockElement(AllBlocks.DEPOT.getDefaultState()).atLocal(0.0F, 2.0F, 0.0F).scale(scale).render(graphics);
        AnimatedKinetics.DEFAULT_LIGHTING.applyLighting();
        Lighting.setupFor3DItems();
        matrixStack.popPose();
    }
}
