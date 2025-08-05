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
	public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		ProgressBarDrawable.right(recipe).draw(poseStack, 21, 19);
		Font font = font();
		for(int i = 0; i < 2; ++i) {
			double chance = getOutputChance(recipe, i);
			if(chance < 1) {
				Component chanceComponent = new TextComponent((int)(chance * 100) + "%");
				font.draw(poseStack, chanceComponent, 52 + i * 18 - font.width(chanceComponent) / 2, 0, 0xFF808080);
			}
			chance = getOutputChance(recipe, i + 2);
			if(chance < 1) {
				Component chanceComponent = new TextComponent((int)(chance * 100) + "%");
				font.draw(poseStack, chanceComponent, 52 + i * 18 - font.width(chanceComponent) / 2, 47, 0xFF808080);
			}
		}
		Component timeComponent = getTimeComponent(recipe);
		font.draw(poseStack, timeComponent, 0, 0, 0xFF808080);
	}
}
