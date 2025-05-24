package net.thedarkpeasant.mojanimation_backport.util;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3f;
import net.thedarkpeasant.mojanimation_backport.accessor.ModelRendererAccessor;
import net.thedarkpeasant.mojanimation_backport.client.model.HierarchicalModel;

public class MBUtils {
    public static MBInitialPose getInitialPose(ModelRenderer pPart) {
        if (!(pPart instanceof ModelRendererAccessor)) return MBInitialPose.ZERO;

        return ((ModelRendererAccessor) pPart).MB$getInitialPose();
    }

    public static void setInitialPose(ModelRenderer pPart, MBInitialPose pPose) {
        if (!(pPart instanceof ModelRendererAccessor)) return;

        ((ModelRendererAccessor) pPart).MB$setInitialPose(pPose);
    }

    public static void resetPose(ModelRenderer pPart) {
        MBInitialPose pose = getInitialPose(pPart);
        pPart.x = pose.x;
        pPart.y = pose.y;
        pPart.z = pose.z;
        pPart.xRot = pose.xRot;
        pPart.yRot = pose.yRot;
        pPart.zRot = pose.zRot;
        setScale(pPart, new Vector3f(1.0F, 1.0F, 1.0F));
    }

    public static void freezePose(ModelRenderer part) {
        setInitialPose(part, new MBInitialPose(part.x, part.y, part.z, part.xRot, part.yRot, part.zRot));

        for (ModelRenderer child : part.children) {
            freezePose(child);
        }
    }

    public static <T extends Entity> void freezePose(HierarchicalModel<T> model) {
        freezePose(model.root());
    }

    public static Vector3f getScale(ModelRenderer pPart) {
        if (!(pPart instanceof ModelRendererAccessor)) return new Vector3f(0.0F, 0.0F, 0.0F);

        return ((ModelRendererAccessor) pPart).MB$getScale();
    }

    public static void setScale(ModelRenderer pPart, Vector3f pScale) {
        if (!(pPart instanceof ModelRendererAccessor)) return;

        ((ModelRendererAccessor) pPart).MB$setScale(pScale);
    }

    public static void offsetPos(ModelRenderer pPart, Vector3f pOffset) {
        if (!(pPart instanceof ModelRendererAccessor)) return;

        ((ModelRendererAccessor) pPart).MB$offsetPos(pOffset);
    }

    public static void offsetRotation(ModelRenderer pPart, Vector3f pOffset) {
        if (!(pPart instanceof ModelRendererAccessor)) return;

        ((ModelRendererAccessor) pPart).MB$offsetRotation(pOffset);
    }

    public static void offsetScale(ModelRenderer pPart, Vector3f pOffset) {
        if (!(pPart instanceof ModelRendererAccessor)) return;

        ((ModelRendererAccessor) pPart).MB$offsetScale(pOffset);
    }

    public static float catmullrom(float pProgress, float pPrev, float pStart, float pEnd, float pNext) {
        return 0.5F * (2.0F * pStart
                + (pEnd - pPrev) * pProgress
                + (2.0F * pPrev - 5.0F * pStart + 4.0F * pEnd - pNext) * pProgress * pProgress
                + (3.0F * pStart - pPrev - 3.0F * pEnd + pNext) * pProgress * pProgress * pProgress);
    }
}