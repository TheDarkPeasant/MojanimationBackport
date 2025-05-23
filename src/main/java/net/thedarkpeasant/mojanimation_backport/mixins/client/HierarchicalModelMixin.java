package net.thedarkpeasant.mojanimation_backport.mixins.client;

import com.mojang.math.Vector3f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.thedarkpeasant.mojanimation_backport.accessor.HierarchicalModelAccessor;
import net.thedarkpeasant.mojanimation_backport.client.animation.AnimationDefinition;
import net.thedarkpeasant.mojanimation_backport.client.animation.KeyframeAnimations;
import net.thedarkpeasant.mojanimation_backport.entity.AnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Mixin(HierarchicalModel.class)
public abstract class HierarchicalModelMixin<E extends Entity> extends EntityModel<E> implements HierarchicalModelAccessor {
    @Unique
    private static final Vector3f MB$ANIMATION_VECTOR_CACHE = new Vector3f();

    @Shadow
    public abstract ModelPart root();

    @Unique
    public Optional<ModelPart> MB$getAnyDescendantWithName(String pName) {
        return pName.equals("root") ? Optional.of(this.root()) : this.root().getAllParts().filter((part) -> part.children.containsKey(pName)).findFirst().map((part) -> part.getChild(pName));
    }

    @Unique
    protected void MB$animate(AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks) {
        this.MB$animate(pAnimationState, pAnimationDefinition, pAgeInTicks, 1.0F);
    }

    @Unique
    protected void MB$animateWalk(AnimationDefinition pAnimationDefinition, float pLimbSwing, float pLimbSwingAmount, float pMaxAnimationSpeed, float pAnimationScaleFactor) {
        long i = (long) (pLimbSwing * 50.0F * pMaxAnimationSpeed);
        float f = Math.min(pLimbSwingAmount * pAnimationScaleFactor, 1.0F);
        KeyframeAnimations.animate(this, pAnimationDefinition, i, f, MB$ANIMATION_VECTOR_CACHE);
    }

    @Unique
    protected void MB$animate(AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks, float pSpeed) {
        pAnimationState.updateTime(pAgeInTicks, pSpeed);
        pAnimationState.ifStarted((state) -> KeyframeAnimations.animate(this, pAnimationDefinition, state.getAccumulatedTime(), 1.0F, MB$ANIMATION_VECTOR_CACHE));
    }

    @Unique
    protected void MB$applyStatic(AnimationDefinition pAnimationDefinition) {
        KeyframeAnimations.animate(this, pAnimationDefinition, 0L, 1.0F, MB$ANIMATION_VECTOR_CACHE);
    }

    @Override
    public Optional<ModelPart> getAnyDescendantWithName(String pName) {
        return this.MB$getAnyDescendantWithName(pName);
    }

    @Override
    public void MB$callAnimate(AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks) {
        this.MB$animate(pAnimationState, pAnimationDefinition, pAgeInTicks);
    }

    @Override
    public void MB$callAnimateWalk(AnimationDefinition pAnimationDefinition, float pLimbSwing, float pLimbSwingAmount, float pMaxAnimationSpeed, float pAnimationScaleFactor) {
        this.MB$animateWalk(pAnimationDefinition, pLimbSwing, pLimbSwingAmount, pMaxAnimationSpeed, pAnimationScaleFactor);
    }

    @Override
    public void MB$callAnimate(AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks, float pSpeed) {
        this.MB$animate(pAnimationState, pAnimationDefinition, pAgeInTicks, pSpeed);
    }

    @Override
    public void MB$callApplyStatic(AnimationDefinition pAnimationDefinition) {
        this.MB$applyStatic(pAnimationDefinition);
    }
}