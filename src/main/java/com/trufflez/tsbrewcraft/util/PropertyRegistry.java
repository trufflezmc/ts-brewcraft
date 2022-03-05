package com.trufflez.tsbrewcraft.util;

import com.trufflez.tsbrewcraft.TsBrewcraft;
import com.trufflez.tsbrewcraft.item.TsItems;
import net.fabricmc.fabric.api.registry.FuelRegistry;

public class PropertyRegistry {
    private static final FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
    
    static {
        // Subject to change
        fuelRegistry.add(TsItems.VODKA, 60);
        fuelRegistry.add(TsItems.RUM, 60);
        fuelRegistry.add(TsItems.WHISKEY, 60);
        fuelRegistry.add(TsItems.BOURBON, 60);
        fuelRegistry.add(TsItems.BRANDY, 60);
        fuelRegistry.add(TsItems.SHOCHU, 60);
        fuelRegistry.add(TsItems.TEQUILA, 60);
        
        fuelRegistry.add(TsItems.MOONSHINE, 10);
    }
    
    public static void init() {
        TsBrewcraft.LOGGER.debug("Appending block and item properties");
    }
}
