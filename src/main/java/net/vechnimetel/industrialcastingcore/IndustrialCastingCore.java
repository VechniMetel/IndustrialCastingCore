package net.vechnimetel.industrialcastingcore;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vechnimetel.industrialcastingcore.block.ModBlocks;
import net.vechnimetel.industrialcastingcore.fluid.ModFluids;
import net.vechnimetel.industrialcastingcore.fluid.type.ModFluidTypes;
import net.vechnimetel.industrialcastingcore.item.ModItems;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IndustrialCastingCore.MODID)

public class IndustrialCastingCore {

    public static final String MODID = "industrial_casting_core";

    public IndustrialCastingCore(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);
    }
}
