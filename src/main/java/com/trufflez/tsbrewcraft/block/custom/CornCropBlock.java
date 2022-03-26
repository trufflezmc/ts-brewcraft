package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.block.TsBlocks;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

import static com.trufflez.tsbrewcraft.block.custom.CornCropBlockTall.HALF;

public class CornCropBlock extends CropBlock implements Fertilizable {
    public static final int MAX_AGE = 2;
    public static final IntProperty AGE;
    
    public CornCropBlock(Settings settings) { super(settings); }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.up()).isAir();
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.up()).isAir();
    }
    
    /*@Override
    public ItemConvertible getSeedsItem() {
        return TsItems.CORN;
    }*/

    /*@Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        this.grow(world, pos);
    }
    
    protected void grow(World world, BlockPos pos) {
        world.setBlockState(pos, (BlockState)
                        TsBlocks.CORN.getDefaultState(),
                3);
    }*/

    @Override // Overriding so "fully grown" block can advance to next growth stage. TODO: optimize for tick lag
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }
    
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getAvailableMoisture(this, world, pos);
                if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                    world.setBlockState(pos, this.withAge(i + 1), 2);
                    //world.setBlockState(pos.up(1), this.withAge(i + 1), 2);
                }
            } else {
                if (random.nextInt((int)(25.0F / 4) + 1) == 0) { // f->4
                    world.setBlockState(pos, TsBlocks.CORN.getDefaultState(), 2);
                    world.setBlockState(pos.up(), TsBlocks.CORN.getDefaultState().with(HALF, DoubleBlockHalf.UPPER), 2);
                }
            }
        }
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
        builder.add(AGE);
    }
    
    static {
        AGE = Properties.AGE_2;
    }
}
