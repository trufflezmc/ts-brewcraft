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
        
        BEER = drink(2).build();
        WHEAT_BEER = drink(2).build();
        LAMBIC = drink(2).build();
        SAKE = drink(2).build();
        RED_WINE = drink(2).build();
        WHITE_WINE = drink(2).build();
        ROSE = drink(2).build();
        CHAMPAGNE = drink(2).build();
        RUM = drink(2).build();
        TEQUILA = drink(2).build();
        VODKA = drink(2).build();
        BRANDY = drink(2).build();
        WHISKEY = drink(2).build();
        BOURBON = drink(2).build();
        SHOCHU = drink(2).build();
        MOONSHINE = drink(2).build();
        VINEGAR = drink(2).build();
    }
    
    public static void init() {
        TsBrewcraft.LOGGER.debug("Registering FoodComponents");
    }
}