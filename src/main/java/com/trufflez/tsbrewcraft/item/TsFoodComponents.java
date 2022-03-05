package com.trufflez.tsbrewcraft.item;

import com.trufflez.tsbrewcraft.TsBrewcraft;
import net.minecraft.item.FoodComponent;

public class TsFoodComponents {
    public static final FoodComponent STEAMED_RICE;
    public static final FoodComponent MOLDY_RICE;
    public static final FoodComponent GRAPES;
    public static final FoodComponent CORN;
    
    public static final FoodComponent BEER;
    public static final FoodComponent WHEAT_BEER;
    public static final FoodComponent MALT_LIQUOR;
    public static final FoodComponent LAMBIC;
    public static final FoodComponent SAKE;
    public static final FoodComponent RED_WINE;
    public static final FoodComponent WHITE_WINE;
    public static final FoodComponent ROSE;
    public static final FoodComponent CHAMPAGNE;
    public static final FoodComponent RUM;
    public static final FoodComponent TEQUILA;
    public static final FoodComponent VODKA;
    public static final FoodComponent BRANDY;
    public static final FoodComponent WHISKEY;
    public static final FoodComponent BOURBON;
    public static final FoodComponent SHOCHU;
    public static final FoodComponent MOONSHINE;
    
    public static final FoodComponent VINEGAR;
    public static final FoodComponent CRAPPY_BEER;
    public static final FoodComponent CRAPPY_WINE;
    public static final FoodComponent STRANGE_WINE;

    private static FoodComponent.Builder drink(int hunger) {
        return (new FoodComponent.Builder()).hunger(hunger).saturationModifier(0.1F).alwaysEdible();
    }

    private static FoodComponent food(int hunger, float saturation) {
        return (new FoodComponent.Builder()).hunger(hunger).saturationModifier(saturation).build();
    }
    
    static {
        STEAMED_RICE = food(8, 0.2f);
        MOLDY_RICE = food(1, 0f);
        GRAPES = food(4, 0.6f);
        CORN = food(6, 0.6f);
        
        BEER = drink(4).build();
        WHEAT_BEER = drink(4).build();
        MALT_LIQUOR = drink(4).build();
        LAMBIC = drink(4).build();
        SAKE = drink(4).build();
        RED_WINE = drink(4).build();
        WHITE_WINE = drink(4).build();
        ROSE = drink(4).build();
        CHAMPAGNE = drink(4).build();
        RUM = drink(2).build();
        TEQUILA = drink(2).build();
        VODKA = drink(2).build();
        BRANDY = drink(2).build();
        WHISKEY = drink(2).build();
        BOURBON = drink(2).build();
        SHOCHU = drink(2).build();
        MOONSHINE = drink(2).build();
        
        VINEGAR = drink(1).build();
        CRAPPY_BEER = drink(2).build();
        CRAPPY_WINE = drink(4).build();
        STRANGE_WINE = drink(2).build();
    }
    
    public static void init() {
        TsBrewcraft.LOGGER.debug("Registering FoodComponents");
    }
}
