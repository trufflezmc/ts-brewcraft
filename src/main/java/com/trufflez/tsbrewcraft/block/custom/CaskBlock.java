package com.trufflez.tsbrewcraft.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CaskBlock extends BlockWithEntity implements BlockEntityProvider {


    public CaskBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CaskBlockEntity(pos, state);
    }
    
    // archaic and possibly unnecessary code
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    
    // not correct
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            // do what we need to propagate on multiplayer
            
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CaskBlockEntity) {
                
            }
        }

        if (world.isClient) {
            //TsBrewcraft.LOG.info("Block right-clicked");
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) { // check block is destroyed?
            // this is how we get the inventory of the block entity
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CaskBlockEntity) {
                // This would drop items if this contained items, but it doesn't
                // Instead, we should check nbt data and drop liquid if it has it
                //ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                
                
                
                // update comparators
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
}
