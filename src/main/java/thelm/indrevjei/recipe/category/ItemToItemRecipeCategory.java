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

public class ItemToItemRecipeCategory<R extends IRRecipe> extends AbstractIRRecipeCategory<R> {

	public ItemToItemRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public int getWidth() {
		return 68;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		addItem(builder, RecipeIngredientRole.INPUT, 1, 15, getInput(recipe, 0), JEIDrawables.SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 47, 15, getOutput(recipe, 0), JEIDrawables.OUTPUT_SLOT);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, R recipe, IFocusGroup focuses) {
		builder.addDrawable(ProgressBarDrawable.right(recipe), 21, 14);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		Font font = font();
		double chance = getOutputChance(recipe, 0);
		if(chance < 1) {
			Component chanceComponent = Component.literal((int)(chance * 100) + "%");
			guiGraphics.drawString(font, chanceComponent, 56 - font.width(chanceComponent) / 2, 37, 0xFF808080, false);
		}
		Component timeComponent = getTimeComponent(recipe);
		guiGraphics.drawString(font, timeComponent, getWidth() - font.width(timeComponent), 0, 0xFF808080, false);
	}
}
