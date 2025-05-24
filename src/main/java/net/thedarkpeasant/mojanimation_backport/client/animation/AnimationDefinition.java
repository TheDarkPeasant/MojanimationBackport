package net.thedarkpeasant.mojanimation_backport.client.animation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class AnimationDefinition {
    private final float lengthInSeconds;
    private final boolean looping;
    private final Map<String, List<AnimationChannel>> boneAnimations;

    public AnimationDefinition(float lengthInSeconds, boolean looping, Map<String, List<AnimationChannel>> boneAnimations) {
        this.lengthInSeconds = lengthInSeconds;
        this.looping = looping;
        this.boneAnimations = boneAnimations;
    }

    public float lengthInSeconds() {
        return lengthInSeconds;
    }

    public boolean looping() {
        return looping;
    }

    public Map<String, List<AnimationChannel>> boneAnimations() {
        return boneAnimations;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Builder {
        private final float length;
        private final Map<String, List<AnimationChannel>> animationByBone = Maps.newHashMap();
        private boolean looping;

        public static Builder withLength(float pLengthInSeconds) {
            return new Builder(pLengthInSeconds);
        }

        private Builder(float pLengthInSeconds) {
            this.length = pLengthInSeconds;
        }

        public Builder looping() {
            this.looping = true;
            return this;
        }

        public Builder addAnimation(String pBone, AnimationChannel pAnimationChannel) {
            this.animationByBone.computeIfAbsent(pBone, (s) -> Lists.newArrayList()).add(pAnimationChannel);
            return this;
        }

        public AnimationDefinition build() {
            return new AnimationDefinition(this.length, this.looping, this.animationByBone);
        }
    }
}