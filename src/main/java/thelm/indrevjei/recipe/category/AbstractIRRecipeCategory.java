package thelm.indrevjei.recipe.category;

import java.util.Arrays;
import java.util.List;

import me.steven.indrev.recipes.machines.IRFluidRecipe;
import me.steven.indrev.recipes.machines.IRRecipe;
import me.steven.indrev.recipes.machines.entries.InputEntry;
import mezz.jei.api.recipe.RecipeType;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.ResourceAmount;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractIRRecipeCategory<R extends IRRecipe> extends AbstractRecipeCategory<R> {

	public AbstractIRRecipeCategory(RecipeType<R> recipeType, Component title) {
		super(recipeType, title);
	}

	public List<ItemStack> getInput(IRRecipe recipe, int index) {
		if(index >= 0 && index < recipe.getInput().length) {
			InputEntry entry = recipe.getInput()[index];
			return Arrays.stream(entry.getIngredient().getItems()).
					map(ItemStack::copy).
					peek(s -> s.setCount(entry.getCount())).
					toList();
		}
		return List.of();
	}

	public ResourceAmount<FluidVariant> getFluidInput(IRRecipe recipe, int index) {
		if(recipe instanceof IRFluidRecipe fluidRecipe && index >= 0 && index < fluidRecipe.getFluidInput().length) {
			return fluidRecipe.getFluidInput()[index];
		}
		return new ResourceAmount<>(FluidVariant.blank(), 0);
	}

	public ItemStack getOutput(IRRecipe recipe, int index) {
		if(index >= 0 && index < recipe.getOutputs().length) {
			return recipe.getOutputs()[index].getStack();
		}
		return ItemStack.EMPTY;
	}

	public double getOutputChance(IRRecipe recipe, int index) {
		if(index >= 0 && index < recipe.getOutputs().length) {
			return recipe.getOutputs()[index].getChance();
		}
		return 1;
	}

	public ResourceAmount<FluidVariant> getFluidOutput(IRRecipe recipe, int index) {
		if(recipe instanceof IRFluidRecipe fluidRecipe && index >= 0 && index < fluidRecipe.getFluidOutput().length) {
			return fluidRecipe.getFluidOutput()[index];
		}
		return new ResourceAmount<>(FluidVariant.blank(), 0);
	}

	public Component getTimeComponent(IRRecipe recipe) {
		return Component.literal(TIME_FORMAT.format(recipe.getTicks() / 20D) + "s");
	}
}
