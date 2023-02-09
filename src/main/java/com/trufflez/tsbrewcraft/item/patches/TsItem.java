package com.trufflez.tsbrewcraft.item.patches;

import com.trufflez.tsbrewcraft.item.Tooltips;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TsItem extends Item {
    public TsItem(Settings settings) {
        super(settings);
    }
    
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Tooltips.addThis(stack, tooltip);
    }
}
