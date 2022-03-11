package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.item.TsItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class RiceCropBlock extends CropBlock {
    public static final int MAX_AGE = 3;
    public static final IntProperty AGE;
    
    public RiceCropBlock(Settings settings) { super(settings); }
    
    @Override
    public ItemConvertible getSeedsItem() {
        return TsItems.RICE;
    }
    
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.DIRT);
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }
    
    // May need to override getAvailableMoisture
    
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return true;
    }
    
    static {
        AGE = Properties.AGE_3;
    }
}
