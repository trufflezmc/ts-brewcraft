package com.trufflez.tsbrewcraft.item;

import com.trufflez.tsbrewcraft.TsBrewcraft;
import com.trufflez.tsbrewcraft.block.TsBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class TsItemGroups {
    public static final ItemGroup MAIN = FabricItemGroupBuilder.build(new Identifier(TsBrewcraft.MOD_ID, "main"),
            () -> new ItemStack(TsBlocks.CASK));
    // Crops (seeds and products), beverages, equipment
}
