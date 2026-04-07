package net.kaupenjoe.mccourse.keymapping;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class ModKeyMappings {
    private static final KeyMapping KAUPEN_KEYMAPPING = new KeyMapping("key.mccourse.kaupen_key",
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, KeyMapping.Category.MISC);
    public static final Lazy<KeyMapping> PRESS_KAUPEN_KEY = Lazy.of(() -> KAUPEN_KEYMAPPING);


    public static void register() {

    }
}
