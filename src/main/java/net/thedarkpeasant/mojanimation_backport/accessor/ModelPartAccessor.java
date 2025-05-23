package net.thedarkpeasant.mojanimation_backport.accessor;

import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.PartPose;

public interface ModelPartAccessor {
    PartPose MB$getInitialPose();

    void MB$setInitialPose(PartPose pPose);

    Vector3f MB$getScale();

    void MB$setScale(Vector3f pScale);

    void MB$offsetPos(Vector3f pOffset);

    void MB$offsetRotation(Vector3f pOffset);

    void MB$offsetScale(Vector3f pOffset);
}
