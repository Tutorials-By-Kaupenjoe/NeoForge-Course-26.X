package net.kaupenjoe.mccourse.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class AddItemStackModifier extends LootModifier {
    public static final MapCodec<AddItemStackModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
            LootModifier.codecStart(instance).and(
                    ItemStackTemplate.CODEC.fieldOf("stack").forGetter(inst -> inst.itemStack)).apply(instance,
                    (lootItemConditions, integer, itemStackTemplate) -> new AddItemStackModifier(lootItemConditions, itemStackTemplate)));
    private final ItemStackTemplate itemStack;

    public AddItemStackModifier(LootItemCondition[] conditionsIn, ItemStackTemplate itemStackTemplate) {
        super(conditionsIn, 1000);
        this.itemStack = itemStackTemplate;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for(LootItemCondition condition : this.conditions) {
            if(!condition.test(context)) {
                return generatedLoot;
            }
        }

        generatedLoot.add(itemStack.create());
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
