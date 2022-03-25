package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.block.TsBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class CaskBlockEntity extends BlockEntity {
    public CaskBlockEntity(BlockPos pos, BlockState state) {
        super(TsBlockEntities.CASK_BLOCKENTITY, pos, state);
    }
    
    
    
    
    
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }
    
    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        //return nbt;
    }
}
