package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.item.TsItems;
import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
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

    public static final IntProperty BINES_AGE;
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty BOTTOM;
    public static final EnumProperty<Direction.Axis> AXIS;
    
    // Bines age:
    //      0: no vines
    //      1: top of vine, budding plant if BOTTOM
    //      2: full block plant thin
    //      3: full block plant thick
    //      4: flowering plant
    
    protected static final VoxelShape X_AXIS_SHAPE;
    protected static final VoxelShape Z_AXIS_SHAPE;
    
    public TrellisBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)this.stateManager.getDefaultState()
                .with(AXIS, Direction.Axis.X)
                .with(WATERLOGGED, false)
                .with(BINES_AGE, 0)
                .with(BOTTOM, true)
        );
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
    
    // TODO: is opacity doing anything?
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 1;
    }
    
    // Cannot place on air
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP) || world.getBlockState(pos.down()).isOf(this);
        // TODO: Use this to destroy vines?
    }
    
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
    
        Direction.Axis axis = ctx.getPlayerFacing().getAxis();
        Boolean bottom = true;
        
        BlockState blockBelow = world.getBlockState(blockPos.down());
        
        // lock placement rotation to trellis below it
        if(blockBelow.isOf(this)){
            axis = blockBelow.get(Properties.AXIS);
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
        
        // TODO: all vines should cut when sheared (getstateforneighborupdate)
        
        BlockState blockBelow = world.getBlockState(pos.down());
        
        // do shearing action on right click
        if(player.getStackInHand(hand).isOf(Items.SHEARS) && state.get(BINES_AGE) >= 1){ // Player is holding shears and bine exists
            // drop items. theoretically this should come after bine is deleted
            if (state.get(BINES_AGE) == 4) {
                // bine is fully grown, drop all items
            } else {
                // bine is not fully grown
            }
            // delete bine
            world.setBlockState(pos, state.with(BINES_AGE, 0));
            // damage shears
            player.getStackInHand(hand).damage(2, player, (e) -> {
                e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
            });
            
            return ActionResult.success(world.isClient);
        }
        // plant vine or extend previous bine like bamboo
        else if (player.getStackInHand(hand).isOf(TsItems.HOPS_RHIZOME) &&      // Player is holding rhizome AND
                (state.get(BINES_AGE) == 0 &&                                   // there is no crop at this location AND
                    (state.get(BOTTOM) ||                                       // this is the bottom trellis OR
                        (blockBelow.isOf(this) &&                               // the block below is trellis AND
                                blockBelow.get(BINES_AGE) >= 1))                // the block below has old enough crop
        )){
            // Plant hops at position
            world.setBlockState(pos, state.with(BINES_AGE, 1));
            
            // Update age of lower hops
            if(blockBelow.isOf(this) && blockBelow.get(BINES_AGE) == 1){
                world.setBlockState(pos.down(), blockBelow.with(BINES_AGE, 2));
            }
            
            if(!player.isCreative()) player.getStackInHand(hand).decrement(1); // decrement if player is in survival
            
            return ActionResult.success(world.isClient);
        }
        // Bonemeal
        else if (player.getStackInHand(hand).isOf(Items.BONE_MEAL)){
            // TODO: create hop bine growth method
        }
        return ActionResult.FAIL;
    }
    
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{WATERLOGGED})
                .add(new Property[]{AXIS})
                .add(new Property[]{BINES_AGE})
                .add(BOTTOM);
    }
    
    static {
        BINES_AGE = Properties.AGE_4;
        WATERLOGGED = Properties.WATERLOGGED;
        AXIS = Properties.AXIS;
        BOTTOM = Properties.BOTTOM;
        X_AXIS_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
        Z_AXIS_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    }
}
