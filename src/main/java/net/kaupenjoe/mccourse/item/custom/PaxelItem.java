package net.kaupenjoe.mccourse.item.custom;

import net.kaupenjoe.mccourse.tag.ModTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.BlockTransformers;

public class PaxelItem extends Item {
    public PaxelItem(ToolMaterial toolMaterial, float attackDamageBaseLine,
                     float attackSpeedBaseline, Properties properties) {
        super(properties.tool(toolMaterial, ModTags.Blocks.PAXEL_MINEABLE, attackDamageBaseLine, attackSpeedBaseline, 1f)
                .delayedComponent(DataComponents.BLOCK_TRANSFORMER, context -> context.getOrThrow(BlockTransformers.AXE))
                .delayedComponent(DataComponents.BLOCK_TRANSFORMER, context -> context.getOrThrow(BlockTransformers.HOE)));
    }
}
