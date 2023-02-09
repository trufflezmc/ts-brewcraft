package com.trufflez.tsbrewcraft.statuseffect;

import com.trufflez.tsbrewcraft.TsBrewcraft;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TsStatusEffects {
    public static final StatusEffect TIPSY;
    public static final StatusEffect DRUNK;
        
    private static StatusEffect register(String id, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(TsBrewcraft.MOD_ID, id), statusEffect);
    }
    
    static {
        //Registry.register(Registry.STATUS_EFFECT, new Identifier(TsBrewcraft.MOD_ID, "tipsy"), TIPSY);
        //Registry.register(Registry.STATUS_EFFECT, new Identifier(TsBrewcraft.MOD_ID, "tipsy"), TIPSY);
        //Registry.register(Registry.STATUS_EFFECT, new Identifier(TsBrewcraft.MOD_ID, "tipsy"), TIPSY);
        
        TIPSY = register("tipsy", new TipsyStatusEffect());
        DRUNK = register("drunk", new DrunkStatusEffect());
    }
    
    public static void init() {
        TsBrewcraft.LOGGER.debug("Registering StatusEffects");
    }
}
