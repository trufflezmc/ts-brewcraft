package com.trufflez.tsbrewcraft.item.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TsMultiConsumable extends Item {
    private final int uses;
    
    public TsMultiConsumable(int uses, Settings settings) {
        super(settings.maxDamage(uses));
        this.uses = uses;
    }

    public int getUses() {
        return this.uses;
    }

    public boolean postUse(ItemStack stack, LivingEntity player) {
        stack.damage(1, player, (e) ->
                e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }
}
