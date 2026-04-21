package net.kaupenjoe.mccourse.entity.client;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.phys.Vec2;

public class TomahawkRenderState extends EntityRenderState {
    public float yaw;
    public float pitch;
    public float spinRotation;
    public boolean isGrounded;
    public Vec2 groundedOffset;
}
