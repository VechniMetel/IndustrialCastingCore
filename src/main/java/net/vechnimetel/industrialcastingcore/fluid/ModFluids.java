package net.vechnimetel.industrialcastingcore.fluid;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vechnimetel.industrialcastingcore.IndustrialCastingCore;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = IndustrialCastingCore.MODID, value = Dist.CLIENT)
public class ModFluids {
    private static final DeferredRegister<Fluid> FLUIDS;

    public static final RegistryObject<FlowingFluid> SUGARCANE_JUICE_SOURCE;
    public static final RegistryObject<FlowingFluid> SUGARCANE_JUICE_FLOWING;

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }

    static {
        FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, IndustrialCastingCore.MODID);

        SUGARCANE_JUICE_SOURCE = FLUIDS.register("sugarcane_juice", SugarcaneJuiceFluid.Source::new);
        SUGARCANE_JUICE_FLOWING = FLUIDS.register("sugarcane_juice_flowing", SugarcaneJuiceFluid.Flowing::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        for (RegistryObject<Fluid> fluid : FLUIDS.getEntries()) {
            ItemBlockRenderTypes.setRenderLayer(fluid.get(), RenderType.translucent());
        }
    }
}
