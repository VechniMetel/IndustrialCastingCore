package net.vechnimetel.industrialcastingcore.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vechnimetel.industrialcastingcore.IndustrialCastingCore;
import net.vechnimetel.industrialcastingcore.fluid.ModFluids;

public class ModBlocks {
    private static final DeferredRegister<Block> BLOCKS;

    public static final RegistryObject<LiquidBlock> SUGARCANE_JUICE;

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    static {
        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, IndustrialCastingCore.MODID);

        SUGARCANE_JUICE = BLOCKS.register("sugarcane_juice",() -> new LiquidBlock(
                ModFluids.SUGARCANE_JUICE_SOURCE, BlockBehaviour.Properties.copy(Blocks.WATER)
        ));
    }
}
