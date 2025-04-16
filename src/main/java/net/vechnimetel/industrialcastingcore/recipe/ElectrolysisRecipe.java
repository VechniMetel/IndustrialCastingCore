package net.vechnimetel.industrialcastingcore.recipe;

import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.sequenced.IAssemblyRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.vechnimetel.industrialcastingcore.IndustrialCastingCore;
import net.vechnimetel.industrialcastingcore.block.ModBlocks;
import net.vechnimetel.industrialcastingcore.jei.category.assembly.AssemblyElectrolysisCategory;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class ElectrolysisRecipe extends ProcessingRecipe<RecipeWrapper> implements IAssemblyRecipe {

    public ElectrolysisRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(ModRecipeTypes.ELECTROLYSIS, params);
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        return this.getIngredients().get(0).test(inv.getItem(0)) &&
                this.getIngredients().get(1).test(inv.getItem(1));
    }

    @Override
    protected int getMaxInputCount() {
        return 2;
    }

    @Override
    protected int getMaxOutputCount() {
        return 1;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Component getDescriptionForAssembly() {
        return Component.translatable(IndustrialCastingCore.MODID+".recipe.assembly.electrolysis");
    }

    @Override
    public void addRequiredMachines(Set<ItemLike> set) {
        set.add(ModBlocks.ELECTROLYSIS.get());
    }

    @Override
    public void addAssemblyIngredients(List<Ingredient> list) {
        list.add(this.getIngredients().get(1));
    }

    @Override
    public Supplier<Supplier<SequencedAssemblySubCategory>> getJEISubCategory() {
        return () -> AssemblyElectrolysisCategory::new;
    }}
