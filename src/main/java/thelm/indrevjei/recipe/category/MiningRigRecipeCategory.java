package thelm.indrevjei.recipe.category;

import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import thelm.indrevjei.IndRevJEI;
import thelm.indrevjei.gui.render.ProgressBarDrawable;
import thelm.indrevjei.recipe.MiningRigRecipe;
import thelm.jeidrawables.JEIDrawables;

public class MiningRigRecipeCategory extends AbstractRecipeCategory<MiningRigRecipe> {

	public static final Component TITLE = Component.translatable("block.indrev.mining_rig_mk4");

	public MiningRigRecipeCategory() {
		super(IndRevJEI.MINING_RIG, TITLE);
	}

	@Override
	public int getWidth() {
		return 68;
	}

	@Override
	public int getHeight() {
		return 36;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, MiningRigRecipe recipe, IFocusGroup focuses) {
		addItem(builder, RecipeIngredientRole.CATALYST, 1, 15, recipe.getDrills(), JEIDrawables.SLOT);
		addItem(builder, RecipeIngredientRole.OUTPUT, 47, 15, recipe.getOutput(), JEIDrawables.OUTPUT_SLOT);
	}

	@Override
	public void draw(MiningRigRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
		ProgressBarDrawable.RIGHT_PROCESS_EMPTY.draw(poseStack, 21, 14);
		Font font = font();
		Component energyComponent = Component.translatable("gui.indrev.tooltip.lftick", recipe.getEnergyReq());
		font.draw(poseStack, energyComponent, getWidth() - font.width(energyComponent), 0, 0xFF808080);
	}
}
