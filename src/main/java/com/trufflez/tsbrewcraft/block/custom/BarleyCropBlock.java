package com.trufflez.tsbrewcraft.block.custom;

import com.trufflez.tsbrewcraft.item.TsItems;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;

public class BarleyCropBlock extends CropBlock {
    public BarleyCropBlock(Settings settings) { super(settings); }
    
    @Override
    public ItemConvertible getSeedsItem() {
        return TsItems.BARLEY;
    }
}
