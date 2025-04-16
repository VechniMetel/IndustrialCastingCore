package net.vechnimetel.industrialcastingcore.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vechnimetel.industrialcastingcore.IndustrialCastingCore;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public enum ModRecipeTypes implements IRecipeTypeInfo {
    ELECTROLYSIS(ElectrolysisRecipe::new);

    private final ResourceLocation id;
    private final RegistryObject<RecipeSerializer<?>> serializerObject;
    private final @Nullable RegistryObject<RecipeType<?>> typeObject;
    private final Supplier<RecipeType<?>> type;

    ModRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = Lang.asId(this.name());
        this.id = ResourceLocation.fromNamespaceAndPath(IndustrialCastingCore.MODID, name);
        this.serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        this.typeObject = Registers.TYPE_REGISTER.register(name, () -> RecipeType.simple(this.id));
        this.type = this.typeObject;
    }

    ModRecipeTypes(ProcessingRecipeBuilder.ProcessingRecipeFactory<?> processingFactory) {
        this((Supplier)(() -> new ProcessingRecipeSerializer(processingFactory)));
    }

    public static void register(IEventBus modEventBus) {
        Registers.SERIALIZER_REGISTER.register(modEventBus);
        Registers.TYPE_REGISTER.register(modEventBus);
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T)(this.serializerObject.get());
    }

    @Override
    public <T extends RecipeType<?>> T getType() {
        return (T)(this.type.get());
    }

    public <C extends Container, T extends Recipe<C>> Optional<T> find(C inv, Level world) {
        return world.getRecipeManager().getRecipeFor(this.getType(), inv, world);
    }

    private static class Registers {
        private static final DeferredRegister<RecipeType<?>> TYPE_REGISTER;
        private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER;
        static {
            TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, IndustrialCastingCore.MODID);
            SERIALIZER_REGISTER = DeferredRegister.create(
                    ForgeRegistries.RECIPE_SERIALIZERS,
                    IndustrialCastingCore.MODID
            );
        }
    }
}
