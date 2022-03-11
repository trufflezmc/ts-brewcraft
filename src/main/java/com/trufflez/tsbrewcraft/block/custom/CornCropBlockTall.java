package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.block.TsBlocks;
import com.trufflez.tsbrewcraft.item.TsItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class CornCropBlockTall extends TallPlantBlock implements Fertilizable {
    public CornCropBlockTall(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }
    
    /*@Override
    public ItemConvertible getSeedsItem() {
        return TsItems.CORN;
    }*/

    // TODO: this isn't even close to right
    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        this.grow(world, pos);
    }

    protected void grow(World world, BlockPos pos) { 
        world.setBlockState(pos.up(), (BlockState)
                        TsBlocks.CORN.getDefaultState(),
                3);
    }
}