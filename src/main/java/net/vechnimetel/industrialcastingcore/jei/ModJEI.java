package net.vechnimetel.industrialcastingcore.jei;

import com.simibubi.create.Create;
import com.simibubi.create.compat.jei.CreateJEI;
import com.simibubi.create.compat.jei.DoubleItemIcon;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.vechnimetel.industrialcastingcore.IndustrialCastingCore;
import net.vechnimetel.industrialcastingcore.block.ModBlocks;
import net.vechnimetel.industrialcastingcore.jei.category.ElectrolysisCategory;
import net.vechnimetel.industrialcastingcore.recipe.ElectrolysisRecipe;
import net.vechnimetel.industrialcastingcore.recipe.ModRecipeTypes;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@JeiPlugin
public class ModJEI implements IModPlugin {
    private static final ResourceLocation UID =
            ResourceLocation.fromNamespaceAndPath(IndustrialCastingCore.MODID, "jei_plugin");
    private final List<CreateRecipeCategory<?>> categories = new ArrayList<>();
    private IIngredientManager ingredientManager;

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        this.categories.clear();
        CategoryBuilder categoryBuilder = this.builder(ElectrolysisRecipe.class);
        categoryBuilder = categoryBuilder.addTypedRecipes(ModRecipeTypes.ELECTROLYSIS);
        Block catalyst = ModBlocks.ELECTROLYSIS.get();
        Block icon = dev.dubhe.anvilcraft.init.ModBlocks.RUBY_LASER.get();
        CreateRecipeCategory<?> electrolysis = categoryBuilder.catalyst(() -> catalyst)
                .doubleItemIcon(catalyst,icon).emptyBackground(177,70)
                .build("electrolysis", ElectrolysisCategory::new);
        this.categories.forEach(category ->
                registration.addRecipeCategories(new IRecipeCategory[]{category}));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        this.ingredientManager = registration.getIngredientManager();
        this.categories.forEach((c) -> c.registerRecipes(registration));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        this.categories.forEach((c) -> c.registerCatalysts(registration));
    }

    private <T extends Recipe<?>> CategoryBuilder<T> builder(Class<? extends T> recipeClass) {
        return new CategoryBuilder<T>(recipeClass);
    }

    private class CategoryBuilder<T extends Recipe<?>> {
        private final Class<? extends T> recipeClass;
        private Predicate<CRecipes> predicate = (cRecipes) -> true;
        private IDrawable background;
        private IDrawable icon;
        private final List<Consumer<List<T>>> recipeListConsumers = new ArrayList();
        private final List<Supplier<? extends ItemStack>> catalysts = new ArrayList();

        public CategoryBuilder(Class<? extends T> recipeClass) {
            this.recipeClass = recipeClass;
        }

        public CategoryBuilder<T> addRecipeListConsumer(Consumer<List<T>> consumer) {
            this.recipeListConsumers.add(consumer);
            return this;
        }

        public CategoryBuilder<T> addTypedRecipes(IRecipeTypeInfo recipeTypeEntry) {
            Objects.requireNonNull(recipeTypeEntry);
            return this.addTypedRecipes(recipeTypeEntry::getType);
        }

        public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType) {
            return this.addRecipeListConsumer((recipes) -> {
                Objects.requireNonNull(recipes);
                CreateJEI.consumeTypedRecipes((t) -> recipes.add((T) t), (RecipeType)recipeType.get());
            });
        }

        public CategoryBuilder<T> catalystStack(Supplier<ItemStack> supplier) {
            this.catalysts.add(supplier);
            return this;
        }

        public CategoryBuilder<T> catalyst(Supplier<ItemLike> supplier) {
            return this.catalystStack(() -> new ItemStack(((ItemLike)supplier.get()).asItem()));
        }

        public CategoryBuilder<T> icon(IDrawable icon) {
            this.icon = icon;
            return this;
        }

        public CategoryBuilder<T> itemIcon(ItemLike item) {
            this.icon(new ItemIcon(() -> new ItemStack(item)));
            return this;
        }

        public CategoryBuilder<T> doubleItemIcon(ItemLike item1, ItemLike item2) {
            this.icon(new DoubleItemIcon(() -> new ItemStack(item1), () -> new ItemStack(item2)));
            return this;
        }

        public CategoryBuilder<T> background(IDrawable background) {
            this.background = background;
            return this;
        }

        public CategoryBuilder<T> emptyBackground(int width, int height) {
            this.background(new EmptyBackground(width, height));
            return this;
        }

        public CreateRecipeCategory<T> build(String name, CreateRecipeCategory.Factory<T> factory) {
            Supplier<List<T>> recipesSupplier;
            if (this.predicate.test(AllConfigs.server().recipes)) {
                recipesSupplier = () -> {
                    List<T> recipes = new ArrayList();

                    for(Consumer<List<T>> consumer : this.recipeListConsumers) {
                        consumer.accept(recipes);
                    }

                    return recipes;
                };
            } else {
                recipesSupplier = () -> Collections.emptyList();
            }

            CreateRecipeCategory.Info<T> info = new CreateRecipeCategory.Info(new mezz.jei.api.recipe.RecipeType(Create.asResource(name), this.recipeClass), Lang.translateDirect("recipe." + name, new Object[0]), this.background, this.icon, recipesSupplier, this.catalysts);
            CreateRecipeCategory<T> category = factory.create(info);
            ModJEI.this.categories.add(category);
            return category;
        }
    }
}
