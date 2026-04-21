package net.kaupenjoe.mccourse.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.entity.custom.DodoEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;

public class DodoRenderer extends MobRenderer<DodoEntity, DodoRenderState, DodoModel> {
    public DodoRenderer(EntityRendererProvider.Context context) {
        super(context, new DodoModel(context.bakeLayer(ModModelLayerLocations.DODO)), 0.5f);
    }

    @Override
    public Identifier getTextureLocation(DodoRenderState entity) {
        if(entity.isSaddled) {
            return Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "textures/entity/dodo/dodo_saddled.png");
        } else {
            return Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "textures/entity/dodo/dodo.png");
        }
    }

    @Override
    public void submit(DodoRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if(state.isBaby) {
            poseStack.scale(0.45f, 0.45f, 0.45f);
        } else {
            poseStack.scale(1f, 1f, 1f);
        }

        super.submit(state, poseStack, nodeCollector, cameraRenderState);
    }

    @Override
    public DodoRenderState createRenderState() {
        return new DodoRenderState();
    }

    @Override
    public void extractRenderState(DodoEntity entity, DodoRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);

        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.isSaddled = entity.isSaddled();
    }
}
