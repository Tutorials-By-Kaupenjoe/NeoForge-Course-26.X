package net.kaupenjoe.mccourse.entity.custom;

import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class DodoEntity extends AbstractHorse {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public DodoEntity(EntityType<? extends AbstractHorse> type, Level level) {
        super(type, level);
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10d)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.TEMPT_RANGE, 16d)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModItems.GOJI_BERRIES);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.DODO.get().create(level, EntitySpawnReason.BREEDING);
    }

    /* RIDEABLE */
    @Override
    public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
        boolean bl = !this.isBaby() && this.isTamed() && player.isSecondaryUseActive();
        if (this.isVehicle() || bl) {
            return super.interact(player, hand, location);
        }
        ItemStack itemStack = player.getItemInHand(hand);
        if (!itemStack.isEmpty()) {
            if (this.isFood(itemStack)) {
                return this.fedFood(player, itemStack);
            }

            if (!this.isTamed()) {
                this.makeMad();
                return InteractionResult.SUCCESS;
            }
        }
        return super.interact(player, hand, location);
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scale) {
        return super.getPassengerAttachmentPoint(passenger, dimensions, scale).add(new Vec3(0.0, -0.65, 0.0).yRot(-this.getYRot() * ((float)Math.PI / 180)));
    }
}
