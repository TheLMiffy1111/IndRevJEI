package thelm.indrevjei.recipe.category;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import me.steven.indrev.gui.widgets.machines.WCustomBarKt;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.ResourceAmount;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import thelm.indrevjei.IndRevJEI;
import thelm.jeidrawables.JEIDrawables;
import thelm.jeidrawables.gui.render.BlankDrawable;
import thelm.jeidrawables.gui.render.ResourceDrawable;

public abstract class AbstractRecipeCategory<R> implements IRecipeCategory<R> {

	public static final NumberFormat TIME_FORMAT = new DecimalFormat("###.##");

	public static final ResourceDrawable TANK_BOTTOM = new ResourceDrawable(WCustomBarKt.getTANK_BOTTOM().image(), 0, 0, 16, 43, 16, 43);
	public static final ResourceDrawable TANK_TOP = new ResourceDrawable(WCustomBarKt.getTANK_TOP().image(), 0, 0, 16, 43, 16, 43);

	public final RecipeType<R> recipeType;
	public final Component title;
	public final IDrawable background;

	public AbstractRecipeCategory(RecipeType<R> recipeType, Component title) {
		this.recipeType = recipeType;
		this.title = title;
		background = new BlankDrawable(getWidth(), getHeight());
	}

	@Override
	public RecipeType<R> getRecipeType() {
		return recipeType;
	}

	@Override
	public Component getTitle() {
		return title;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	public abstract int getWidth();

	public int getHeight() {
		return 46;
	}

	@Override
	public IDrawable getIcon() {
		return null;
	}

	public Font font() {
		return Minecraft.getInstance().font;
	}

	public IJeiHelpers jeiHelpers() {
		return IndRevJEI.jeiHelpers;
	}

	public IPlatformFluidHelper<?> fluidHelper() {
		return jeiHelpers().getPlatformFluidHelper();
	}

	public IRecipeSlotBuilder addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, IDrawable background) {
		return builder.addSlot(ingredientRole, x, y).setBackground(background, 8 - background.getWidth() / 2, 8 - background.getHeight() / 2);
	}

	public IRecipeSlotBuilder addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, List<ItemStack> itemStacks, IDrawable background) {
		return addItem(builder, ingredientRole, x, y, background).addItemStacks(itemStacks);
	}

	public IRecipeSlotBuilder addItem(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, ItemStack itemStack, IDrawable background) {
		return addItem(builder, ingredientRole, x, y, background).addItemStack(itemStack);
	}

	public IRecipeSlotBuilder addFluid(IRecipeLayoutBuilder builder, RecipeIngredientRole ingredientRole, int x, int y, ResourceAmount<FluidVariant> fluidStack) {
		Fluid fluid = fluidStack.resource().getFluid();
		long amount = fluidStack.amount() / (FluidConstants.BUCKET / fluidHelper().bucketVolume());
		long fraction = fluidStack.amount() % (FluidConstants.BUCKET / 1000);
		CompoundTag data = fluidStack.resource().copyNbt();
		IRecipeSlotBuilder slot = builder.addSlot(ingredientRole, x, y).setBackground(TANK_BOTTOM, -1, -1).setOverlay(TANK_TOP, -1, -1).setFluidRenderer(Math.max(amount, 1), false, 14, 41).addTooltipCallback(JEIDrawables.appendFraction(fraction));
		if(fluid != Fluids.EMPTY && fluidStack.amount() > 0) {
			slot.addFluidStack(fluid, amount, data);
		}
		return slot;
	}
}
