package net.kaupenjoe.mccourse.block.entity.custom;

import net.kaupenjoe.mccourse.block.custom.GrowthChamberBlock;
import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.menu.custom.GrowthChamberMenu;
import net.kaupenjoe.mccourse.recipe.ModRecipes;
import net.kaupenjoe.mccourse.recipe.custom.GrowthChamberRecipe;
import net.kaupenjoe.mccourse.recipe.custom.GrowthChamberRecipeInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GrowthChamberBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStacksResourceHandler itemStacksResourceHandler = new ItemStacksResourceHandler(4) {
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            super.onContentsChanged(index, previousContents);
            GrowthChamberBlockEntity.this.setChanged();
        }
    };

    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int INPUT_SLOT_3 = 2;

    private static final int OUTPUT_SLOT = 3;

    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;
    private final int DEFAULT_MAX_PROGRESS = 72;

    public GrowthChamberBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GROWTH_CHAMBER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> GrowthChamberBlockEntity.this.progress;
                    case 1 -> GrowthChamberBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0:
                        GrowthChamberBlockEntity.this.progress = pValue;
                    case 1:
                        GrowthChamberBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.mccourse.growth_chamber");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new GrowthChamberMenu(pContainerId, pPlayerInventory, this, this.itemStacksResourceHandler, this.data);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        output.putInt("growth_chamber.progress", progress);
        output.putInt("growth_chamber.max_progress", maxProgress);

        output.putChild("inv", itemStacksResourceHandler);

        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        progress = input.getInt("growth_chamber.progress").get();
        maxProgress = input.getInt("growth_chamber.max_progress").get();

        input.child("inv").ifPresent(itemStacksResourceHandler::deserialize);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemStacksResourceHandler.size());
        for (int i = 0; i < itemStacksResourceHandler.size(); i++) {
            ItemAccess itemAccess = ItemAccess.forHandlerIndex(itemStacksResourceHandler, 0);
            inv.setItem(i, new ItemStack(itemAccess.getResource().getItem(), itemAccess.getAmount()));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    public void tick(Level level, BlockPos pPos, BlockState pState) {
        if (hasRecipe() && isOutputSlotEmptyOrReceivable()) {
            increaseCraftingProgress();
            level.setBlockAndUpdate(pPos, pState.setValue(GrowthChamberBlock.LIT, true));
            setChanged(level, pPos, pState);

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }

        } else {
            resetProgress();
            level.setBlockAndUpdate(pPos, pState.setValue(GrowthChamberBlock.LIT, false));
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = DEFAULT_MAX_PROGRESS;
    }

    private void craftItem() {
        Optional<RecipeHolder<GrowthChamberRecipe>> recipe = getCurrentRecipe();
        ItemStack output = recipe.get().value().output().create();

        try (Transaction transaction = Transaction.open(null)) {

            ItemAccess itemAccess = ItemAccess.forHandlerIndex(itemStacksResourceHandler, OUTPUT_SLOT);

            itemStacksResourceHandler.extract(INPUT_SLOT_1, itemStacksResourceHandler.getResource(INPUT_SLOT_1), 1, transaction);
            itemStacksResourceHandler.extract(INPUT_SLOT_2, itemStacksResourceHandler.getResource(INPUT_SLOT_2), 1, transaction);
            itemStacksResourceHandler.extract(INPUT_SLOT_3, itemStacksResourceHandler.getResource(INPUT_SLOT_3), 1, transaction);
            itemStacksResourceHandler.set(OUTPUT_SLOT, ItemResource.of(output), itemAccess.getAmount() + output.getCount());

            transaction.commit();
        }
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.itemStacksResourceHandler.getResource(OUTPUT_SLOT).isEmpty() ||
                this.itemStacksResourceHandler.getResource(OUTPUT_SLOT).toStack().getCount() < this.itemStacksResourceHandler.getResource(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<GrowthChamberRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().assemble(
                new GrowthChamberRecipeInput(
                        itemStacksResourceHandler.getResource(INPUT_SLOT_1).toStack(),
                        itemStacksResourceHandler.getResource(INPUT_SLOT_2).toStack(),
                        itemStacksResourceHandler.getResource(INPUT_SLOT_3).toStack()));
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private Optional<RecipeHolder<GrowthChamberRecipe>> getCurrentRecipe() {
        return ((ServerLevel) this.level).recipeAccess()
                .getRecipeFor(ModRecipes.GROWTH_CHAMBER_TYPE.get(),
                        new GrowthChamberRecipeInput(
                                itemStacksResourceHandler.getResource(INPUT_SLOT_1).toStack(),
                                itemStacksResourceHandler.getResource(INPUT_SLOT_2).toStack(),
                                itemStacksResourceHandler.getResource(INPUT_SLOT_3).toStack()), level);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return itemStacksResourceHandler.getResource(OUTPUT_SLOT).isEmpty() ||
                itemStacksResourceHandler.getResource(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = itemStacksResourceHandler.getResource(OUTPUT_SLOT).isEmpty() ? 64 : itemStacksResourceHandler.getResource(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = itemStacksResourceHandler.getResource(OUTPUT_SLOT).toStack().getCount();

        return maxCount >= currentCount + count;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Override
    public void onDataPacket(Connection net, ValueInput valueInput) {
        super.onDataPacket(net, valueInput);
    }
}
