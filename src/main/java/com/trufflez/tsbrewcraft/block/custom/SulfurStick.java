package com.trufflez.tsbrewcraft.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;

import java.util.Random;

public class SulfurStick extends Block {
    public static final BooleanProperty LIT;
    
    protected static final VoxelShape BOUNDING_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);

    public SulfurStick(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false));
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!state.get(LIT)) {
            ItemStack itemStack = player.getStackInHand(hand);
            
            if (hand == Hand.MAIN_HAND && !canLightWith(itemStack) && canLightWith(player.getStackInHand(Hand.OFF_HAND))) {
                
                return ActionResult.PASS; // I don't understand what this does
                
            } else if (canLightWith(itemStack)) {
                
                // Light the block
                
                if(state.getProperties().contains(WallSulfurStick.FACING)){
                    world.setBlockState(pos, this.getDefaultState().with(LIT, true).with(WallSulfurStick.FACING, state.get(WallSulfurStick.FACING)));
                } else {
                    world.setBlockState(pos, this.getDefaultState().with(LIT, true));
                }
                
                // Consume item if fire charge
                
                if (!player.getAbilities().creativeMode && itemStack.isOf(Items.FIRE_CHARGE)) {
                    itemStack.decrement(1);
                }

                return ActionResult.success(world.isClient);

            } else {
                
                if (!world.isClient) {
                    world.playSound(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return ActionResult.SUCCESS;
                }

                return ActionResult.SUCCESS;
            }
        } else {
            return ActionResult.success(false); // Do not swing hand
        }
    }

    private static boolean canLightWith(ItemStack stack) {
        return stack.isOf(Items.FLINT_AND_STEEL) || stack.isOf(Items.FIRE_CHARGE) || stack.isOf(Items.TORCH);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(LIT);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient) {
            world.playSound(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }

        double d = (double) pos.getX() + 0.0D;
        double e = (double) pos.getY() + 0.0D;
        double f = (double) pos.getZ() + 0.0D;
        
        world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);

        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
    }
    
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BOUNDING_SHAPE;
    }
    
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            double d = (double) pos.getX() + 0.5D;
            double e = (double) pos.getY() + 0.7D;
            double f = (double) pos.getZ() + 0.5D;
            world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.FLAME, d, e, f, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    static {
        LIT = RedstoneTorchBlock.LIT;
    }
}
