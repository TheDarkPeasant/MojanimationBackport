package net.thedarkpeasant.mojanimation_backport.client.animation;

import com.mojang.math.Vector3f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.thedarkpeasant.mojanimation_backport.accessor.HierarchicalModelAccessor;
import net.thedarkpeasant.mojanimation_backport.util.MBUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class KeyframeAnimations {
    public static void animate(EntityModel<?> pModel, AnimationDefinition pAnimationDefinition, long pAccumulatedTime, float pScale, Vector3f pAnimationVecCache) {
        if (!(pModel instanceof HierarchicalModelAccessor) || !(pModel instanceof HierarchicalModel<?>)) return;

        float f = getElapsedSeconds(pAnimationDefinition, pAccumulatedTime);

        for (Map.Entry<String, List<AnimationChannel>> entry : pAnimationDefinition.boneAnimations().entrySet()) {
            Optional<ModelPart> optional = MBUtils.getAnyDescendantWithName((HierarchicalModel<?>) pModel, entry.getKey());
            List<AnimationChannel> list = entry.getValue();
            optional.ifPresent((part) -> list.forEach((channel) -> {
                Keyframe[] akeyframe = channel.keyframes();
                int i = Math.max(0, Mth.binarySearch(0, akeyframe.length, (index) -> f <= akeyframe[index].timestamp()) - 1);
                int j = Math.min(akeyframe.length - 1, i + 1);
                Keyframe keyframe = akeyframe[i];
                Keyframe keyframe1 = akeyframe[j];
                float f1 = f - keyframe.timestamp();
                float f2;
                if (j != i) {
                    f2 = Mth.clamp(f1 / (keyframe1.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
                } else {
                    f2 = 0.0F;
                }

                keyframe1.interpolation().apply(pAnimationVecCache, f2, akeyframe, i, j, pScale);
                channel.target().apply(part, pAnimationVecCache);
            }));
        }

    }

    private static float getElapsedSeconds(AnimationDefinition pAnimationDefinition, long pAccumulatedTime) {
        float f = (float) pAccumulatedTime / 1000.0F;
        return pAnimationDefinition.looping() ? f % pAnimationDefinition.lengthInSeconds() : f;
    }

    public static Vector3f posVec(float pX, float pY, float pZ) {
        return new Vector3f(pX, -pY, pZ);
    }

    public static Vector3f degreeVec(float pXDegrees, float pYDegrees, float pZDegrees) {
        return new Vector3f(pXDegrees * ((float) Math.PI / 180F), pYDegrees * ((float) Math.PI / 180F), pZDegrees * ((float) Math.PI / 180F));
    }

    public static Vector3f scaleVec(double pXScale, double pYScale, double pZScale) {
        return new Vector3f((float) (pXScale - 1.0D), (float) (pYScale - 1.0D), (float) (pZScale - 1.0D));
    }
}
