package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.item.TsItems;
import com.trufflez.tsbrewcraft.recipe.KegProducts;
import com.trufflez.tsbrewcraft.util.ItemTypes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;
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
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new KegBlockEntity(pos, state); }
    
    // archaic and possibly unnecessary code
    @Override
    public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }
    
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
                        world.setBlockState(pos, state.with(SEALED, true));
                    } else {
                        kegBlockEntity.unseal();
                        world.setBlockState(pos, state.with(SEALED, false));
                        
                        Direction facing = state.get(FACING);
                        
                        if (facing != Direction.UP) {
                            if (kegBlockEntity.hasProduct()) {
                                
                                BlockPos checkPos = pos.offset(facing);
                                
                                if (world.getBlockState(checkPos).isAir()) {
                                    dumpContents(world, checkPos, kegBlockEntity);
                                }
                            }
                        }
                    }
                    
                } else if (SulfurStick.canLightWith(item)) { // Attempt to light the keg.
                    
                    if (!player.isCreative() && item.isOf(Items.FIRE_CHARGE)) item.decrement(1);
                    lightKeg(world, pos);
                    
                } else if (item.isOf(TsItems.SULFUR_STICK)) {
                    
                    if (!kegBlockEntity.isSealed() && !state.get(SULFUR)) {
                        world.setBlockState(pos, state.with(SULFUR, true));
                    }
                    
                } else { // Put the held item into the keg
                    
                    if (!kegBlockEntity.addItem(item)) {
                        // TODO (mc1.19.3): Figure out how to send chat messages to player again
                        //player.sendMessage(new TranslatableTextContent("message.tsbrewcraft.kegfull").(Formatting.GOLD), Util.NIL_UUID);
                    }
                }
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        Direction facing = state.get(FACING);
        if (facing != Direction.UP) {
            if (world.getBlockState(pos.offset(facing)).isAir()) {
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof KegBlockEntity kegBlockEntity) {
                    if (!kegBlockEntity.isSealed() && kegBlockEntity.hasProduct()) { // Redundant check in case keg was opened without block update
                        dumpContents((World) world, pos.offset(facing), kegBlockEntity);
                    }
                }
            }
        }
        return state;
    }
    
    public void dumpContents(World world, BlockPos pos, KegBlockEntity kegBlockEntity) { // Overwrites position with keg contents
        if (kegBlockEntity.hasProduct()) { // redundant check
            world.setBlockState(pos, Blocks.WATER.getDefaultState());
        }
        kegBlockEntity.setProduct(KegProducts.NONE);
        kegBlockEntity.setHasProduct(false);
    }
    
    public void lightKeg(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof KegBlockEntity kegBlockEntity) {
            if (kegBlockEntity.hasProduct()) {
                String product = kegBlockEntity.getProduct();
                if (ItemTypes.isFlammable(product) && !kegBlockEntity.isSealed()) {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    world.createExplosion(null, null, null, pos.getX(), pos.getY(), pos.getZ(), 5.0f, true, World.ExplosionSourceType.NONE);
                } else if (ItemTypes.isExplosive(product) || (ItemTypes.isFlammable(product) && kegBlockEntity.isSealed())){
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    world.createExplosion(null, null, null, pos.getX(), pos.getY(), pos.getZ(), 8.0f, true, World.ExplosionSourceType.BLOCK);
                }
            } else if (state.get(SULFUR)) {
                kegBlockEntity.setLit();
            }
        } 
    }
    
    // TODO: may need to replace with a function that gets called after a certain number of ticks
    
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof KegBlockEntity kegBlockEntity) {
            if (kegBlockEntity.isLit()) {
                
                if (kegBlockEntity.isSealed()) {
                    kegBlockEntity.setClean(true);
                }
                
                kegBlockEntity.setLit();

                world.playSound(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                
                world.setBlockState(pos, state.with(SULFUR, false));
            }
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(SULFUR); // this doesn't seem to be the best option, but I don't want to add another boolean property
    }
    
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof KegBlockEntity kegBlockEntity) {
                kegBlockEntity.dropItems(world, pos, state, kegBlockEntity);
                world.updateComparators(pos,this);
                
                if (kegBlockEntity.hasProduct()) { // sets previous location of keg to keg contents
                    dumpContents(world, pos, kegBlockEntity);
                }
                
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
