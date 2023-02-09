package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.block.TsBlocks;
import com.trufflez.tsbrewcraft.item.TsItems;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class DeadCropBlock extends Block implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;
    
    public DeadCropBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override // identical to "getSeedsItem" extending CropBlock
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        Item i = Items.AIR; // null item
        
        Block b = state.getBlock();
        
        if (b == TsBlocks.DEAD_RICE)    i = TsItems.RICE;
        if (b == TsBlocks.DEAD_AGAVE)   i = TsItems.AGAVE;
        if (b == TsBlocks.DEAD_BARLEY)  i = TsItems.BARLEY;
        if (b == TsBlocks.DEAD_CORN)    i = TsItems.CORN;
        if (b == TsBlocks.DEAD_GRAPE)   i = TsItems.GRAPE_SEEDS;
        if (b == TsBlocks.DEAD_HOPS)    i = TsItems.HOPS;
                
        return new ItemStack(i);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState(); // break block if invalid placement
        } else { // if valid, 
            if ((Boolean)state.get(WATERLOGGED)) { // update water tick if waterlogged
                world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            }

            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
    }
}
