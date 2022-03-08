package com.trufflez.tsbrewcraft.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class DrunkStatusEffect extends StatusEffect {
    public DrunkStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xD6DE83);
    }
    
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
    
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            //((PlayerEntity) entity).addExperience(1 << amplifier);
        }
    }
}
