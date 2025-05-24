package net.thedarkpeasant.mojanimation_backport.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.thedarkpeasant.mojanimation_backport.client.animation.AnimationDefinition;
import net.thedarkpeasant.mojanimation_backport.client.animation.KeyframeAnimations;
import net.thedarkpeasant.mojanimation_backport.entity.AnimationState;

import java.lang.reflect.Field;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public abstract class HierarchicalModel<T extends Entity> extends EntityModel<T> {
    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    public abstract ModelRenderer root();

    public ObjectList<ModelRenderer> rootAndChildren() {
        ObjectList<ModelRenderer> list = new ObjectArrayList<>();
        list.add(this.root());
        list.addAll(this.root().children);
        return list;
    }

    @Override
    public void renderToBuffer(MatrixStack pMatrixStack, IVertexBuilder pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.root().render(pMatrixStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }

    public Optional<ModelRenderer> getAnyDescendantWithName(String pName) {
        if ("root".equals(pName)) {
            return Optional.of(this.root());
        }

        try {
            Field field = this.getClass().getDeclaredField(pName);
            field.setAccessible(true);
            Object value = field.get(this);
            if (value instanceof ModelRenderer) {
                return Optional.of((ModelRenderer) value);
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }

        return Optional.empty();
    }

    protected void animate(AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks) {
        this.animate(pAnimationState, pAnimationDefinition, pAgeInTicks, 1.0F);
    }

    protected void animateWalk(AnimationDefinition pAnimationDefinition, float pLimbSwing, float pLimbSwingAmount, float pMaxSpeed, float pScaleFactor) {
        long time = (long) (pLimbSwing * 50.0F * pMaxSpeed);
        float strength = Math.min(pLimbSwingAmount * pScaleFactor, 1.0F);
        KeyframeAnimations.animate(this, pAnimationDefinition, time, strength, ANIMATION_VECTOR_CACHE);
    }

    protected void animate(AnimationState pAnimationState, AnimationDefinition pAnimationDefinition, float pAgeInTicks, float pSpeed) {
        pAnimationState.updateTime(pAgeInTicks, pSpeed);
        pAnimationState.ifStarted(state -> KeyframeAnimations.animate(this, pAnimationDefinition, state.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE));
    }

    protected void applyStatic(AnimationDefinition definition) {
        KeyframeAnimations.animate(this, definition, 0L, 1.0F, ANIMATION_VECTOR_CACHE);
    }
}