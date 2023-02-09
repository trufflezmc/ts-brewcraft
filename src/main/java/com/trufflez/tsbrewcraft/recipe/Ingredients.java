package com.trufflez.tsbrewcraft.recipe;

import com.trufflez.tsbrewcraft.item.TsItems;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;

public class Ingredients {
    
    public static KegProducts getIngredientType(ItemStack i) {
        if (isWineType(i)) {        // Makes wine
            return KegProducts.WINE;
        } else if (isBeerType(i)) { // Makes beer or other types
            return KegProducts.BEER;
        } else if (isMashType(i)) { // Makes mash for vodka, etc
            return KegProducts.MASH;
        } else if (isSakeType(i)) { // Makes sake
            return KegProducts.SAKE;
        } else if (isKefirType(i)) { // Makes kefir
            return  KegProducts.KEFIR;
        } else if (isVegetable(i)) { // Inedible plant matter. Some organic matter are vegetables, so return this first
            return KegProducts.STRANGE_WINE;
        } else if (isOrganicMatter(i) || isPotion(i) || i.isFood()) { // Stuff that just rots
            return KegProducts.ROT;
        } else { // Everything not caught, hopefully just mineral blocks and other things
            return KegProducts.NONE;
        }
    }
    
    public static boolean isWineType(ItemStack i) {
        return i.isOf( TsItems.GRAPES ) ||
                i.isOf( Items.APPLE ) ||
                i.isOf( Items.GOLDEN_APPLE ) ||
                i.isOf( Items.ENCHANTED_GOLDEN_APPLE ) ||
                i.isOf( Items.SWEET_BERRIES ) ||
                i.isOf( Items.GLOW_BERRIES );
    }
    
    public static boolean isBeerType(ItemStack i) {
        return i.isOf( TsItems.BARLEY ) || 
                i.isOf( TsItems.PALE_MALT ) ||
                i.isOf( TsItems.DARK_MALT ) ||
                i.isOf( TsItems.CORN ) ||
                i.isOf( Items.WHEAT );
    }
    
    public static boolean isMashType(ItemStack i) {
        return i.isOf( TsItems.AGAVE ) ||
                i.isOf( Items.POTATO ) ||
                i.isOf( Items.BAKED_POTATO ) ||
                i.isOf( Items.BEETROOT ) ||
                i.isOf( Items.SUGAR_CANE ) ||
                i.isOf( Items.SUGAR ) ||
                i.isOf( Items.CARROT ) ||
                i.isOf( Items.GOLDEN_CARROT );
    }
    
    public static boolean isStrangeWineType(ItemStack i) { // original "strange wine" system
        return isVegetable(i) || isMashType(i);
    }
    
    public static boolean isSakeType(ItemStack i) {
        return i.isOf( TsItems.MOLDY_RICE );
    }
    
    public static boolean isKefirType(ItemStack i) {
        return i.isOf( Items.MILK_BUCKET );
    }
    
    public static boolean isVegetable(ItemStack i) { // Makes wine
        RegistryEntry<Item> r = i.getItem().getRegistryEntry();
        return r.isIn(ItemTags.FLOWERS) ||
                r.isIn(ItemTags.SMALL_FLOWERS) ||
                r.isIn(ItemTags.TALL_FLOWERS) ||
                i.isOf(Items.TALL_GRASS) ||
                i.isOf(Items.GRASS) ||
                i.isOf(Items.BAMBOO) ||
                i.isOf(Items.CACTUS) ||
                i.isOf(Items.KELP) ||
                i.isOf(Items.PUMPKIN) ||
                i.isOf(Items.MELON);
    }
    
    public static boolean isOrganicMatter(ItemStack i) { // Makes rot
        RegistryEntry<Item> r = i.getItem().getRegistryEntry();
        
        return isCompostable(i) ||
                r.isIn(ItemTags.FISHES) ||
                i.isOf(TsItems.RICE) ||
                i.isOf(TsItems.STEAMED_RICE) ||
                i.isOf(Items.SPONGE) ||
                i.isOf(Items.WET_SPONGE) ||
                i.isOf(Items.AXOLOTL_BUCKET) ||
                i.isOf(Items.COD_BUCKET) ||
                i.isOf(Items.SALMON_BUCKET) ||
                i.isOf(Items.TROPICAL_FISH_BUCKET) ||
                i.isOf(Items.PUFFERFISH_BUCKET) ||
                i.isOf(Items.BEEHIVE) ||
                i.isOf(Items.CHORUS_PLANT) ||
                i.isOf(Items.CHORUS_FLOWER) ||
                i.isOf(Items.FEATHER) ||
                i.isOf(Items.GLOW_INK_SAC) ||
                i.isOf(Items.INK_SAC) ||
                i.isOf(Items.GHAST_TEAR);
    }
    
    public static boolean isPotion(ItemStack i) {
        return i.isOf(Items.POTION);
    }
    
    public static boolean isCompostable(ItemStack i) {
        return CompostingChanceRegistry.INSTANCE.get(i.getItem()) >= 0.0F;
    }
}
