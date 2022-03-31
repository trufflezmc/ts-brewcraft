package com.trufflez.tsbrewcraft.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class CaskBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final EnumProperty<WallMountLocation> FACE;
    public static final DirectionProperty FACING;
    public static BooleanProperty OPEN;
    
    public CaskBlock(Settings settings) {
        super(settings);
    }
    
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlaceAt(world, pos, getDirection(state).getOpposite());
    }


    public static boolean canPlaceAt(WorldView world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction.getOpposite());
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction[] var2 = ctx.getPlacementDirections();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Direction direction = var2[var4];
            BlockState blockState;
            if (direction.getAxis() == Direction.Axis.Y) {
                blockState = this.getDefaultState().with(FACE, direction == Direction.UP ? WallMountLocation.CEILING : WallMountLocation.FLOOR).with(FACING, ctx.getPlayerFacing());
            } else {
                blockState = this.getDefaultState().with(FACE, WallMountLocation.WALL).with(FACING, direction.getOpposite());
            }

            if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                return blockState;
            }
        }

        return null;
    }

    private static Direction getDirection(BlockState state) {
        switch(state.get(FACE)) {
            case CEILING:
                return Direction.DOWN;
            case FLOOR:
                return Direction.UP;
            default:
                return state.get(FACING);
        }
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
    /*@Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    }*/

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            
            if (blockEntity instanceof CaskBlockEntity caskBlockEntity) {
                ItemStack item = player.getStackInHand(hand);
                
                if (item.isEmpty()) { // empty hand
                    boolean opened = caskBlockEntity.getOpenedState();
                    
                    if (opened) {
                        caskBlockEntity.setClosed();
                    } else {
                        caskBlockEntity.setOpened();
                        
                        if (state.get(FACE) == WallMountLocation.CEILING) {

                        }
                    }
                    
                } else {
                    
                    if (caskBlockEntity.addItem(item)) {
                        item.decrement(1);
                    } else {
                        player.sendSystemMessage(new TranslatableText("message.tsbrewcraft.caskfull").formatted(Formatting.GOLD), Util.NIL_UUID);
                    }
                }
            }
        }

        if (world.isClient) {
            //TsBrewcraft.LOG.info("Block right-clicked");
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) { // check block is destroyed?
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CaskBlockEntity caskBlockEntity) {
                // This would drop items if this contained items, but it doesn't
                // Instead, we should check nbt data and drop liquid if it has it
                //ItemScatterer.spawn(world, pos, (Inventory) blockEntity);

                caskBlockEntity.dropItems(world, pos, state, caskBlockEntity);
                
                
                
                // update comparators
                world.updateComparators(pos,this);
            }
            
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
    
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, FACE, OPEN);
    }
    
    static {
        FACE = WallMountedBlock.FACE;
        FACING = HorizontalFacingBlock.FACING;
        OPEN = Properties.OPEN;
    }
}
