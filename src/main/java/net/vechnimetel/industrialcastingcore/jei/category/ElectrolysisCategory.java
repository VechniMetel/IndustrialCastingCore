package net.vechnimetel.industrialcastingcore.jei.category;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.utility.Lang;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.crafting.Ingredient;
import net.vechnimetel.industrialcastingcore.jei.animation.AnimatedElectrolysis;
import net.vechnimetel.industrialcastingcore.recipe.ElectrolysisRecipe;

public class ElectrolysisCategory extends CreateRecipeCategory<ElectrolysisRecipe> {
    private final AnimatedElectrolysis electrolysis = new AnimatedElectrolysis();

    public ElectrolysisCategory(Info<ElectrolysisRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ElectrolysisRecipe recipe, IFocusGroup iFocusGroup) {

        builder.addSlot(RecipeIngredientRole.INPUT, 27, 51).setBackground(getRenderedSlot(), -1, -1)
                .addIngredients((Ingredient)recipe.getIngredients().get(0));

        IRecipeSlotBuilder slot = (IRecipeSlotBuilder) builder.addSlot(RecipeIngredientRole.INPUT, 51, 5).setBackground(getRenderedSlot(), -1, -1)
                .addIngredients((Ingredient)recipe.getIngredients().get(1));

        slot.addTooltipCallback((recipeSlotView, tooltip) ->
                tooltip.add(1, Lang.translateDirect("recipe.deploying.not_consumed")
                        .withStyle(ChatFormatting.GOLD)));

        ProcessingOutput output = recipe.getRollableResults().get(0);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 132, 51).setBackground(getRenderedSlot(output), -1, -1)
                .addItemStack(output.getStack());
    }

    @Override
    public void draw(ElectrolysisRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        AllGuiTextures.JEI_SHADOW.render(graphics, 62, 57);
        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 126, 29);
        this.electrolysis.draw(graphics,this.getBackground().getWidth() / 2 - 13, 22);
    }
}
