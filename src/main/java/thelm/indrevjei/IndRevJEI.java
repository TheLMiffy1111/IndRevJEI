package thelm.indrevjei;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Streams;

import me.steven.indrev.IndustrialRevolution;
import me.steven.indrev.api.machines.Tier;
import me.steven.indrev.config.IRConfig;
import me.steven.indrev.gui.IRInventoryScreen;
import me.steven.indrev.recipes.machines.CompressorRecipe;
import me.steven.indrev.recipes.machines.CondenserRecipe;
import me.steven.indrev.recipes.machines.FluidInfuserRecipe;
import me.steven.indrev.recipes.machines.IRRecipe;
import me.steven.indrev.recipes.machines.IRRecipeType;
import me.steven.indrev.recipes.machines.InfuserRecipe;
import me.steven.indrev.recipes.machines.LaserRecipe;
import me.steven.indrev.recipes.machines.ModuleRecipe;
import me.steven.indrev.recipes.machines.PulverizerRecipe;
import me.steven.indrev.recipes.machines.RecyclerRecipe;
import me.steven.indrev.recipes.machines.SawmillRecipe;
import me.steven.indrev.recipes.machines.SmelterRecipe;
import me.steven.indrev.registry.IRBlockRegistry;
import me.steven.indrev.registry.IRItemRegistry;
import me.steven.indrev.registry.MachineRegistry;
import me.steven.indrev.utils.UtilsKt;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeManager;
import thelm.indrevjei.event.CreativeTabEventHandler;
import thelm.indrevjei.gui.handler.IRInventoryRecipeClickAreaHandler;
import thelm.indrevjei.ingredient.subtype.EnergyItemSubtypeInterpreter;
import thelm.indrevjei.recipe.MiningRigRecipe;
import thelm.indrevjei.recipe.category.FluidToItemRecipeCategory;
import thelm.indrevjei.recipe.category.ItemFluidToItemFluidRecipeCategory;
import thelm.indrevjei.recipe.category.ItemToFluidRecipeCategory;
import thelm.indrevjei.recipe.category.ItemToFourItemRecipeCategory;
import thelm.indrevjei.recipe.category.ItemToItemRecipeCategory;
import thelm.indrevjei.recipe.category.ItemToTwoItemRecipeCategory;
import thelm.indrevjei.recipe.category.LaserRecipeCategory;
import thelm.indrevjei.recipe.category.MiningRigRecipeCategory;
import thelm.indrevjei.recipe.category.ModuleRecipeCategory;
import thelm.indrevjei.recipe.category.TwoItemToItemRecipeCategory;

public class IndRevJEI implements IModPlugin {

	public static final ResourceLocation UID = new ResourceLocation("indrevjei:indrev");
	public static final Logger LOGGER = LogManager.getLogger();

	public static IJeiHelpers jeiHelpers;
	public static IJeiRuntime jeiRuntime;

	public static final RecipeType<PulverizerRecipe> PULVERIZER = createRecipeType(PulverizerRecipe.Companion.getTYPE(), PulverizerRecipe.class);
	public static final RecipeType<InfuserRecipe> INFUSER = createRecipeType(InfuserRecipe.Companion.getTYPE(), InfuserRecipe.class);
	public static final RecipeType<CompressorRecipe> COMPRESSOR = createRecipeType(CompressorRecipe.Companion.getTYPE(), CompressorRecipe.class);
	public static final RecipeType<RecyclerRecipe> RECYCLER = createRecipeType(RecyclerRecipe.Companion.getTYPE(), RecyclerRecipe.class);
	public static final RecipeType<FluidInfuserRecipe> FLUID_INFUSER = createRecipeType(FluidInfuserRecipe.Companion.getTYPE(), FluidInfuserRecipe.class);
	public static final RecipeType<CondenserRecipe> CONDENSER = createRecipeType(CondenserRecipe.Companion.getTYPE(), CondenserRecipe.class);
	public static final RecipeType<SmelterRecipe> SMELTER = createRecipeType(SmelterRecipe.Companion.getTYPE(), SmelterRecipe.class);
	public static final RecipeType<SawmillRecipe> SAWMILL = createRecipeType(SawmillRecipe.Companion.getTYPE(), SawmillRecipe.class);
	public static final RecipeType<ModuleRecipe> MODULE = createRecipeType(ModuleRecipe.Companion.getTYPE(), ModuleRecipe.class);
	public static final RecipeType<LaserRecipe> LASER = createRecipeType(LaserRecipe.Companion.getTYPE(), LaserRecipe.class);

	public static final RecipeType<MiningRigRecipe> MINING_RIG = new RecipeType<>(UtilsKt.identifier("mining_rig"), MiningRigRecipe.class);

