package com.trufflez.tsbrewcraft.item;

import com.trufflez.tsbrewcraft.TsBrewcraft;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TsItems {
    public static final Item BARLEY;
    public static final Item KILNED_BARLEY;
    public static final Item HOPS;
    
    public static final Item RICE;
    public static final Item STEAMED_RICE;
    public static final Item MOLDY_RICE;

    public static final Item GRAPES;
    
    public static final Item BEER;
    public static final Item WHEAT_BEER;
    public static final Item LAMBIC;
    public static final Item SAKE;
    public static final Item RED_WINE;
    public static final Item WHITE_WINE;
    public static final Item ROSE;
    public static final Item CHAMPANGE;
    public static final Item RUM;
    public static final Item TEQUILA;
    public static final Item VODKA;
    public static final Item BRANDY;
    public static final Item WHISKEY;
    public static final Item BOURBON;
    public static final Item SHOCHU;
    public static final Item MOONSHINE;

    public static final Item VINEGAR;
    
    //private static BlockItem blockItem(Block block) { return new BlockItem(block, new FabricItemSettings().group(TsItemGroups.MAIN)); }

    private static Item register(String id, Item item) { return Registry.register(Registry.ITEM, new Identifier(TsBrewcraft.MOD_ID, id), item); }
    //private static BlockItem register(String id, BlockItem blockItem) { return Registry.register(Registry.ITEM, new Identifier(TsBrewcraft.MOD_ID, id), blockItem); }
    
    static {
        BARLEY = register("barley", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        KILNED_BARLEY = register("kilned_barley", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        HOPS = register("hops", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));

        RICE = register("rice", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        STEAMED_RICE = register("steamed_rice", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        MOLDY_RICE = register("moldy_rice", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));

        GRAPES = register("grapes", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        
        BEER = register("beer", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        WHEAT_BEER = register("wheat_beer", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        LAMBIC = register("lambic", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        SAKE = register("sake", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        RED_WINE = register("red_wine", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        WHITE_WINE = register("white_wine", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        ROSE = register("rose", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        CHAMPANGE = register("champange", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        RUM = register("rum", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        TEQUILA = register("tequila", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        VODKA = register("vodka", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        BRANDY = register("brandy", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        WHISKEY = register("whiskey", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        BOURBON = register("bourbon", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        SHOCHU = register("shochu", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
        MOONSHINE = register("moonshine", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));

        VINEGAR = register("vinegar", new Item(new FabricItemSettings().group(TsItemGroups.MAIN)));
    }
    
    public static void init() {
        TsBrewcraft.LOGGER.debug("Registering items");
    }
}
