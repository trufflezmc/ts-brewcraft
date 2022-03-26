package com.trufflez.tsbrewcraft.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.List;

public class Tooltips {
    
    public static TranslatableText tooltip(String item) {
        return (TranslatableText) new TranslatableText("tooltip.tsbrewcraft." + item).formatted(Formatting.GRAY);
    }
    
    public static void addThis(ItemStack stack, List<Text> tooltip){
        Item i = stack.getItem();
        
        if(i==TsItems.BEER) tooltip.add(tooltip("beer"));
        if(i==TsItems.WHEAT_BEER) tooltip.add(tooltip("wheat_beer"));
        if(i==TsItems.MALT_LIQUOR) tooltip.add(tooltip("malt_liquor"));
        if(i==TsItems.LAMBIC) tooltip.add(tooltip("lambic"));
        if(i==TsItems.SAKE) tooltip.add(tooltip("sake"));
        if(i==TsItems.RED_WINE) tooltip.add(tooltip("red_wine"));
        if(i==TsItems.WHITE_WINE) tooltip.add(tooltip("white_wine"));
        if(i==TsItems.ROSE) tooltip.add(tooltip("rose"));
        if(i==TsItems.CHAMPAGNE) tooltip.add(tooltip("champagne"));
        if(i==TsItems.RUM) tooltip.add(tooltip("rum"));
        if(i==TsItems.TEQUILA) tooltip.add(tooltip("tequila"));
        if(i==TsItems.VODKA) tooltip.add(tooltip("vodka"));
        if(i==TsItems.BRANDY) tooltip.add(tooltip("brandy"));
        if(i==TsItems.WHISKEY) tooltip.add(tooltip("whiskey"));
        if(i==TsItems.BOURBON) tooltip.add(tooltip("bourbon"));
        if(i==TsItems.SHOCHU) tooltip.add(tooltip("shochu"));
        if(i==TsItems.MOONSHINE) {
            tooltip.add(tooltip("moonshine"));
            tooltip.add(tooltip("moonshine2"));
        }
        if(i==TsItems.KEFIR) tooltip.add(tooltip("kefir"));
        if(i==TsItems.VINEGAR) tooltip.add(tooltip("vinegar"));
        if(i==TsItems.CRAPPY_BEER) tooltip.add(tooltip("crappy_beer"));
        if(i==TsItems.CRAPPY_WINE) tooltip.add(tooltip("crappy_wine"));
        if(i==TsItems.STRANGE_WINE) tooltip.add(tooltip("strange_wine"));
        
        if(i==TsItems.BARLEY) tooltip.add(tooltip("barley"));
        if(i==TsItems.PALE_MALT) tooltip.add(tooltip("pale_malt"));
        if(i==TsItems.HOPS) tooltip.add(tooltip("hops"));
        if(i==TsItems.RICE) tooltip.add(tooltip("rice"));
        if(i==TsItems.STEAMED_RICE) tooltip.add(tooltip("steamed_rice"));
        if(i==TsItems.MOLDY_RICE) tooltip.add(tooltip("moldy_rice"));
        if(i==TsItems.GRAPES) tooltip.add(tooltip("grapes"));
        if(i==TsItems.CORN) tooltip.add(tooltip("corn"));
        if(i==TsItems.AGAVE) tooltip.add(tooltip("agave"));
        
        if(i==TsItems.MALTOV_COCKTAIL) tooltip.add(tooltip("maltov_cocktail"));
    }
}
