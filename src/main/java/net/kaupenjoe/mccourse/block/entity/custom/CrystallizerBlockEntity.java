package net.kaupenjoe.mccourse.block.entity.custom;

import net.kaupenjoe.mccourse.block.custom.CrystallizerBlock;
import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.block.entity.sub.EnergyBlockEntity;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.menu.custom.CrystallizerMenu;
import net.kaupenjoe.mccourse.recipe.ModRecipes;
import net.kaupenjoe.mccourse.recipe.custom.CrystallizerRecipe;
import net.kaupenjoe.mccourse.recipe.custom.CrystallizerRecipeInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.transfer.RangedResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class CrystallizerBlockEntity extends EnergyBlockEntity implements MenuProvider {
    public final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(4) {
        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            super.onContentsChanged(index, previousContents);
            CrystallizerBlockEntity.this.setChanged();
        }
    };

    private final ResourceHandler<ItemResource> leftHandler = RangedResourceHandler.of(inventory, FLUID_ITEM_SLOT, FLUID_ITEM_SLOT + 1);
    private final ResourceHandler<ItemResource> topHandler = RangedResourceHandler.of(inventory, INPUT_SLOT, INPUT_SLOT + 1);
    private final ResourceHandler<ItemResource> bottomHandler = RangedResourceHandler.of(inventory, OUTPUT_SLOT, OUTPUT_SLOT + 1);
    private final ResourceHandler<ItemResource> rightHandler = RangedResourceHandler.of(inventory, ENERGY_ITEM_SLOT, ENERGY_ITEM_SLOT + 1);

    private final ResourceHandler<ItemResource> frontBackHandler = RangedResourceHandler.of(inventory, INPUT_SLOT, OUTPUT_SLOT + 1);

    private static final int FLUID_ITEM_SLOT = 0;
    private static final int INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int ENERGY_ITEM_SLOT = 3;

    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;

    private static final int ENERGY_CRAFT_AMOUNT = 25;      // per tick
    private static final int FLUID_CRAFT_AMOUNT = 1000;     // per craft


    private final FluidStacksResourceHandler FLUID_TANK = new FluidStacksResourceHandler(1, 16000) {
        @Override
        protected void onContentsChanged(int index, FluidStack previousContents) {
            setChanged();
            if(!getLevel().isClientSide()) {
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isValid(int index, FluidResource resource) {
            return true;
        }
    };


    public CrystallizerBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.CRYSTALLIZER_BE.get(), worldPosition, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int dataId) {
                return switch (dataId) {
                    case 0 -> CrystallizerBlockEntity.this.progress;
                    case 1 -> CrystallizerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int dataId, int value) {
                switch (dataId) {
                    case 0: CrystallizerBlockEntity.this.progress = value;
                    case 1: CrystallizerBlockEntity.this.maxProgress = value;
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
        return Component.translatable("block.mccourse.crystallizer");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new CrystallizerMenu(containerId, inventory, this, this.inventory, this.data);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("crystallizer.progress", progress);
        output.putInt("crystallizer.max_progress", maxProgress);

        output.putChild("inventory", inventory);

        FLUID_TANK.serialize(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        progress = input.getIntOr("crystallizer.progress", 0);
        maxProgress = input.getIntOr("crystallizer.max_progress", 72);

        input.child("inventory").ifPresent(inventory::deserialize);

        FLUID_TANK.deserialize(input);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.size());
        for (int i = 0; i < inventory.size(); i++) {
            ItemAccess itemAccess = ItemAccess.forHandlerIndex(inventory, 0);
            inv.setItem(i, new ItemStack(itemAccess.getResource().getItem(), itemAccess.getAmount()));
        }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    public ResourceHandler<ItemResource> getItemHandler(Direction direction) {
        if (direction == null)
            return inventory;

        Direction facing = this.getBlockState().hasProperty(BlockStateProperties.HORIZONTAL_FACING)
                ? this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)
                : Direction.NORTH;

        return switch (getRelativeSide(facing, direction)) {
            case UP -> topHandler;
            case DOWN -> bottomHandler;
            case WEST -> leftHandler;
            case EAST -> rightHandler;
            case NORTH, SOUTH -> frontBackHandler;
        };
    }

    private Direction getRelativeSide(Direction facing, Direction absoluteSide) {
        if (absoluteSide.getAxis().isVertical()) return absoluteSide;

        if (absoluteSide == facing) return Direction.NORTH;
        if (absoluteSide == facing.getOpposite()) return Direction.SOUTH;
        if (absoluteSide == facing.getClockWise()) return Direction.WEST;
        if (absoluteSide == facing.getCounterClockWise()) return Direction.EAST;

        return absoluteSide;
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if(hasRecipe() && isOutputSlotEmptyOrReceivable()) { // isOutputSlotEmptyOrReceivable redundant?
            increaseCraftingProgress();
            useEnergyForCrafting();
            setChanged(level, pos, state);
            level.setBlockAndUpdate(pos, state.setValue(CrystallizerBlock.LIT, true));

            if(hasCraftingFinished()) {
                craftItem();
                extractFluidForCrafting();
                resetProgress();
            }
        } else {
            resetProgress();
            level.setBlockAndUpdate(pos, state.setValue(CrystallizerBlock.LIT, false));
        }

        if (hasFluidItemStackInSlot()) {
            transferFluidFromItemToTank();
        }

        // Debugging-ish Methods
        if(hasItemInEnergySlot()) {
            fillUpOnEnergy();
        }
    }


    private boolean hasRecipe() {
        Optional<RecipeHolder<CrystallizerRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().assemble(new CrystallizerRecipeInput(inventory.getResource(INPUT_SLOT).toStack()));

        boolean outputSlotAmount = canInsertAmountIntoOutputSlot(output.getCount());
        boolean outputSlotItem = canInsertItemIntoOutputSlot(output);
        boolean hasEnoughEnergy = hasEnoughEnergyToCraft();
        boolean hasEnoughFluid = hasEnoughFluidToCraft();

        return outputSlotItem && outputSlotAmount && hasEnoughEnergy && hasEnoughFluid;
    }

    private Optional<RecipeHolder<CrystallizerRecipe>> getCurrentRecipe() {
        return ((ServerLevel) level).recipeAccess()
                .getRecipeFor(ModRecipes.CRYSTALLIZER_TYPE.get(),
                        new CrystallizerRecipeInput(inventory.getResource(INPUT_SLOT).toStack()), level);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return inventory.getResource(OUTPUT_SLOT).isEmpty() ||
                inventory.getResource(OUTPUT_SLOT).is(output.getItem());
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = inventory.getResource(OUTPUT_SLOT).isEmpty() ? 64 : inventory.getResource(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = inventory.getAmountAsInt(OUTPUT_SLOT);

        return maxCount >= currentCount + count;
    }

    private void craftItem() {
        Optional<RecipeHolder<CrystallizerRecipe>> recipe = getCurrentRecipe();
        ItemStack output = recipe.get().value().output().create();

        try(Transaction transaction = Transaction.openRoot()) {
            ItemAccess itemAccess = ItemAccess.forHandlerIndex(inventory, OUTPUT_SLOT);

            inventory.extract(inventory.getResource(INPUT_SLOT), 1, transaction);
            inventory.set(OUTPUT_SLOT, ItemResource.of(output), itemAccess.getAmount() + output.getCount());

            transaction.commit();
        }
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return inventory.getResource(OUTPUT_SLOT).isEmpty() ||
                inventory.getResource(OUTPUT_SLOT).test(stack -> stack.count() < stack.getMaxStackSize());
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    /* ENERGY */

    private boolean hasEnoughEnergyToCraft() {
        return this.ENERGY_STORAGE.getAmountAsInt() >= ENERGY_CRAFT_AMOUNT * maxProgress;
    }

    private void useEnergyForCrafting() {
        try(Transaction transaction = Transaction.openRoot()) {
            this.ENERGY_STORAGE.extract(ENERGY_CRAFT_AMOUNT, transaction);
            transaction.commit();
        }
    }

    private void fillUpOnEnergy() {
        try(Transaction transaction = Transaction.openRoot()) {
            this.ENERGY_STORAGE.insert(160, transaction);
            transaction.commit();
        }
    }

    private boolean hasItemInEnergySlot() {
        return inventory.getResource(ENERGY_ITEM_SLOT).is(ModItems.RADISH.get());
    }

    /* FLUID HANDLING */
    public FluidStacksResourceHandler getFluidTank(@Nullable Direction direction) {
        return this.FLUID_TANK;
    }

    public FluidStack getFluid() {
        return new FluidStack(FLUID_TANK.getResource(0).getFluid(), FLUID_TANK.getAmountAsInt(0));
    }

    private void transferFluidFromItemToTank() {
        try(Transaction transaction = Transaction.openRoot()) {
            ItemAccess itemAccess = ItemAccess.forHandlerIndex(inventory, FLUID_ITEM_SLOT);
            var itemCapability = itemAccess.getCapability(Capabilities.Fluid.ITEM);

            int fluidMoved = ResourceHandlerUtil.move(itemCapability, FLUID_TANK, fluidResource -> true,
                    FluidType.BUCKET_VOLUME, transaction);

            if(fluidMoved == FluidType.BUCKET_VOLUME) {
                transaction.commit();
            }
        }
    }

    private boolean hasFluidItemStackInSlot() {
        return !inventory.getResource(FLUID_ITEM_SLOT).isEmpty()
                && ItemAccess.forHandlerIndex(inventory, FLUID_ITEM_SLOT).getCapability(Capabilities.Fluid.ITEM) != null
                && ItemAccess.forHandlerIndex(inventory, FLUID_ITEM_SLOT).getCapability(Capabilities.Fluid.ITEM).getAmountAsInt(0) != 0;
    }

    private void extractFluidForCrafting() {
        try(Transaction transaction = Transaction.openRoot()) {
            FLUID_TANK.extract(FLUID_TANK.getResource(0), FLUID_CRAFT_AMOUNT, transaction);
            transaction.commit();
        }
    }

    private boolean hasEnoughFluidToCraft() {
        return FLUID_TANK.getAmountAsInt(0) >= FLUID_CRAFT_AMOUNT;
    }
}
