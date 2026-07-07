package net.kaupenjoe.mccourse.menu.custom;

import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.entity.custom.BatteryBlockEntity;
import net.kaupenjoe.mccourse.menu.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BatteryMenu extends AbstractContainerMenu {
    public final BatteryBlockEntity blockEntity;
    private final Level level;

    public BatteryMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public BatteryMenu(int pContainerId, Inventory inv, BlockEntity blockEntity) {
        super(ModMenuTypes.BATTERY_MENU.get(), pContainerId);
        checkContainerSize(inv, 1);
        this.blockEntity = ((BatteryBlockEntity) blockEntity);
        this.level = inv.player.level();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.BATTERY.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
