package thelm.indrevjei.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import me.steven.indrev.IndustrialRevolution;
import me.steven.indrev.registry.MaterialHelper;
import me.steven.indrev.utils.HiddenitemsKt;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

@Mixin(value = MaterialHelper.class, targets = {
		"me.steven.indrev.registry.MaterialHelper$withItems$1$1",
		"me.steven.indrev.registry.MaterialHelper$withOre$2",
		"me.steven.indrev.registry.MaterialHelper$withOre$3",
		"me.steven.indrev.registry.MaterialHelper$withOre$4",
		"me.steven.indrev.registry.MaterialHelper$withTools$1",
		"me.steven.indrev.registry.MaterialHelper$withTools$2",
		"me.steven.indrev.registry.MaterialHelper$withTools$3",
		"me.steven.indrev.registry.MaterialHelper$withTools$4",
		"me.steven.indrev.registry.MaterialHelper$withTools$5",
		"me.steven.indrev.registry.MaterialHelper$withArmor$1",
		"me.steven.indrev.registry.MaterialHelper$withArmor$2",
		"me.steven.indrev.registry.MaterialHelper$withArmor$3",
		"me.steven.indrev.registry.MaterialHelper$withArmor$4",
		"me.steven.indrev.registry.MaterialHelper$withBlock$1",
})
public class MaterialHelperMixin {

	@ModifyArg(method = "*", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;register(Lnet/minecraft/core/Registry;Lnet/minecraft/resources/ResourceLocation;Ljava/lang/Object;)Ljava/lang/Object;"))
	public Object beforeRegister(Registry<?> registry, ResourceLocation name, Object value) {
		if(value instanceof Item item && !HiddenitemsKt.hide(name)) {
			ItemGroupEvents.modifyEntriesEvent(IndustrialRevolution.INSTANCE.getMOD_GROUP_KEY()).register(entries -> entries.accept(item));
		}
		return value;
	}
}
