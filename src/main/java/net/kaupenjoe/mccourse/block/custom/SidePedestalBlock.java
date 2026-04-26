package net.kaupenjoe.mccourse.block.custom;

import com.mojang.serialization.MapCodec;
import net.kaupenjoe.mccourse.block.entity.custom.PedestalBlockEntity;
import net.kaupenjoe.mccourse.block.entity.custom.SidePedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.jspecify.annotations.Nullable;

public class SidePedestalBlock extends PedestalBlock {
    public static final MapCodec<SidePedestalBlock> CODEC = simpleCodec(SidePedestalBlock::new);

    public SidePedestalBlock(Properties properties) {
        super(properties);
    }

    /* BLOCK ENTITY */
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new SidePedestalBlockEntity(worldPosition, blockState);
    }
}
