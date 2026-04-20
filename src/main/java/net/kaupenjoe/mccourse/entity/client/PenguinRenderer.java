package net.kaupenjoe.mccourse.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.entity.custom.PenguinEntity;
import net.kaupenjoe.mccourse.entity.variant.PenguinVariant;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class PenguinRenderer extends MobRenderer<PenguinEntity, PenguinRenderState, PenguinModel> {
    private static final Map<PenguinVariant, Identifier> TEXTURE_BY_VARIANT =
            Util.make(Maps.newEnumMap(PenguinVariant.class), map -> {
                map.put(PenguinVariant.DEFAULT,
                        Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "textures/entity/penguin/penguin.png"));
                map.put(PenguinVariant.ALBINO,
                        Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "textures/entity/penguin/penguin_albino.png"));
                map.put(PenguinVariant.CLUB,
                        Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "textures/entity/penguin/penguin_club.png"));
            });

    public PenguinRenderer(EntityRendererProvider.Context context) {
        super(context, new PenguinModel(context.bakeLayer(ModModelLayerLocations.PENGUIN)), 0.85f);
    }

    @Override
    public Identifier getTextureLocation(PenguinRenderState state) {
        return TEXTURE_BY_VARIANT.get(state.variant);
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
        state.variant = entity.getVariant();
    }
}
