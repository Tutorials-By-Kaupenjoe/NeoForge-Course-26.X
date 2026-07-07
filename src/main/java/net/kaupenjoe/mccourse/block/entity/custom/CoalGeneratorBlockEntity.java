package net.kaupenjoe.mccourse.block.entity.custom;

import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.block.entity.ModEnergyUtil;
import net.kaupenjoe.mccourse.block.entity.sub.EnergyBlockEntity;
import net.kaupenjoe.mccourse.menu.custom.CoalGeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

public class CoalGeneratorBlockEntity extends EnergyBlockEntity implements MenuProvider {
    public final ItemStacksResourceHandler itemStacksResourceHandler = new ItemStacksResourceHandler(1) {
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            super.onContentsChanged(index, previousContents);
            CoalGeneratorBlockEntity.this.setChanged();
        }
    };

    private static final int INPUT_SLOT = 0;

    protected final ContainerData data;
    private int burnProgress = 160;
    private int maxBurnProgress = 160;
    private boolean isBurning = false;

    public CoalGeneratorBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.COAL_GENERATOR_BE.get(), worldPosition, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> CoalGeneratorBlockEntity.this.burnProgress;
                    case 1 -> CoalGeneratorBlockEntity.this.maxBurnProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> CoalGeneratorBlockEntity.this.burnProgress = pValue;
                    case 1 -> CoalGeneratorBlockEntity.this.maxBurnProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public void assignEnergyStorage() {
        this.ENERGY_STORAGE = createEnergyStorage(64000, 320);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Coal Generator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new CoalGeneratorMenu(containerId, inventory, this, this.itemStacksResourceHandler, this.data);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (hasFuelItemInSlot()) {
            if (!isBurningFuel()) {
                startBurning();
            }
        }

        if (isBurningFuel()) {
            increaseBurnTimer();
            if (currentFuelDoneBurning()) {
                resetBurning();
            }
            fillUpOnEnergy();
        }

        pushEnergyToNeighbourAbove();
    }

    private void pushEnergyToNeighbourAbove() {
        if (ModEnergyUtil.doesBlockHaveEnergyStorage(this.worldPosition.above(), this.level)) {
            ModEnergyUtil.move(this.worldPosition, this.worldPosition.above(), 320, this.level);
        }
    }

    private boolean hasFuelItemInSlot() {
        return this.itemStacksResourceHandler.getResource(INPUT_SLOT).is(Items.COAL);
    }

    private boolean isBurningFuel() {
        return isBurning;
    }

    private void startBurning() {
        try (Transaction transaction = Transaction.openRoot()) {
            this.itemStacksResourceHandler.extract(INPUT_SLOT, ItemResource.of(Items.COAL), 1, transaction);
            transaction.commit();
        }

        isBurning = true;
    }

    private void increaseBurnTimer() {
        this.burnProgress--;
    }

    private boolean currentFuelDoneBurning() {
        return this.burnProgress <= 0;
    }

    private void resetBurning() {
        isBurning = false;
        this.burnProgress = 160;
    }

    private void fillUpOnEnergy() {
        try (Transaction transaction = Transaction.openRoot()) {
            this.ENERGY_STORAGE.insert(320, transaction);
            transaction.commit();
        }
    }


    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStacksResourceHandler.size());
        for (int i = 0; i < itemStacksResourceHandler.size(); i++) {
            ItemAccess itemAccess = ItemAccess.forHandlerIndex(itemStacksResourceHandler, 0);
            inventory.setItem(i, new ItemStack(itemAccess.getResource().getItem(), itemAccess.getAmount()));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putChild("inv", itemStacksResourceHandler);

        output.putInt("coal_generator.burn_progress", burnProgress);
        output.putInt("coal_generator.max_burn_progress", maxBurnProgress);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        input.child("inv").ifPresent(itemStacksResourceHandler::deserialize);

        burnProgress = input.getInt("coal_generator.burn_progress").get();
        maxBurnProgress = input.getInt("coal_generator.max_burn_progress").get();
    }


}