	public IndRevJEI() {
		ItemGroupEvents.modifyEntriesEvent(IndustrialRevolution.INSTANCE.getMOD_GROUP_KEY()).register(new CreativeTabEventHandler());;
	}

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		EnergyItemSubtypeInterpreter energy = new EnergyItemSubtypeInterpreter();
		registration.registerSubtypeInterpreter(IRItemRegistry.INSTANCE.getMINING_DRILL_MK1(), energy);
		registration.registerSubtypeInterpreter(IRItemRegistry.INSTANCE.getMINING_DRILL_MK2(), energy);
		registration.registerSubtypeInterpreter(IRItemRegistry.INSTANCE.getMINING_DRILL_MK3(), energy);
		registration.registerSubtypeInterpreter(IRItemRegistry.INSTANCE.getMINING_DRILL_MK4(), energy);
		registration.registerSubtypeInterpreter(IRItemRegistry.INSTANCE.getBATTERY(), energy);
		registration.registerSubtypeInterpreter(IRItemRegistry.INSTANCE.getMODULAR_ARMOR_HELMET(), energy);
		registration.registerSubtypeInterpreter(IRItemRegistry.INSTANCE.getMODULAR_ARMOR_CHEST(), energy);
		registration.registerSubtypeInterpreter(IRItemRegistry.INSTANCE.getMODULAR_ARMOR_LEGGINGS(), energy);
		registration.registerSubtypeInterpreter(IRItemRegistry.INSTANCE.getMODULAR_ARMOR_BOOTS(), energy);
		registration.registerSubtypeInterpreter(IRItemRegistry.INSTANCE.getPORTABLE_CHARGER_ITEM(), energy);
		registration.registerSubtypeInterpreter(IRItemRegistry.INSTANCE.getGAMER_AXE_ITEM(), energy);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		jeiHelpers = registration.getJeiHelpers();

		if(checkDisabled()) {
			return;
		}

		registration.addRecipeCategories(new ItemToTwoItemRecipeCategory<>(PULVERIZER, Component.translatable("indrev.category.rei.pulverizing")));
		registration.addRecipeCategories(new TwoItemToItemRecipeCategory<>(INFUSER, Component.translatable("indrev.category.rei.infusing")));
		registration.addRecipeCategories(new ItemToItemRecipeCategory<>(COMPRESSOR, Component.translatable("indrev.category.rei.compressing")));
		registration.addRecipeCategories(new ItemToItemRecipeCategory<>(RECYCLER, Component.translatable("indrev.category.rei.recycling")));
		registration.addRecipeCategories(new ItemFluidToItemFluidRecipeCategory<>(FLUID_INFUSER, Component.translatable("indrev.category.rei.fluid_infusing")));
		registration.addRecipeCategories(new FluidToItemRecipeCategory<>(CONDENSER, Component.translatable("indrev.category.rei.condensing")));
		registration.addRecipeCategories(new ItemToFluidRecipeCategory<>(SMELTER, Component.translatable("indrev.category.rei.smelting")));
		registration.addRecipeCategories(new ItemToFourItemRecipeCategory<>(SAWMILL, Component.translatable("indrev.category.rei.sawmill")));
		registration.addRecipeCategories(new ModuleRecipeCategory());
		registration.addRecipeCategories(new LaserRecipeCategory());

		registration.addRecipeCategories(new MiningRigRecipeCategory());
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
		registration.addRecipes(PULVERIZER, recipeManager.getAllRecipesFor(PulverizerRecipe.Companion.getTYPE()));
		registration.addRecipes(INFUSER, recipeManager.getAllRecipesFor(InfuserRecipe.Companion.getTYPE()));
		registration.addRecipes(COMPRESSOR, recipeManager.getAllRecipesFor(CompressorRecipe.Companion.getTYPE()));
		registration.addRecipes(RECYCLER, recipeManager.getAllRecipesFor(RecyclerRecipe.Companion.getTYPE()));
		registration.addRecipes(FLUID_INFUSER, recipeManager.getAllRecipesFor(FluidInfuserRecipe.Companion.getTYPE()));
		registration.addRecipes(CONDENSER, recipeManager.getAllRecipesFor(CondenserRecipe.Companion.getTYPE()));
		registration.addRecipes(SMELTER, recipeManager.getAllRecipesFor(SmelterRecipe.Companion.getTYPE()));
		registration.addRecipes(SAWMILL, recipeManager.getAllRecipesFor(SawmillRecipe.Companion.getTYPE()));
		registration.addRecipes(MODULE, recipeManager.getAllRecipesFor(ModuleRecipe.Companion.getTYPE()));
		registration.addRecipes(LASER, recipeManager.getAllRecipesFor(LaserRecipe.Companion.getTYPE()));

