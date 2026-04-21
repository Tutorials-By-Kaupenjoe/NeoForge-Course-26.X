package net.kaupenjoe.mccourse.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.entity.custom.TomahawkProjectileEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class TomahawkRenderer extends EntityRenderer<TomahawkProjectileEntity, TomahawkRenderState> {
    private TomahawkModel model;

    public TomahawkRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new TomahawkModel(context.bakeLayer(ModModelLayerLocations.TOMAHAWK));
    }

    @Override
    public TomahawkRenderState createRenderState() {
        return new TomahawkRenderState();
    }

    @Override
    public void extractRenderState(TomahawkProjectileEntity entity, TomahawkRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.yaw = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
        state.pitch = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());

        state.spinRotation = (entity.tickCount + partialTicks) * 30.0F;

        state.groundedOffset = entity.getGroundedOffset();
        state.isGrounded = entity.isInGround();
    }

    @Override
    public void submit(TomahawkRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                       CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.pushPose();

        if (!state.isGrounded) {
            poseStack.mulPose(Axis.YP.rotationDegrees(state.yaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(state.spinRotation));
            poseStack.translate(0, -1.0f, 0);
        } else {
            poseStack.mulPose(Axis.YP.rotationDegrees(state.groundedOffset.y));
            poseStack.mulPose(Axis.XP.rotationDegrees(state.groundedOffset.x));
            poseStack.translate(0, -1.0f, 0);
        }

        submitNodeCollector.order(1).submitModel(this.model, state, poseStack, RenderTypes.entitySolid(getTextureLocation()),
                state.lightCoords, OverlayTexture.NO_OVERLAY, -1,null, state.outlineColor, null);
        poseStack.popPose();
    }

    public Identifier getTextureLocation() {
        return Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "textures/entity/tomahawk/tomahawk.png");
    }
}
