package net.thedarkpeasant.mojanimation_backport.util;

import com.mojang.math.Vector3f;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.thedarkpeasant.mojanimation_backport.accessor.HierarchicalModelAccessor;
import net.thedarkpeasant.mojanimation_backport.accessor.ModelPartAccessor;

import java.util.Optional;

public class MBUtils {
    public static Optional<ModelPart> getAnyDescendantWithName(HierarchicalModel<?> pModel, String pName) {
        if (!(pModel instanceof HierarchicalModelAccessor accessor)) return Optional.empty();

        return accessor.getAnyDescendantWithName(pName);
    }

    public static PartPose getInitialPose(ModelPart pPart) {
        if (!((Object) pPart instanceof ModelPartAccessor)) return PartPose.ZERO;

        return ((ModelPartAccessor) (Object) pPart).MB$getInitialPose();
    }

    public static void setInitialPose(ModelPart pPart, PartPose pPose) {
        if (!((Object) pPart instanceof ModelPartAccessor)) return;

        ((ModelPartAccessor) (Object) pPart).MB$setInitialPose(pPose);
    }

    public static Vector3f getScale(ModelPart pPart) {
        if (!((Object) pPart instanceof ModelPartAccessor)) return new Vector3f();

        return ((ModelPartAccessor) (Object) pPart).MB$getScale();
    }

    public static void setScale(ModelPart pPart, Vector3f pScale) {
        if (!((Object) pPart instanceof ModelPartAccessor)) return;

        ((ModelPartAccessor) (Object) pPart).MB$setScale(pScale);
    }

    public static void offsetPos(ModelPart pPart, Vector3f pOffset) {
        if (!((Object) pPart instanceof ModelPartAccessor)) return;

        ((ModelPartAccessor) (Object) pPart).MB$offsetPos(pOffset);
    }

    public static void offsetRotation(ModelPart pPart, Vector3f pOffset) {
        if (!((Object) pPart instanceof ModelPartAccessor)) return;

        ((ModelPartAccessor) (Object) pPart).MB$offsetRotation(pOffset);
    }

    public static void offsetScale(ModelPart pPart, Vector3f pOffset) {
        if (!((Object) pPart instanceof ModelPartAccessor)) return;

        ((ModelPartAccessor) (Object) pPart).MB$offsetScale(pOffset);
    }

    public static float catmullrom(float pProgress, float pPrev, float pStart, float pEnd, float pNext) {
        return 0.5F * (2.0F * pStart
                + (pEnd - pPrev) * pProgress
                + (2.0F * pPrev - 5.0F * pStart + 4.0F * pEnd - pNext) * pProgress * pProgress
                + (3.0F * pStart - pPrev - 3.0F * pEnd + pNext) * pProgress * pProgress * pProgress);
    }
}
