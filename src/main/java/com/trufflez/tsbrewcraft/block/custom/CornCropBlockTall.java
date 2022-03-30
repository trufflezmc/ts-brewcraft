package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.block.TsBlocks;
import com.trufflez.tsbrewcraft.item.TsItems;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class CornCropBlockTall extends TallPlantBlock implements Fertilizable {
    public static final int MAX_AGE = 2;
    public static final IntProperty AGE;
    public static final EnumProperty<DoubleBlockHalf> HALF;

    public CornCropBlockTall(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(HALF, DoubleBlockHalf.LOWER).with(AGE, 0));
    }
    
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = (DoubleBlockHalf)state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y 
                && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)
                && (!(neighborState.isOf(this) || neighborState.isOf(TsBlocks.YOUNG_CORN)) || neighborState.get(HALF) == doubleBlockHalf)) {
            return Blocks.AIR.getDefaultState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER
                    && direction == Direction.DOWN
                    && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
        //return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        //return state;
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }
    
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
    
    @Override // identical to "getSeedsItem" extending CropBlock
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(TsItems.CORN);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int growthAmount = this.getAge(state) + MathHelper.nextInt(world.random, 2, 5);
        growthAmount = Math.max(growthAmount, MAX_AGE);
        
        setGrowth(world, pos, state, growthAmount);
    }
    
    private void setGrowth(ServerWorld world, BlockPos pos, BlockState state, int growthAmount) {
        if (this.getAge(state) < MAX_AGE && state.get(HALF) == DoubleBlockHalf.LOWER) {
            world.setBlockState(pos, this.withAge(growthAmount), 2); // flags was 3
            world.setBlockState(pos.up(1), this.withAge(growthAmount).with(HALF, DoubleBlockHalf.UPPER), 2);
        }
    }

    // This has protected access, so we have to remake it here
    protected static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockPos = pos.down();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float g = 0.0F;
                BlockState blockState = world.getBlockState(blockPos.add(i, 0, j));
                if (blockState.isOf(Blocks.FARMLAND)) {
                    g = 1.0F;
                    if ((Integer)blockState.get(FarmlandBlock.MOISTURE) > 0) {
                        g = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    g /= 4.0F;
                }

                f += g;
            }
        }
        
        // This is the logic for making crops grow slower if they're not planted in rows.
        
        /*BlockPos blockPos2 = pos.north();
        BlockPos blockPos3 = pos.south();
        BlockPos blockPos4 = pos.west();
        BlockPos blockPos5 = pos.east();
        boolean bl = world.getBlockState(blockPos4).isOf(block) || world.getBlockState(blockPos5).isOf(block);
        boolean bl2 = world.getBlockState(blockPos2).isOf(block) || world.getBlockState(blockPos3).isOf(block);
        if (bl && bl2) {
            f /= 2.0F;
        } else {
            boolean bl3 = world.getBlockState(blockPos4.north()).isOf(block) || world.getBlockState(blockPos5.north()).isOf(block) || world.getBlockState(blockPos5.south()).isOf(block) || world.getBlockState(blockPos4.south()).isOf(block);
            if (bl3) {
                f /= 2.0F;
            }
        }*/

        return f;
    }
    
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getAvailableMoisture(this, world, pos);
                if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                    this.setGrowth(world, pos, state, i + 1);
                }
            }
        }
    }
    
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < MAX_AGE; // Tick only when able to tick
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
        builder.add(AGE, HALF);
    }
    
    static {
        AGE = Properties.AGE_2;
        HALF = Properties.DOUBLE_BLOCK_HALF;
    }
}
