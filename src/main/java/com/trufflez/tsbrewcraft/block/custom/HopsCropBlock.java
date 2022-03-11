package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.item.TsItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class HopsCropBlock extends CropBlock {
    public static final int MAX_AGE = 2;
    public static final IntProperty AGE;
    
    public HopsCropBlock(Settings settings) { super(settings); }
    
    @Override
    public ItemConvertible getSeedsItem() {
        return TsItems.HOPS;
    }
    
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.DIRT) ||
                floor.isOf(Blocks.GRASS_BLOCK) ||
                floor.isOf(Blocks.PODZOL) ||
                floor.isOf(Blocks.MYCELIUM) ||
                floor.isOf(Blocks.COARSE_DIRT) ||
                floor.isOf(Blocks.ROOTED_DIRT) ||
                floor.isOf(Blocks.MOSS_BLOCK);
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }
    
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return true;
    }
    
    static {
        AGE = Properties.AGE_2;
    }
}
