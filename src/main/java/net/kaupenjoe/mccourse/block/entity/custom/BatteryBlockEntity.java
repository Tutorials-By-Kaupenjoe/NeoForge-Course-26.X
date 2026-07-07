package net.kaupenjoe.mccourse.block.entity.custom;

import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.block.entity.ModEnergyUtil;
import net.kaupenjoe.mccourse.block.entity.sub.EnergyBlockEntity;
import net.kaupenjoe.mccourse.menu.custom.BatteryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

public class BatteryBlockEntity extends EnergyBlockEntity implements MenuProvider {
    public BatteryBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.BATTERY_BE.get(), pPos, pBlockState);
    }

    @Override
    public void assignEnergyStorage() {
        this.ENERGY_STORAGE = createEnergyStorage(6400000, 6400);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Battery");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new BatteryMenu(pContainerId, pPlayerInventory, this);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        pushEnergyToNeighbourAbove();
    }

    private void pushEnergyToNeighbourAbove() {
        if (ModEnergyUtil.doesBlockHaveEnergyStorage(this.worldPosition.above(), this.level)) {
            ModEnergyUtil.move(this.worldPosition, this.worldPosition.above(), 6400, this.level);
        }
    }
}
