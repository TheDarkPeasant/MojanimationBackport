package net.thedarkpeasant.mojanimation_backport.accessor;

import net.minecraft.util.math.vector.Vector3f;
import net.thedarkpeasant.mojanimation_backport.util.MBInitialPose;

public interface ModelRendererAccessor {
    MBInitialPose MB$getInitialPose();

    void MB$setInitialPose(MBInitialPose pPose);

    Vector3f MB$getScale();

    void MB$setScale(Vector3f pScale);

    void MB$offsetPos(Vector3f pOffset);

    void MB$offsetRotation(Vector3f pOffset);

    void MB$offsetScale(Vector3f pOffset);
}