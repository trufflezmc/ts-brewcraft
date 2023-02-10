package com.trufflez.tsbrewcraft.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class TrellisBlock extends PillarBlock implements Waterloggable {
    
    
    // TODO: add crop age states
    // TODO: make plantable on farmland
    
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty BOTTOM;
    public static final EnumProperty<Direction.Axis> AXIS;

    protected static final VoxelShape X_AXIS_SHAPE;
    protected static final VoxelShape Z_AXIS_SHAPE;
    
    public TrellisBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)this.stateManager.getDefaultState().with(AXIS, Direction.Axis.X));
    }
    
    // TODO: add culling shapes

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch((Direction.Axis)state.get(AXIS)) {
            case X:
            default:
                return X_AXIS_SHAPE;
            case Z:
                return Z_AXIS_SHAPE;
        }
    }
    
    // Break if cannot be placed here
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1);
        }
        
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    
    // Break automatically cannot be placed here
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 1;
    }
    
    // Cannot place on air
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP) || world.getBlockState(pos.down()).isOf(this);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();

        Direction.Axis axis = ctx.getPlayerFacing().getAxis();
        Boolean bottom = true;
        
        BlockState blockBelowState = world.getBlockState(blockPos.down());
        
        if(blockBelowState.isOf(this)){
            axis = blockBelowState.get(Properties.AXIS);
            bottom = false;
        }
        
        return (BlockState)this.getDefaultState()
                .with(WATERLOGGED, world.getFluidState(blockPos).getFluid() == Fluids.WATER)
                .with(AXIS, axis)
                .with(BOTTOM, bottom);
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // do shearing action on right click
        if(player.getStackInHand(hand).isOf(Items.SHEARS)){ // TODO: only make shearing action possible with hops crop
            return ActionResult.success(world.isClient);
        }
        return ActionResult.FAIL;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{WATERLOGGED})
                .add(new Property[]{AXIS})
                .add(BOTTOM);
    }
    
    static {
        WATERLOGGED = Properties.WATERLOGGED;
        AXIS = Properties.AXIS;
        BOTTOM = Properties.BOTTOM;
        X_AXIS_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
        Z_AXIS_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    }
}
