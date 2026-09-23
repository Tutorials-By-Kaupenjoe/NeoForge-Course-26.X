package net.kaupenjoe.mccourse.worldgen.tree;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.worldgen.ModFeatures;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower EBONY = new TreeGrower(MCCourse.MOD_ID + ":ebony",
            WeightedList.of(ModFeatures.EBONY_TREE_KEY),  WeightedList.of(), WeightedList.of(), null);
}
