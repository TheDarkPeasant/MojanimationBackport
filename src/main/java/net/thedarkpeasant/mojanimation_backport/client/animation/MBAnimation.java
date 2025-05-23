package net.thedarkpeasant.mojanimation_backport.client.animation;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.thedarkpeasant.mojanimation_backport.accessor.HierarchicalModelAccessor;
import net.thedarkpeasant.mojanimation_backport.entity.AnimationState;
import net.thedarkpeasant.mojanimation_backport.util.MBUtils;

@OnlyIn(Dist.CLIENT)
public class MBAnimation {
    public static void resetPose(ModelPart pPart) {
        pPart.loadPose(MBUtils.getInitialPose(pPart));
    }

    public static void animate(HierarchicalModel<?> pModel, AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks) {
        if (!(pModel instanceof HierarchicalModelAccessor accessor)) return;

        accessor.MB$callAnimate(pAnimationState, pAnimationDefinition, pAgeInTicks);
    }

    public static void animateWalk(HierarchicalModel<?> pModel, AnimationDefinition pAnimationDefinition, float pLimbSwing, float pLimbSwingAmount, float pMaxAnimationSpeed, float pAnimationScaleFactor) {
        if (!(pModel instanceof HierarchicalModelAccessor accessor)) return;

        accessor.MB$callAnimateWalk(pAnimationDefinition, pLimbSwing, pLimbSwingAmount, pMaxAnimationSpeed, pAnimationScaleFactor);
    }

    public static void animate(HierarchicalModel<?> pModel, AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks, float pSpeed) {
        if (!(pModel instanceof HierarchicalModelAccessor accessor)) return;

        accessor.MB$callAnimate(pAnimationState, pAnimationDefinition, pAgeInTicks, pSpeed);
    }

    public static void applyStatic(HierarchicalModel<?> pModel, AnimationDefinition pAnimationDefinition) {
        if (!(pModel instanceof HierarchicalModelAccessor accessor)) return;

        accessor.MB$callApplyStatic(pAnimationDefinition);
    }
}
