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

public class ItemToFourItemRecipeCategory<R extends IRRecipe> extends AbstractIRRecipeCategory<R> {

	public ItemToFourItemRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	@Override
	public int getWidth() {
		return 78;
	}

	@Override
	public int getHeight() {
		return 56;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
		addItem(builder, RecipeIngredientRole.INPUT, 1, 20, getInput(recipe, 0), JEIDrawables.SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 43, 11, getOutput(recipe, 0), JEIDrawables.SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 61, 11, getOutput(recipe, 1), JEIDrawables.SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 43, 29, getOutput(recipe, 2), JEIDrawables.SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 61, 29, getOutput(recipe, 3), JEIDrawables.SLOT);
	}

	@Override
	public void createRecipeExtras(IRecipeExtrasBuilder builder, R recipe, IFocusGroup focuses) {
		builder.addDrawable(ProgressBarDrawable.right(recipe), 21, 19);
	}

	@Override
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		Font font = font();
		for(int i = 0; i < 2; ++i) {
			double chance = getOutputChance(recipe, i);
			if(chance < 1) {
				Component chanceComponent = Component.literal((int)(chance * 100) + "%");
				guiGraphics.drawString(font, chanceComponent, 52 + i * 18 - font.width(chanceComponent) / 2, 0, 0xFF808080, false);
			}
			chance = getOutputChance(recipe, i + 2);
			if(chance < 1) {
				Component chanceComponent = Component.literal((int)(chance * 100) + "%");
				guiGraphics.drawString(font, chanceComponent, 52 + i * 18 - font.width(chanceComponent) / 2, 47, 0xFF808080, false);
			}
		}
		Component timeComponent = getTimeComponent(recipe);
		guiGraphics.drawString(font, timeComponent, 0, 0, 0xFF808080, false);
	}
}
