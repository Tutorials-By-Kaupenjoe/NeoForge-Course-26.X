package net.kaupenjoe.mccourse.block.entity.custom;

import net.kaupenjoe.mccourse.block.custom.AtomicSeparatorBlock;
import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.menu.custom.AtomicSeparatorMenu;
import net.kaupenjoe.mccourse.recipe.ModRecipes;
import net.kaupenjoe.mccourse.recipe.custom.AtomicSeparatorRecipe;
import net.kaupenjoe.mccourse.recipe.custom.AtomicSeparatorRecipeInput;
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

import java.util.List;
import java.util.Optional;

public class AtomicSeparatorBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStacksResourceHandler itemStacksResourceHandler = new ItemStacksResourceHandler(4) {
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            super.onContentsChanged(index, previousContents);
            AtomicSeparatorBlockEntity.this.setChanged();
        }
    };

    private static final int OUTPUT_SLOT_1 = 0;
    private static final int OUTPUT_SLOT_2 = 1;
    private static final int OUTPUT_SLOT_3 = 2;

    private static final int INPUT_SLOT = 3;

    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;
    private final int DEFAULT_MAX_PROGRESS = 72;

    public AtomicSeparatorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ATOMIC_SEPARATOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> AtomicSeparatorBlockEntity.this.progress;
                    case 1 -> AtomicSeparatorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0:
                        AtomicSeparatorBlockEntity.this.progress = pValue;
                    case 1:
                        AtomicSeparatorBlockEntity.this.maxProgress = pValue;
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
        return Component.translatable("block.mccourse.atomic_separator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new AtomicSeparatorMenu(pContainerId, pPlayerInventory, this, this.itemStacksResourceHandler, this.data);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        output.putInt("atomic_separator.progress", progress);
        output.putInt("atomic_separator.max_progress", maxProgress);

        output.putChild("inv", itemStacksResourceHandler);

        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        progress = input.getInt("atomic_separator.progress").get();
        maxProgress = input.getInt("atomic_separator.max_progress").get();

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
            level.setBlockAndUpdate(pPos, pState.setValue(AtomicSeparatorBlock.LIT, true));
            setChanged(level, pPos, pState);

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }

        } else {
            resetProgress();
            level.setBlockAndUpdate(pPos, pState.setValue(AtomicSeparatorBlock.LIT, false));
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = DEFAULT_MAX_PROGRESS;
    }

    private void craftItem() {
        Optional<RecipeHolder<AtomicSeparatorRecipe>> recipe = getCurrentRecipe();
        List<ItemStack> outputs = recipe.get().value().getOutputs();

        try (Transaction transaction = Transaction.open(null)) {
            itemStacksResourceHandler.extract(itemStacksResourceHandler.getResource(INPUT_SLOT), 1, transaction);

            ItemAccess itemAccess1 = ItemAccess.forHandlerIndex(itemStacksResourceHandler, OUTPUT_SLOT_1);
            ItemAccess itemAccess2 = ItemAccess.forHandlerIndex(itemStacksResourceHandler, OUTPUT_SLOT_2);
            ItemAccess itemAccess3 = ItemAccess.forHandlerIndex(itemStacksResourceHandler, OUTPUT_SLOT_3);

            itemStacksResourceHandler.set(OUTPUT_SLOT_1, ItemResource.of(outputs.get(0)),itemAccess1.getAmount() + outputs.get(0).getCount());
            itemStacksResourceHandler.set(OUTPUT_SLOT_2, ItemResource.of(outputs.get(1)),itemAccess2.getAmount() + outputs.get(1).getCount());
            itemStacksResourceHandler.set(OUTPUT_SLOT_3, ItemResource.of(outputs.get(2)),itemAccess3.getAmount() + outputs.get(2).getCount());

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
        for(int i = 0; i < 3; i++) {
            if(!(itemStacksResourceHandler.getResource(i).isEmpty() ||
                    this.itemStacksResourceHandler.getResource(i).toStack().getCount() <
                    this.itemStacksResourceHandler.getResource(i).getMaxStackSize())) {
                return false;
            }
        }

        return true;
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<AtomicSeparatorRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }

        List<ItemStack> outputs = recipe.get().value().getOutputs();

        return canInsertAmountIntoOutputSlot(outputs) && canInsertItemIntoOutputSlot(outputs);
    }

    private Optional<RecipeHolder<AtomicSeparatorRecipe>> getCurrentRecipe() {
        return ((ServerLevel) this.level).recipeAccess()
                .getRecipeFor(ModRecipes.ATOMIC_SEPARATOR_TYPE.get(),
                        new AtomicSeparatorRecipeInput(itemStacksResourceHandler.getResource(INPUT_SLOT).toStack()), level);
    }

    private boolean canInsertItemIntoOutputSlot(List<ItemStack> outputs) {
        for(int i = 0; i < outputs.size(); i++) {
            if(!(itemStacksResourceHandler.getResource(i).isEmpty() ||
                    itemStacksResourceHandler.getResource(i).getItem() == outputs.get(i).getItem())) {
                return false;
            }
        }
        return true;
    }

    private boolean canInsertAmountIntoOutputSlot(List<ItemStack> outputs) {
        int maxCount;
        int currentCount;

        for(int i = 0; i < outputs.size(); i++) {
            maxCount = itemStacksResourceHandler.getResource(i).isEmpty() ? 64
                    : itemStacksResourceHandler.getResource(i).getMaxStackSize();
            currentCount = itemStacksResourceHandler.getAmountAsInt(i);

            if(!(maxCount >= currentCount + outputs.get(i).getCount())) {
                return false;
            }
        }
        return true;
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
