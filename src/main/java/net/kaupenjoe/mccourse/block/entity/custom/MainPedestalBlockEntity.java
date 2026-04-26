package net.kaupenjoe.mccourse.block.entity.custom;

import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.recipe.ModRecipes;
import net.kaupenjoe.mccourse.recipe.custom.PedestalRecipe;
import net.kaupenjoe.mccourse.recipe.custom.PedestalRecipeInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.joml.Vector2i;

import java.util.List;
import java.util.Optional;

public class MainPedestalBlockEntity extends PedestalBlockEntity {
    public static List<Vector2i> offsets = List.of(
            new Vector2i(3, 0),
            new Vector2i(2, 2),
            new Vector2i(0, 3),
            new Vector2i(-2, 2),

            new Vector2i(-3, 0),
            new Vector2i(-2, -2),
            new Vector2i(0, -3),
            new Vector2i(2, -2));

    public int count = 0;
    private int maxCount = 60; // 3 seconds

    public MainPedestalBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.MAIN_PEDESTAL_BE.get(), worldPosition, blockState);
    }

    /* PEDESTAL CRAFTING */
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!hasRecipe())
            return;

        if(countFinished()) {
            // We are finished crafting
            count = 0;
            exchangeItemInMainPedestal();
            removeItemsFromSidePedestals();
            spawnVisualLightningBolt((ServerLevel) level, pos);
            spawnExplosionParticles((ServerLevel) level);
        } else {
            // if Crafting progress increases
            countUp();
            spawnCraftingParticles(level);
        }
    }

    private void countUp() {
        count++;
    }

    private void spawnVisualLightningBolt(ServerLevel level, BlockPos blockPos) {
        EntityType.LIGHTNING_BOLT.spawn(level, blockPos, EntitySpawnReason.TRIGGERED).setVisualOnly(true);
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean countFinished() {
        return count >= maxCount;
    }

    public boolean hasRecipe() {
        Optional<RecipeHolder<PedestalRecipe>> recipe = getCurrentRecipe();
        return recipe.isPresent();
    }

    private Optional<RecipeHolder<PedestalRecipe>> getCurrentRecipe() {
        return ((ServerLevel) this.level).recipeAccess()
                .getRecipeFor(ModRecipes.PEDESTAL_TYPE.get(), new PedestalRecipeInput(
                        this.inventory.getResource(0).toStack(),

                        offsets.stream().map(offset -> {
                            if(hasSidePedestals()) {
                                return ((SidePedestalBlockEntity) level.getBlockEntity(this.getBlockPos().offset(offset.x, 0, offset.y))).
                                        inventory.getResource(0).toStack();
                            } else {
                                return ItemStack.EMPTY;
                            }}).toList()), level);
    }

    private boolean hasSidePedestals() {
        return offsets.stream().allMatch(this::isSidePedestal);
    }

    private boolean isSidePedestal(Vector2i offset) {
        return level.getBlockState(this.getBlockPos().offset(offset.x, 0, offset.y)).is(ModBlocks.SIDE_PEDESTAL);
    }

    private void exchangeItemInMainPedestal() {
        Optional<RecipeHolder<PedestalRecipe>> recipe = getCurrentRecipe();

        recipe.ifPresent(pedestalRecipeRecipeHolder ->
                this.inventory.set(0, ItemResource.of(pedestalRecipeRecipeHolder.value().output()), 1));
    }

    private void removeItemsFromSidePedestals() {
        offsets.forEach(offset -> ((SidePedestalBlockEntity) level.getBlockEntity(this.getBlockPos().offset(offset.x, 0, offset.y)))
                .inventory.set(0, ItemResource.EMPTY, 0));
    }

    private void spawnExplosionParticles(ServerLevel level) {
        double x = this.getBlockPos().getX();
        double y = this.getBlockPos().getY();
        double z = this.getBlockPos().getZ();
        level.sendParticles(ParticleTypes.EXPLOSION_EMITTER,
                x + 0.5f, y + 1.2f, z + 0.5f, 0, 0, 0, 0, 0.25f);
    }

    private void spawnCraftingParticles(Level level) {
        offsets.forEach(offset -> {
            ItemStack stack = ((SidePedestalBlockEntity) level.getBlockEntity(this.getBlockPos().offset(offset.x, 0, offset.y))).inventory.getResource(0).toStack();
            double x = this.getBlockPos().offset(offset.x, 0, offset.y).getX();
            double y = this.getBlockPos().offset(offset.x, 0, offset.y).getY();
            double z = this.getBlockPos().offset(offset.x, 0, offset.y).getZ();

            BlockPos direction = getBlockPos().subtract(this.getBlockPos().offset(offset.x, 0, offset.y));

            ((ServerLevel) level).sendParticles(new ItemParticleOption
                    (ParticleTypes.ITEM, stack.getItem()), x + 0.5f, y + 1.2f, z + 0.5f, 0, direction.getX(), direction.getY(), direction.getZ(), 0.25f);
        });
    }
}
