package net.vechnimetel.industrialcastingcore.fluid.type;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vechnimetel.industrialcastingcore.IndustrialCastingCore;

public class ModFluidTypes {

    private static final DeferredRegister<FluidType> FLUID_TYPES;

    public static final RegistryObject<SugarcaneJuiceFluidType> SUGARCANE_JUICE;

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }

    static {
        FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, IndustrialCastingCore.MODID);

        SUGARCANE_JUICE = FLUID_TYPES.register("sugarcane_juice", SugarcaneJuiceFluidType::new);
    }
}
