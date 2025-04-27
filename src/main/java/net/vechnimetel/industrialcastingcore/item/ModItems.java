package net.vechnimetel.industrialcastingcore.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vechnimetel.industrialcastingcore.IndustrialCastingCore;
import net.vechnimetel.industrialcastingcore.block.ModBlocks;
import net.vechnimetel.industrialcastingcore.fluid.ModFluids;

public class ModItems {
    private static final DeferredRegister<Item> ITEMS;

    public static final RegistryObject<BucketItem> SUGARCANE_JUICE_BUCKET;
    public static final RegistryObject<ElectrolysisBlockItem> ELECTROLYSIS;
    public static final RegistryObject<BlockItem> ELECTRIC_MOTOR;
    public static final RegistryObject<BlockItem> KINETIC_GENERATOR;

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    static {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, IndustrialCastingCore.MODID);

        SUGARCANE_JUICE_BUCKET = ITEMS.register("sugarcane_juice_bucket",() -> new BucketItem(
                ModFluids.SUGARCANE_JUICE_SOURCE, new Item.Properties().stacksTo(1)
        ));
        ELECTROLYSIS = ITEMS.register("electrolysis",ElectrolysisBlockItem::new);
        ELECTRIC_MOTOR = ITEMS.register("electric_motor",
                () -> new BlockItem(ModBlocks.ELECTRIC_MOTOR.get(),new Item.Properties()));
        KINETIC_GENERATOR = ITEMS.register("kinetic_generator",
                () -> new BlockItem(ModBlocks.KINETIC_GENERATOR.get(),new Item.Properties()));
    }
}
