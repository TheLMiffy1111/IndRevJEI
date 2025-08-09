package thelm.indrevjei.ingredient.subtype;

import me.steven.indrev.utils.EnergyutilsKt;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import team.reborn.energy.api.EnergyStorage;

public class EnergyItemSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {

	@Override
	public String apply(ItemStack ingredient, UidContext context) {
		if(context == UidContext.Ingredient) {
			EnergyStorage storage = EnergyutilsKt.energyOf(ingredient);
			if(storage != null && storage.getCapacity() > 0 && storage.getAmount() == storage.getCapacity()) {
				return "f";
			}
		}
		return NONE;
	}
}
