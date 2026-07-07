package net.kaupenjoe.mccourse.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class ModEnergyUtil {
    public static boolean move(BlockPos from, BlockPos to, int amount, Level level) {
        EnergyHandler fromStorage = level.getCapability(Capabilities.Energy.BLOCK, from, null);
        EnergyHandler toStorage = level.getCapability(Capabilities.Energy.BLOCK, to, null);

        if(canEnergyStorageExtractThisAmount(fromStorage, amount)) {
            return false;
        }

        if(canEnergyStorageStillReceiveEnergy(toStorage)) {
            return false;
        }

        try (Transaction transaction = Transaction.openRoot()) {
            int maxAmountToReceive = toStorage.insert(amount, transaction);
            fromStorage.extract(maxAmountToReceive, transaction);

            // toStorage.insert(extractedEnergy, transaction);
            transaction.commit();
        }


        return true;
    }

    private static boolean canEnergyStorageStillReceiveEnergy(EnergyHandler toStorage) {
        // No more Energy to draw or cannot extract
        return toStorage.getAmountAsInt() >= toStorage.getCapacityAsInt();
    }

    private static boolean canEnergyStorageExtractThisAmount(EnergyHandler fromStorage, int amount) {
        // No more Space to receive or cannot receive
        return fromStorage.getAmountAsInt() <= 0 || fromStorage.getAmountAsInt() < amount;
    }

    public static boolean doesBlockHaveEnergyStorage(BlockPos positionToCheck, Level level) {
         return level.getBlockEntity(positionToCheck) != null
                && level.getCapability(Capabilities.Energy.BLOCK, positionToCheck, null) != null;
    }
}
