package net.thedarkpeasant.mojanimation_backport.client.animation;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.thedarkpeasant.mojanimation_backport.util.MBUtils;

@OnlyIn(Dist.CLIENT)
public class AnimationChannel {
    private final Target target;
    private final Keyframe[] keyframes;

    public AnimationChannel(Target target, Keyframe... keyframes) {
        this.target = target;
        this.keyframes = keyframes;
    }

    public Target target() {
        return target;
    }

    public Keyframe[] keyframes() {
        return keyframes;
    }

    @OnlyIn(Dist.CLIENT)
    public interface Interpolation {
        Vector3f apply(Vector3f pAnimationVector, float pKeyframeDelta, Keyframe[] pKeyframes, int pCurrentKeyframeIdx, int pNextKeyframeIdx, float pScale);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Interpolations {
        public static final Interpolation LINEAR = (pAnimationVector, pKeyframeDelta, pKeyframes, pCurrentKeyframeIdx, pNextKeyframeIdx, pScale) -> {
            Vector3f start = pKeyframes[pCurrentKeyframeIdx].target();
            Vector3f end = pKeyframes[pNextKeyframeIdx].target();
            pAnimationVector.set(
                    MathHelper.lerp(pKeyframeDelta, start.x(), end.x()) * pScale,
                    MathHelper.lerp(pKeyframeDelta, start.y(), end.y()) * pScale,
                    MathHelper.lerp(pKeyframeDelta, start.z(), end.z()) * pScale
            );
            return pAnimationVector;
        };

        public static final Interpolation CATMULLROM = (pAnimationVector, pKeyframeDelta, pKeyframes, pCurrentKeyframeIdx, pNextKeyframeIdx, pScale) -> {
            Vector3f prev = pKeyframes[Math.max(0, pCurrentKeyframeIdx - 1)].target();
            Vector3f start = pKeyframes[pCurrentKeyframeIdx].target();
            Vector3f end = pKeyframes[pNextKeyframeIdx].target();
            Vector3f next = pKeyframes[Math.min(pKeyframes.length - 1, pNextKeyframeIdx + 1)].target();
            pAnimationVector.set(
                    MBUtils.catmullrom(pKeyframeDelta, prev.x(), start.x(), end.x(), next.x()) * pScale,
                    MBUtils.catmullrom(pKeyframeDelta, prev.y(), start.y(), end.y(), next.y()) * pScale,
                    MBUtils.catmullrom(pKeyframeDelta, prev.z(), start.z(), end.z(), next.z()) * pScale
            );
            return pAnimationVector;
        };
    }

    @OnlyIn(Dist.CLIENT)
    public interface Target {
        void apply(ModelRenderer pModelPart, Vector3f pAnimationVector);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Targets {
        public static final Target POSITION = MBUtils::offsetPos;
        public static final Target ROTATION = MBUtils::offsetRotation;
        public static final Target SCALE = MBUtils::offsetScale;
    }
}
