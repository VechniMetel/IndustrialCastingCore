package net.vechnimetel.industrialcastingcore.fluid;

import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.vechnimetel.industrialcastingcore.block.ModBlocks;
import net.vechnimetel.industrialcastingcore.fluid.type.ModFluidTypes;
import net.vechnimetel.industrialcastingcore.item.ModItems;

public class SugarcaneJuiceFluid {

    private static final ForgeFlowingFluid.Properties PROPERTIES;

    public static class Source extends ForgeFlowingFluid.Source {
        public Source() {
            super(PROPERTIES);
        }
    }

    public static class Flowing extends ForgeFlowingFluid.Flowing {
        public Flowing() {
            super(PROPERTIES);
        }
    }

    static {
        PROPERTIES = new ForgeFlowingFluid.Properties(
                ModFluidTypes.SUGARCANE_JUICE,
                ModFluids.SUGARCANE_JUICE_SOURCE,
                ModFluids.SUGARCANE_JUICE_FLOWING)
                .slopeFindDistance(2).levelDecreasePerBlock(2)
                .block(ModBlocks.SUGARCANE_JUICE).bucket(ModItems.SUGARCANE_JUICE_BUCKET);
    }
}
