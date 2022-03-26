package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.block.TsBlocks;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class SteamedRiceBlock extends Block {
    //public static final BooleanProperty MOLD;
    
    public SteamedRiceBlock(Settings settings) {
        super(settings);
        //this.setDefaultState(this.stateManager.getDefaultState().with(this.getMold(), false));
    }

    //public boolean hasRandomTicks(BlockState state) {
        //return !this.isMoldy(state);
    //}
    
    //protected boolean isMoldy(BlockState state) {
    //    return state.get(this.getMold());
    //}
    
    //public BooleanProperty getMold() {
     //   return MOLD;
    //}
    
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        //if (!this.isMoldy(state)) {
            if (random.nextInt((int) (25.0F / 4) + 1) == 0) { // f = 4
                world.setBlockState(pos, TsBlocks.MOLDY_RICE.getDefaultState());
            }
        //}
    }
    
    //protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//        builder.add(MOLD);
   // }
    
    //static {
    //    MOLD = BooleanProperty.of("mold");
   // }
}
