package com.trufflez.tsbrewcraft.item;

import com.trufflez.tsbrewcraft.TsBrewcraft;
import com.trufflez.tsbrewcraft.block.TsBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TsItemGroups {
    
    public static ItemGroup MAIN;
    
    public static void registerItemGroup(){
        MAIN = FabricItemGroup.builder(new Identifier(TsBrewcraft.MOD_ID, "main"))
                .displayName(Text.literal("T's Brewcraft"))
                .icon(() -> new ItemStack(TsItems.HOPS)).build();
    }
    
   // = new FabricItemGroup(new Identifier(TsBrewcraft.MOD_ID, "main"));
//            .icon(() -> new ItemStack(TsItems.HOPS))
//            .entries((enabledFeatures, entries, operatorEnabled) -> {
//                entries.add(TsBlocks.KEG);
//            })
//            .build();
            //() -> new ItemStack(TsBlocks.KEG));
    // Crops (seeds and products), beverages, equipment
    
    
}
