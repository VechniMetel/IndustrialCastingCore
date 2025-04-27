package net.vechnimetel.industrialcastingcore.render;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.vechnimetel.industrialcastingcore.block.entity.ModBlockEntityTypes;

public class ModRenderers {

    public static void register(IEventBus eventBus) {
        eventBus.addListener(ModRenderers::registerBlockEntityRenderer);
    }

    public static void registerBlockEntityRenderer(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(ModBlockEntityTypes.ELECTROLYSIS.get(),ElectrolysisRenderer::new);
        BlockEntityRenderers.register(ModBlockEntityTypes.KINETIC_GENERATOR.get(), KineticGeneratorRenderer::new);
        BlockEntityRenderers.register(ModBlockEntityTypes.ELECTRIC_MOTOR.get(), ElectricMotorRenderer::new);
    }
}
