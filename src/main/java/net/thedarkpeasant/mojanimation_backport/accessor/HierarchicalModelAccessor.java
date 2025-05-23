package net.thedarkpeasant.mojanimation_backport.accessor;

import net.minecraft.client.model.geom.ModelPart;
import net.thedarkpeasant.mojanimation_backport.client.animation.AnimationDefinition;
import net.thedarkpeasant.mojanimation_backport.entity.AnimationState;

import java.util.Optional;

public interface HierarchicalModelAccessor {
    Optional<ModelPart> getAnyDescendantWithName(String pName);

    void MB$callAnimate(AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks);

    void MB$callAnimateWalk(AnimationDefinition pAnimationDefinition, float pLimbSwing, float pLimbSwingAmount, float pMaxAnimationSpeed, float pAnimationScaleFactor);

    void MB$callAnimate(AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks, float pSpeed);

    void MB$callApplyStatic(AnimationDefinition pAnimationDefinition);
}
