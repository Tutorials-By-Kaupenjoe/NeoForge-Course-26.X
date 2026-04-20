package net.kaupenjoe.mccourse.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.entity.custom.PenguinEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

public class PenguinRenderer extends MobRenderer<PenguinEntity, PenguinRenderState, PenguinModel> {
    public PenguinRenderer(EntityRendererProvider.Context context) {
        super(context, new PenguinModel(context.bakeLayer(ModModelLayerLocations.PENGUIN)), 0.85f);
    }

    @Override
    public Identifier getTextureLocation(PenguinRenderState state) {
        return Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "textures/entity/penguin/penguin.png");
    }

    @Override
    public PenguinRenderState createRenderState() {
        return new PenguinRenderState();
    }

    @Override
    public void submit(PenguinRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if(state.isBaby) {
            poseStack.scale(0.35f, 0.35f, 0.35f);
        } else {
            poseStack.scale(1f, 1f, 1f);
        }

        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public void extractRenderState(PenguinEntity entity, PenguinRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
    }
}
