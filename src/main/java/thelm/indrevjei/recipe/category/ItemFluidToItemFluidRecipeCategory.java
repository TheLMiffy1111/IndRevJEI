package thelm.indrevjei.recipe.category;

import com.mojang.blaze3d.vertex.PoseStack;

import me.steven.indrev.recipes.machines.IRRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import thelm.indrevjei.gui.render.ProgressBarDrawable;
import thelm.jeidrawables.JEIDrawables;

public class ItemFluidToItemFluidRecipeCategory<R extends IRRecipe> extends AbstractIRRecipeCategory<R> {

	public ItemFluidToItemFluidRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public int getWidth() {
		return 96;
	}

	@Override
	public int getHeight() {
		return 43;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		addItem(builder, RecipeIngredientRole.INPUT, 19, 13, getInput(recipe, 0), JEIDrawables.SLOT);
		addFluid(builder, RecipeIngredientRole.INPUT, 1, 1, getFluidInput(recipe, 0));
		addItem(builder, RecipeIngredientRole.OUTPUT, 61, 13, getOutput(recipe, 0), JEIDrawables.SLOT);
		addFluid(builder, RecipeIngredientRole.OUTPUT, 81, 1, getFluidOutput(recipe, 0));
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		ProgressBarDrawable.right(recipe).draw(poseStack, 39, 12);
		Font font = font();
		double chance = getOutputChance(recipe, 0);
		if(chance < 1) {
			Component chanceComponent = new TextComponent((int)(chance * 100) + "%");
			font.draw(poseStack, chanceComponent, 70 - font.width(chanceComponent) / 2, 31, 0xFF808080);
		}
		Component timeComponent = getTimeComponent(recipe);
		font.draw(poseStack, timeComponent, 78 - font.width(timeComponent), 0, 0xFF808080);
	}
}
