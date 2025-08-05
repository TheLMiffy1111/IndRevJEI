package thelm.indrevjei.mixin;

import org.spongepowered.asm.mixin.Mixin;

import me.steven.indrev.items.armor.IRModularArmorItem;
import me.steven.indrev.items.energy.IRBatteryItem;
import me.steven.indrev.items.energy.IRGamerAxeItem;
import me.steven.indrev.items.energy.IRMiningDrillItem;
import me.steven.indrev.items.energy.IRPortableChargerItem;
import me.steven.indrev.utils.EnergyutilsKt;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import team.reborn.energy.api.EnergyStorage;

@Mixin({IRMiningDrillItem.class, IRBatteryItem.class, IRModularArmorItem.class, IRPortableChargerItem.class, IRGamerAxeItem.class})
public class EnergyItemMixin extends Item {

	private EnergyItemMixin(Item.Properties properties) {
		super(properties);
	}

	@Override
	public void fillItemCategory(CreativeModeTab category, NonNullList<ItemStack> items) {
		super.fillItemCategory(category, items);
		if(allowedIn(category)) {
			ItemStack stack = new ItemStack(this);
			EnergyStorage storage = EnergyutilsKt.energyOf(stack);
			if(storage != null && storage.getCapacity() > 0) {
				stack.getOrCreateTag().putLong("energy", storage.getCapacity());
				items.add(stack);
			}
		}
	}
}
