package net.vechnimetel.industrialcastingcore.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vechnimetel.industrialcastingcore.IndustrialCastingCore;
import net.vechnimetel.industrialcastingcore.block.entity.ModBlockEntities;
import net.vechnimetel.industrialcastingcore.render.ElectrolysisRenderer;

@Mod.EventBusSubscriber(modid = IndustrialCastingCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEventHandler {
    @SubscribeEvent
    public static void registerBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.ELECTROLYSIS.get(), ElectrolysisRenderer::new);
    }
}
