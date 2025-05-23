package net.thedarkpeasant.mojanimation_backport.client.animation;

import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record Keyframe(float timestamp, Vector3f target, AnimationChannel.Interpolation interpolation) {
}