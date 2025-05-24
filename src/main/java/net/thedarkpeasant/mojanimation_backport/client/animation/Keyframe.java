package net.thedarkpeasant.mojanimation_backport.client.animation;

import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Keyframe {
    private final float timestamp;
    private final Vector3f target;
    private final AnimationChannel.Interpolation interpolation;

    public Keyframe(float timestamp, Vector3f target, AnimationChannel.Interpolation interpolation) {
        this.timestamp = timestamp;
        this.target = target;
        this.interpolation = interpolation;
    }

    public float timestamp() {
        return timestamp;
    }

    public Vector3f target() {
        return target;
    }

    public AnimationChannel.Interpolation interpolation() {
        return interpolation;
    }
}