package net.vechnimetel.industrialcastingcore.block.entity;


import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vechnimetel.industrialcastingcore.IndustrialCastingCore;
import net.vechnimetel.industrialcastingcore.block.KineticGeneratorBlock;
import net.vechnimetel.industrialcastingcore.block.ModBlocks;

public class ModBlockEntityTypes {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES;

    public static final RegistryObject<BlockEntityType<ElectrolysisBlockEntity>> ELECTROLYSIS;
    public static final RegistryObject<BlockEntityType<ElectricMotorBlockEntity>> ELECTRIC_MOTOR;
    public static final RegistryObject<BlockEntityType<KineticGeneratorBlockEntity>> KINETIC_GENERATOR;

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }

    static {
        BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, IndustrialCastingCore.MODID);

        ELECTROLYSIS = BLOCK_ENTITY_TYPES.register(
                "electrolysis",
                () -> BlockEntityType.Builder.of(ElectrolysisBlockEntity::new,
                        ModBlocks.ELECTROLYSIS.get()).build(null)
        );
        ELECTRIC_MOTOR = BLOCK_ENTITY_TYPES.register(
                "electric_motor",
                () -> BlockEntityType.Builder.of(ElectricMotorBlockEntity::new,
                        ModBlocks.ELECTRIC_MOTOR.get()).build(null)
        );
        KINETIC_GENERATOR = BLOCK_ENTITY_TYPES.register(
                "kinetic_generator",
                () -> BlockEntityType.Builder.of(KineticGeneratorBlockEntity::new,
                        ModBlocks.KINETIC_GENERATOR.get()).build(null)
        );
    }
}
