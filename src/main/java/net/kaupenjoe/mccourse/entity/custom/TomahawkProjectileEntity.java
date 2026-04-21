package net.kaupenjoe.mccourse.entity.custom;

import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec2;

public class TomahawkProjectileEntity extends AbstractArrow {
    private static final EntityDataAccessor<Float> OFFSET_X =
            SynchedEntityData.defineId(TomahawkProjectileEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> OFFSET_Y =
            SynchedEntityData.defineId(TomahawkProjectileEntity.class, EntityDataSerializers.FLOAT);

    public TomahawkProjectileEntity(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
    }

    public TomahawkProjectileEntity(ServerLevel level, LivingEntity shooter, ItemStack stack) {
        super(ModEntities.TOMAHAWK.get(), shooter, level, new ItemStack(ModItems.TOMAHAWK.get(), 1), null);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.TOMAHAWK.get(), 1);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), 4);

        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    public boolean isInGround() {
        return super.isInGround();
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putFloat("GroundedOffsetX", this.entityData.get(OFFSET_X));
        output.putFloat("GroundedOffsetY", this.entityData.get(OFFSET_Y));
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.entityData.set(OFFSET_X, input.getFloatOr("GroundedOffsetX", 0f));
        this.entityData.set(OFFSET_Y, input.getFloatOr("GroundedOffsetY", 0f));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(OFFSET_X, 0.0f);
        entityData.define(OFFSET_Y, 0.0f);
    }

    public Vec2 getGroundedOffset() {
        return new Vec2(this.entityData.get(OFFSET_X), this.entityData.get(OFFSET_Y));
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        float impactYaw = this.getYRot();

        if(result.getDirection() == Direction.DOWN) {
            this.setOffset(115f, impactYaw);
        } else if(result.getDirection() == Direction.UP) {
            this.setOffset(285f, impactYaw);
        } else {
            this.setOffset(215f, impactYaw);
        }
    }

    // Helper method to keep things clean
    private void setOffset(float x, float y) {
        this.entityData.set(OFFSET_X, x);
        this.entityData.set(OFFSET_Y, y);
    }
}
