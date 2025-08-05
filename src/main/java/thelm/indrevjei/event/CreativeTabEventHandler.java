package thelm.indrevjei.event;

import me.steven.indrev.registry.IRItemRegistry;
import me.steven.indrev.utils.EnergyutilsKt;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import team.reborn.energy.api.EnergyStorage;

public class CreativeTabEventHandler implements ItemGroupEvents.ModifyEntries {

	@Override
	public void modifyEntries(FabricItemGroupEntries entries) {
		addCharged(entries, IRItemRegistry.INSTANCE.getMINING_DRILL_MK1());
		addCharged(entries, IRItemRegistry.INSTANCE.getMINING_DRILL_MK2());
		addCharged(entries, IRItemRegistry.INSTANCE.getMINING_DRILL_MK3());
		addCharged(entries, IRItemRegistry.INSTANCE.getMINING_DRILL_MK4());
		addCharged(entries, IRItemRegistry.INSTANCE.getBATTERY());
		addCharged(entries, IRItemRegistry.INSTANCE.getMODULAR_ARMOR_HELMET());
		addCharged(entries, IRItemRegistry.INSTANCE.getMODULAR_ARMOR_CHEST());
		addCharged(entries, IRItemRegistry.INSTANCE.getMODULAR_ARMOR_LEGGINGS());
		addCharged(entries, IRItemRegistry.INSTANCE.getMODULAR_ARMOR_BOOTS());
		addCharged(entries, IRItemRegistry.INSTANCE.getPORTABLE_CHARGER_ITEM());
		addCharged(entries, IRItemRegistry.INSTANCE.getGAMER_AXE_ITEM());
	}

	public void addCharged(FabricItemGroupEntries entries, Item item) {
		ItemStack stack = new ItemStack(item);
		EnergyStorage storage = EnergyutilsKt.energyOf(stack);
		if(storage != null && storage.getCapacity() > 0) {
			stack.getOrCreateTag().putLong("energy", storage.getCapacity());
			entries.addAfter(item, stack);
		}
	}
}
