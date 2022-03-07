package com.trufflez.tsbrewcraft.item.patches;

import com.trufflez.tsbrewcraft.item.Tooltips;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TsAliasedBlockItem extends AliasedBlockItem {
    public TsAliasedBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Tooltips.addThis(stack, tooltip);
    }
}
