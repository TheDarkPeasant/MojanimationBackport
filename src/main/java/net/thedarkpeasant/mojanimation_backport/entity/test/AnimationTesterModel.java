package net.thedarkpeasant.mojanimation_backport.entity.test;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.thedarkpeasant.mojanimation_backport.client.model.HierarchicalModel;
import net.thedarkpeasant.mojanimation_backport.util.MBUtils;

public class AnimationTesterModel<T extends Entity> extends HierarchicalModel<T> {
    private final ModelRenderer root;
    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer rightArm;
    private final ModelRenderer leftArm;
    private final ModelRenderer rightLeg;
    private final ModelRenderer leftLeg;

    public AnimationTesterModel() {
        texWidth = 64;
        texHeight = 64;

        root = new ModelRenderer(this);
        root.setPos(0.0F, 24.0F, 0.0F);

        head = new ModelRenderer(this);
        head.setPos(0.0F, -24.0F, 0.0F);
        root.addChild(head);
        head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        head.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);

        body = new ModelRenderer(this);
        body.setPos(0.0F, -24.0F, 0.0F);
        root.addChild(body);
        body.texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
        body.texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);

        rightArm = new ModelRenderer(this);
        rightArm.setPos(-5.0F, -22.0F, 0.0F);
        root.addChild(rightArm);
        rightArm.texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        rightArm.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

        leftArm = new ModelRenderer(this);
        leftArm.setPos(5.0F, -22.0F, 0.0F);
        root.addChild(leftArm);
        leftArm.texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        leftArm.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

        rightLeg = new ModelRenderer(this);
        rightLeg.setPos(-1.9F, -12.0F, 0.0F);
        root.addChild(rightLeg);
        rightLeg.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        rightLeg.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

        leftLeg = new ModelRenderer(this);
        leftLeg.setPos(1.9F, -12.0F, 0.0F);
        root.addChild(leftLeg);
        leftLeg.texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
        leftLeg.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

        MBUtils.freezePose(this);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.rootAndChildren().forEach((MBUtils::resetPose));

        if (entity instanceof TestEntity) {
            TestEntity test = (TestEntity) entity;
            this.animate(test.positionState, TestAnimations.POSITION, ageInTicks);
            this.animate(test.rotationState, TestAnimations.ROTATION, ageInTicks);
            this.animate(test.scaleState, TestAnimations.SCALE, ageInTicks);
            this.animate(test.noLoopState, TestAnimations.NO_LOOP, ageInTicks);
        }
    }

    @Override
    public ModelRenderer root() {
        return this.root;
    }
}