		if(IRConfig.miningRigConfig != null) {
			registration.addRecipes(MINING_RIG, IRConfig.miningRigConfig.getAllowedTags().entrySet().stream().
					flatMap(entry -> Streams.stream(BuiltInRegistries.ITEM.getTagOrEmpty(TagKey.create(Registries.ITEM, new ResourceLocation(entry.getKey())))).
							filter(Holder::isBound).
							map(holder -> new MiningRigRecipe(holder.value(), entry.getValue()))).
					toList());
		}
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		MachineRegistry.Companion machines = MachineRegistry.Companion;
		for(Tier tier : machines.getPULVERIZER_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getPULVERIZER_REGISTRY().block(tier), PULVERIZER);
		}
		for(Tier tier : machines.getPULVERIZER_FACTORY_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getPULVERIZER_FACTORY_REGISTRY().block(tier), PULVERIZER);
		}
		for(Tier tier : machines.getSOLID_INFUSER_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getSOLID_INFUSER_REGISTRY().block(tier), INFUSER);
		}
		for(Tier tier : machines.getSOLID_INFUSER_FACTORY_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getSOLID_INFUSER_FACTORY_REGISTRY().block(tier), INFUSER);
		}
		for(Tier tier : machines.getCOMPRESSOR_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getCOMPRESSOR_REGISTRY().block(tier), COMPRESSOR);
		}
		for(Tier tier : machines.getCOMPRESSOR_FACTORY_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getCOMPRESSOR_FACTORY_REGISTRY().block(tier), COMPRESSOR);
		}
		for(Tier tier : machines.getRECYCLER_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getRECYCLER_REGISTRY().block(tier), RECYCLER);
		}
		for(Tier tier : machines.getFLUID_INFUSER_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getFLUID_INFUSER_REGISTRY().block(tier), FLUID_INFUSER);
		}
		for(Tier tier : machines.getCONDENSER_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getCONDENSER_REGISTRY().block(tier), CONDENSER);
		}
		for(Tier tier : machines.getSMELTER_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getSMELTER_REGISTRY().block(tier), SMELTER);
		}
		for(Tier tier : machines.getSAWMILL_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getSAWMILL_REGISTRY().block(tier), SAWMILL);
		}
		for(Tier tier : machines.getMODULAR_WORKBENCH_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getMODULAR_WORKBENCH_REGISTRY().block(tier), MODULE);
		}
		for(Tier tier : machines.getLASER_EMITTER_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getLASER_EMITTER_REGISTRY().block(tier), LASER);
		}
		registration.addRecipeCatalyst(IRBlockRegistry.INSTANCE.getCAPSULE_BLOCK(), LASER);

		for(Tier tier : machines.getMINING_RIG_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getMINING_RIG_REGISTRY().block(tier), MINING_RIG);
		}
		registration.addRecipeCatalyst(IRBlockRegistry.INSTANCE.getDRILL_BOTTOM(), MINING_RIG);

		for(Tier tier : machines.getELECTRIC_FURNACE_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getELECTRIC_FURNACE_REGISTRY().block(tier), RecipeTypes.SMELTING, RecipeTypes.BLASTING, RecipeTypes.SMOKING);
		}
		for(Tier tier : machines.getELECTRIC_FURNACE_FACTORY_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getELECTRIC_FURNACE_FACTORY_REGISTRY().block(tier), RecipeTypes.SMELTING, RecipeTypes.BLASTING, RecipeTypes.SMOKING);
		}
		registration.addRecipeCatalyst(IRItemRegistry.INSTANCE.getBLAST_FURNACE_UPGRADE(), RecipeTypes.BLASTING);
		registration.addRecipeCatalyst(IRItemRegistry.INSTANCE.getSMOKER_UPGRADE(), RecipeTypes.SMOKING);

		for(Tier tier : machines.getCOAL_GENERATOR_REGISTRY().getTiers()) {
			registration.addRecipeCatalyst(machines.getCOAL_GENERATOR_REGISTRY().block(tier), RecipeTypes.FUELING);
		}
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		if(checkDisabled()) {
			return;
		}

		registration.addGenericGuiContainerHandler(IRInventoryScreen.class, new IRInventoryRecipeClickAreaHandler());
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		IndRevJEI.jeiRuntime = jeiRuntime;
	}

	public static <R extends IRRecipe> RecipeType<R> createRecipeType(IRRecipeType<R> irRecipeType, Class<R> irRecipeClass) {
		return new RecipeType<>(irRecipeType.getId(), irRecipeClass);
	}

	public boolean checkDisabled() {
		if(FabricLoader.getInstance().isModLoaded("rei_plugin_compatibilities")) {
			LOGGER.warn("IndRevJEI is disabled with REIPC as Industrial Revolution has native REI support");
			return true;
		}
		if(FabricLoader.getInstance().isModLoaded("extra-mod-integrations")) {
			LOGGER.warn("IndRevJEI is disabled with ExMI");
			return true;
		}
		if(FabricLoader.getInstance().isModLoaded("indrev-emi-plugin")) {
			LOGGER.warn("IndRevJEI is disabled with IndRev EMI Plugin");
			return true;
		}
		return false;
	}
}
