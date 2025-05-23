package net.thedarkpeasant.mojanimation_backport.mixins.client;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.thedarkpeasant.mojanimation_backport.util.MBUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PartDefinition.class)
public class PartDefinitionMixin {
    @Final
    @Shadow
    private PartPose partPose;

    @Inject(method = "bake", at = @At("RETURN"))
    private void MB$injectBake(int pTexWidth, int pTexHeight, CallbackInfoReturnable<ModelPart> cir) {
        MBUtils.setInitialPose(cir.getReturnValue(), this.partPose);
    }
}
