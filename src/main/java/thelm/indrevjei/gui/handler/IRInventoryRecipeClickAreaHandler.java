package thelm.indrevjei.gui.handler;

import java.util.Collection;
import java.util.List;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import me.steven.indrev.blockentities.MachineBlockEntity;
import me.steven.indrev.gui.IRInventoryScreen;
import me.steven.indrev.gui.screenhandlers.IRGuiScreenHandler;
import me.steven.indrev.gui.screenhandlers.ScreenhandlersKt;
import me.steven.indrev.items.upgrade.Enhancer;
import me.steven.indrev.items.upgrade.IREnhancerItem;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import thelm.indrevjei.IndRevJEI;

public class IRInventoryRecipeClickAreaHandler implements IGuiContainerHandler<IRInventoryScreen<?>> {

	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(IRInventoryScreen<?> containerScreen, double guiMouseX, double guiMouseY) {
		MenuType<?> type = containerScreen.getMenu().getType();
		if(type == ScreenhandlersKt.getPULVERIZER_HANDLER()) {
			return List.of(IGuiClickableArea.createBasic(79, 21, 18, 18, IndRevJEI.PULVERIZER));
		}
		if(type == ScreenhandlersKt.getPULVERIZER_FACTORY_HANDLER()) {
			return List.of(IGuiClickableArea.createBasic(39, 30, 119, 18, IndRevJEI.PULVERIZER));
		}
		if(type == ScreenhandlersKt.getSOLID_INFUSER_HANDLER()) {
			return List.of(IGuiClickableArea.createBasic(92, 32, 18, 18, IndRevJEI.INFUSER));
		}
		if(type == ScreenhandlersKt.getSOLID_INFUSER_FACTORY_HANDLER()) {
			return List.of(IGuiClickableArea.createBasic(39, 48, 119, 18, IndRevJEI.INFUSER));
		}
		if(type == ScreenhandlersKt.getCOMPRESSOR_HANDLER()) {
			return List.of(IGuiClickableArea.createBasic(80, 32, 18, 18, IndRevJEI.COMPRESSOR));
		}
		if(type == ScreenhandlersKt.getCOMPRESSOR_FACTORY_HANDLER()) {
			return List.of(IGuiClickableArea.createBasic(39, 30, 119, 18, IndRevJEI.COMPRESSOR));
		}
		if(type == ScreenhandlersKt.getRECYCLER_HANDLER()) {
			return List.of(IGuiClickableArea.createBasic(71, 32, 18, 18, IndRevJEI.RECYCLER));
		}
		if(type == ScreenhandlersKt.getFLUID_INFUSER_HANDLER()) {
			return List.of(IGuiClickableArea.createBasic(90, 32, 18, 18, IndRevJEI.FLUID_INFUSER));
		}
		if(type == ScreenhandlersKt.getCONDENSER_HANDLER()) {
			return List.of(IGuiClickableArea.createBasic(72, 32, 18, 18, IndRevJEI.CONDENSER));
		}
		if(type == ScreenhandlersKt.getSMELTER_HANDLER()) {
			return List.of(IGuiClickableArea.createBasic(86, 32, 18, 18, IndRevJEI.SMELTER));
		}
		if(type == ScreenhandlersKt.getSAWMILL_HANDLER()) {
			return List.of(IGuiClickableArea.createBasic(74, 32, 18, 18, IndRevJEI.SAWMILL));
		}

		if(type == ScreenhandlersKt.getELECTRIC_FURNACE_HANDLER()) {
			Object2IntMap<Enhancer> enhancers = getEnhancers(containerScreen);
			if(enhancers.containsKey(Enhancer.BLAST_FURNACE)) {
				return List.of(IGuiClickableArea.createBasic(80, 32, 18, 18, RecipeTypes.BLASTING));
			}
			if(enhancers.containsKey(Enhancer.SMOKER)) {
				return List.of(IGuiClickableArea.createBasic(80, 32, 18, 18, RecipeTypes.SMOKING));
			}
			return List.of(IGuiClickableArea.createBasic(80, 32, 18, 18, RecipeTypes.SMELTING));
		}
		if(type == ScreenhandlersKt.getELECTRIC_FURNACE_FACTORY_HANDLER()) {
			Object2IntMap<Enhancer> enhancers = getEnhancers(containerScreen);
			if(enhancers.containsKey(Enhancer.BLAST_FURNACE)) {
				return List.of(IGuiClickableArea.createBasic(39, 30, 119, 18, RecipeTypes.BLASTING));
			}
			if(enhancers.containsKey(Enhancer.SMOKER)) {
				return List.of(IGuiClickableArea.createBasic(39, 30, 119, 18, RecipeTypes.SMOKING));
			}
			return List.of(IGuiClickableArea.createBasic(39, 30, 119, 18, RecipeTypes.SMELTING));
		}

		if(type == ScreenhandlersKt.getCOAL_GENERATOR_HANDLER()) {
			return List.of(IGuiClickableArea.createBasic(73, 19, 14, 14, RecipeTypes.FUELING));
		}
		return List.of();
	}

	public static Object2IntMap<Enhancer> getEnhancers(IRInventoryScreen<?> containerScreen) {
		if(containerScreen.getMenu() instanceof IRGuiScreenHandler menu) {
			BlockEntity be = menu.getCtx().evaluate(Level::getBlockEntity).get();
			if(be instanceof MachineBlockEntity<?> machine) {
				Object2IntMap<Enhancer> enhancers = new Object2IntOpenHashMap<>();
				for(int slot : machine.getEnhancerComponent().getSlots()) {
					ItemStack stack = machine.getInventoryComponent().getInventory().getItem(slot);
					if(!stack.isEmpty() && stack.getItem() instanceof IREnhancerItem enhancerItem) {
						enhancers.mergeInt(enhancerItem.getEnhancer(), stack.getCount(), (o, v) -> o + v);
					}
				}
				return enhancers;
			}
		}
		return Object2IntMaps.emptyMap();
	}
}
