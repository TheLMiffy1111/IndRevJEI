package thelm.indrevjei.recipe.category;

import com.mojang.blaze3d.vertex.PoseStack;

import me.steven.indrev.recipes.machines.LaserRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import thelm.indrevjei.IndRevJEI;
import thelm.indrevjei.gui.render.ProgressBarDrawable;
import thelm.jeidrawables.JEIDrawables;

public class LaserRecipeCategory extends AbstractIRRecipeCategory<LaserRecipe> {

	public static final Component TITLE = Component.translatable("block.indrev.laser_emitter_mk4");

	public LaserRecipeCategory() {
		super(IndRevJEI.LASER, TITLE);
	}

	@Override
	public int getWidth() {
		return 68;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, LaserRecipe recipe, IFocusGroup focuses) {
		addItem(builder, RecipeIngredientRole.INPUT, 1, 15, getInput(recipe, 0), JEIDrawables.SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 47, 15, getOutput(recipe, 0), JEIDrawables.OUTPUT_SLOT);
	}

	@Override
	public void draw(LaserRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		ProgressBarDrawable.RIGHT_PROCESS_EMPTY.draw(poseStack, 21, 14);
		Font font = font();
		double chance = getOutputChance(recipe, 0);
		if(chance < 1) {
			Component chanceComponent = Component.literal((int)(chance * 100) + "%");
			font.draw(poseStack, chanceComponent, 56 - font.width(chanceComponent) / 2, 37, 0xFF808080);
		}
		int energyReq = recipe.getTicks();
		String energyKey;
		String energyText;
		if(energyReq < 1000) {
			energyKey = "gui.indrev.tooltip.lf";
			energyText = TIME_FORMAT.format(energyReq);
		}
		else if(energyReq < 1000000) {
			energyKey = "indrevjei.tooltip.lf.kilo";
			energyText = TIME_FORMAT.format(energyReq / 1000D);
		}
		else {
			energyKey = "indrevjei.tooltip.lf.mega";
			energyText = TIME_FORMAT.format(energyReq / 1000000D);
		}
		Component energyComponent = Component.translatable(energyKey, energyText);
		font.draw(poseStack, energyComponent, getWidth() - font.width(energyComponent), 0, 0xFF808080);
	}
}
