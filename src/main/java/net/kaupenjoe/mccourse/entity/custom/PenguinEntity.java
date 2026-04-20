package net.kaupenjoe.mccourse.entity.custom;

import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.entity.variant.PenguinVariant;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Util;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.Tags;
import org.jspecify.annotations.Nullable;

public class PenguinEntity extends Animal {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.INT);

    private final ServerBossEvent bossEvent =
            new ServerBossEvent(this.uuid, Component.literal("The Powerful Penguin"), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.NOTCHED_10);

    // Idle Animations
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public PenguinEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        // This determines the AI of the mob
        goalSelector.addGoal(0, new FloatGoal(this));

        goalSelector.addGoal(1, new PanicGoal(this, 2d));
        goalSelector.addGoal(2, new BreedGoal(this, 1.25d));
        goalSelector.addGoal(3, new TemptGoal(this, 1.25d, stack -> stack.is(Tags.Items.FOODS_RAW_FISH), false));

        goalSelector.addGoal(4, new FollowParentGoal(this, 1.25f));

        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1f));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6f));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createPenguinAttributes() {
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10d)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.TEMPT_RANGE, 16d)
                .add(Attributes.FOLLOW_RANGE, 16d);
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 20;
            this.idleAnimationState.start(this.tickCount); // * TickCount?
        } else {
            this.idleAnimationTimeout--;
        }
    }

    /* VARIANTS */

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(VARIANT, 0);
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    public PenguinVariant getVariant() {
        return PenguinVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(PenguinVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Variant", this.getTypeVariant());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.entityData.set(VARIANT, input.getIntOr("Variant", 0));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                                  EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
        PenguinVariant variant = Util.getRandom(PenguinVariant.values(), this.random);
        this.setVariant(variant);
        return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
    }


    /* SOUNDS */

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.GLOW_SQUID_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.FROG_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.BAT_DEATH;
    }

    /* BREEDABLE (extending Animal class) */
    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        PenguinEntity baby = ModEntities.PENGUIN.get().create(level, EntitySpawnReason.BREEDING);
        PenguinVariant babyVariant = Util.getRandom(PenguinVariant.values(), this.random);
        baby.setVariant(babyVariant);
        return baby;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Tags.Items.FOODS_RAW_FISH);
    }

    /* BOSS BAR */
    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }
}
