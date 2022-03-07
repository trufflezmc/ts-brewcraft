package com.trufflez.tsbrewcraft.block;

import com.trufflez.tsbrewcraft.TsBrewcraft;
import com.trufflez.tsbrewcraft.block.custom.*;
import com.trufflez.tsbrewcraft.item.TsItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TsBlocks {

    public static final Block AGAVE;
    public static final Block BARLEY;
    public static final Block CORN;
    public static final Block GRAPE;
    public static final Block HOPS;
    public static final Block RICE;
    
    public static final Block KEG;
    public static final Block BARREL;
    
    public static final Block SULFUR_STICK;
    
    // Register shortcuts

    private static Block register(String id, Block block) {
        registerBlockItem(id, block);
        return Registry.register(Registry.BLOCK, new Identifier(TsBrewcraft.MOD_ID, id), block);
    }

    private static Block registerItemless(String id, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(TsBrewcraft.MOD_ID, id), block);
    }
    
    private static Item registerBlockItem(String id, Block block) {
        return Registry.register(Registry.ITEM, new Identifier(TsBrewcraft.MOD_ID, id),
                new BlockItem(block, new FabricItemSettings().group(TsItemGroups.MAIN)));
    }
    
    static {
        AGAVE = registerItemless("agave_plant", new Block(FabricBlockSettings.of(Material.LEAVES).nonOpaque().breakInstantly()));
        BARLEY = registerItemless("barley_plant", new BarleyCropBlock(FabricBlockSettings.copy(Blocks.WHEAT).nonOpaque().noCollision()));
        CORN = registerItemless("corn_plant", new Block(FabricBlockSettings.of(Material.LEAVES).nonOpaque().noCollision().breakInstantly()));
        GRAPE = registerItemless("grape_plant", new Block(FabricBlockSettings.of(Material.LEAVES).nonOpaque().strength(2.0f)));
        HOPS = registerItemless("hops_plant", new Block(FabricBlockSettings.copy(Blocks.WHEAT).nonOpaque().noCollision()));
        RICE = registerItemless("rice_plant", new RiceCropBlock(FabricBlockSettings.copy(Blocks.WHEAT).nonOpaque().noCollision()));
        
        KEG = register("keg", new KegBlock(FabricBlockSettings.of(Material.WOOD).strength(4.0f)));
        BARREL = register("barrel", new BarrelBlock(FabricBlockSettings.of(Material.WOOD).strength(4.0f)));

        SULFUR_STICK = register("sulfur_stick", new SulfurStick(FabricBlockSettings.of(Material.STONE).breakInstantly()));
    }
    
    public static void init() {
        TsBrewcraft.LOGGER.debug("Registering blocks");
    }
}
