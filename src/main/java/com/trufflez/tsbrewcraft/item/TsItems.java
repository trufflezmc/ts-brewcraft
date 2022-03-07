package com.trufflez.tsbrewcraft.item;

import com.trufflez.tsbrewcraft.TsBrewcraft;
import com.trufflez.tsbrewcraft.block.TsBlocks;
import com.trufflez.tsbrewcraft.item.custom.DrinkItem;
import com.trufflez.tsbrewcraft.item.custom.MaltovCocktail;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TsItems {
    public static final Item BARLEY;
    public static final Item PALE_MALT;
    public static final Item HOPS;
    public static final Item RICE;
    public static final Item STEAMED_RICE;
    public static final Item MOLDY_RICE;
    public static final Item GRAPES;
    public static final Item GRAPE_SEEDS;
    public static final Item CORN;
    public static final Item CORN_KERNELS;
    public static final Item AGAVE;
    
    public static final Item BEER;
    public static final Item WHEAT_BEER;
    public static final Item MALT_LIQUOR;
    public static final Item LAMBIC;
    public static final Item SAKE;
    public static final Item RED_WINE;
    public static final Item WHITE_WINE;
    public static final Item ROSE;
    public static final Item CHAMPAGNE;
    
    public static final Item RUM;
    public static final Item TEQUILA;
    public static final Item VODKA;
    public static final Item BRANDY;
    public static final Item WHISKEY;
    public static final Item BOURBON;
    public static final Item SHOCHU;
    public static final Item MOONSHINE;
    
    public static final Item VINEGAR;
    public static final Item CRAPPY_BEER;
    public static final Item CRAPPY_WINE;
    public static final Item STRANGE_WINE;
    
    public static final Item BEER_BOTTLE;
    public static final Item WINE_BOTTLE;
    public static final Item CORK;
    public static final Item CROWN_CAP;
    public static final Item MUSELET;
    public static final Item WINE_THIEF;

    public static final Item MALTOV_COCKTAIL;
    
    //private static BlockItem blockItem(Block block) { return new BlockItem(block, new FabricItemSettings().group(TsItemGroups.MAIN)); }
    
    // Takes "new" + custom item type
    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(TsBrewcraft.MOD_ID, id), item);
    }
    
    // Takes standard FoodComponent
    private static Item register(String id, FoodComponent item) {
        return Registry.register(Registry.ITEM, new Identifier(TsBrewcraft.MOD_ID, id), new Item(settings().food(item)));
    }
    
    // Takes DrinkItem FoodComponent
    private static Item register(String id, FoodComponent item, int uses, int strength) {
        return Registry.register(Registry.ITEM, new Identifier(TsBrewcraft.MOD_ID, id),
                new DrinkItem(uses, strength, settings().food(item)));
    }
    
    // Takes just the name
    private static Item register(String id) {
        return Registry.register(Registry.ITEM, new Identifier(TsBrewcraft.MOD_ID, id), new Item(settings()));
    }
    
    // Takes Block and makes BlockItem
    private static Item register(String id, Block block) {
        return Registry.register(Registry.ITEM, new Identifier(TsBrewcraft.MOD_ID, id), new BlockItem(block, settings()));
    }
    
    // I just don't like typing this out a million times
    private static Item.Settings settings() {
        return new FabricItemSettings().group(TsItemGroups.MAIN);
    }
    private static Item.Settings settings(ItemGroup group) {
        return new FabricItemSettings().group(group);
    }
    
    //private static BlockItem register(String id, BlockItem blockItem) { return Registry.register(Registry.ITEM, new Identifier(TsBrewcraft.MOD_ID, id), blockItem); }
    
    static {
        BARLEY = register("barley", TsBlocks.BARLEY);
        PALE_MALT = register("pale_malt");
        HOPS = register("hops");
        RICE = register("rice", TsBlocks.RICE);
        STEAMED_RICE = register("steamed_rice", TsConsumables.STEAMED_RICE);
        MOLDY_RICE = register("moldy_rice", TsConsumables.MOLDY_RICE);
        GRAPES = register("grapes", TsConsumables.GRAPES);
        GRAPE_SEEDS = register("grape_seeds");
        CORN = register("corn");
        CORN_KERNELS = register("corn_kernels");
        AGAVE = register("agave");
        
        BEER = register("beer", TsConsumables.BEER, 2, 5);
        WHEAT_BEER = register("wheat_beer", TsConsumables.WHEAT_BEER, 2, 7);
        MALT_LIQUOR = register("malt_liquor", TsConsumables.MALT_LIQUOR, 2, 7);
        LAMBIC = register("lambic", TsConsumables.LAMBIC, 2, 7);
        SAKE = register("sake", TsConsumables.SAKE, 4, 15);
        RED_WINE = register("red_wine", TsConsumables.RED_WINE, 4, 12);
        WHITE_WINE = register("white_wine", TsConsumables.WHITE_WINE, 4, 12);
        ROSE = register("rose", TsConsumables.ROSE, 4, 12);
        CHAMPAGNE = register("champagne", TsConsumables.CHAMPAGNE, 4, 14);
        RUM = register("rum", TsConsumables.RUM, 4, 60);
        TEQUILA = register("tequila", TsConsumables.TEQUILA, 4, 40);
        VODKA = register("vodka", TsConsumables.VODKA, 4, 70);
        BRANDY = register("brandy", TsConsumables.BRANDY, 4, 40);
        WHISKEY = register("whiskey", TsConsumables.WHISKEY, 4, 40);
        BOURBON = register("bourbon", TsConsumables.BOURBON, 4, 40);
        SHOCHU = register("shochu", TsConsumables.SHOCHU, 4, 40);
        MOONSHINE = register("moonshine", TsConsumables.MOONSHINE, 1, 90);
        
        VINEGAR = register("vinegar", TsConsumables.VINEGAR, 1, 0);
        CRAPPY_BEER = register("crappy_beer", TsConsumables.CRAPPY_BEER, 2, 3);
        CRAPPY_WINE = register("crappy_wine", TsConsumables.CRAPPY_WINE, 2, 12);
        STRANGE_WINE = register("strange_wine", TsConsumables.STRANGE_WINE, 2, 12);
        
        BEER_BOTTLE = register("beer_bottle");
        WINE_BOTTLE = register("wine_bottle");
        CORK = register("cork");
        CROWN_CAP = register("crown_cap");
        MUSELET = register("muselet");
        WINE_THIEF = register("wine_thief");
        
        MALTOV_COCKTAIL = register("maltov_cocktail", new MaltovCocktail(settings()));
    }
    
    public static void init() {
        TsBrewcraft.LOGGER.debug("Registering items");
    }
}
