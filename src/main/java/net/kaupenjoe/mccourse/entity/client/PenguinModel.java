package net.kaupenjoe.mccourse.entity.client;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class PenguinModel extends EntityModel<PenguinRenderState> {
    private final ModelPart penguin;
    private final ModelPart upper_body_group;
    private final ModelPart body;
    private final ModelPart head_group;

    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation idlingAnimation;

    public PenguinModel(ModelPart root) {
        super(root);
        this.penguin = root.getChild("penguin");
        this.upper_body_group = this.penguin.getChild("upper_body_group");
        this.body = this.upper_body_group.getChild("body");
        this.head_group = this.body.getChild("head_group");

        walkingAnimation = PenguinAnimations.WALK.bake(root);
        idlingAnimation = PenguinAnimations.IDLE.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition penguin = partdefinition.addOrReplaceChild("penguin", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition upper_body_group = penguin.addOrReplaceChild("upper_body_group", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = upper_body_group.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 25).addBox(-6.0F, -6.0F, -5.0F, 12.0F, 15.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition leftwing = body.addOrReplaceChild("leftwing", CubeListBuilder.create(), PartPose.offset(-6.0F, -4.0F, 0.0F));

        PartDefinition wing_r1 = leftwing.addOrReplaceChild("wing_r1", CubeListBuilder.create().texOffs(48, 23).addBox(-12.0F, -14.0F, 1.0F, 6.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 13.0F, 9.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition rightwing = body.addOrReplaceChild("rightwing", CubeListBuilder.create(), PartPose.offset(6.0F, -4.0F, 0.0F));

        PartDefinition wing_r2 = rightwing.addOrReplaceChild("wing_r2", CubeListBuilder.create().texOffs(48, 23).addBox(-3.0F, -6.5F, -1.0F, 6.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 5.5F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition head_group = body.addOrReplaceChild("head_group", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition headmain = head_group.addOrReplaceChild("headmain", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -7.0F, -4.0F, 10.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(28, 7).addBox(-5.0F, -7.0F, -4.0F, 10.0F, 7.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = headmain.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 1).addBox(1.0F, -4.0F, -1.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -7.0F, 0.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition beak = head_group.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(0, 15).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(8, 15).addBox(-1.0F, 1.0F, -8.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition feet = penguin.addOrReplaceChild("feet", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition righty = feet.addOrReplaceChild("righty", CubeListBuilder.create().texOffs(45, 39).mirror().addBox(-2.0F, 0.0F, -4.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition lefty = feet.addOrReplaceChild("lefty", CubeListBuilder.create().texOffs(45, 39).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(PenguinRenderState state) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(state.yRot, state.xRot);

        this.walkingAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 2f, 2.5f);
        this.idlingAnimation.apply(state.idleAnimationState, state.ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head_group.yRot = headYaw * ((float)Math.PI / 180f);
        this.head_group.xRot = headPitch *  ((float)Math.PI / 180f);
    }
}
