package net.kaupenjoe.mccourse.item;

import com.google.common.collect.Maps;
import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.tag.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.Map;

public class ModArmorMaterials {
    private static ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID =
            ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));
    public static ResourceKey<EquipmentAsset> ZIRCON_KEY = ResourceKey.create(ROOT_ID,
            Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "zircon"));

    public static final ArmorMaterial ZIRCON_ARMOR_MATERIAL = new ArmorMaterial(29,
            makeDefense(5, 7, 9, 5, 11), 18, SoundEvents.ARMOR_EQUIP_DIAMOND,
            2f, 0.1f, ModTags.Items.ZIRCON_REPAIRABLES, ZIRCON_KEY);


    private static Map<ArmorType, Integer> makeDefense(int boots, int legs, int chest, int helm, int body) {
        return Maps.newEnumMap(
                Map.of(ArmorType.BOOTS, boots, ArmorType.LEGGINGS, legs, ArmorType.CHESTPLATE, chest, ArmorType.HELMET, helm, ArmorType.BODY, body)
        );
    }
}
