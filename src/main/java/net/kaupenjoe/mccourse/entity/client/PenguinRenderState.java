package net.kaupenjoe.mccourse.entity.client;

import net.kaupenjoe.mccourse.entity.variant.PenguinVariant;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class PenguinRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public PenguinVariant variant;
}
