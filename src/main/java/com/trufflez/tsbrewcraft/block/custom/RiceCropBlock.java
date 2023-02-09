package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.block.TsBlocks;
import com.trufflez.tsbrewcraft.item.TsItems;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import static com.trufflez.tsbrewcraft.block.custom.CornCropBlockTall.HALF;

public class RiceCropBlock extends CropBlock implements Waterloggable {
    public static final int MAX_AGE = 3;
    public static final IntProperty AGE;
    public static final BooleanProperty WATERLOGGED;
    
    public RiceCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(this.getAgeProperty(), 0).with(WATERLOGGED, false));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        return (BlockState)this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
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
    public ItemConvertible getSeedsItem() {
        return TsItems.RICE;
    }
    
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.DIRT) || floor.isOf(Blocks.MUD);
        //return true;
    }
    
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            int i = this.getAge(state);
            boolean waterlogged = state.get(WATERLOGGED);
            if (i < 2) { // growth stages 0 and 1
                //float f = getAvailableMoisture(this, world, pos);
                if (random.nextInt(24) == 0) {
                    if (waterlogged) {
                        growRice(state, world, pos);
                    } else {
                        killRice(state, world, pos);
                    }
                }
            } else if (i < this.getMaxAge()) {
                if (random.nextInt(24) == 0) {
                    if (!waterlogged) {
                        growRice(state, world, pos);
                    } else {
                        killRice(state, world, pos);
                    }
                }
            }
        }
    }
    
    private void growRice(BlockState state,  ServerWorld world,  BlockPos pos) {
        world.setBlockState(pos, this.withAge(this.getAge(state) + 1).with(WATERLOGGED, state.get(WATERLOGGED)), 2);
    }
    
    private void killRice(BlockState state,  ServerWorld world,  BlockPos pos) {
        world.setBlockState(pos, TsBlocks.DEAD_RICE.getDefaultState().with(WATERLOGGED, state.get(WATERLOGGED)));
    }
    
    @Override
    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public IntProperty getAgeProperty() {
        return AGE;
    }
    
    public int getMaxAge() {
        return MAX_AGE;
    }

    protected int getAge(BlockState state) {
        return (Integer)state.get(this.getAgeProperty());
    }

    public BlockState withAge(int age) {
        return (BlockState)this.getDefaultState().with(this.getAgeProperty(), age);
    }
    
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, WATERLOGGED);
    }

    static {
        AGE = Properties.AGE_3;
        WATERLOGGED = Properties.WATERLOGGED;
    }
}
