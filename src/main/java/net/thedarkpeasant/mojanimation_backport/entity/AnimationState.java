package net.thedarkpeasant.mojanimation_backport.entity;

import net.minecraft.util.Mth;

import java.util.function.Consumer;

public class AnimationState {
    private static final long STOPPED = Long.MAX_VALUE;
    private long lastTime = STOPPED;
    private long accumulatedTime;

    public void start(int pTickCount) {
        this.lastTime = (long) pTickCount * 1000L / 20L;
        this.accumulatedTime = 0L;
    }

    public void startIfStopped(int pTickCount) {
        if (!this.isStarted()) {
            this.start(pTickCount);
        }
    }

    public void animateWhen(boolean pCondition, int pTickCount) {
        if (pCondition) {
            this.startIfStopped(pTickCount);
        } else {
            this.stop();
        }
    }

    public void stop() {
        this.lastTime = STOPPED;
    }

    public void ifStarted(Consumer<AnimationState> pAction) {
        if (this.isStarted()) {
            pAction.accept(this);
        }
    }

    public void updateTime(float pAgeInTicks, float pSpeed) {
        if (this.isStarted()) {
            long i = Mth.lfloor(pAgeInTicks * 1000.0F / 20.0F);
            this.accumulatedTime += (long) ((float) (i - this.lastTime) * pSpeed);
            this.lastTime = i;
        }
    }

    public long getAccumulatedTime() {
        return this.accumulatedTime;
    }

    public boolean isStarted() {
        return this.lastTime != STOPPED;
    }
}