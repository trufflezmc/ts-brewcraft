package com.trufflez.tsbrewcraft.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
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
import org.jetbrains.annotations.Nullable;

public class KegBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING;
    public static BooleanProperty SULFUR;
    public static BooleanProperty SEALED;
    
    public KegBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(FACING, Direction.UP)
                .with(SULFUR, false)
                .with(SEALED, false));
    }
    
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new KegBlockEntity(pos, state);
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
            
            if (blockEntity instanceof KegBlockEntity kegBlockEntity) {
                ItemStack item = player.getStackInHand(hand);
                
                if (item.isEmpty()) { // Seal and unseal keg
                    boolean sealed = kegBlockEntity.isSealed();
                    
                    if (!sealed) {
                        kegBlockEntity.seal();
                    } else {
                        kegBlockEntity.unseal();
                        
                        if (state.get(FACING) == Direction.DOWN) {
                            if (kegBlockEntity.hasProduct()) {
                                if (world.getBlockState(pos.down()).isAir()) {
                                    dumpContents(world, pos.down(), kegBlockEntity);
                                    
                                }
                            }
                        }
                    }
                    
                } else if (SulfurStick.canLightWith(item)) { // Attempt to light the keg.
                    if (!player.isCreative() && item.isOf(Items.FIRE_CHARGE)) {
                        item.decrement(1);
                    }
                    lightKeg(world, pos, state);
                } else { // Put the held item into the keg
                    
                    if (kegBlockEntity.addItem(item)) {
                        if (!player.isCreative()) {
                            item.decrement(1);
                        }
                    } else {
                        player.sendSystemMessage(new TranslatableText("message.tsbrewcraft.kegfull").formatted(Formatting.GOLD), Util.NIL_UUID);
                    }
                }
            }
        }

        if (world.isClient) {
            //TsBrewcraft.LOG.info("Block right-clicked");
        }

        return ActionResult.SUCCESS;
    }
    
    public void dumpContents(World world, BlockPos pos, KegBlockEntity kegBlockEntity) {
        if (kegBlockEntity.hasProduct()) { // redundant check
            world.setBlockState(pos, Blocks.WATER.getDefaultState().with(FACING, Direction.UP).with(SEALED, true));
        }
    }
    
    public void lightKeg(World world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof KegBlockEntity kegBlockEntity) {
            //if (kegBlockEntity.)
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) { // check block is destroyed?
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof KegBlockEntity kegBlockEntity) {
                // This would drop items if this contained items, but it doesn't
                // Instead, we should check nbt data and drop liquid if it has it
                //ItemScatterer.spawn(world, pos, (Inventory) blockEntity);

                kegBlockEntity.dropItems(world, pos, state, kegBlockEntity);
                
                
                
                // update comparators
                world.updateComparators(pos,this);
            }
            
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }
    
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, SULFUR, SEALED);
    }
    
    static {
        FACING = Properties.FACING;
        SULFUR = BooleanProperty.of("sulfur");
        SEALED = BooleanProperty.of("sealed");
    }
}
