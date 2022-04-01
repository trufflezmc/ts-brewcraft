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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TsBlocks {

    public static final Block AGAVE;
    public static final Block BARLEY;
    public static final Block CORN;
    public static final Block YOUNG_CORN;
    public static final Block GRAPE;
    public static final Block HOPS;
    public static final Block RICE;

    public static final Block DEAD_AGAVE;
    public static final Block DEAD_BARLEY;
    public static final Block DEAD_CORN;
    public static final Block DEAD_GRAPE;
    public static final Block DEAD_HOPS;
    public static final Block DEAD_RICE;

    public static final Block STEAMED_RICE;
    public static final Block MOLDY_RICE;
        
    public static final Block KEG;
        
    public static final Block SULFUR_STICK;
    public static final Block WALL_SULFUR_STICK;
    
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
        AGAVE = registerItemless("agave_plant", new Block(FabricBlockSettings.of(Material.PLANT).nonOpaque().breakInstantly()));
        BARLEY = registerItemless("barley_plant", new BarleyCropBlock(FabricBlockSettings.copy(Blocks.WHEAT).nonOpaque().noCollision()));
        CORN = registerItemless("corn_plant", new CornCropBlockTall(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision().breakInstantly().ticksRandomly()));
        YOUNG_CORN = registerItemless("young_corn_plant", new CornCropBlock(FabricBlockSettings.copy(Blocks.WHEAT).nonOpaque().noCollision().breakInstantly().ticksRandomly()));
        GRAPE = registerItemless("grape_plant", new Block(FabricBlockSettings.of(Material.LEAVES).nonOpaque().strength(4.0f)));
        HOPS = registerItemless("hops_plant", new HopsCropBlock(FabricBlockSettings.of(Material.LEAVES).nonOpaque().strength(2.0f)));
        RICE = registerItemless("rice_plant", new RiceCropBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision()));

        DEAD_AGAVE = registerItemless("dead_agave", new DeadCropBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision()));
        DEAD_BARLEY = registerItemless("dead_barley", new DeadCropBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision()));
        DEAD_CORN = registerItemless("dead_corn", new DeadCropBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision()));
        DEAD_GRAPE = registerItemless("dead_grape", new DeadCropBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision()));
        DEAD_HOPS = registerItemless("dead_hops", new DeadCropBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision()));
        DEAD_RICE = registerItemless("dead_rice", new DeadCropBlock(FabricBlockSettings.of(Material.PLANT).nonOpaque().noCollision()));

        STEAMED_RICE = registerItemless("steamed_rice", new SteamedRiceBlock(FabricBlockSettings.of(Material.WOOD).nonOpaque().breakInstantly().ticksRandomly()));
        MOLDY_RICE = registerItemless("moldy_rice", new Block(FabricBlockSettings.of(Material.WOOD).nonOpaque().breakInstantly()));
        
        KEG = registerItemless("keg", new KegBlock(FabricBlockSettings.of(Material.WOOD).strength(4.0f)));
        
        SULFUR_STICK = registerItemless("sulfur_stick", new SulfurStick(FabricBlockSettings.of(Material.WOOD).nonOpaque().noCollision().breakInstantly()));
        WALL_SULFUR_STICK = registerItemless("wall_sulfur_stick", new WallSulfurStick(FabricBlockSettings.of(Material.WOOD).nonOpaque().noCollision().breakInstantly()));
    }
    
    public static void init() {
        TsBrewcraft.LOGGER.debug("Registering blocks");
    }
}
