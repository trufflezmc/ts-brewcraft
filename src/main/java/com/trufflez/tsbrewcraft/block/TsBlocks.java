package com.trufflez.tsbrewcraft.block;

import com.trufflez.tsbrewcraft.TsBrewcraft;
import com.trufflez.tsbrewcraft.block.custom.*;
import com.trufflez.tsbrewcraft.item.TsItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TsBlocks {
    
    public static final Block KEG;
    public static final Block BARREL;

    public static final Block SULFUR_STICK;
    
    // Register shortcuts

    private static Block register(String id, Block block) {
        registerBlockItem(id, block);
        return Registry.register(Registry.BLOCK, new Identifier(TsBrewcraft.MOD_ID, id), block);
    }

    private static Item registerBlockItem(String id, Block block) {
        return Registry.register(Registry.ITEM, new Identifier(TsBrewcraft.MOD_ID, id),
                new BlockItem(block, new FabricItemSettings().group(TsItemGroups.MAIN)));
    }
    
    static {
        KEG = register("keg", new KegBlock(FabricBlockSettings.of(Material.WOOD).strength(4.0f)));
        BARREL = register("barrel", new BarrelBlock(FabricBlockSettings.of(Material.WOOD).strength(4.0f)));

        SULFUR_STICK = register("barrel", new SulfurStick(FabricBlockSettings.of(Material.WOOD).strength(4.0f)));
    }
    
    public static void init() {
        TsBrewcraft.LOGGER.debug("Registering blocks");
    }
}