package net.kaupenjoe.mccourse.block.entity.custom;

import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SidePedestalBlockEntity extends PedestalBlockEntity {
    public SidePedestalBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.SIDE_PEDESTAL_BE.get(), worldPosition, blockState);
    }
}
