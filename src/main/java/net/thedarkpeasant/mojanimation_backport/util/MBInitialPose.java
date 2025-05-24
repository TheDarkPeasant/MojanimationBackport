package net.thedarkpeasant.mojanimation_backport.util;

public class MBInitialPose {
    public static final MBInitialPose ZERO = new MBInitialPose(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    public final float x;
    public final float y;
    public final float z;
    public final float xRot;
    public final float yRot;
    public final float zRot;

    public MBInitialPose(float x, float y, float z, float xRot, float yRot, float zRot) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xRot = xRot;
        this.yRot = yRot;
        this.zRot = zRot;
    }
}
