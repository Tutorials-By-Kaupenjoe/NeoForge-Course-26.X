package net.kaupenjoe.mccourse.datagen;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.sound.ModSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundsProvider extends SoundDefinitionsProvider {
    public ModSoundsProvider(PackOutput output) {
        super(output, MCCourse.MOD_ID);
    }

    @Override
    public void registerSounds() {
        add(ModSounds.CHISEL_USE.get(), definition().subtitle("sounds.mccourse.chisel_use")
                .with(sound(Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "chisel_use"))));

        add(ModSounds.MAGIC_BLOCK_BREAK.get(), definition().subtitle("sounds.mccourse.magic_block_break")
                .with(sound(Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "magic_block_break"))));
        add(ModSounds.MAGIC_BLOCK_STEP.get(), definition().subtitle("sounds.mccourse.magic_block_step")
                .with(sound(Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "magic_block_step"))));
        add(ModSounds.MAGIC_BLOCK_PLACE.get(), definition().subtitle("sounds.mccourse.magic_block_place")
                .with(sound(Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "magic_block_place"))));
        add(ModSounds.MAGIC_BLOCK_HIT.get(), definition().subtitle("sounds.mccourse.magic_block_hit")
                .with(sound(Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "magic_block_hit"))));
        add(ModSounds.MAGIC_BLOCK_FALL.get(), definition().subtitle("sounds.mccourse.magic_block_fall")
                .with(sound(Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "magic_block_fall"))));

    }
}
