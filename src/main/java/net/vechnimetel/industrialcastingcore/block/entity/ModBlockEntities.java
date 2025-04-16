package net.vechnimetel.industrialcastingcore.block.entity;


import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vechnimetel.industrialcastingcore.IndustrialCastingCore;
import net.vechnimetel.industrialcastingcore.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, IndustrialCastingCore.MODID);

    public static final RegistryObject<BlockEntityType<ElectrolysisBlockEntity>> ELECTROLYSIS =
            BLOCK_ENTITIES.register("electrolysis",() ->
                    BlockEntityType.Builder.of(ElectrolysisBlockEntity::new,
                            ModBlocks.ELECTROLYSIS.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
