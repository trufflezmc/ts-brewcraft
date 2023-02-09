package com.trufflez.tsbrewcraft.item.custom;

import com.trufflez.tsbrewcraft.item.Tooltips;
import com.trufflez.tsbrewcraft.item.TsItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DrinkItem extends TsMultiConsumable {
    private final int strength;
    
    public DrinkItem(int uses, int strength, Settings settings) {
        super(uses, settings);
        this.strength = strength;
    }
    
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Tooltips.addThis(stack, tooltip);
    }
    
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        
        //postUse(stack, user);
        
        if (user instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
            //Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        
        if (!world.isClient) {
            //user.removeStatusEffect(StatusEffects.POISON);
            // we will apply status effects here, probably
        }
        
        // give the player an empty bottle
        //if (stack.isEmpty()) {
        //    return new ItemStack(Items.GLASS_BOTTLE); // what to do here?
        //} else {
            if (user instanceof PlayerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {
                stack.damage(1, user, (e) -> {
                    //e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                    
                    ItemStack itemStack = getEmptyBottle(stack);
                    
                    PlayerEntity playerEntity = (PlayerEntity)user;
                    if (!playerEntity.getInventory().insertStack(itemStack)) {
                        playerEntity.dropItem(itemStack, false);
                    }
                });
            }
            return stack;
        //}
    }
    
    private ItemStack getEmptyBottle(ItemStack stack) {
        if(     stack.isOf(TsItems.BEER)||
                stack.isOf(TsItems.WHEAT_BEER)||
                stack.isOf(TsItems.MALT_LIQUOR)||
                stack.isOf(TsItems.LAMBIC)||
                stack.isOf(TsItems.CRAPPY_BEER)
        ) { return new ItemStack(TsItems.BEER_BOTTLE); }
        else if(stack.isOf(TsItems.RED_WINE)||
                stack.isOf(TsItems.WHITE_WINE)||
                stack.isOf(TsItems.CHAMPAGNE)||
                stack.isOf(TsItems.CRAPPY_WINE)||
                stack.isOf(TsItems.STRANGE_WINE)
        ) { return new ItemStack(TsItems.WINE_BOTTLE); }
        else{ return new ItemStack(Items.GLASS_BOTTLE); }
    }
    
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }
    
    public SoundEvent getDrinkSound() { return SoundEvents.ENTITY_GENERIC_DRINK; }
    
    public SoundEvent getEatSound() { return SoundEvents.ENTITY_GENERIC_DRINK; }
    
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        //return ItemUsage.consumeHeldItem(world, user, hand);
        //user.getStackInHand(hand).finishUsing(world, user);
        
        //ItemStack stack = user.getStackInHand(hand);
        //stack.damage(1, user, (e) -> {});
        
        return TypedActionResult.consume(user.getStackInHand(hand));
        
        //return new TypedActionResult<>(ActionResult.CONSUME_PARTIAL, user.getStackInHand(hand));
    }
}
