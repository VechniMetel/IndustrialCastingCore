package net.vechnimetel.industrialcastingcore;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.vechnimetel.industrialcastingcore.item.ModItems;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IndustrialCastingCore.MODID);

    public static final RegistryObject<CreativeModeTab> INDUSTRIAL_CASTING = CREATIVE_MODE_TABS.register("industrial_casting",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.KINETIC_GENERATOR.get()))
                    .title(Component.translatable("itemgroup.industrial_casting"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.SUGARCANE_JUICE_BUCKET.get());

                        pOutput.accept(ModItems.ELECTROLYSIS.get());
                        pOutput.accept(ModItems.KINETIC_GENERATOR.get());
                        pOutput.accept(ModItems.ELECTRIC_MOTOR.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
