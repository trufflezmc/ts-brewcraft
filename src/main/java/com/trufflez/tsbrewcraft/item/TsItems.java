package com.trufflez.tsbrewcraft.item;

import com.trufflez.tsbrewcraft.TsBrewcraft;
import com.trufflez.tsbrewcraft.block.TsBlocks;
import com.trufflez.tsbrewcraft.item.custom.DrinkItem;
import com.trufflez.tsbrewcraft.item.custom.MaltovCocktail;
import com.trufflez.tsbrewcraft.item.patches.TsAliasedBlockItem;
import com.trufflez.tsbrewcraft.item.patches.TsItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class TsItems {
    public static final Item AGAVE;
    public static final Item BARLEY;
    public static final Item MALT;
    //public static final Item DARK_MALT;
    public static final Item HOPS;
    public static final Item HOPS_RHIZOME;
    public static final Item RICE;
    public static final Item STEAMED_RICE;
    public static final Item MOLDY_RICE;
    public static final Item GRAPES;
    public static final Item GRAPE_SEEDS;
    public static final Item CORN;
    public static final Item STEAMED_CORN;
    public static final Item CORN_SUGAR;
        
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

    public static final Item KEFIR;
    public static final Item VINEGAR;
    public static final Item CRAPPY_BEER;
    public static final Item CRAPPY_WINE;
    public static final Item STRANGE_WINE;
    
    public static final Item BEER_BOTTLE;
    public static final Item WINE_BOTTLE;
    public static final Item SPIRITS_BOTTLE;
    public static final Item CORK;
    public static final Item CROWN_CAP;
    public static final Item MUSELET;
    public static final Item WINE_THIEF;

    public static final Item MALTOV_COCKTAIL;
    
    // BlockItems, registered here for tooltips

    public static final Item KEG;
    public static final Item TRELLIS;
    public static final Item SULFUR_STICK;
    
    //private static BlockItem blockItem(Block block) { return new BlockItem(block, new FabricItemSettings().group(TsItemGroups.MAIN)); }
    
    // Takes "new" + custom item type
    private static Item register(String id, Item item) {
        addToItemGroup(TsItemGroups.MAIN, item);
        return Registry.register(Registries.ITEM, new Identifier(TsBrewcraft.MOD_ID, id), item);
    }
    
    // Takes standard FoodComponent
    private static Item register(String id, FoodComponent item) {
        Item foodItem = Registry.register(Registries.ITEM, new Identifier(TsBrewcraft.MOD_ID, id), new TsItem(settings().food(item)));
        addToItemGroup(TsItemGroups.MAIN, foodItem);
        return foodItem;
    }
    
    // Takes DrinkItem FoodComponent
    private static Item register(String id, FoodComponent item, int uses, int strength) {
        Item drinkItem = Registry.register(Registries.ITEM, new Identifier(TsBrewcraft.MOD_ID, id),
                new DrinkItem(uses, strength, settings().food(item)));
        addToItemGroup(TsItemGroups.MAIN, drinkItem);
        return drinkItem;
    }
    
    // Takes just the name
    private static Item register(String id) {
        Item item = Registry.register(Registries.ITEM, new Identifier(TsBrewcraft.MOD_ID, id), new TsItem(settings()));
        addToItemGroup(TsItemGroups.MAIN, item);
        return item;
    }
    
    // Takes Block and makes BlockItem
    private static Item register(String id, Block block) {
        Item item = Registry.register(Registries.ITEM, new Identifier(TsBrewcraft.MOD_ID, id), new BlockItem(block, settings()));
        addToItemGroup(TsItemGroups.MAIN, item);
        return item;
    }
    
    // I just don't like typing this out a million times
    private static Item.Settings settings() {
        return new FabricItemSettings();
    }
//    private static Item.Settings settings(ItemGroup group) {
//        // TODO (mc1.19.3): add to item group
//        return new FabricItemSettings();
//    }
    
    
    public static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }
    
    //private static BlockItem register(String id, BlockItem blockItem) { return Registry.register(Registry.ITEM, new Identifier(TsBrewcraft.MOD_ID, id), blockItem); }
    
    static {
        AGAVE = register("agave", new TsAliasedBlockItem(TsBlocks.AGAVE, settings())); // TODO: make agave edible
        BARLEY = register("barley", new TsAliasedBlockItem(TsBlocks.BARLEY, settings()));
        MALT = register("malt"); // TODO: make malt edible and give achievement
        //DARK_MALT = register("dark_malt");
        HOPS = register("hops"); // TODO: make hops edible
        HOPS_RHIZOME = register("hops_rhizome"); // TODO: make rhizome edible
        RICE = register("rice", new TsAliasedBlockItem(TsBlocks.RICE, settings()));
        STEAMED_RICE = register("steamed_rice", new TsAliasedBlockItem(TsBlocks.STEAMED_RICE, settings().food(TsConsumables.STEAMED_RICE)));
        MOLDY_RICE = register("moldy_rice", new TsAliasedBlockItem(TsBlocks.MOLDY_RICE, settings().food(TsConsumables.MOLDY_RICE))); // TODO: give mrice status effect
        GRAPES = register("grapes", TsConsumables.GRAPES);
        GRAPE_SEEDS = register("grape_seeds", new TsAliasedBlockItem(TsBlocks.GRAPE, settings()));
        CORN = register("corn", new TsAliasedBlockItem(TsBlocks.YOUNG_CORN, settings()));
        STEAMED_CORN = register("steamed_corn", TsConsumables.STEAMED_CORN);
        CORN_SUGAR = register("corn_sugar");
                
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

        KEFIR = register("kefir", TsConsumables.KEFIR, 3, 0);
        VINEGAR = register("vinegar", TsConsumables.VINEGAR, 1, 0);
        CRAPPY_BEER = register("crappy_beer", TsConsumables.CRAPPY_BEER, 2, 3);
        CRAPPY_WINE = register("crappy_wine", TsConsumables.CRAPPY_WINE, 2, 12);
        STRANGE_WINE = register("strange_wine", TsConsumables.STRANGE_WINE, 2, 12);
        
        BEER_BOTTLE = register("beer_bottle");
        WINE_BOTTLE = register("wine_bottle");
        SPIRITS_BOTTLE = register("spirits_bottle");
        CORK = register("cork");
        CROWN_CAP = register("crown_cap");
        MUSELET = register("muselet");
        WINE_THIEF = register("wine_thief");
        
        MALTOV_COCKTAIL = register("maltov_cocktail", new MaltovCocktail(settings()));
        
        // BlockItems, registered here for tooltips
        
        KEG = register("keg", TsBlocks.KEG);
        TRELLIS = register("trellis", TsBlocks.TRELLIS);
        SULFUR_STICK = register("sulfur_stick", new VerticallyAttachableBlockItem(TsBlocks.SULFUR_STICK, TsBlocks.WALL_SULFUR_STICK, settings(), Direction.DOWN));
    }
    
    // TODO (mc1.19.3): create massive method here for adding things to the itemgroup
    
    public static void init() {
        TsBrewcraft.LOGGER.debug("Registering items");
    }
}
