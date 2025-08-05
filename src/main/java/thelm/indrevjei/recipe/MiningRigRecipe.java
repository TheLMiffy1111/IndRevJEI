package thelm.indrevjei.recipe;

import java.util.List;

import me.steven.indrev.registry.IRItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record MiningRigRecipe(Item ore, int cost) {

	public List<ItemStack> getDrills() {
		return List.of(
				new ItemStack(IRItemRegistry.INSTANCE.getSTONE_DRILL_HEAD()),
				new ItemStack(IRItemRegistry.INSTANCE.getIRON_DRILL_HEAD()),
				new ItemStack(IRItemRegistry.INSTANCE.getDIAMOND_DRILL_HEAD()),
				new ItemStack(IRItemRegistry.INSTANCE.getNETHERITE_DRILL_HEAD()));
	}

	public ItemStack getOutput() {
		return new ItemStack(ore);
	}

	public int getEnergyReq() {
		return cost * 16;
	}
}
