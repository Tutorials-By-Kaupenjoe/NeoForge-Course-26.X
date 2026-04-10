package net.kaupenjoe.mccourse.block.custom;

import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.TriState;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class RiceCropBlock extends CropBlock {
    public RiceCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.RICE_SHOOT.get();
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState stateBelow = level.getBlockState(pos.below());
        if (stateBelow.is(Blocks.WATER)) {
            BlockPos posBelow = pos.below();

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState relativeState = level.getBlockState(posBelow.relative(direction));
                FluidState fluidstate = level.getFluidState(posBelow.relative(direction));
                if (fluidstate.is(FluidTags.WATER) || relativeState.is(Blocks.FROSTED_ICE)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition,
                                    Direction facing, BlockState plant) {
        BlockState blockState = level.getBlockState(soilPosition);
        if(blockState.is(Blocks.WATER)) {
            return TriState.TRUE;
        } else {
            return TriState.FALSE;
        }
    }
}
