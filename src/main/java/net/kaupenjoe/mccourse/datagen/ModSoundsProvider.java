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

    }
}
