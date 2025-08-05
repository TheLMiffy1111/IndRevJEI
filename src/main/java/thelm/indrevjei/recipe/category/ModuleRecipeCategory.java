package thelm.indrevjei.recipe.category;

import com.mojang.blaze3d.vertex.PoseStack;

import me.steven.indrev.recipes.machines.ModuleRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import thelm.indrevjei.IndRevJEI;
import thelm.jeidrawables.JEIDrawables;

public class ModuleRecipeCategory extends AbstractIRRecipeCategory<ModuleRecipe> {

	public static final Component TITLE = new TranslatableComponent("indrev.category.rei.module");

	public static final int[][][] LAYOUTS = {
			{{37, 1}},
			{{1, 37}, {73, 37}},
			{{37, 1}, {1, 69}, {73, 69}},
			{{37, 1}, {1, 37}, {73, 37}, {37, 73}},
			{{37, 1}, {1, 37}, {73, 37}, {15, 73}, {59, 73}},
			{{37, 1}, {1, 19}, {73, 19}, {1, 55}, {73, 55}, {37, 73}},
	};

	public ModuleRecipeCategory() {
		super(IndRevJEI.MODULE, TITLE);
	}

	@Override
	public int getWidth() {
		return 90;
	}

	@Override
	public int getHeight() {
		return 90;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, ModuleRecipe recipe, IFocusGroup focuses) {
		int[][] layout = LAYOUTS[Mth.clamp(recipe.getInput().length - 1, 0, 5)];
		for(int i = 0; i < layout.length; ++i) {
			addItem(builder, RecipeIngredientRole.INPUT, layout[i][0], layout[i][1], getInput(recipe, i), JEIDrawables.SLOT);
		}
		addItem(builder, RecipeIngredientRole.OUTPUT, 37, 37, getOutput(recipe, 0), JEIDrawables.OUTPUT_SLOT);
	}

	@Override
	public void draw(ModuleRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		Font font = font();
		double chance = getOutputChance(recipe, 0);
		if(chance < 1) {
			Component chanceComponent = new TextComponent((int)(chance * 100) + "%");
			font.draw(poseStack, chanceComponent, 46 - font.width(chanceComponent) / 2, 59, 0xFF808080);
		}
		Component timeComponent = getTimeComponent(recipe);
		font.draw(poseStack, timeComponent, getWidth() - font.width(timeComponent), 0, 0xFF808080);
	}
}
