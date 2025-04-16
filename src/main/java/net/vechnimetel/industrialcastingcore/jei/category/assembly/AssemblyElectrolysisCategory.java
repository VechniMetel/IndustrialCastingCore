package net.vechnimetel.industrialcastingcore.jei.category.assembly;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import com.simibubi.create.foundation.utility.Lang;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.crafting.Ingredient;
import net.vechnimetel.industrialcastingcore.jei.animation.AnimatedElectrolysis;

public class AssemblyElectrolysisCategory extends SequencedAssemblySubCategory {

    private final AnimatedElectrolysis electrolysis = new AnimatedElectrolysis();

    public AssemblyElectrolysisCategory() {
        super(25);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SequencedRecipe<?> recipe, IFocusGroup focuses, int x) {

        IRecipeSlotBuilder slot = (IRecipeSlotBuilder)builder.addSlot(RecipeIngredientRole.INPUT, x + 4, 15)
                .setBackground(CreateRecipeCategory.getRenderedSlot(), -1, -1)
                .addIngredients((Ingredient)recipe.getRecipe().getIngredients().get(1));

        slot.addTooltipCallback((recipeSlotView, tooltip) ->
                tooltip.add(1, Lang.translateDirect("recipe.deploying.not_consumed")
                        .withStyle(ChatFormatting.GOLD)));
    }

    @Override
    public void draw(SequencedRecipe<?> recipe, GuiGraphics graphics, double mouseX, double mouseY, int index) {
        PoseStack ms = graphics.pose();
        this.electrolysis.offset = index;
        ms.pushPose();
        ms.translate(-7.0F, 50.0F, 0.0F);
        ms.scale(0.75F, 0.75F, 0.75F);
        this.electrolysis.draw(graphics,this.getWidth()/2,0);
        ms.popPose();
    }
}
