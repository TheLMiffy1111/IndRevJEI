package thelm.indrevjei.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.steven.indrev.utils.HiddenitemsKt;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

@Mixin(Item.class)
public class ItemMixin {

	@Inject(method = "allowedIn", at = @At("HEAD"), cancellable = true)
	public void modifyAllowedIn(CallbackInfoReturnable<Boolean> info) {
		if(HiddenitemsKt.hide(Registry.ITEM.getKey(Item.class.cast(this)))) {
			info.setReturnValue(false);
		}
	}
}
