package thelm.indrevjei.recipe.category;

import me.steven.indrev.recipes.machines.IRRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import thelm.indrevjei.gui.render.ProgressBarDrawable;
import thelm.jeidrawables.JEIDrawables;

public class ItemToFluidRecipeCategory<R extends IRRecipe> extends AbstractIRRecipeCategory<R> {

	public ItemToFluidRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public int getWidth() {
		return 58;
	}

	@Override
	public int getHeight() {
		return 43;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		addItem(builder, RecipeIngredientRole.INPUT, 1, 13, getInput(recipe, 0), JEIDrawables.SLOT);
		addFluid(builder, RecipeIngredientRole.OUTPUT, 43, 1, getFluidOutput(recipe, 0));
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, R recipe, IFocusGroup focuses) {
		builder.addDrawable(ProgressBarDrawable.right(recipe), 21, 12);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		Font font = font();
		Component timeComponent = getTimeComponent(recipe);
		guiGraphics.drawString(font, timeComponent, 0, 0, 0xFF808080, false);
	}
}
