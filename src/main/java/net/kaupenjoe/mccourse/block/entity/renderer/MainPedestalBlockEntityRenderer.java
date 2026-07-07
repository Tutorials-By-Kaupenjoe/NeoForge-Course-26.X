package net.kaupenjoe.mccourse.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.kaupenjoe.mccourse.block.entity.custom.PedestalBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector2i;

import java.util.List;

public class MainPedestalBlockEntityRenderer extends PedestalBlockEntityRenderer {
    List<Vector2i> offsets = List.of(
            new Vector2i(3, 0),
            new Vector2i(2, 2),
            new Vector2i(0, 3),
            new Vector2i(2, -2),

            new Vector2i(-2, 2),
            new Vector2i(-2, -2),
            new Vector2i(0, -3),
            new Vector2i(-3, 0));

    private final BlockModelResolver blockResolver;
    private final BlockModelRenderState blockRenderState = new BlockModelRenderState();

    public MainPedestalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        blockResolver = context.blockModelResolver();
    }

    @Override
    public void extractRenderState(PedestalBlockEntity blockEntity, PedestalBlockEntityRenderState state, float partialTicks,
                                   Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);

        blockResolver.update(blockRenderState, blockEntity.getBlockState(), BlockDisplayContext.create());
    }

    @Override
    public void submit(PedestalBlockEntityRenderState state, PoseStack poseStack,
                       SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
        offsets.forEach(offset -> {
            if(state.level.getBlockState(state.blockPos.offset(offset.x, 0, offset.y)).isAir()) {
                renderSidePedestal(poseStack, submitNodeCollector, offset.x, offset.y);
            }
        });
    }

    private void renderSidePedestal(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, float xOffset, float zOffset) {
        poseStack.pushPose();
        poseStack.translate(xOffset, 0f, zOffset);
        // blockRenderState.submitMultiLayer(poseStack, submitNodeCollector, 400, OverlayTexture.WHITE_OVERLAY_V, 0);
        blockRenderState.submit(poseStack, submitNodeCollector, 400, 2, 0);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

    @Override
    public AABB getRenderBoundingBox(PedestalBlockEntity blockEntity) {
        return AABB.unitCubeFromLowerCorner(Vec3.atCenterOf(blockEntity.getBlockPos())).inflate(32f);
    }
}
