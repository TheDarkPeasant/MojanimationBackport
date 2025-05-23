package net.thedarkpeasant.mojanimation_backport.entity.test;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.thedarkpeasant.mojanimation_backport.entity.AnimationState;

public class TestEntity extends PathfinderMob {
    private static final EntityDataAccessor<Integer> ANIMATION = SynchedEntityData.defineId(TestEntity.class, EntityDataSerializers.INT);
    public final AnimationState positionState = new AnimationState();
    public final AnimationState rotationState = new AnimationState();
    public final AnimationState scaleState = new AnimationState();
    public final AnimationState noLoopState = new AnimationState();
    public final AnimationState staticState = new AnimationState();

    protected TestEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION, 0);
    }

    public void setAnimation(int animation) {
        this.entityData.set(ANIMATION, animation);
    }

    public int getAnimation() {
        return this.entityData.get(ANIMATION);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level.isClientSide) {
            this.positionState.animateWhen(this.getAnimation() == 1, this.tickCount);
            this.rotationState.animateWhen(this.getAnimation() == 2, this.tickCount);
            this.scaleState.animateWhen(this.getAnimation() == 3, this.tickCount);
            this.noLoopState.animateWhen(this.getAnimation() == 4, this.tickCount);
            this.staticState.animateWhen(this.getAnimation() == 5, this.tickCount);
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        boolean result = super.hurt(pSource, pAmount);

        if (!this.level.isClientSide && result) {
            this.setAnimation(this.getAnimation() + 1);
        }

        return result;
    }
}