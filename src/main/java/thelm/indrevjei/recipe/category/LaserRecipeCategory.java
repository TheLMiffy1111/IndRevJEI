package thelm.indrevjei.recipe.category;

import me.steven.indrev.recipes.machines.LaserRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
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
	public void createRecipeExtras(IRecipeExtrasBuilder builder, LaserRecipe recipe, IFocusGroup focuses) {
		builder.addDrawable(ProgressBarDrawable.RIGHT_PROCESS_EMPTY, 21, 14);
	}

	@Override
	public void draw(LaserRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		Font font = font();
		double chance = getOutputChance(recipe, 0);
		if(chance < 1) {
			Component chanceComponent = Component.literal((int)(chance * 100) + "%");
			guiGraphics.drawString(font, chanceComponent, 56 - font.width(chanceComponent) / 2, 37, 0xFF808080, false);
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
		guiGraphics.drawString(font, energyComponent, getWidth() - font.width(energyComponent), 0, 0xFF808080, false);
	}
}
