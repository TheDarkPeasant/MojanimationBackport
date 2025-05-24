package net.thedarkpeasant.mojanimation_backport.mixins.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.vector.Vector3f;
import net.thedarkpeasant.mojanimation_backport.accessor.ModelRendererAccessor;
import net.thedarkpeasant.mojanimation_backport.util.MBInitialPose;
import net.thedarkpeasant.mojanimation_backport.util.MBUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelRenderer.class)
public abstract class ModelRendererMixin implements ModelRendererAccessor {
    @Unique
    private static final float MB$DEFAULT_SCALE = 1.0F;
    @Unique
    private float MB$xScale = MB$DEFAULT_SCALE;
    @Unique
    private float MB$yScale = MB$DEFAULT_SCALE;
    @Unique
    private float MB$zScale = MB$DEFAULT_SCALE;
    @Unique
    private MBInitialPose MB$initialPose = MBInitialPose.ZERO;

    @Shadow
    public float x;
    @Shadow
    public float y;
    @Shadow
    public float z;
    @Shadow
    public float xRot;
    @Shadow
    public float yRot;
    @Shadow
    public float zRot;

    @Inject(method = "copyFrom", at = @At("HEAD"))
    private void MB$injectCopyFrom(ModelRenderer pModelPart, CallbackInfo ci) {
        Vector3f scale = MBUtils.getScale(pModelPart);
        this.MB$xScale = scale.x();
        this.MB$yScale = scale.y();
        this.MB$zScale = scale.z();
    }

    @Inject(method = "translateAndRotate", at = @At("TAIL"))
    private void MB$injectTranslateAndRotate(MatrixStack pPoseStack, CallbackInfo ci) {
        if (this.MB$xScale != MB$DEFAULT_SCALE || this.MB$yScale != MB$DEFAULT_SCALE || this.MB$zScale != MB$DEFAULT_SCALE) {
            pPoseStack.scale(this.MB$xScale, this.MB$yScale, this.MB$zScale);
        }
    }

    @Override
    public MBInitialPose MB$getInitialPose() {
        return this.MB$initialPose;
    }

    @Override
    public void MB$setInitialPose(MBInitialPose pPose) {
        this.MB$initialPose = pPose;
    }

    @Override
    public Vector3f MB$getScale() {
        return new Vector3f(this.MB$xScale, this.MB$yScale, this.MB$zScale);
    }

    @Override
    public void MB$setScale(final Vector3f pScale) {
        this.MB$xScale = pScale.x();
        this.MB$yScale = pScale.y();
        this.MB$zScale = pScale.z();
    }

    @Override
    public void MB$offsetPos(Vector3f pOffset) {
        this.x += pOffset.x();
        this.y += pOffset.y();
        this.z += pOffset.z();
    }

    @Override
    public void MB$offsetRotation(Vector3f pOffset) {
        this.xRot += pOffset.x();
        this.yRot += pOffset.y();
        this.zRot += pOffset.z();
    }

    @Override
    public void MB$offsetScale(Vector3f pOffset) {
        this.MB$xScale += pOffset.x();
        this.MB$yScale += pOffset.y();
        this.MB$zScale += pOffset.z();
    }
}
