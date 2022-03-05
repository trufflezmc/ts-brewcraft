package com.trufflez.tsbrewcraft.item.custom;

import com.trufflez.tsbrewcraft.item.TsItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.Tainted;
import java.util.List;

public class DrinkItem extends Item {
    public DrinkItem(Settings settings) {
        super(settings);
    }
    
    
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(stack.isOf(TsItems.BEER)) tooltip.add(new TranslatableText("item.tsbrewcraft.beer.tooltip"));
        if(stack.isOf(TsItems.WHEAT_BEER)) tooltip.add(new TranslatableText("item.tsbrewcraft.wheat_beer.tooltip"));
        if(stack.isOf(TsItems.MALT_LIQUOR)) tooltip.add(new TranslatableText("item.tsbrewcraft.malt_liquor.tooltip"));
        if(stack.isOf(TsItems.LAMBIC)) tooltip.add(new TranslatableText("item.tsbrewcraft.lambic.tooltip"));
        if(stack.isOf(TsItems.SAKE)) tooltip.add(new TranslatableText("item.tsbrewcraft.sake.tooltip"));
        if(stack.isOf(TsItems.RED_WINE)) tooltip.add(new TranslatableText("item.tsbrewcraft.red_wine.tooltip"));
        if(stack.isOf(TsItems.WHITE_WINE)) tooltip.add(new TranslatableText("item.tsbrewcraft.white_wine.tooltip"));
        if(stack.isOf(TsItems.ROSE)) tooltip.add(new TranslatableText("item.tsbrewcraft.rose.tooltip"));
        if(stack.isOf(TsItems.CHAMPAGNE)) tooltip.add(new TranslatableText("item.tsbrewcraft.champagne.tooltip"));
        if(stack.isOf(TsItems.RUM)) tooltip.add(new TranslatableText("item.tsbrewcraft.rum.tooltip"));
        if(stack.isOf(TsItems.TEQUILA)) tooltip.add(new TranslatableText("item.tsbrewcraft.tequila.tooltip"));
        if(stack.isOf(TsItems.VODKA)) tooltip.add(new TranslatableText("item.tsbrewcraft.vodka.tooltip"));
        if(stack.isOf(TsItems.BRANDY)) tooltip.add(new TranslatableText("item.tsbrewcraft.brandy.tooltip"));
        if(stack.isOf(TsItems.WHISKEY)) tooltip.add(new TranslatableText("item.tsbrewcraft.whiskey.tooltip"));
        if(stack.isOf(TsItems.BOURBON)) tooltip.add(new TranslatableText("item.tsbrewcraft.bourbon.tooltip"));
        if(stack.isOf(TsItems.SHOCHU)) tooltip.add(new TranslatableText("item.tsbrewcraft.shochu.tooltip"));
        if(stack.isOf(TsItems.MOONSHINE)) { tooltip.add(new TranslatableText("item.tsbrewcraft.moonshine.tooltip")); tooltip.add(new TranslatableText("item.tsbrewcraft.moonshine.tooltip2")); }
        
        if(stack.isOf(TsItems.VINEGAR)) tooltip.add(new TranslatableText("item.tsbrewcraft.vinegar.tooltip"));
        if(stack.isOf(TsItems.CRAPPY_BEER)) tooltip.add(new TranslatableText("item.tsbrewcraft.crappy_beer.tooltip"));
        if(stack.isOf(TsItems.CRAPPY_WINE)) tooltip.add(new TranslatableText("item.tsbrewcraft.crappy_wine.tooltip"));
        if(stack.isOf(TsItems.STRANGE_WINE)) tooltip.add(new TranslatableText("item.tsbrewcraft.strange_wine.tooltip"));
    }
    
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (user instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        
        if (!world.isClient) {
            //user.removeStatusEffect(StatusEffects.POISON);
            // we will apply status effects here, probably
        }
        
        // give the player an empty bottle
        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE); // what to do here?
        } else {
            if (user instanceof PlayerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {

                ItemStack itemStack = getEmptyBottle(stack);
                
                PlayerEntity playerEntity = (PlayerEntity)user;
                if (!playerEntity.getInventory().insertStack(itemStack)) {
                    playerEntity.dropItem(itemStack, false);
                }
            }
            return stack;
        }
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
    
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}
